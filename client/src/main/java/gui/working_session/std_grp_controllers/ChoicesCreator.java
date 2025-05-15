package gui.working_session.std_grp_controllers;

import gui.utilities.command_util.DynamicPopUpUtil;
import gui.utilities.command_util.RemoveKeyUtil;
import gui.utilities.tools.ResourceManager;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main_objects.StdGroupUltimate;


public class ChoicesCreator {

    private final String baseCommands = "/il8n/commands";
    private final String fxmlStdGrp = "/gui/working_session/StdGrpPopUp.fxml";
    public ContextMenu createContextMenu(StdGroupUltimate groupUltimate, ResourceManager resourceManager, TableView<StdGroupUltimate> tableView) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem updateItem = updateItem(contextMenu, resourceManager, tableView, groupUltimate);
        MenuItem replaceItem = replaceItem(contextMenu, resourceManager, tableView, groupUltimate);
        MenuItem removeItem = removeItem(contextMenu, resourceManager, tableView, groupUltimate);
        MenuItem detailItem = detailItem(contextMenu, resourceManager, tableView, groupUltimate);
        contextMenu.getItems().addAll(updateItem, replaceItem, removeItem, detailItem);
        return contextMenu;
    }

    private MenuItem updateItem(ContextMenu menu, ResourceManager resourceManager, TableView<StdGroupUltimate> tableView, StdGroupUltimate groupUltimate) {
        MenuItem updateItem = new MenuItem(resourceManager.getString(baseCommands, "update"));
        updateItem.setOnAction(event -> {
            Stage stage = DynamicPopUpUtil.releaseBasicPopUp(fxmlStdGrp, new UpdateController(groupUltimate));
            DynamicPopUpUtil.givePositionPopUp(stage, tableView, 800, 600, 0, 0);
            menu.hide();
        });
        return updateItem;
    }

    private MenuItem replaceItem(ContextMenu menu, ResourceManager resourceManager, TableView<StdGroupUltimate> tableView, StdGroupUltimate groupUltimate) {
        MenuItem replaceItem = new MenuItem(resourceManager.getString(baseCommands, "replace_if_lower"));
        replaceItem.setOnAction(event -> {
            Stage stage = DynamicPopUpUtil.releaseBasicPopUp(fxmlStdGrp, new ReplaceController(groupUltimate));
            DynamicPopUpUtil.givePositionPopUp(stage, tableView, 800, 600, 0, 0);
            menu.hide();
        });
        return replaceItem;
    }

    private MenuItem removeItem(ContextMenu menu, ResourceManager resourceManager, TableView<StdGroupUltimate> tableView, StdGroupUltimate groupUltimate) {
        MenuItem removeItem = new MenuItem(resourceManager.getString(baseCommands, "remove_key"));
        removeItem.setOnAction(event -> {
            RemoveKeyUtil removeKeyUtil = new RemoveKeyUtil();
            removeKeyUtil.questionChoice(tableView, groupUltimate, resourceManager);
            menu.hide();
        });
        return removeItem;
    }

    private MenuItem detailItem(ContextMenu menu, ResourceManager resourceManager, TableView<StdGroupUltimate> tableView, StdGroupUltimate groupUltimate) {
        MenuItem detailItem = new MenuItem(resourceManager.getString(baseCommands, "details"));
        detailItem.setOnAction(event -> {
            Stage stage = DynamicPopUpUtil.releaseBasicPopUp(fxmlStdGrp, new SeeDetailsUtil(groupUltimate));
            DynamicPopUpUtil.givePositionPopUp(stage, tableView, 800, 600, 0, 0);
            menu.hide();
        });
        return detailItem;
    }
}
