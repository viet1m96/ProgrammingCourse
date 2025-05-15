package gui.utilities.buttons;

import gui.utilities.tools.ResourceManager;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CloseButtonUtil {
    private final static String baseTable = "/il8n/tableView";
    public static Button getCloseButton(ResourceManager resourceManager) {
        Button closeButton = new Button(resourceManager.getString(baseTable, "close.button"));
        closeButton.setOnAction(event -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
        return closeButton;
    }
}
