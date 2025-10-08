package gui.working_session;

import enums.Color;
import enums.FormOfEducation;
import enums.Semester;
import file_processor.gui_file.FileSelector;
import file_processor.gui_file.ResultRender;
import file_processor.logic.CommandArgClassifier;
import file_processor.logic.CommandTypeClassifier;
import file_processor.logic.CommandVBoxClassifier;
import file_processor.logic.FileReaderMode;
import gui.utilities.command_util.*;
import gui.utilities.tools.*;
import gui.working_session.std_grp_controllers.ChoicesCreator;
import gui.working_session.std_grp_controllers.InsertController;
import gui.working_session.std_grp_controllers.RemoveGreaterController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main_objects.StdGroupUltimate;
import network.Client;
import network.CommandHistory;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;


public class TableViewController implements Localizable {
    @FXML private TableView<StdGroupUltimate> tableView;
    @FXML private VBox vBox;
    @FXML private AnchorPane anchorTable;
    @FXML private TableColumn<StdGroupUltimate, String> keyColumn;
    @FXML private TableColumn<StdGroupUltimate, String> groupNameColumn;
    @FXML private TableColumn<StdGroupUltimate, LocalDate> createDateColumn;
    @FXML private TableColumn<StdGroupUltimate, Long> stdCountColumn;
    @FXML private TableColumn<StdGroupUltimate, Long> expStudentColumn;
    @FXML private TableColumn<StdGroupUltimate, FormOfEducation> eduFormColumn;
    @FXML private TableColumn<StdGroupUltimate, Semester> semesterColumn;
    @FXML private TableColumn<StdGroupUltimate, String> creatorColumn;
    @FXML private TableColumn<StdGroupUltimate, Integer> groupXColumn;
    @FXML private TableColumn<StdGroupUltimate, Integer> groupYColumn;
    @FXML private TableColumn<StdGroupUltimate, String> adminNameColumn;
    @FXML private TableColumn<StdGroupUltimate, LocalDate> adminBirthColumn;
    @FXML private TableColumn<StdGroupUltimate, Integer> adminWeightColumn;
    @FXML private TableColumn<StdGroupUltimate, Color> adminEyeColumn;
    @FXML private TableColumn<StdGroupUltimate, Long> adminXColumn;
    @FXML private TableColumn<StdGroupUltimate, Integer> adminYColumn;
    @FXML private TableColumn<StdGroupUltimate, Integer> adminZColumn;
    @FXML private TableColumn<StdGroupUltimate, String> adminPlaceColumn;

    @FXML private Button insertButton;
    @FXML private Button historyButton;
    @FXML private Button clearButton;
    @FXML private Button removeGreater;
    @FXML private Button instructionButton;
    @FXML private Button informationButton;
    @FXML private Button scriptButton;
    @FXML private Button signOutButton;
    @FXML private Button clearFilterButton;
    @FXML private Button exitButton;
    @FXML private Label  curUserLabel;
    @FXML private ComboBox<String> languageBox;

    private StdGroupUltimate selectedGroup;
    private ResourceManager resourceManager;
    private final String baseTable = "/il8n/tableView";
    private final String baseInsert = "/il8n/insertPopUp";
    private final String baseCommands = "/il8n/commands";
    private final String baseStarters = "/il8n/Starters";
    private final String fxmlStdGrp = "/gui/working_session/StdGrpPopUp.fxml";
    private final String fxmlMain = "/gui/starters/Main.fxml";
    private  ObservableList<StdGroupUltimate> groups = FXCollections.observableArrayList();
    private  ObservableList<StdGroupUltimate> backUpGroups = FXCollections.observableArrayList();
    private final SortUtil sortUtil = new SortUtil();
    private final FilterUtil filterUtil = new FilterUtil();
    private final ChoicesCreator choicesCreator = new ChoicesCreator();
    private ContextMenu contextMenu;

