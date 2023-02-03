package example.uTils;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionSqlServer {
    private static final ConnectionSqlServer connectionSqlServer = new ConnectionSqlServer();
    private static Connection connection;

    private ConnectionSqlServer() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=database1", "sa", "sa");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ConnectionSqlServer getConnectionSqlServer() {
        return connectionSqlServer;
    }

    public Connection getSqlServerConnection() {
        return connection;
    }
}
