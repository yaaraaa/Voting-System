package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CandidatesModel {
    private int ID;
    private String firstName;
    private String lastName;
    private String position;
    private int numberOfVotes;

    public CandidatesModel() {

    }

    // constructor for adding candidate object to observable list (for table view)
    public CandidatesModel(int ID, String firstName, String lastName, String position, int numberOfVotes) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.numberOfVotes = numberOfVotes;
    }

    // Setters for input checking, returns boolean to be used in controller class
    public boolean setFirstName(String firstName) {
        if (firstName.equals("") || !firstName.matches("[a-zA-Z]+"))
            return false;
        this.firstName = firstName;
        return true;
    }

    public boolean setLastName(String lastName) {
        if (lastName.equals("") || !lastName.matches("[a-zA-Z]+"))
            return false;
        this.lastName = lastName;
        return true;
    }

    public boolean setPosition(String position) {
        if (position.equals(""))
            return false;
        this.position = position;
        return true;
    }

    public void setNumberOfVotes(int numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public int getID() {
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPosition() {
        return position;
    }

    public int getNumberOfVotes() {
        return numberOfVotes;
    }

    // takes a candidate's ID then checks if it is available in database (for deletion)
    public boolean isValidID(String ID) throws SQLException {
        JDBC jdbc = JDBC.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        String st = "select * from candidates Where id = ?";
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

    // inserts a candidate into database
    public void insertCandidate(String first_name, String last_name, String position) throws SQLException {
        JDBC jdbc = JDBC.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        String st = "INSERT INTO candidates " +
                "(first_name, last_name, position, number_of_votes) VALUES (?, ?, ?, ?)";
        try {
            statement = jdbc.getConnection().prepareStatement(st);
            statement.setString(1, first_name);
            statement.setString(2, last_name);
            statement.setString(3, position);
            statement.setString(4, "0");
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

    // stores all data of candidates in an observable list (for table view)
    public ObservableList<CandidatesModel> fetchCandidatesData() throws SQLException {
        JDBC jdbc = JDBC.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        ObservableList<CandidatesModel> candidates = FXCollections.observableArrayList();
        String st = "select * from candidates";
        try {
            statement = jdbc.getConnection().prepareStatement(st);
            result = statement.executeQuery();
            while (result.next()) {
                candidates.add(new CandidatesModel(
                        Integer.parseInt(result.getString(1)),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        Integer.parseInt(result.getString(5))));
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
        return candidates;
    }

    // deletes a candidate by id
    public static void deleteCandidates(int ID) throws SQLException {
        JDBC jdbc = JDBC.getInstance();
        Connection connection =null;
        PreparedStatement statement = null;
        String st = "delete from candidates where id = ?";
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
