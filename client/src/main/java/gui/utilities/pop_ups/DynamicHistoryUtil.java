package gui.utilities.pop_ups;

import gui.utilities.tools.ResourceManager;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import network.CommandHistory;

import java.util.Queue;

public class DynamicHistoryUtil extends DynamicPopUpUtil {
    public static void showHistoryPopUp(ResourceManager resourceManager, ActionEvent mainEvent) {
        Queue<String> history = CommandHistory.getHistory();
        Stage popUpStage = releasePopUp();
        VBox box = new VBox();
        box.setPadding(new Insets(10));
        box.setSpacing(5);
        box.setAlignment(Pos.TOP_CENTER);
        box.setPrefWidth(300);
        box.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-color: gray;");
        for (String command : history) {
            Label label = new Label(resourceManager.getString("/il8n/commands", command));
            label.setWrapText(true);
            box.getChildren().add(label);
        }

        Button closeButton = new Button(resourceManager.getString("/il8n/tableView", "close.button"));
        closeButton.setPrefWidth(100);
        closeButton.setOnAction(event -> popUpStage.close());
        box.getChildren().add(closeButton);
        Scene scene = new Scene(box);
        popUpStage.setScene(scene);
        popUpStage.setTitle(resourceManager.getString("/il8n/tableView", "history"));
        addBasicFuncs(popUpStage, mainEvent, 0, 0);
    }
}