    public void setGroups(ObservableList<StdGroupUltimate> groups, ObservableList<StdGroupUltimate> backUpGroups) {
        this.groups.setAll(groups);
        tableView.setItems(this.groups);
        this.backUpGroups.setAll(backUpGroups);
    }
    public void initialize() {
        resourceManager = ResourceManager.getInstance();
        resourceManager.registerController(this, baseTable);
        resourceManager.registerController(this, baseStarters);
        resourceManager.registerController(this, baseInsert);
        resourceManager.registerController(this, baseCommands);
        setUpTableColumns();
        setUpButtons();
        LanguageBoxUtil.setUp(baseStarters, languageBox, resourceManager);
        Platform.runLater(this::changeSize);
        tableView.setOnMouseClicked(this::handleMouseClicked);
    }

    @Override
    public void updateLocalization() {
        setUpTableColumns();
        setUpButtons();
        LanguageBoxUtil.updateLanguage(baseStarters, languageBox, resourceManager);
    }

    private void setUpButtons() {
        insertButton.setText(resourceManager.getString(baseTable, "insert"));
        historyButton.setText(resourceManager.getString(baseTable, "history"));
        clearButton.setText(resourceManager.getString(baseTable, "clear"));
        removeGreater.setText(resourceManager.getString(baseTable, "remove_if_greater"));
        instructionButton.setText(resourceManager.getString(baseTable, "help"));
        informationButton.setText(resourceManager.getString(baseTable, "info"));
        scriptButton.setText(resourceManager.getString(baseTable, "script"));
        signOutButton.setText(resourceManager.getString(baseTable, "sign_out"));
        clearFilterButton.setText(resourceManager.getString(baseTable, "clear_filter"));
        exitButton.setText(resourceManager.getString(baseTable, "exit"));
        curUserLabel.setText(resourceManager.getString(baseTable, "user") + ": " + Client.getUsername());
    }

    private void setUpTableColumns() {
        buildFactory(keyColumn, "search_key");
        buildFactory(groupNameColumn, "group_name");
        buildFactory(createDateColumn, "creation_date");
        buildFactory(stdCountColumn, "student_count");
        buildFactory(expStudentColumn, "expelled_students");
        buildFactory(eduFormColumn, "form_of_education");
        buildFactory(semesterColumn, "semester");
        buildFactory(creatorColumn, "creator");
        buildFactory(groupXColumn, "group_x");
        buildFactory(groupYColumn, "group_y");
        buildFactory(adminNameColumn, "admin_name");
        buildFactory(adminBirthColumn, "birthday");
        buildFactory(adminWeightColumn, "weight");
        buildFactory(adminEyeColumn, "eye_color");
        buildFactory(adminXColumn, "x");
        buildFactory(adminYColumn, "y");
        buildFactory(adminZColumn, "z");
        buildFactory(adminPlaceColumn, "place");
    }

    private void buildFactory(TableColumn<StdGroupUltimate, ?> column, String fieldName) {
        column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        column.setUserData(fieldName);
        setColumnHeaders(column, fieldName);
    }

    private void setColumnHeaders(TableColumn<StdGroupUltimate, ?> column, String key) {
        String columnName = resourceManager.getString(baseTable, key);
        Label title = new Label(columnName);
        Button sortButton = new Button("⇅");
        Button filterButton = new Button("⚲");
        HBox header = new HBox(5, title, sortButton, filterButton);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(5));
        column.setGraphic(header);

