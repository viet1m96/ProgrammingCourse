package file_processor.gui_file.results;

import enums.Color;
import enums.FormOfEducation;
import enums.Semester;
import goods.Response;
import gui.utilities.tools.ResourceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main_objects.StdGroupUltimate;
import main_objects.StudyGroup;
import utilities.StudyGrpTransformer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TableStudyGroupBoxer implements VboxOutput{


    private TableColumn<StdGroupUltimate, String> keyColumn = new TableColumn<>();
    private TableColumn<StdGroupUltimate, String> groupNameColumn= new TableColumn<>();
    private TableColumn<StdGroupUltimate, LocalDate> createDateColumn= new TableColumn<>();
    private TableColumn<StdGroupUltimate, Long> stdCountColumn= new TableColumn<>();
    private TableColumn<StdGroupUltimate, Long> expStudentColumn= new TableColumn<>();
    private TableColumn<StdGroupUltimate, FormOfEducation> eduFormColumn= new TableColumn<>();
    private TableColumn<StdGroupUltimate, Semester> semesterColumn= new TableColumn<>();
    private TableColumn<StdGroupUltimate, String> creatorColumn= new TableColumn<>();
    private TableColumn<StdGroupUltimate, Integer> groupXColumn= new TableColumn<>();
    private TableColumn<StdGroupUltimate, Integer> groupYColumn= new TableColumn<>();
    private TableColumn<StdGroupUltimate, String> adminNameColumn= new TableColumn<>();
    private TableColumn<StdGroupUltimate, LocalDate> adminBirthColumn= new TableColumn<>();
    private TableColumn<StdGroupUltimate, Integer> adminWeightColumn= new TableColumn<>();
    private TableColumn<StdGroupUltimate, Color> adminEyeColumn= new TableColumn<>();
    private TableColumn<StdGroupUltimate, Long> adminXColumn= new TableColumn<>();
    private TableColumn<StdGroupUltimate, Integer> adminYColumn= new TableColumn<>();
    private TableColumn<StdGroupUltimate, Integer> adminZColumn= new TableColumn<>();
    private TableColumn<StdGroupUltimate, String> adminPlaceColumn= new TableColumn<>();


    @Override
    public VBox returnResultAsVbox(ResourceManager resourceManager, String command, Response response) {
        List<StudyGroup> studyGroups = response.getStudyGroups();
        if(studyGroups == null || studyGroups.isEmpty()) return giveEmptyResult(response);
        List<StdGroupUltimate> studyGroupUltimates = new ArrayList<>();
        for(StudyGroup studyGroup : studyGroups) {
            StdGroupUltimate studyGroupUltimate = StudyGrpTransformer.transformToUltimate(studyGroup);
            studyGroupUltimates.add(studyGroupUltimate);
        }
        TableView<StdGroupUltimate> tableView = createTableView(resourceManager, studyGroupUltimates);
        VBox mainVBox = new VBox();
        mainVBox.setAlignment(Pos.TOP_LEFT);
        mainVBox.setPadding(new Insets(10));


        Label titleLabel = new Label(resourceManager.getString(baseCommands, command));
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        mainVBox.getChildren().add(titleLabel);

        double tableHeight = (tableView.getItems().size() + 1) * 24;
        tableHeight += 24;

        tableView.setPrefHeight(tableHeight);

        mainVBox.getChildren().add(tableView);
        VBox.setVgrow(tableView, javafx.scene.layout.Priority.ALWAYS);

        return mainVBox;
    }


    private TableView<StdGroupUltimate> createTableView(ResourceManager resourceManager, List<StdGroupUltimate> studyGroupUltimates) {
        TableView<StdGroupUltimate> tableView = new TableView<>();
        buildFactory(keyColumn, "search_key", resourceManager);
        buildFactory(groupNameColumn, "group_name", resourceManager);
        buildFactory(createDateColumn, "creation_date", resourceManager);
        buildFactory(stdCountColumn, "student_count", resourceManager);
        buildFactory(expStudentColumn, "expelled_students", resourceManager);
        buildFactory(eduFormColumn, "form_of_education", resourceManager);
        buildFactory(semesterColumn, "semester", resourceManager);
        buildFactory(creatorColumn, "creator", resourceManager);
        buildFactory(groupXColumn, "group_x", resourceManager);
        buildFactory(groupYColumn, "group_y", resourceManager);
        buildFactory(adminNameColumn, "admin_name", resourceManager);
        buildFactory(adminBirthColumn, "birthday", resourceManager);
        buildFactory(adminWeightColumn, "weight", resourceManager);
        buildFactory(adminEyeColumn, "eye_color", resourceManager);
        buildFactory(adminXColumn, "x", resourceManager);
        buildFactory(adminYColumn, "y", resourceManager);
        buildFactory(adminZColumn, "z", resourceManager);
        buildFactory(adminPlaceColumn, "place", resourceManager);
        ObservableList<StdGroupUltimate> data = FXCollections.observableArrayList(studyGroupUltimates);
        tableView.setItems(data);

        tableView.getColumns().addAll(
                keyColumn, groupNameColumn, createDateColumn, stdCountColumn, expStudentColumn,
                eduFormColumn, semesterColumn, creatorColumn, groupXColumn, groupYColumn,
                adminNameColumn, adminBirthColumn, adminWeightColumn, adminEyeColumn,
                adminXColumn, adminYColumn, adminZColumn, adminPlaceColumn
        );
        return tableView;
    }

    private void buildFactory(TableColumn<StdGroupUltimate, ?> column, String fieldName, ResourceManager resourceManager) {
        column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        column.setUserData(fieldName);
        setColumnHeaders(column, fieldName, resourceManager);
    }

    private void setColumnHeaders(TableColumn<StdGroupUltimate, ?> column, String key, ResourceManager resourceManager) {
        String columnName = resourceManager.getString(baseTable, key);
        Label title = new Label(columnName);
        HBox header = new HBox(5, title);
        header.setPrefWidth(150);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(5));
        column.setGraphic(header);
    }

    private VBox giveEmptyResult(Response response) {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        List<String> notices = response.getNotice();
        for(String notice : notices) {
            Label label = new Label(notice);
            box.getChildren().add(label);
        }
        return box;
    }

}
