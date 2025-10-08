package gui.utilities.buttons;

import gui.utilities.tools.ResourceManager;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CancelButtonUtil {
    private final static String insertPopUp = "/il8n/insertPopUp";
    public static Button getCancelButton(ResourceManager resourceManager) {
        Button cancelButton = new Button();
        cancelButton.setText(resourceManager.getString(insertPopUp, "cancel.button"));
        cancelButton.setOnAction(e -> {
            cancelButton.setOnAction(event -> {
                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();
            });
        });
        return cancelButton;
    }
}
