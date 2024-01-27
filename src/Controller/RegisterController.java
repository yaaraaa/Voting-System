package Controller;

import Model.RegisterModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class RegisterController {
    RegisterModel registerModel = new RegisterModel();
    @FXML
    private TextField firstName,
            lastName,
            email,
            gender,
            password,
            passwordConfirmation,
            phoneNumber,
            city;
    @FXML
    private DatePicker birthday;

    public void maleChoice() {
        gender.setText("Male");
    }
    public void femaleChoice() {
        gender.setText("Female");
    }
    public void cairoChoice() {
        city.setText("Cairo");
    }
    public void gizaChoice() {
        city.setText("Giza");
    }
    public void alexChoice() {
        city.setText("Alexandria");
    }


    @FXML
    private Label firstNameError,
            lastNameError,
            emailError,
            genderError,
            passwordError,
            passwordMatchError,
            phoneNumberError,
            cityError,
            birthdayError,
            accountExistsError;

    boolean validFirstName,
            validLastName,
            validEmail,
            validGender,
            validPassword,
            validPasswordConfirmation,
            validPhoneNumber,
            validCity,
            validBirthday,
            accountAlreadyExists;

    // when button is clicked the setter methods are called for input validation then data is inserted if valid
    public void register(ActionEvent event) throws SQLException, IOException {
        validFirstName = registerModel.setFirstName(firstName.getText());
        validLastName = registerModel.setLastName(lastName.getText());
        validEmail = registerModel.setEmail(email.getText());
        validGender = registerModel.setGender(gender.getText());
        validPassword = registerModel.setPassword(password.getText());
        validPasswordConfirmation  = registerModel.setPasswordConfirmation(passwordConfirmation.getText());
        validPhoneNumber = registerModel.setPhoneNumber(phoneNumber.getText());
        validCity = registerModel.setCity(city.getText());
        validBirthday = registerModel.setBirthday(String.valueOf(birthday.getValue()));
        accountAlreadyExists = registerModel.accountExists(email.getText());
        registerModel.setVoteStatus();

        displayErrors();
        if (!validFirstName || !validLastName || !validEmail || !validGender || !validPassword
                || !validPasswordConfirmation || !validPhoneNumber || !validCity || !validBirthday || accountAlreadyExists) {
            return;
        }

        // insert data if valid input
        registerModel.insertVoters(registerModel.getFirstName(),
                registerModel.getLastName(),
                registerModel.getEmail(),
                registerModel.getGender(),
                registerModel.getPassword(),
                String.valueOf(registerModel.getPhoneNumber()),
                registerModel.getCity(),
                registerModel.getBirthday(),
                String.valueOf(registerModel.getVoteStatus()));
        switchToElections(event);
    }

    // for displaying or removing error messages to user
    public void displayErrors() {
        if (!validFirstName)
            firstNameError.setText("invalid format, use letters");
        else
            firstNameError.setText("");

        if (!validLastName)
            lastNameError.setText("invalid format, use letters");
        else
            lastNameError.setText("");

        if (!validEmail)
            emailError.setText("enter an email");
        else
            emailError.setText("");

        if (!validGender)
            genderError.setText("select a gender");
        else
            genderError.setText("");

        if (!validPassword)
            passwordError.setText("enter a password");
        else
            passwordError.setText("");

        if (!validPasswordConfirmation)
            passwordMatchError.setText("password does not match");
        else
            passwordMatchError.setText("");

        if (!validPhoneNumber)
            phoneNumberError.setText("invalid format, use digits only");
        else
            phoneNumberError.setText("");

        if (!validCity)
            cityError.setText("select a city");
        else
            cityError.setText("");

        if(!validBirthday)
            birthdayError.setText("select a date");
        else
            birthdayError.setText("");

        if(accountAlreadyExists)
            accountExistsError.setText("An account already exists with this email");
        else
            accountExistsError.setText("");
    }

    // for switching scenes
    public void switchToLogin(ActionEvent event) throws IOException {
        Globals.root = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
        Globals.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Globals.scene = new Scene(Globals.root);
        Globals.stage.setTitle("Login");
        Globals.stage.setScene(Globals.scene);
        Globals.stage.show();
    }

    public void switchToElections(ActionEvent event) throws IOException {
        Globals.root = FXMLLoader.load(getClass().getResource("../View/Elections.fxml"));
        Globals.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Globals.scene = new Scene(Globals.root);
        Globals.stage.setTitle("Elections");
        Globals.stage.setScene(Globals.scene);
        Globals.stage.show();
    }
}