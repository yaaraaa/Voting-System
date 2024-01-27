package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PositionsModel {
    private int ID;
    private String positionName;
    private int maxVotes;

    private static ArrayList<String> positionsList = new ArrayList<>();

    public PositionsModel() {
    }

    // constructor for adding position object to observable list
    public PositionsModel(int ID, String positionName, int maxVotes) throws SQLException {
        this.ID = ID;
        this.positionName = positionName;
        this.maxVotes = maxVotes;
    }

    // Setters for input checking, returns boolean to be used in controller class
    public boolean setPositionName(String positionName) {
        if (positionName.equals("") || !positionName.matches("[a-zA-Z][a-zA-Z ]+"))
            return false;
        this.positionName = positionName;
        return true;
    }

    public boolean setMaxVotes(String maxVotes) {
        try {
            Integer.parseInt(maxVotes);
        }
        catch(NumberFormatException e) {
            return false;
        }
        if (maxVotes.equals("") || Integer.parseInt(maxVotes) < 1)
            return false;
        this.maxVotes = Integer.parseInt(maxVotes);
        return true;
    }

    public int getID() {
        return ID;
    }

    public String getPositionName() {
        return positionName;
    }

    public int getMaxVotes() {
        return maxVotes;
    }


    // takes a position's ID then checks if it is available in database (for deletion)
    public boolean isValidID(String ID) throws SQLException {
        JDBC jdbc = JDBC.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        String st = "select * from positions Where id = ?";
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

    // inserts a position into database
    public void insertPosition(String position_name, String max_votes) throws SQLException {
        JDBC jdbc = JDBC.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        String st = "INSERT INTO positions " +
                "(position_name, max_votes) VALUES (?, ?)";
        try {
            statement = jdbc.getConnection().prepareStatement(st);
            statement.setString(1, position_name);
            statement.setString(2, max_votes);
            statement.executeUpdate();
            positionsList.add(position_name);
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

    // stores all data of positions in an observable list (for table view)
    public ObservableList<PositionsModel> fetchPositionsData() throws SQLException {
        JDBC jdbc = JDBC.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        ObservableList<PositionsModel> positions = FXCollections.observableArrayList();
        String st = "Select * from positions";
        try {
            statement = jdbc.getConnection().prepareStatement(st);
            result = statement.executeQuery();
            while (result.next()) {
                positions.add(new PositionsModel(
                        Integer.parseInt(result.getString(1)),
                        result.getString(2),
                        Integer.parseInt(result.getString(3))));
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
        return positions;
    }

    // deletes a position by id
    public static void deletePositions(int ID) throws SQLException {
        JDBC jdbc = JDBC.getInstance();
        Connection connection =null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String positionName = null;

        String st1 = "select position_name from positions where id = ?";
        String st2 = "delete from positions where id = ?";
        try {
            statement = jdbc.getConnection().prepareStatement(st1);
            statement.setString(1, String.valueOf(ID));
            resultSet = statement.executeQuery();
            if(resultSet.next()) {
                positionName = resultSet.getString(1);
            }
            statement = jdbc.getConnection().prepareStatement(st2);
            statement.setString(1, String.valueOf(ID));
            statement.executeUpdate();
            positionsList.remove(positionName);
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

    // fetches position names from database to be used in candidates position selection button
    public ArrayList<String> getPositionsList() throws SQLException {
        positionsList.clear();

        JDBC jdbc = JDBC.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        String st = "select position_name from positions";
        try {
            statement = jdbc.getConnection().prepareStatement(st);
            result = statement.executeQuery();
            while (result.next()) {
               positionsList.add(result.getString(1));
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
            if(result!=null){
                result.close();
            }
        }
        return positionsList;
    }

}
