package Controller;

import Model.PositionsModel;
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

public class PositionsController implements Initializable {

    PositionsModel positionsModel = new PositionsModel();

    @FXML
    private TableView<PositionsModel> positionsTable;

    @FXML
    private TableColumn<PositionsModel, Integer> idColumn;

    @FXML
    private TableColumn<PositionsModel, String> positionNameColumn;

    @FXML
    private TableColumn<PositionsModel, Integer> maxVotesColumn;

    @FXML
    private TextField positionNameField,
            maxVotesField,
            idField;

    @FXML
    private Label nameError,
            numberError,
            idError;
    boolean validPositionName, validMaxVote;

    public void displayErrors() {
        if(!validMaxVote)
            numberError.setText("invalid number format");
        else
            numberError.setText("");

        if(!validPositionName)
            nameError.setText("invalid name format");
        else
            nameError.setText("");
    }

    @FXML
    void addPosition(ActionEvent event) throws SQLException {
        validPositionName = positionsModel.setPositionName(positionNameField.getText());
        validMaxVote = positionsModel.setMaxVotes(maxVotesField.getText());

        displayErrors();
        if(!validMaxVote || !validPositionName) {
            return;
        }
        else {
            positionsModel.insertPosition(positionsModel.getPositionName(),
                    String.valueOf(positionsModel.getMaxVotes()));
            positionsTable.setItems(positionsModel.fetchPositionsData());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        positionNameColumn.setCellValueFactory(new PropertyValueFactory<>("positionName"));
        maxVotesColumn.setCellValueFactory(new PropertyValueFactory<>("maxVotes"));

        try {
            positionsTable.setItems(positionsModel.fetchPositionsData());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void deletePosition(ActionEvent event) throws SQLException {
        if(positionsModel.isValidID(idField.getText())) {
            PositionsModel.deletePositions(Integer.parseInt(idField.getText()));
            positionsTable.setItems(positionsModel.fetchPositionsData());
            idError.setText("");
        }
        else
            idError.setText("id does not exist in database");
    }

    public void switchToVoters(ActionEvent event) throws IOException {
        Globals.root = FXMLLoader.load(getClass().getResource("../View/Voters.fxml"));
        Globals.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Globals.scene = new Scene(Globals.root);
        Globals.stage.setTitle("Voters");
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
