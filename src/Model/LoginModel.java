package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    // checks if valid password and email entered by user
    public boolean isValidInput(String email, String password) throws SQLException {
        JDBC jdbc = JDBC.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        String st = "select password from voters Where email = ?";
        try {
            statement = jdbc.getConnection().prepareStatement(st);
            statement.setString(1, email);
            result = statement.executeQuery();
            if (result.next()) {
                // check if password in argument matches the one in databases
                if(password.equals(result.getString(1)))
                    return true;
            }
            // if password doesn't match or email doesn't exist in database
            else
                return false;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if (statement!=null){
                statement.close();
            }
            if(connection!=null){
                connection.close();
            }
            if(result!=null){
                result.close();
            }
        }
        return false;
    }

}
