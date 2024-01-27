package Model;

import java.sql.*;

public class JDBC {
    private static JDBC jdbc;
    private JDBC() { }
    public static JDBC getInstance() {
        if (jdbc==null){
            jdbc=new JDBC();
        }
        return jdbc;
    }
    protected static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/voting_system", "root", "Root123*");
        return connection;
    }
}

