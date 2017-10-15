package lab.jdbc.cp;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Supplier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class ConnectionPoolTest {

    @Test
    void get() throws SQLException {
        Supplier<Connection> connectionSupplier = new ConnectionPool("/init.sql");
        //language=H2
        val sql = "SELECT id, first_name, last_name, permission, dob, email, password, address, telephone FROM Person";

        int count = 0;
        try (Connection connection = connectionSupplier.get();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                System.out.println(resultSet.getString("first_name"));
                count++;
            }
        }

        assertThat(count, is(5));
    }
}