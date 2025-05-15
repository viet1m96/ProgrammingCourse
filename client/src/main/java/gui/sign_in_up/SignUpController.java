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

import javafx.scene.control.*;

import javafx.scene.image.ImageView;

import javafx.scene.text.Text;
import network.Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SignUpController implements Localizable {

    @FXML private TextField usernameField;
    @FXML private Text criteriaUsername;
    @FXML private PasswordField passwordField;
    @FXML private Text lengthText;
    @FXML private Text upperCaseText;
    @FXML private Text lowerCaseText;
    @FXML private Text specialCharText;
    @FXML private Text digitText;
    @FXML private Button signUpSubmitButton;
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
        signUpSubmitButton.setDisable(true);
        BackButton.setDisable(false);
        BackButtonUtil.initImage(BackButtonImage);
        setUpButtons();
        setupUsernameValidation();
        setupPasswordValidation();
        updateSignUpButtonEnable();
    }

    @Override
    public void updateLocalization() {
        setUpButtons();
    }

    public void setUpButtons() {
        signUpSubmitButton.setText(resourceManager.getString(baseName, "signUp.button"));
        usernameField.setPromptText(resourceManager.getString(baseName, "username.text"));
        passwordField.setPromptText(resourceManager.getString(baseName, "password.text"));
        criteriaUsername.setText(resourceManager.getString(baseName, "criteriaNotEmpty.text"));
        lengthText.setText(resourceManager.getString(baseName, "minLength.text"));
        lowerCaseText.setText(resourceManager.getString(baseName, "lowerCaseCriteria.text"));
        upperCaseText.setText(resourceManager.getString(baseName, "upperCaseCriteria.text"));
        digitText.setText(resourceManager.getString(baseName, "digitCriteria.text"));
        specialCharText.setText(resourceManager.getString(baseName, "specialCharCriteria.text"));
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
            boolean meetsLength = newValue.length() >= 8;
            boolean meetsUpperCase = newValue.matches(".*[A-Z].*");
            boolean meetsLowerCase = newValue.matches(".*[a-z].*");
            boolean meetsDigits = newValue.matches(".*\\d.*");
            boolean meetsSpecialCharacters = newValue.matches(".*[!@#$%^&*].*");

            TextUpdater.updateTextColor(lengthText, meetsLength);
            TextUpdater.updateTextColor(upperCaseText, meetsUpperCase);
            TextUpdater.updateTextColor(lowerCaseText, meetsLowerCase);
            TextUpdater.updateTextColor(digitText, meetsDigits);
            TextUpdater.updateTextColor(specialCharText, meetsSpecialCharacters);

            validPassword.set(meetsLength && meetsUpperCase && meetsLowerCase && meetsDigits && meetsSpecialCharacters);

        });
    }


    private void updateSignUpButtonEnable() {
        signUpSubmitButton.disableProperty().bind(Bindings.createBooleanBinding(() -> !validPassword.get() || !validUsername.get(), validPassword, validUsername));
    }

    public void BackButtonClicked(ActionEvent actionEvent) {
        BackButtonUtil.BackButtonClicked(actionEvent, BackButton);

    }

    public void signUpButtonClicked(ActionEvent actionEvent) throws IOException {
        List<String> arguments = new ArrayList<>();
        arguments.add(usernameField.getText());
        arguments.add(SHA1Hashing.hashStringSHA1(passwordField.getText()));
        Request request = new Request("sign_up", arguments, null);
        try {
            Response response = Client.work(request);
            AlertUtil.showResultAlert(response);
            if(response.getSuccess()) {
                Client.setAccount(arguments.get(0), arguments.get(1), request.getToken());
                FXMLLoaderUlti.changeSceneWithoutHistory(actionEvent, "/gui/working_session/mainView.fxml");
            }
        } catch (NetworkException e) {
            AlertUtil.showErrorAlert("Network Error", e.toString());
        }
    }
}