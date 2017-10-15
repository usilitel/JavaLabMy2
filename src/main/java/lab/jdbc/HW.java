package lab.jdbc;

import lombok.SneakyThrows;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class HW {
    @SneakyThrows
    public static void main(String... args) {
        Class.forName("org.gjt.mm.mysql.Driver");
        Properties properties = new Properties();
        try (InputStream resourceAsStream = HW.class.getResourceAsStream("/jdbc.properties");) {
            properties.load(resourceAsStream);
            String url = (String) properties.remove("url");

            assert properties.size() == 2;
            assert properties.containsKey("user");
            assert properties.containsKey("password");

//            String user = properties.getProperty("user");
//            String password = properties.getProperty("password");

            try (Connection connection = DriverManager.getConnection(url, properties)){
                connection.createStatement();

            }
        }

    }
}
