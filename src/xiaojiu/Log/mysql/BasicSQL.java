package xiaojiu.Log.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
@Deprecated
public class BasicSQL {
    public static String user;
    public static String password;
    public static String url;

    public static Connection GetConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static void InitSQL() throws SQLException {
        Connection connection = GetConnection();
        Statement statement = connection.createStatement();
        statement.execute("CREATE DATABASE IF NOT EXISTS BlockBreak");

        statement.close();
        connection.close();
    }

    public static void Init() {
        try {
            InitSQL();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
