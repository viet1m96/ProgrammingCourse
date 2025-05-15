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



public class RemoveGreaterController extends BaseController {

    public void initialize() {
        super.initialize();
    }
    @Override
    public void setUpButtons() {
        setUpCancelButton();
        funcButton.setText(resourceManager.getString(insertPop, "apply.button"));
        setUpFuncButton();
        funcButton.setOnAction(this::handleRemoveGreaterButtonClicked);
    }

    @FXML
    public void handleRemoveGreaterButtonClicked(ActionEvent event) {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        StdGroupUltimate groupUltimate = getIn4();
        StudyGroup studyGroup = StudyGrpTransformer.transformToNormal(groupUltimate);
        try {
            Request request = new Request("remove_greater", null, studyGroup, Client.getToken());
            Response response = Client.work(request);
            AlertUtil.showResultAlert(response);
        } catch (Exception e) {
            AlertUtil.showErrorAlert("Network Error", e.toString());
        }
        stage.close();
    }
}
