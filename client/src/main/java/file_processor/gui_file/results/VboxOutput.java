package file_processor.gui_file.results;

import goods.Response;
import gui.utilities.tools.ResourceManager;
import javafx.scene.layout.VBox;

public interface VboxOutput {
    String baseCommands = "/il8n/commands";
    String baseTable = "/il8n/tableView";
    VBox returnResultAsVbox(ResourceManager resourceManager, String command, Response response);
}
