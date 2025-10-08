package gui.working_session.std_grp_controllers;


import javafx.event.ActionEvent;

import main_objects.StdGroupUltimate;


public class UpdateController extends BaseController {

    private final StdGroupUltimate updatingGroup;

    public UpdateController(StdGroupUltimate updatingGroup) {
        this.updatingGroup = updatingGroup;
    }

    public void initialize() {
        super.initialize();
        setUpInfo(updatingGroup);
    }

    @Override
    public void setUpButtons() {
        setUpCancelButton();
        funcButton.setText(resourceManager.getString(baseCommands, "update"));
        funcButton.setOnAction(this::handleUpdateButtonClicked);
    }

    public void handleUpdateButtonClicked(ActionEvent event) {
       sendClicked("update", event);
    }



}
