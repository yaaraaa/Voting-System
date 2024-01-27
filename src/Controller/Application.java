package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoad = new FXMLLoader(Application.class.getResource("../View/Positions.fxml"));
        Stage stage2 = new Stage();
        Scene scene2 = new Scene(fxmlLoad.load(), 1300, 750);
        stage2.setTitle("Positions");
        stage2.setScene(scene2);
        stage2.show();

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("../View/Register.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 750);
        stage.setTitle("Register");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}