package gui.utilities.pop_ups;

import gui.utilities.tools.AlertUtil;
import gui.utilities.tools.FXMLLoaderUlti;
import gui.working_session.std_grp_controllers.BaseController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BigPopUpUtil extends DynamicPopUpUtil {

    public static Stage releaseNewPopUp(String fxmlPath, BaseController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(FXMLLoaderUlti.class.getResource(fxmlPath));
            loader.setController(controller);
            Parent root = loader.load();
            Stage popupStage = releasePopUp();
            Scene scene = new Scene(root);
            popupStage.setScene(scene);
            return popupStage;
        } catch (IOException e) {
            AlertUtil.showErrorAlert("Couldn't load FXML file", e.getMessage());
        }
        return null;
    }

}
