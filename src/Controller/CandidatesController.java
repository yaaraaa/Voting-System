package Controller;

import Model.CandidatesModel;
import Model.PositionsModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CandidatesController implements Initializable{
    CandidatesModel candidatesModel = new CandidatesModel();
    PositionsModel positionsModel = new PositionsModel();

    @FXML
    private TableView<CandidatesModel> candidatesTable;
    @FXML
    private TableColumn<CandidatesModel, String> firstNameColumn;
    @FXML
    private TableColumn<CandidatesModel, String> lastNameColumn;
    @FXML
    private TableColumn<CandidatesModel, String> positionColumn;
    @FXML
    private TableColumn<CandidatesModel, Integer> idColumn;
    @FXML
    private TableColumn<CandidatesModel, Integer>  numberOfVotesColumn;

    @FXML
    private TextField firstNameField,
            LastNameField,
            PositionField,
            idField;

    @FXML
    private Label firstNameError,
            positionsError,
            lastNameError,
            idError;

    @FXML
    private MenuButton PositionsMenu;

    boolean validFirstName,
    validLastName,
    validPosition;

    // for displaying input errors to user
    public void displayErrors() {
        if (!validFirstName)
            firstNameError.setText("invalid name format");
        else
            firstNameError.setText("");

        if (!validLastName)
            lastNameError.setText("invalid name format");
        else
            lastNameError.setText("");

        if (!validPosition)
            positionsError.setText("please select position");
        else
            positionsError.setText("");
    }

    // adds candidates to database then displays it to user
    @FXML
    void addCandidate(ActionEvent event) throws SQLException {
        validFirstName = candidatesModel.setFirstName(firstNameField.getText());
        validLastName = candidatesModel.setLastName(LastNameField.getText());
        validPosition = candidatesModel.setPosition(PositionField.getText());

        displayErrors();
        if(!validFirstName || !validLastName || !validPosition) {
            return;
        }
        candidatesModel.insertCandidate(candidatesModel.getFirstName(),
                candidatesModel.getLastName(),
                candidatesModel.getPosition());
        candidatesTable.setItems(candidatesModel.fetchCandidatesData());
    }

    // Get positions from positions model then assigns to list of positions of candidates to be added
    public void assignPositionsItems() throws SQLException {
        ArrayList<String> positions = positionsModel.getPositionsList();;
        MenuItem [] menuItems = new MenuItem[positions.size()];
        for(int i = 0; i < positions.size(); i++) {
            menuItems[i] = new MenuItem(positions.get(i));
            int finalI = i;
            menuItems[i].setOnAction(e -> {
                PositionField.setText(positions.get(finalI));
            });
            PositionsMenu.getItems().add(menuItems[i]);
        }
    }

    // initialization of some nodes, similar to a class's constructor but for javaFX
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        numberOfVotesColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfVotes"));

        try {
            candidatesTable.setItems(candidatesModel.fetchCandidatesData());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            assignPositionsItems();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // if valid id entered candidate is deleted
    @FXML
    void deleteCandidate(ActionEvent event) throws SQLException {
        if(candidatesModel.isValidID(idField.getText())) {
            candidatesModel.deleteCandidates(Integer.parseInt(idField.getText()));
            candidatesTable.setItems(candidatesModel.fetchCandidatesData());
            idError.setText("");
        }
        else
            idError.setText("id does not exist in database");
    }

    // for switching scenes
    public void switchToPositions(ActionEvent event) throws IOException {
        Globals.root = FXMLLoader.load(getClass().getResource("../View/Positions.fxml"));
        Globals.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Globals.scene = new Scene(Globals.root);
        Globals.stage.setTitle("Positions");
        Globals.stage.setScene(Globals.scene);
        Globals.stage.show();
    }

    public void switchToVoters(ActionEvent event) throws IOException {
        Globals.root = FXMLLoader.load(getClass().getResource("../View/Voters.fxml"));
        Globals.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Globals.scene = new Scene(Globals.root);
        Globals.stage.setTitle("Voters");
        Globals.stage.setScene(Globals.scene);
        Globals.stage.show();
    }
}
