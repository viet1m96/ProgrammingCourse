package gui.utilities.tools;

import gui.history.SceneHistory;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class FXMLLoaderUlti {

    public static void changeSceneWithHistory(ActionEvent actionEvent, String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(FXMLLoaderUlti.class.getResource(fxmlPath)));
        Scene currentScene = ((Node) actionEvent.getSource()).getScene();
        Stage stage = (Stage) currentScene.getWindow();
        SceneHistory.pushScene(currentScene);
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
        stage.show();
    }

    public static void changeSceneWithoutHistory(ActionEvent actionEvent, String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(FXMLLoaderUlti.class.getResource(fxmlPath)));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
        stage.show();
    }
}
