package gui.working_session.std_grp_controllers;

import javafx.event.ActionEvent;
import main_objects.StdGroupUltimate;

public class ReplaceController extends BaseController {
    private final StdGroupUltimate replacingGroup;

    public ReplaceController(StdGroupUltimate updatingGroup) {
        this.replacingGroup = updatingGroup;
    }

    public void initialize() {
        super.initialize();
        setUpInfo(replacingGroup);
    }

    @Override
    public void setUpButtons() {
        setUpCancelButton();
        funcButton.setText(resourceManager.getString(baseCommands, "replace_if_lower"));
        funcButton.setOnAction(this::handleReplaceButtonClicked);
    }

    public void handleReplaceButtonClicked(ActionEvent event) {
        sendClicked("replace_if_lower", event);
    }
}
