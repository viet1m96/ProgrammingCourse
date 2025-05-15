package gui.utilities.tools;

import goods.Response;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.List;

public class AlertUtil {
    public static void showInformationAlert(String title, String content) {
        showAlert(title, content, Alert.AlertType.INFORMATION);
    }
    public static void showErrorAlert(String title, String content) {
        showAlert(title, content, Alert.AlertType.ERROR);
    }

    private static void showAlert(String title, String content, Alert.AlertType alertType) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.showAndWait();
        });
    }

    public static void showResultAlert(Response response) {
        StringBuilder content = new StringBuilder();
        List<String> notice = response.getNotice();
        for(String str : notice) {
            content.append(str).append("\n");
        }
        if(response.getSuccess()) {
            showInformationAlert("Successfully", content.toString());
        } else {
            showErrorAlert("Unsuccessfully", content.toString());
        }
    }
}
