package Controller;

import Model.LoginModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    LoginModel loginModel = new LoginModel();
    @FXML
    private TextField email, password;
    @FXML
    private Label logInError;

    boolean validLogin;


    // if input is valid scene is switched to elections otherwise error is displayed
    public void login(ActionEvent event) throws SQLException, IOException {
        validLogin = loginModel.isValidInput(email.getText(), password.getText());
        if(validLogin) {
            logInError.setText("");
            switchToElections(event);
        }
        else {
            logInError.setText("invalid email and/or password");
        }
    }

    // for switching scenes
    public void switchToRegister(ActionEvent event) throws IOException {
        Globals.root = FXMLLoader.load(getClass().getResource("../View/Register.fxml"));
        Globals.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Globals.scene = new Scene(Globals.root);
        Globals.stage.setTitle("Register");
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
