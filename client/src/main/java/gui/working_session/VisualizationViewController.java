package gui.working_session;

import gui.utilities.command_util.DynamicPopUpUtil;
import gui.utilities.tools.LanguageBoxUtil;
import gui.utilities.tools.Localizable;
import gui.utilities.tools.ResourceManager;
import gui.working_session.graph.InteractiveGraph;
import gui.working_session.std_grp_controllers.InsertController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main_objects.StdGroupUltimate;


import java.util.ArrayList;
import java.util.List;

public class VisualizationViewController implements Localizable {

    @FXML
    private Button insertButton;
    @FXML
    private ComboBox<String> languageComboBox;

    private InteractiveGraph graph;
    private ResourceManager resourceManager;
    private final String baseTable = "/il8n/tableView";
    private final String baseInsert = "/il8n/insertPopUp";
    private final String baseCommands = "/il8n/commands";
    private final String baseStarters = "/il8n/Starters";
    private final String fxmlStdGrp = "/gui/working_session/StdGrpPopUp.fxml";
    @FXML private AnchorPane visualizationPane;


    public void initialize() {
        resourceManager = ResourceManager.getInstance();
        resourceManager.registerController(this, baseTable);
        resourceManager.registerController(this, baseInsert);
        resourceManager.registerController(this, baseCommands);
        resourceManager.registerController(this, baseStarters);
        LanguageBoxUtil.setUp(baseStarters, languageComboBox, resourceManager);
        graph = new InteractiveGraph(resourceManager);
        visualizationPane.getChildren().add(0, graph);

        AnchorPane.setTopAnchor(graph, 0.0);
        AnchorPane.setBottomAnchor(graph, 0.0);
        AnchorPane.setLeftAnchor(graph, 0.0);
        AnchorPane.setRightAnchor(graph, 0.0);

    }


    @Override
    public void updateLocalization() {
        LanguageBoxUtil.updateLanguage(baseStarters, languageComboBox, resourceManager);
    }

    @FXML
    public void handleLanguageClicked(ActionEvent event) {
        LanguageBoxUtil.handleLanguageClicked(event, resourceManager, languageComboBox);
    }

    @FXML
    public void handleInsertClicked(ActionEvent event) {
        Stage stage = DynamicPopUpUtil.releaseBasicPopUp(fxmlStdGrp, new InsertController());
        DynamicPopUpUtil.givePositionPopUp(stage, visualizationPane, 800, 800, 0, 0);
    }


    public void setGroups(ObservableList<StdGroupUltimate> groups) {
        List<StdGroupUltimate> newGroups = new ArrayList<>(groups);
        graph.updateGraphGroups(newGroups);
    }
}
