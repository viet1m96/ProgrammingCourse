package gui.utilities.pop_ups;

import exceptions.network_exception.NetworkException;
import goods.Request;
import goods.Response;
import gui.utilities.tools.AlertUtil;
import gui.utilities.tools.ResourceManager;
import gui.utilities.buttons.CloseButtonUtil;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import network.Client;



import java.util.List;

public class DynamicInfoUtil extends DynamicPopUpUtil {
    private List<String> handleRequest() {
        Request request = new Request("info", null, Client.getToken());
        List<String> result = null;
        try {
            Response response = Client.work(request);
            result = response.getResult();
        } catch (NetworkException e) {
            AlertUtil.showErrorAlert("Network Error", e.toString());
            return null;
        }
        return result;
    }

    public void showInfoPopup(ResourceManager resourceManager, ActionEvent event) {
        List<String> result = handleRequest();
        if(result == null) return;
        Scene scene = initScene(resourceManager, result);
        Stage stage = releasePopUp();
        stage.setScene(scene);
        addBasicFuncs(stage, event, -600, -200);
    }

    public Scene initScene(ResourceManager resourceManager, List<String> results) {
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
        mainLayout.setAlignment(Pos.CENTER);

        for(String s : results) {
            Label label = new Label(s);
            label.setWrapText(true);
            mainLayout.getChildren().add(label);
        }

        Button closeButton = CloseButtonUtil.getCloseButton(resourceManager);
        mainLayout.getChildren().add(closeButton);

        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        return new Scene(scrollPane, 500, 180);
    }
}
