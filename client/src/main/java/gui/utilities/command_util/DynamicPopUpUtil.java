package gui.utilities.command_util;


import gui.utilities.tools.AlertUtil;
import gui.utilities.tools.FXMLLoaderUlti;
import gui.working_session.std_grp_controllers.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logging.LogUtil;

import java.io.IOException;


public class DynamicPopUpUtil {



    public static Stage releaseBasicPopUp() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UTILITY);
        return popupStage;
    }

    public static Stage releaseBasicPopUp(String fxmlPath, BaseController controller) {
        Stage popupStage = releaseBasicPopUp();
        try {
            FXMLLoader loader = new FXMLLoader(FXMLLoaderUlti.class.getResource(fxmlPath));
            loader.setController(controller);
            Parent root = loader.load();
            popupStage = releaseBasicPopUp();
            Scene scene = new Scene(root);
            popupStage.setScene(scene);
            return popupStage;
        } catch (IOException e) {
            LogUtil.logTrace(e);
            AlertUtil.showErrorAlert("Couldn't load FXML file", e.getMessage(), popupStage);
        }
        return null;
    }

    public static void givePositionPopUp(Stage popupStage, ActionEvent e, int toleranceX, int toleranceY) {
        Node headerNode = (Node) (e.getSource());
        Point2D screenPos = headerNode.localToScreen(
                headerNode.getBoundsInLocal().getMaxX(),
                headerNode.getBoundsInLocal().getMaxY()
        );

        popupStage.setX(screenPos.getX() + toleranceX);
        popupStage.setY(screenPos.getY() + toleranceY);
        popupStage.showAndWait();
    }

    public static void givePositionPopUp(Stage stage, Node node, double stageWidth, double stageHeight, int toleranceX, int toleranceY) {
        Bounds nodeBounds = node.localToScreen(node.getBoundsInLocal());

        Screen screen = Screen.getPrimary();
        for (Screen s : Screen.getScreens()) {
            if (s.getBounds().contains(nodeBounds.getCenterX(), nodeBounds.getCenterY())) {
                screen = s;
                break;
            }
        }

        double stageX = nodeBounds.getMinX();
        double stageY = nodeBounds.getMinY();

        Rectangle2D screenBounds = screen.getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();
        if (stageX + stageWidth > screenWidth) {
            stageX = screenWidth - stageWidth;
        }
        if (stageY + stageHeight > screenHeight) {
            stageY = screenHeight - stageHeight;
        }

        stage.setX(stageX + toleranceX);
        stage.setY(stageY + toleranceY);
        stage.show();
    }
}
