package Controller;

import Model.VotersModel;
import Model.RegisterModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class VotersController implements Initializable {
    VotersModel votersModel = new VotersModel();

    @FXML
    private TableView<RegisterModel> votersTable;
    @FXML
    private TableColumn<RegisterModel, String> firstNameColumn,
            lastNameColumn,
            emailColumn,
            genderColumn,
            passwordColumn,
            cityColumn,
            birthdayColumn;

    @FXML
    private TableColumn<RegisterModel, Integer> idColumn,
            phoneNumberColumn,
            voteStatusColumn;


    // initializes some scene nodes, similar to a class's constructor but for javafx
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("Password"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("City"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<>("Birthday"));
        voteStatusColumn.setCellValueFactory(new PropertyValueFactory<>("VoteStatus"));
        try {
            votersTable.setItems(votersModel.fetchVotersData());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private TextField IDField;

    @FXML
    private Label idError;

    // deletes voter if valid id entered
    @FXML
    void deleteVoter(ActionEvent event) throws SQLException {
        if(votersModel.isValidID(IDField.getText())) {
            VotersModel.deleteVoters(Integer.parseInt(IDField.getText()));
            votersTable.setItems(votersModel.fetchVotersData());
            idError.setText("");
        }
        else
            idError.setText("id does not exist in database");
    }

    // switches scenes
    public void switchToPositions(ActionEvent event) throws IOException {
        Globals.root = FXMLLoader.load(getClass().getResource("../View/Positions.fxml"));
        Globals.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Globals.scene = new Scene(Globals.root);
        Globals.stage.setTitle("Positions");
        Globals.stage.setScene(Globals.scene);
        Globals.stage.show();
    }

    public void switchToCandidates(ActionEvent event) throws IOException {
        Globals.root = FXMLLoader.load(getClass().getResource("../View/Candidates.fxml"));
        Globals.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Globals.scene = new Scene(Globals.root);
        Globals.stage.setTitle("Candidates");
        Globals.stage.setScene(Globals.scene);
        Globals.stage.show();
    }
}
