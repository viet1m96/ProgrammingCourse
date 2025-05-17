package gui.working_session.std_grp_controllers;


import enums.Color;
import enums.FormOfEducation;
import enums.Semester;
import goods.Request;
import goods.Response;
import gui.utilities.tools.AlertUtil;
import gui.utilities.tools.Localizable;
import gui.utilities.tools.ResourceManager;
import gui.utilities.tools.TextUpdater;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main_objects.StdGroupUltimate;

import main_objects.StudyGroup;
import network.Client;

import utilities.StudyGrpTransformer;
import validators.InputForAdminValidator;
import validators.InputForGroupValidator;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class BaseController implements Localizable {

    @FXML
    protected Label searchKey;
    @FXML
    protected TextField searchKeyVal;
    @FXML
    protected Text searchKeyCondition;

    @FXML
    protected Label groupName;
    @FXML
    protected TextField groupNameVal;
    @FXML
    protected Text groupNameCondition;

    @FXML
    protected Label studCount;
    @FXML
    protected TextField studCountVal;
    @FXML
    protected Text studCountCondition;

    @FXML
    protected Label expStudent;
    @FXML
    protected TextField expStudentVal;
    @FXML
    protected Text expStudentCondition;

    @FXML
    protected Label formEdu;
    @FXML
    protected TextField formEduVal;
    @FXML
    protected Text formEduCondition;

    @FXML
    protected Label semester;
    @FXML
    protected TextField semesterVal;
    @FXML
    protected Text semesterCondition;

    @FXML
    protected Label groupX;
    @FXML
    protected TextField groupXVal;
    @FXML
    protected Text groupXCondition;

    @FXML
    protected Label groupY;
    @FXML
    protected TextField groupYVal;
    @FXML
    protected Text groupYCondition;

    @FXML
    protected Label adminName;
    @FXML
    protected TextField adminNameVal;
    @FXML
    protected Text adminNameCondition;

    @FXML
    protected Label birthday;
    @FXML
    protected TextField birthdayVal;
    @FXML
    protected Text birthdayCondition;

    @FXML
    protected Label weight;
    @FXML
    protected TextField weightVal;
    @FXML
    protected Text weightCondition;

    @FXML
    protected Label eyeColor;
    @FXML
    protected TextField eyeColorVal;
    @FXML
    protected Text eyeColorCondition;

    @FXML
    protected Label x;
    @FXML
    protected TextField xVal;
    @FXML
    protected Text xCondition;

    @FXML
    protected Label y;
    @FXML
    protected TextField yVal;
    @FXML
    protected Text yCondition;

    @FXML
    protected Label z;
    @FXML
    protected TextField zVal;
    @FXML
    protected Text zCondition;

    @FXML
    protected Label place;
    @FXML
    protected TextField placeVal;
    @FXML
    protected Text placeCondition;

    protected final String insertPop = "/il8n/insertPopUp";
    protected final String tableView = "/il8n/tableView";
    protected final String baseCommands = "/il8n/commands";
    @FXML
    protected Button funcButton;
    @FXML
    protected Button cancelButton;


    protected ResourceManager resourceManager;

    protected final BooleanProperty searchKeyValid = new SimpleBooleanProperty(false);
    protected final BooleanProperty groupNameValid = new SimpleBooleanProperty(false);
    protected final BooleanProperty stdCountValid = new SimpleBooleanProperty(false);
    protected final BooleanProperty expStudValid = new SimpleBooleanProperty(false);
    protected final BooleanProperty formEduValid = new SimpleBooleanProperty(true);
    protected final BooleanProperty semesterValid = new SimpleBooleanProperty(false);
    protected final BooleanProperty groupXValid = new SimpleBooleanProperty(false);
    protected final BooleanProperty groupYValid = new SimpleBooleanProperty(false);
    protected final BooleanProperty adminNameValid = new SimpleBooleanProperty(false);
    protected final BooleanProperty birthdayValid = new SimpleBooleanProperty(true);
    protected final BooleanProperty weightValid = new SimpleBooleanProperty(true);
    protected final BooleanProperty eyeColorValid = new SimpleBooleanProperty(false);
    protected final BooleanProperty xValid = new SimpleBooleanProperty(true);
    protected final BooleanProperty yValid = new SimpleBooleanProperty(true);
    protected final BooleanProperty zValid = new SimpleBooleanProperty(true);
    protected final BooleanProperty placeValid = new SimpleBooleanProperty(true);

    protected void initialize() {
        resourceManager = ResourceManager.getInstance();
        resourceManager.registerController(this, insertPop);
        resourceManager.registerController(this, tableView);
        addTextForGroup();
        addTextForAdmin();
        setUpButtons();
    }

    @Override
    public void updateLocalization() {
        addTextForGroup();
        addTextForAdmin();
    }

    private void addTextForGroup() {
        setUpOneCondition(searchKey, searchKeyVal, searchKeyCondition, "search_key", "search_key_con", searchKeyValid, InputForGroupValidator.isValidSearchKey);
        setUpOneCondition(groupName, groupNameVal, groupNameCondition, "group_name", "group_name_con", groupNameValid, InputForGroupValidator.isValidName);
        setUpOneCondition(studCount, studCountVal, studCountCondition, "student_count", "stud_count_con", stdCountValid, InputForGroupValidator.isValidStudCount);
        setUpOneCondition(expStudent, expStudentVal, expStudentCondition, "expelled_students", "stud_count_con", expStudValid, InputForGroupValidator.isValidStudCount);
        setUpOneCondition(formEdu, formEduVal, formEduCondition, "form_of_education", "form_edu_con", formEduValid, InputForGroupValidator.isValidEduForm);
        setUpOneCondition(semester, semesterVal, semesterCondition, "semester", "semester_con", semesterValid, InputForGroupValidator.isValidSemester);
        setUpOneCondition(groupX, groupXVal, groupXCondition, "group_x", "group_x_con", groupXValid, InputForGroupValidator.isValidGroupX);
        setUpOneCondition(groupY, groupYVal, groupYCondition, "group_y", "group_y_con", groupYValid, InputForGroupValidator.isValidGroupY);
    }

    private void addTextForAdmin() {
        setUpOneCondition(adminName, adminNameVal, adminNameCondition, "admin_name", "admin_con", adminNameValid, InputForGroupValidator.isValidName);
        setUpOneCondition(birthday, birthdayVal, birthdayCondition, "birthday", "birthday_con", birthdayValid, InputForAdminValidator.isValidBirthday);
        setUpOneCondition(weight, weightVal, weightCondition, "weight", "weight_con", weightValid, InputForAdminValidator.isValidWeight);
        setUpOneCondition(eyeColor, eyeColorVal, eyeColorCondition, "eye_color", "eye_con", eyeColorValid, InputForAdminValidator.isValidEyeColor);
        setUpOneCondition(x, xVal, xCondition, "x", "xyz_con", xValid, InputForAdminValidator.isValidCoordinate);
        setUpOneCondition(y, yVal, yCondition, "y", "xyz_con", yValid, InputForAdminValidator.isValidCoordinate);
        setUpOneCondition(z, zVal, zCondition, "z", "xyz_con", zValid, InputForAdminValidator.isValidCoordinate);
        setUpOneCondition(place, placeVal, placeCondition, "place", "place_con", placeValid, InputForAdminValidator.isValidPlace);
        placeVal.setDisable(true);
        xVal.textProperty().addListener((observable, oldValue, newValue) -> updatePlaceValEnablement());
        yVal.textProperty().addListener((observable, oldValue, newValue) -> updatePlaceValEnablement());
        zVal.textProperty().addListener((observable, oldValue, newValue) -> updatePlaceValEnablement());
    }


    private void setUpOneCondition(Label label
            , TextField textField
            , Text condition
            , String keyLabel
            , String keyCondition
            , BooleanProperty keyValid
            , Predicate<String> validator) {
        label.setText(resourceManager.getString(tableView, keyLabel));
        label.setStyle("-fx-font-weight: bold;");
        condition.setText(resourceManager.getString(insertPop, keyCondition));
        final Text temp = condition;
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean valid = validator.test(newValue);
            TextUpdater.updateTextColor(temp, valid);
            keyValid.set(valid);
        });
    }


    private void updatePlaceValEnablement() {
        boolean anyCoordinateNotEmpty = (!xVal.getText().isEmpty()
                && !yVal.getText().isEmpty()
                && !zVal.getText().isEmpty()) && (xValid.get() && yValid.get() && zValid.get());
        placeVal.setDisable(!anyCoordinateNotEmpty);
    }

    public abstract void setUpButtons();
    protected void setUpCancelButton() {
        cancelButton.setText(resourceManager.getString(insertPop, "cancel.button"));
        cancelButton.setOnAction(this::handleCancelButtonClicked);
    }
    protected StdGroupUltimate getIn4() {
        StdGroupUltimate groupUltimate = new StdGroupUltimate();
        groupUltimate.setSearch_key(searchKeyVal.getText());
        groupUltimate.setGroup_name(groupNameVal.getText());
        groupUltimate.setCreation_date(LocalDate.now());
        groupUltimate.setStudent_count(Long.parseLong(studCountVal.getText()));
        groupUltimate.setExpelled_students(Long.parseLong(expStudentVal.getText()));
        if(formEduVal.getText() == null || formEduVal.getText().isEmpty()){
            groupUltimate.setEduForm(null);
        } else {
            groupUltimate.setEduForm(FormOfEducation.valueOf(formEduVal.getText().toUpperCase()));
        }
        groupUltimate.setSemester(Semester.valueOf(semesterVal.getText().toUpperCase()));
        groupUltimate.setCreator(Client.getUsername());
        groupUltimate.setGroup_x(Integer.parseInt(groupXVal.getText()));
        groupUltimate.setGroup_y(Integer.parseInt(groupYVal.getText()));
        groupUltimate.setAdmin_name(adminNameVal.getText());
        if(birthdayVal.getText() == null || birthdayVal.getText().isEmpty()){
            groupUltimate.setBirthday(null);
        } else {
            groupUltimate.setBirthday(LocalDate.parse(birthdayVal.getText()));
        }
        if(weightVal.getText() == null || weightVal.getText().isEmpty()){
            groupUltimate.setWeight(null);
        } else {
            groupUltimate.setWeight(Integer.parseInt(weightVal.getText()));
        }
        groupUltimate.setEye_color(Color.valueOf(eyeColorVal.getText().toUpperCase()));
        if(xVal.getText() == null || xVal.getText().isEmpty()){
            groupUltimate.setX(null);
        } else {
            groupUltimate.setX(Long.parseLong(xVal.getText()));
        }
        if(yVal.getText() == null || yVal.getText().isEmpty()){
            groupUltimate.setY(null);
        } else {
            groupUltimate.setY(Integer.parseInt(yVal.getText()));
        }
        if(zVal.getText() == null || zVal.getText().isEmpty()){
            groupUltimate.setZ(null);
        } else {
            groupUltimate.setZ(Integer.parseInt(zVal.getText()));
        }
        if(placeVal.getText() == null || placeVal.getText().isEmpty()){
            groupUltimate.setPlace(null);
        } else {
            groupUltimate.setPlace(placeVal.getText());
        }
        return groupUltimate;
    }

    protected void setUpFuncButton() {
        funcButton.disableProperty().bind(
                searchKeyValid.not()
                        .or(groupNameValid.not())
                        .or(stdCountValid.not())
                        .or(expStudValid.not())
                        .or(formEduValid.not())
                        .or(semesterValid.not())
                        .or(groupXValid.not())
                        .or(groupYValid.not())
                        .or(adminNameValid.not())
                        .or(birthdayValid.not())
                        .or(weightValid.not())
                        .or(eyeColorValid.not())
                        .or(xValid.not())
                        .or(yValid.not())
                        .or(zValid.not())
                        .or(placeValid.not())
        );
    }
    @FXML
    public void handleCancelButtonClicked(ActionEvent event) {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        stage.close();
    }

    protected void sendClicked(String cmd, ActionEvent event) {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        System.out.println("here");
        StdGroupUltimate groupUltimate = getIn4();
        System.out.println("next");
        StudyGroup studyGroup = StudyGrpTransformer.transformToNormal(groupUltimate);
        List<String> arguments = new ArrayList<>();
        arguments.add(searchKeyVal.getText());
        try {
            Request request = new Request(cmd, arguments, studyGroup, Client.getToken());
            Response response = Client.work(request);
            AlertUtil.showResultAlert(response, event);
        } catch (Exception e) {
            AlertUtil.showErrorAlert("Network Error", e.toString(), event);
        }
        stage.close();
    }

    protected void setUpInfo(StdGroupUltimate updatingGroup) {
        searchKeyVal.setText(updatingGroup.getSearch_key());
        groupNameVal.setText(updatingGroup.getGroup_name());
        studCountVal.setText(updatingGroup.getStudent_count().toString());
        expStudentVal.setText(updatingGroup.getExpelled_students().toString());
        if(updatingGroup.getForm_of_education() == null) {
            formEduVal.setText("");
        } else {
            formEduVal.setText(updatingGroup.getForm_of_education().toString());
        }
        semesterVal.setText(updatingGroup.getSemester().toString());
        groupXVal.setText(updatingGroup.getGroup_x().toString());
        groupYVal.setText(updatingGroup.getGroup_y().toString());
        adminNameVal.setText(updatingGroup.getAdmin_name());
        if(updatingGroup.getBirthday() == null) {
            birthdayVal.setText("");
        } else {
            birthdayVal.setText(updatingGroup.getBirthday().toString());
        }
        if(updatingGroup.getWeight() == null) {
            weightVal.setText("");
        } else {
            weightVal.setText(updatingGroup.getWeight().toString());
        }
        eyeColorVal.setText(updatingGroup.getEye_color().toString());
        if(updatingGroup.getX() == null) {
            xVal.setText("");
        } else {
            xVal.setText(updatingGroup.getX().toString());
        }
        if(updatingGroup.getY() == null) {
            yVal.setText("");
        } else {
            yVal.setText(updatingGroup.getY().toString());
        }
        if(updatingGroup.getZ() == null) {
            zVal.setText("");
        } else {
            zVal.setText(updatingGroup.getZ().toString());
        }
        if(updatingGroup.getPlace() == null) {
            placeVal.setText("");
        } else {
            placeVal.setText(updatingGroup.getPlace());
        }
        searchKeyVal.setEditable(false);
    }

}
