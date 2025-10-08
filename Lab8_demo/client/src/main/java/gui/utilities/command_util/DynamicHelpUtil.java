package gui.utilities.command_util;

import exceptions.network_exception.NetworkException;
import goods.Request;
import goods.Response;
import gui.utilities.tools.AlertUtil;
import gui.utilities.tools.ResourceManager;
import gui.utilities.buttons.CloseButtonUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main_objects.Instruction;
import network.Client;

import java.util.List;

public class DynamicHelpUtil extends DynamicPopUpUtil {
    private static final String baseTable = "/il8n/tableView";
    public void showInstructionPopUp(ActionEvent actionEvent, ResourceManager resourceManager) {
        List<Instruction> instructions = handleRequest();
        if(instructions == null) return;
        Scene scene = initScene(resourceManager, instructions);
        Stage stage = releaseBasicPopUp();
        stage.setScene(scene);
        givePositionPopUp(stage, actionEvent, -800, -800);
    }

    public static TableView<Instruction> initTable(ResourceManager resourceManager, List<Instruction> instructions) {
        TableView<Instruction> tableView = new TableView<>();
        TableColumn<Instruction, String> commandColumn = new TableColumn<>(resourceManager.getString(baseTable, "command"));
        commandColumn.setCellValueFactory(new PropertyValueFactory<>("command"));
        TableColumn<Instruction, String> descriptionColumn = new TableColumn<>(resourceManager.getString(baseTable, "description"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        TableColumn<Instruction, String> syntaxColumn = new TableColumn<>(resourceManager.getString(baseTable, "syntax"));
        syntaxColumn.setCellValueFactory(new PropertyValueFactory<>("syntax"));

        tableView.getColumns().addAll(commandColumn, descriptionColumn, syntaxColumn);

        ObservableList<Instruction> data = FXCollections.observableArrayList(instructions);
        tableView.setItems(data);

        commandColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        descriptionColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.5));
        syntaxColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.3));
        return tableView;
    }

    private Scene initScene(ResourceManager resourceManager, List<Instruction> instructions) {
        TableView<Instruction> tableView = initTable(resourceManager, instructions);
        Button closeButton = CloseButtonUtil.getCloseButton(resourceManager);

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(tableView, closeButton);
        layout.setPadding(new Insets(0, 0, 10, 0));
        VBox.setVgrow(tableView, javafx.scene.layout.Priority.ALWAYS);

        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setContent(layout);
        AnchorPane root = new AnchorPane(scrollPane);
        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);


        return new Scene(root, 800, 600);
    }

    private List<Instruction> handleRequest() {
        Request request = new Request("help", null, Client.getToken());
        List<Instruction> instructions = null;
        try {
            Response response = Client.work(request);
            instructions = response.getInstructions();
        } catch (NetworkException e) {
            AlertUtil.showErrorAlert("Network Error", e.toString(), (Stage)null);
            return null;
        }
        return instructions;
    }
}
