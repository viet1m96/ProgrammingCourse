package gui.utilities.buttons;

import gui.history.SceneHistory;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class BackButtonUtil {

    public static void initImage(ImageView BackButtonImage) {
//        String backButtonImagePath = System.getenv("BACK_IMAGE");
//        URL imageUrl = getClass().getResource(backButtonImagePath);
        URL imageUrl = BackButtonUtil.class.getResource("/images/backButton.png");
        assert imageUrl != null;
        BackButtonImage.setImage(new Image(imageUrl.toString(), true));
    }

    public static void BackButtonClicked(ActionEvent actionEvent, Button BackButton) {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene currentScene = stage.getScene();
        Scene scene = SceneHistory.popScene();
        if (scene != null) {
            BackButton.setDisable(false);
            stage.setScene(scene);
            currentScene.setRoot(new Pane());
            stage.show();
        } else {
            BackButton.setDisable(true);
        }

    }
}
