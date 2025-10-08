package gui.utilities.command_util;

import exceptions.network_exception.NetworkException;
import goods.Request;
import goods.Response;
import gui.utilities.tools.AlertUtil;
import gui.utilities.tools.ResourceManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main_objects.StdGroupUltimate;
import network.Client;

import java.util.ArrayList;
import java.util.List;

public class RemoveKeyUtil {

    private final String baseCommands = "/il8n/commands";
    public void handleRequest(String searchKey) {
        List<String> arguments = new ArrayList<>();
        arguments.add(searchKey);
        Request request = new Request("remove_key", arguments, Client.getToken());
        try {
            Response response = Client.work(request);
            AlertUtil.showResultAlert(response, (Stage)null);
        } catch (NetworkException e) {
            AlertUtil.showErrorAlert("Network Error", e.toString(), (Stage)null);
        }
    }

    public void questionChoice(Node tableView, StdGroupUltimate group, ResourceManager resourceManager) {
        Stage stage = DynamicPopUpUtil.releaseBasicPopUp();
        Label questionLabel = new Label(resourceManager.getString(baseCommands, "confirm.delete") + " " + group.getSearch_key() + " ?");
        questionLabel.setWrapText(true);

        Button yesButton = new Button(resourceManager.getString(baseCommands, "yes"));
        yesButton.setOnAction(event -> {
            handleRequest(group.getSearch_key());
            stage.close();
        });

        Button noButton = new Button(resourceManager.getString(baseCommands, "no"));
        noButton.setOnAction(event -> {
            stage.close();
        });


        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(yesButton, noButton);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(questionLabel, buttonBox);

        Scene scene = new Scene(layout, 400, 200);
        stage.setScene(scene);
        DynamicPopUpUtil.givePositionPopUp(stage, tableView, 100, 50, 200, 50);
    }
}
