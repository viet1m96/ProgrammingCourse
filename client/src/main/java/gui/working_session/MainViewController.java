package gui.working_session;

import exceptions.network_exception.NetworkException;
import goods.Request;
import goods.Response;
import gui.utilities.tools.AlertUtil;
import gui.utilities.tools.Localizable;
import gui.utilities.tools.ResourceManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import main_objects.StdGroupUltimate;
import main_objects.StudyGroup;
import network.Client;
import utilities.StudyGrpTransformer;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class MainViewController implements Localizable {
    @FXML private Tab TableViewTab;
    @FXML private Tab VisualizationViewTab;
    @FXML private TabPane tabPane;
    private ResourceManager resourceManager;
    private final String baseName = "/il8n/Starters";
    private TableViewController tableViewController;
    private VisualizationViewController visualizationViewController;
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public void initialize() throws IOException {
        resourceManager = ResourceManager.getInstance();
        resourceManager.registerController(this, baseName);
        FXMLLoader TableViewLoader = new FXMLLoader(getClass().getResource("/gui/working_session/tableView.fxml"));
        AnchorPane tableViewRoot = TableViewLoader.load();
        tableViewController = TableViewLoader.getController();
        TableViewTab.setContent(tableViewRoot);

        FXMLLoader VisualizationLoader = new FXMLLoader(getClass().getResource("/gui/working_session/visualizationView.fxml"));
        AnchorPane visualizationViewRoot = VisualizationLoader.load();
        VisualizationViewController visualizationViewController = VisualizationLoader.getController();
        VisualizationViewTab.setContent(visualizationViewRoot);
        setUpTabName();
        tabPane.getSelectionModel().select(TableViewTab);

        startBackgroundThread();
    }

    public void setUpTabName() {
        TableViewTab.setText(resourceManager.getString(baseName, "TableViewName"));
        VisualizationViewTab.setText(resourceManager.getString(baseName, "VisualizationViewName"));
    }

    @Override
    public void updateLocalization() {
        setUpTabName();
    }

    private void startBackgroundThread() {
        executor.scheduleAtFixedRate(() -> {
            try {
                if(Client.getToken() == null) {
                    shutdown();
                    return;
                }
                Request request = new Request("show", null, Client.getToken());
                Response response = Client.work(request);
                List<StudyGroup> studyGroups = response.getStudyGroups();
                ObservableList<StdGroupUltimate> newGroups = FXCollections.observableArrayList();
                ObservableList<StdGroupUltimate> newBackUpGroups = FXCollections.observableArrayList();
                for (StudyGroup studyGroup : studyGroups) {
                    StdGroupUltimate group = StudyGrpTransformer.transformToUltimate(studyGroup);
                    newGroups.add(group);
                    StdGroupUltimate group2 = new StdGroupUltimate(group);
                    newBackUpGroups.add(group2);
                }
                Platform.runLater(() -> {
                    tableViewController.setGroups(newGroups, newBackUpGroups);
                    tableViewController.applySort();
                    tableViewController.applyFilters();
                });
            } catch (NetworkException e) {
                AlertUtil.showErrorAlert("Network Error", "Network Broken");
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void shutdown() {
        if (!executor.isShutdown()) {
            executor.shutdown();
            new Thread(() -> {
                try {
                    executor.awaitTermination(3, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    System.err.println("Interrupted while waiting for executor to terminate: " + e.getMessage());
                } finally {
                    System.out.println("Background thread shut down.");
                }
            }).start();
        }
    }



}
