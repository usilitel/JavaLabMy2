package lab.jdbc.cp;

import lombok.SneakyThrows;
import lombok.experimental.Delegate;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

class PooledConnection implements Connection {

    @Delegate(excludes = Closeable.class)
    private Connection connection;

    private Consumer<PooledConnection> closer;

    @SneakyThrows
    PooledConnection(Connection connection, Consumer<PooledConnection> closer) {
        this.connection = connection;
        this.connection.setAutoCommit(true);
        this.closer = closer;
    }

    public void reallyClose() throws SQLException {
        connection.close();
    }

    @Override
    public void close() throws SQLException {
        if (connection.isClosed()) {
            throw new SQLException("Attempting to close closed connection.");
        }
        if (connection.isReadOnly()) {
            connection.setReadOnly(false);
        }
        connection.setAutoCommit(true);
        closer.accept(this);
    }

}
