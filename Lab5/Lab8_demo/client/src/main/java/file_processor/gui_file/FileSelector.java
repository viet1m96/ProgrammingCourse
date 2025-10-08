package file_processor.gui_file;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileSelector {
    public FileSelector() {}

    public static File getFileAddress(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt", "*.text", "*.log", "*.csv")
        );
        return fileChooser.showOpenDialog(stage);
    }
}
