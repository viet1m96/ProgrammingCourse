package gui.starters;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import gui.utilities.buttons.BackButtonUtil;
import gui.utilities.tools.FXMLLoaderUlti;
import gui.utilities.tools.Localizable;
import gui.utilities.tools.ResourceManager;

import java.io.IOException;


public class ChoicesSteps implements Localizable {
    @FXML private Button SignInButton;
    @FXML private Button SignUpButton;
    @FXML private Label welcomeLabel;
    @FXML  private Button BackButton;
    @FXML  private ImageView BackButtonImage;

    private ResourceManager resourceManager;
    private final String baseName = "/il8n/Starters";


    @FXML
    public void initialize() {
        BackButton.setDisable(false);
        BackButtonUtil.initImage(BackButtonImage);

        resourceManager = ResourceManager.getInstance();
        resourceManager.registerController(this, baseName);
        updateLocalization();
    }

    @Override
    public void updateLocalization() {
        welcomeLabel.setText(resourceManager.getString(baseName, "welcome.label"));
        SignInButton.setText(resourceManager.getString(baseName, "signIn.button"));
        SignUpButton.setText(resourceManager.getString(baseName, "signUp.button"));
    }


    public void SignInButtonClicked(ActionEvent event) throws IOException {
        FXMLLoaderUlti.changeSceneWithHistory(event, "/gui/sign_in_up/SignIn.fxml");
    }

    public void SignUpButtonClicked(ActionEvent event) throws IOException {
        FXMLLoaderUlti.changeSceneWithHistory(event, "/gui/sign_in_up/SignUp.fxml");
    }

    public void BackButtonClicked(ActionEvent event)  {
        BackButtonUtil.BackButtonClicked(event, BackButton);
    }
}
