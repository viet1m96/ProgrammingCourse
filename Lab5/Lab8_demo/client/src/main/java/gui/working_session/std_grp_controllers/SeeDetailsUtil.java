package gui.working_session.std_grp_controllers;

import javafx.fxml.FXML;
import main_objects.StdGroupUltimate;

public class SeeDetailsUtil extends BaseController{

    private StdGroupUltimate group;

    public SeeDetailsUtil(StdGroupUltimate group) {
        this.group = group;
    }
    public void initialize() {
        super.initialize();
        funcButton.setText("Close");
    }
    @Override
    public void setUpButtons() {
        cancelButton.setVisible(false);
        cancelButton.setManaged(false);
        funcButton.setText(resourceManager.getString(tableView, "close.button"));
        funcButton.setOnAction(this::handleCancelButtonClicked);
        disableAllConditions();
        setUpInfo(group);
        disableAllConditions();
        disableEditable();
    }

    private void disableAllConditions() {
        searchKeyCondition.setVisible(false);
        groupNameCondition.setVisible(false);
        studCountCondition.setVisible(false);
        expStudentCondition.setVisible(false);
        formEduCondition.setVisible(false);
        semesterCondition.setVisible(false);
        groupXCondition.setVisible(false);
        groupYCondition.setVisible(false);
        adminNameCondition.setVisible(false);
        birthdayCondition.setVisible(false);
        weightCondition.setVisible(false);
        eyeColorCondition.setVisible(false);
        xCondition.setVisible(false);
        yCondition.setVisible(false);
        zCondition.setVisible(false);
        placeCondition.setVisible(false);
    }

    private void disableEditable() {
        searchKeyVal.setEditable(false);
        groupNameVal.setEditable(false);
        studCountVal.setEditable(false);
        expStudentVal.setEditable(false);
        formEduVal.setEditable(false);
        semesterVal.setEditable(false);
        groupXVal.setEditable(false);
        groupYVal.setEditable(false);
        adminNameVal.setEditable(false);
        birthdayVal.setEditable(false);
        weightVal.setEditable(false);
        eyeColorVal.setEditable(false);
        xVal.setEditable(false);
        yVal.setEditable(false);
        zVal.setEditable(false);
        placeVal.setEditable(false);
    }
}
