package gui.utilities.pop_ups;

import gui.utilities.tools.ResourceManager;
import gui.utilities.buttons.CancelButtonUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main_objects.StdGroupUltimate;

public class DynamicFilterUtil extends DynamicPopUpUtil {
    public void showFilterPopUp(ActionEvent e
            , ResourceManager resourceManager
            , TableColumn<StdGroupUltimate, ?> col
            , FilterUtil filterUtil
            , ObservableList<StdGroupUltimate> groups) {

        Stage popupStage = releasePopUp();
        Scene popupScene = initScene(e, resourceManager, col, filterUtil, groups);
        String columnName = (String) col.getUserData();
        popupStage.setTitle(resourceManager.getString("/il8n/tableView", columnName));
        popupStage.setScene(popupScene);
        addBasicFuncs(popupStage, e, 0, 0);
    }

    private Scene initScene(ActionEvent e
            , ResourceManager resourceManager
            , TableColumn<StdGroupUltimate, ?> col
            , FilterUtil filterUtil
            , ObservableList<StdGroupUltimate> groups) {
        TextField filterField = new TextField();
        Button applyButton = new Button();
        Button cancelButton = CancelButtonUtil.getCancelButton(resourceManager);
        applyButton.setText(resourceManager.getString("/il8n/insertPopUp", "apply.button"));
        filterField.setPromptText(resourceManager.getString("/il8n/insertPopUp", "filterCriteria.prompt"));
        VBox popUpContent = new VBox(5, filterField);
        popUpContent.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-color: gray;");
        popUpContent.setAlignment(Pos.CENTER_LEFT);
        HBox end = new HBox();
        end.setAlignment(Pos.CENTER);
        end.setSpacing(10);
        end.getChildren().add(applyButton);
        end.getChildren().add(cancelButton);
        popUpContent.getChildren().add(end);
        String columnName = (String) col.getUserData();
        applyButton.setOnAction(event -> {
            String filterValue = filterField.getText();
            if (filterValue != null && !filterValue.isEmpty()) {
                filterUtil.addFilter(columnName, filterValue);
                filterUtil.applyCurFilters(groups);
            }
            Stage stage = (Stage) applyButton.getScene().getWindow();
            stage.close();
        });
        return new Scene(popUpContent);
    }
}
