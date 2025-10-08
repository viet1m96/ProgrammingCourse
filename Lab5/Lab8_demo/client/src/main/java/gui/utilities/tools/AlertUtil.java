package gui.utilities.tools;

import goods.Response;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class AlertUtil {
    public static void showInformationAlert(String title, String content, Stage ownerStage) {
        showAlert(title, content, Alert.AlertType.INFORMATION, ownerStage);
    }
    public static void showInformationAlert(String title, String content, ActionEvent actionEvent) {
        showAlert(title, content, Alert.AlertType.INFORMATION, (Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));
    }
    public static void showErrorAlert(String title, String content, Stage ownerStage) {
        showAlert(title, content, Alert.AlertType.ERROR, ownerStage);
    }

    public static void showErrorAlert(String title, String content, ActionEvent actionEvent) {
        showAlert(title, content, Alert.AlertType.ERROR, (Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));
    }

    private static void showAlert(String title, String content, Alert.AlertType alertType, Stage ownerStage) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            if(ownerStage != null) alert.initOwner(ownerStage);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.getButtonTypes().setAll(ButtonType.OK);

            alert.showAndWait();
        });
    }

    public static void showResultAlert(Response response, Stage ownerStage) {
        StringBuilder content = new StringBuilder();
        List<String> notice = response.getNotice();
        for(String str : notice) {
            content.append(str).append("\n");
        }
        if(response.getSuccess()) {
            showInformationAlert("Successfully", content.toString(), ownerStage);
        } else {
            showErrorAlert("Unsuccessfully", content.toString(), ownerStage);
        }
    }

    public static void showResultAlert(Response response, ActionEvent actionEvent) {
        showResultAlert(response, (Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));
    }
}