        sortButton.setOnAction(event -> sortUtil.handleSort(groups, tableView, column));
        filterButton.setOnAction(event -> {
            DynamicFilterUtil filterPopUp = new DynamicFilterUtil();
            filterPopUp.showFilterPopUp(event, resourceManager,column, filterUtil, groups);
            tableView.setItems(groups);
        });
    }

    public void applySort() {
        sortUtil.applyCurSort(groups, tableView);
    }

    public void applyFilters() {
        filterUtil.applyCurFilters(groups);
    }

    public void handleClearFiltersButtonClicked(ActionEvent event) {
        filterUtil.clearFilters();
        groups.setAll(backUpGroups);
        sortUtil.applyCurSort(groups, tableView);
    }

    public void handleHistoryButtonClicked(ActionEvent event) {
        CommandHistory.addHistory("history");
        DynamicHistoryUtil.showHistoryPopUp(resourceManager, event);
    }

    public void handleClearButtonClicked(ActionEvent event) {
        ClearUtil clearUtil = new ClearUtil();
        clearUtil.handleRequest();
    }

    private void changeSize()  {
        anchorTable.widthProperty().addListener((observable, oldValue, newValue) -> {
           Platform.runLater(() -> {
               double anchorWidth = newValue.doubleValue();
               double tableWidth = tableView.getWidth();
               if((anchorWidth - tableWidth > vBox.getPrefWidth()) || (anchorWidth - tableWidth < vBox.getPrefWidth() && vBox.getPrefWidth() > 200) ) {
                   vBox.setPrefWidth(anchorWidth - tableWidth);
               } else {
                   vBox.setPrefWidth(200);
               }
           });
        });
    }

    @FXML
    private void handleMouseClicked(MouseEvent event) {
        StdGroupUltimate clickedGroup = tableView.getSelectionModel().getSelectedItem();

        if (clickedGroup == null) {
            hideContextMenu();
            return;
        }

        if (event.getButton() == MouseButton.SECONDARY) {
            hideContextMenu();

            contextMenu = choicesCreator.createContextMenu(clickedGroup, resourceManager, tableView);
            contextMenu.show(tableView, event.getScreenX(), event.getScreenY());

            event.consume();
        } else {
            hideContextMenu();
        }
    }
    private void hideContextMenu() {
        if (contextMenu != null && contextMenu.isShowing()) {
            contextMenu.hide();
            contextMenu = null;
        }
    }

    @FXML
    public void handleLanguageClicked(ActionEvent event) {
        LanguageBoxUtil.handleLanguageClicked(event, resourceManager, languageBox);
    }

    @FXML
    public void handleInsertButtonClicked(ActionEvent e) {
        Stage stage = DynamicPopUpUtil.releaseBasicPopUp(fxmlStdGrp, new InsertController());
        DynamicPopUpUtil.givePositionPopUp(stage, tableView, 800, 800, 0, 0);
    }
    @FXML
    public void handleExitButtonClicked(ActionEvent event) {
        System.exit(0);
    }
    @FXML
    public void handleInstructionButtonClicked(ActionEvent event) {
        DynamicHelpUtil helpPopUpUtil = new DynamicHelpUtil();
        helpPopUpUtil.showInstructionPopUp(event, resourceManager);
    }
    @FXML
    public void handleInformationButtonClicked(ActionEvent event) {
        DynamicInfoUtil infoPopUpUtil = new DynamicInfoUtil();
        infoPopUpUtil.showInfoPopup(resourceManager, event);
    }
    @FXML
    public void handleRemoveGreaterButtonClicked(ActionEvent event) {
        Stage stage = DynamicPopUpUtil.releaseBasicPopUp(fxmlStdGrp, new RemoveGreaterController());
        DynamicPopUpUtil.givePositionPopUp(stage, tableView, 800, 800, 0, 0);
    }
    @FXML
    public void handleSignOutButtonClicked(ActionEvent event) throws IOException {
        Client.setAccount(null, null, null);
        FXMLLoaderUlti.changeSceneWithoutHistory(event, fxmlMain);
    }

    @FXML
    public void handleExecuteScriptButtonClicked(ActionEvent event) {
        CommandTypeClassifier.init();
        CommandArgClassifier.init();
        CommandVBoxClassifier.init();
        File file = FileSelector.getFileAddress(new Stage());
        if(file != null) {
            ResultRender resultRender = new ResultRender();
            resultRender.initRenderStage(resourceManager, event, tableView);
            FileReaderMode fileReaderMode = new FileReaderMode(resourceManager, resultRender);
            fileReaderMode.mainProcess(file.getAbsolutePath());
        }
    }
}
