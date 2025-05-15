package gui.sign_in_up;

import authorization_lib.SHA1Hashing;
import exceptions.network_exception.NetworkException;
import goods.Request;
import goods.Response;
import gui.utilities.buttons.BackButtonUtil;
import gui.utilities.tools.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.image.ImageView;

import javafx.scene.text.Text;

import network.Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SignInController implements Localizable {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Text criteriaUsername;
    @FXML private Text criteriaPassword;
    @FXML private Button signInSubmitButton;
    @FXML private Button BackButton;
    @FXML private ImageView BackButtonImage;
    private ResourceManager resourceManager;
    private final String baseName = "/il8n/Starters";

    private final BooleanProperty validPassword = new SimpleBooleanProperty(false);
    private final BooleanProperty validUsername = new SimpleBooleanProperty(false);
    @FXML
    public void initialize() {
        resourceManager = ResourceManager.getInstance();
        resourceManager.registerController(this, baseName);
        signInSubmitButton.setDisable(true);
        BackButton.setDisable(false);
        BackButtonUtil.initImage(BackButtonImage);
        updateLocalization();
        setupUsernameValidation();
        setupPasswordValidation();
        updateSignUpButtonEnable();
    }

    @Override
    public void updateLocalization() {
        signInSubmitButton.setText(resourceManager.getString(baseName, "signIn.button"));
        criteriaUsername.setText(resourceManager.getString(baseName, "criteriaNotEmpty.text"));
        criteriaPassword.setText(resourceManager.getString(baseName, "criteriaNotEmpty.text"));
        usernameField.setPromptText(resourceManager.getString(baseName, "username.text"));
        passwordField.setPromptText(resourceManager.getString(baseName, "password.text"));

    }

    private void setupUsernameValidation() {
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean meetsLength = !newValue.isEmpty();
            TextUpdater.updateTextColor(criteriaUsername, meetsLength);
            validUsername.set(meetsLength);
        });
    }

    private void setupPasswordValidation() {
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean meetsLength = !newValue.isEmpty();
            TextUpdater.updateTextColor(criteriaPassword, meetsLength);
            validPassword.set(meetsLength);

        });
    }


    private void updateSignUpButtonEnable() {
        signInSubmitButton.disableProperty().bind(Bindings.createBooleanBinding(() -> !validPassword.get() || !validUsername.get(), validPassword, validUsername));
    }

    public void BackButtonClicked(ActionEvent actionEvent) {
        BackButtonUtil.BackButtonClicked(actionEvent, BackButton);
    }

    public void SignInButtonClicked(ActionEvent actionEvent) throws IOException {
        List<String> arguments = new ArrayList<>();
        arguments.add(usernameField.getText());
        arguments.add(SHA1Hashing.hashStringSHA1(passwordField.getText()));
        Request request = new Request("sign_in", arguments, null);
        arguments.forEach(System.out::println);
        try {
            Response response = Client.work(request);
            AlertUtil.showResultAlert(response);
            if(response.getSuccess()) {
                Client.setAccount(arguments.get(0), arguments.get(1), response.getResult().get(0));
                FXMLLoaderUlti.changeSceneWithoutHistory(actionEvent, "/gui/working_session/mainView.fxml");
            }
        } catch (NetworkException e) {
            AlertUtil.showErrorAlert("Network Error", e.toString());
        }

    }


}
