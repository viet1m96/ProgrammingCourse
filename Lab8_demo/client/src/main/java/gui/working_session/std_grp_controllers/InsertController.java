package gui.working_session.std_grp_controllers;

import goods.Request;
import goods.Response;
import gui.utilities.tools.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import main_objects.StdGroupUltimate;
import main_objects.StudyGroup;
import network.Client;
import utilities.StudyGrpTransformer;

import java.util.ArrayList;
import java.util.List;

public class InsertController extends BaseController {

    public void initialize() {
        super.initialize();
    }
    @Override
    public void setUpButtons() {
        funcButton.setText(resourceManager.getString(insertPop, "insert.button"));
        setUpCancelButton();
        setUpFuncButton();
        funcButton.setOnAction(this::handleInsertButtonClicked);
    }

    @FXML
    public void handleInsertButtonClicked(ActionEvent event) {
        sendClicked("insert", event);
    }
}
