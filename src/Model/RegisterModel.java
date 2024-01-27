package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterModel {
    private int ID;
    private String FirstName,
            LastName,
            Email,
            Gender,
            Password,
            PasswordConfirmation;
    private int PhoneNumber;
    private String City,
            Birthday;

    private int VoteStatus;

    public RegisterModel() {

    }

    // constructor for adding candidate object to observable list (for table view)
    public RegisterModel(int ID, String FirstName, String LastName, String Email, String Gender, String Password, int PhoneNumber,
                         String City, String Birthday, int VoteStatus) {
        this.ID = ID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.Gender = Gender;
        this.Password = Password;
        this.PhoneNumber = PhoneNumber;
        this.City = City;
        this.Birthday = Birthday;
        this.VoteStatus = VoteStatus;
    }

    // setters for validating input, returns boolean to be used in controller
    public boolean setBirthday(String birthday) {
        if (birthday.equals(""))
            return false;
        Birthday = birthday;
        return true;
    }
    public boolean setCity(String city) {
        if (city.equals(""))
            return false;
        City = city;
        return true;
    }
    // || !email.matches("\"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$\"")
    public boolean setEmail(String email) {
        if (email.equals(""))
            return false;
        Email = email;
        return true;
    }
    public boolean setFirstName(String firstName) {
        if (firstName.equals("") || !firstName.matches("[a-zA-Z]+"))
            return false;
        FirstName = firstName;
        return true;
    }
    public boolean setGender(String gender) {
        if (gender.equals(""))
            return false;
        Gender = gender;
        return true;
    }
    public boolean setLastName(String lastName) {
        if (lastName.equals("") || !lastName.matches("[a-zA-Z]+"))
            return false;
        LastName = lastName;
        return true;
    }
    // || !password.matches("\"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$\"\n"
    public boolean setPassword(String password) {
        // password has to be minimum 8 number, at least one uppercase letter, one lowercase letter, a numbers, and a special character
        if (password.equals(""))
            return false;
        Password = password;
        return true;
    }
    public boolean setPasswordConfirmation(String passwordConfirmation) {
        // confirms if password confirmation matches password
        if (!passwordConfirmation.equals(Password))
            return false;
        PasswordConfirmation = passwordConfirmation;
        return true;
    }
    public boolean setPhoneNumber(String phoneNumber) {
        // checks if password is all numbers
        try {
            Integer.parseInt(phoneNumber);
        }
        catch(NumberFormatException e) {
            return false;
        }
        if (phoneNumber.equals(""))
            return false;
        PhoneNumber = Integer.parseInt(phoneNumber);
        return true;
    }

    public void setVoteStatus() {
        VoteStatus = 0;
    }

    public int getID() {
        return ID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return Email;
    }

    public String getGender() {
        return Gender;
    }

    public String getPassword() {
        return Password;
    }

    public int getPhoneNumber() {
        return PhoneNumber;
    }

    public String getCity() {
        return City;
    }

    public String getBirthday() {
        return Birthday;
    }

    public int getVoteStatus() {
        return VoteStatus;
    }

    // inserts voters to database
    public void insertVoters(String first_name, String last_name, String email, String gender, String password, String phone_number, String city, String birthday, String vote_status) throws SQLException {
        JDBC jdbc = JDBC.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        String st = "INSERT INTO voters " +
                "(first_name, last_name, email, gender, password, phone_number, city, birthday, vote_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            statement = jdbc.getConnection().prepareStatement(st);
            statement.setString(1, first_name);
            statement.setString(2, last_name);
            statement.setString(3, email);
            statement.setString(4, gender);
            statement.setString(5, password);
            statement.setString(6, phone_number);
            statement.setString(7, city);
            statement.setString(8, birthday);
            statement.setString(9, vote_status);
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

    // check if account with the same email already exists
    public boolean accountExists(String email) throws SQLException {
        JDBC jdbc = JDBC.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        String st = "select email from voters Where email = ?";
        try {
            statement = jdbc.getConnection().prepareStatement(st);
            statement.setString(1, email);
            result = statement.executeQuery();
            if (result.next()) {
                if(email.equals(result.getString(1)))
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

}
