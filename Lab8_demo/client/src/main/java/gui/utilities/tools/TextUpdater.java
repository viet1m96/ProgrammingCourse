package gui.utilities.tools;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class TextUpdater {

    public static void updateTextColor(Text text, boolean isValid) {
        text.setFill(isValid ? Color.GREEN : Color.RED);
    }
}
