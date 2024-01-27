package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VotersModel {
    // getting voters data from sql and storing it in a list
    public ObservableList<RegisterModel> fetchVotersData() throws SQLException {
        JDBC jdbc = JDBC.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        ObservableList<RegisterModel> voters = FXCollections.observableArrayList();
        String st = "Select * from voters order by first_name, last_name";
        try {
            statement = jdbc.getConnection().prepareStatement(st);
            result = statement.executeQuery();
            while (result.next()) {
                voters.add(new RegisterModel(
                        Integer.parseInt(result.getString(1)),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getString(6),
                        Integer.parseInt(result.getString(7)),
                        result.getString(8),
                        result.getString(9),
                        Integer.parseInt(result.getString(10))));
            }
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
            if (result!=null){
                result.close();
            }
        }
        return voters;
    }

    // checks if id exists in database (for deletion)
    public boolean isValidID(String ID) throws SQLException {
        JDBC jdbc = JDBC.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        String st = "select * from voters Where id = ?";
        try {
            statement = jdbc.getConnection().prepareStatement(st);
            statement.setString(1, ID);
            result = statement.executeQuery();
            if (result.next()) {
                if(ID.equals(result.getString(1)))
                    return true;
            }
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

    // deletes voter from database
    public static void deleteVoters(int ID) throws SQLException {
        JDBC jdbc = JDBC.getInstance();
        Connection connection =null;
        PreparedStatement statement = null;
        String st = "delete from voters where id = ?";
        try {
            statement = jdbc.getConnection().prepareStatement(st);
            statement.setString(1, String.valueOf(ID));
            statement.executeUpdate();
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
        }
    }
}
