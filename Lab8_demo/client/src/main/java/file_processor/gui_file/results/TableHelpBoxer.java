package file_processor.gui_file.results;

import goods.Response;
import gui.utilities.command_util.DynamicHelpUtil;
import gui.utilities.tools.ResourceManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main_objects.Instruction;

import java.util.List;

public class TableHelpBoxer implements VboxOutput {

    @Override
    public VBox returnResultAsVbox(ResourceManager resourceManager, String command, Response response) {
        List<Instruction> instructions = response.getInstructions();
        TableView<Instruction> tableView = DynamicHelpUtil.initTable(resourceManager, instructions);
        VBox mainVBox = new VBox();
        mainVBox.setAlignment(Pos.TOP_LEFT);
        mainVBox.setPadding(new Insets(10));

        Label titleLabel = new Label(resourceManager.getString(baseTable, command));
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        mainVBox.getChildren().add(titleLabel);

        VBox.setVgrow(tableView, javafx.scene.layout.Priority.ALWAYS);

        mainVBox.getChildren().add(tableView);
        return mainVBox;
    }

}
