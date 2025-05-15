package gui.utilities.pop_ups;


import javafx.event.ActionEvent;
import javafx.geometry.Point2D;

import javafx.scene.Node;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class DynamicPopUpUtil {

    public static void addBasicFuncs(Stage popupStage, ActionEvent e, int toleranceX, int toleranceY) {
        Node headerNode = (Node) (e.getSource());
        Point2D screenPos = headerNode.localToScreen(
                headerNode.getBoundsInLocal().getMaxX(),
                headerNode.getBoundsInLocal().getMaxY()
        );

        popupStage.setX(screenPos.getX() + toleranceX);
        popupStage.setY(screenPos.getY() + toleranceY);
        popupStage.showAndWait();
    }

    protected static Stage releasePopUp() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UTILITY);
        return popupStage;
    }
}
