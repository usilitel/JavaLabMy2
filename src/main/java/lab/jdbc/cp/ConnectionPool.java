package lab.jdbc.cp;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import lombok.val;

import java.io.*;
import java.sql.*;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Supplier;

@Log4j2
public class ConnectionPool implements Supplier<Connection>, Closeable {
    private BlockingQueue<Connection> connectionQueue;

    @SneakyThrows
    public ConnectionPool() {
        Locale.setDefault(Locale.ENGLISH);

        val properties = new Properties() {
            @SneakyThrows
            Properties load() {
                try (val inputStream = ConnectionPool.class.getResourceAsStream(
                        "/jdbc.properties")) {
                    load(inputStream);

                    assert size() > 3 && size() < 6;
                    assert containsKey("driver");
                    assert containsKey("url");
                    assert containsKey("user");
                    assert containsKey("password");

                    return this;
                }
            }
        }.load();

        try {
            Class.forName((String) properties.remove("driver"));
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException("Can't find database driver class", e);
        }

        val poolSizePropertyValue = (String) properties.remove("poolSize");
        int poolSize = poolSizePropertyValue == null ? 5 : Integer.parseInt(poolSizePropertyValue);

        val url = (String) properties.remove("url");
//        assert PROPERTIES.size() == 2;

        try {
            connectionQueue = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                val connection = DriverManager.getConnection(url, properties);
                val pooledConnection = new PooledConnection(connection, pooledConnection1 -> {
                    try {
                        connectionQueue.put(pooledConnection1);
                    } catch (InterruptedException e) {
                        log.error(e);
                    }
                });
                connectionQueue.add(pooledConnection);
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("SQLException in ConnectionPool", e);
        }
    }

    @SneakyThrows
    public ConnectionPool(String sqlFileName) {
        this();
        try (val reader = new BufferedReader(
                new InputStreamReader(
                        ConnectionPool.class.getResourceAsStream(sqlFileName)))) {
            String line;
            StringBuilder sql = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sql.append(line);
            }

            try (val connection = get();
                 val statement = connection.createStatement()) {
                statement.executeUpdate(sql.toString());
            }
        }
    }

    @Override
    public Connection get() throws ConnectionPoolException {
        try {
            return connectionQueue.take();
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Error connecting to the data source.", e);
        }
    }

    @Override
    public void close() {
        try {
            Connection connection;
            while ((connection = connectionQueue.poll()) != null) {
                if (!connection.getAutoCommit())
                    connection.commit();
                ((PooledConnection) connection).reallyClose();
            }
        } catch (SQLException e) {
             log.error("Error closing the connection.", e);
        }
    }
}
