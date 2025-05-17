package file_processor.gui_file.results;

import goods.Response;
import gui.utilities.tools.ResourceManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class NoticeResultBoxer implements VboxOutput{

    @Override
    public VBox returnResultAsVbox(ResourceManager resourceManager, String command, Response response) {
        List<String> notice = response.getNotice();
        List<String> result = response.getResult();
        VBox mainVBox = new VBox(5);
        mainVBox.setAlignment(Pos.CENTER_LEFT);
        mainVBox.setPadding(new Insets(10));

        Label titleLabel = new Label(resourceManager.getString(baseCommands, command));
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        mainVBox.getChildren().add(titleLabel);
        addInfo(mainVBox, result);
        addInfo(mainVBox, notice);
        return mainVBox;
    }

    private void addInfo(VBox mainVBox, List<String> info) {
        if(info != null && !info.isEmpty()) {
            for (String line : info) {
                Label lineLabel = new Label(line);
                lineLabel.setWrapText(true);
                mainVBox.getChildren().add(lineLabel);
            }
        }
    }
}
