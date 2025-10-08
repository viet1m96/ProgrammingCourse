package gui.starters;

import gui.utilities.tools.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import logging.LogUtil;
import network.Client;
import network.ClientTransporter;


import java.io.IOException;
import java.util.Objects;


public class MainApplication extends Application implements Localizable {

    @FXML
    private Label welcomeLabel;
    @FXML
    private Button continueButton;
    @FXML
    private ComboBox<String> languageComboBox;

    private ResourceManager resourceManager;
    private final String baseName = "/il8n/Starters";

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/starters/main.fxml"));
        loader.setResources(ResourceManager.getInstance().getBundle(baseName));
        Parent root = loader.load(Objects.requireNonNull(getClass().getResource("/gui/starters/Main.fxml")));

        Scene mainScene = new Scene(root);
        stage.setScene(mainScene);
        stage.show();

    }

    public void initialize() {
        resourceManager = ResourceManager.getInstance();
        resourceManager.registerController(this, baseName);
        LanguageBoxUtil.setUp(baseName, languageComboBox, resourceManager);
        setUpButtons();
    }

    public void setUpButtons() {
        welcomeLabel.setText(resourceManager.getString(baseName, "welcome.label"));
        continueButton.setText(resourceManager.getString(baseName, "continue.button"));
    }

    @Override
    public void updateLocalization() {
        setUpButtons();
        LanguageBoxUtil.updateLanguage(baseName, languageComboBox, resourceManager);
    }

    @FXML
    private void handleLanguageClicked(ActionEvent event) {
        LanguageBoxUtil.handleLanguageClicked(event, resourceManager, languageComboBox);
    }

    public void continueButtonClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoaderUlti.changeSceneWithHistory(actionEvent, "/gui/starters/SignInUpChoices.fxml");
    }

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            AlertUtil.showErrorAlert("Fatal exception in thread: " + t.getName(), e.toString(), (Stage)null);
            LogUtil.logTrace(e);
        });
        ClientTransporter transporter = new ClientTransporter();
        Client.setTransporter(transporter);
        launch();
    }
}
