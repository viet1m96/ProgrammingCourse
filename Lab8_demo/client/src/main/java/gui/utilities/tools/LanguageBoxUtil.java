package gui.utilities.tools;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;

import java.util.Locale;

public class LanguageBoxUtil {

    public static void setUp(String baseName, ComboBox<String> languageComboBox, ResourceManager resourceManager) {
        languageComboBox.getItems().addAll(
                resourceManager.getString(baseName, "engChoice.text"),
                resourceManager.getString(baseName, "engAUChoice.text"),
                resourceManager.getString(baseName, "rusChoice.text"),
                resourceManager.getString(baseName, "dutChoice.text"),
                resourceManager.getString(baseName, "sweChoice.text")
        );
    }

    public static void updateLanguage(String baseName, ComboBox<String> languageComboBox, ResourceManager resourceManager) {
        languageComboBox.getItems().set(0, resourceManager.getString(baseName, "engChoice.text"));
        languageComboBox.getItems().set(1, resourceManager.getString(baseName, "engAUChoice.text"));
        languageComboBox.getItems().set(2, resourceManager.getString(baseName, "rusChoice.text"));
        languageComboBox.getItems().set(3, resourceManager.getString(baseName, "dutChoice.text"));
        languageComboBox.getItems().set(4, resourceManager.getString(baseName, "sweChoice.text"));
    }

    public static void handleLanguageClicked(ActionEvent actionEvent, ResourceManager resourceManager, ComboBox<String> languageComboBox) {
        String selection = languageComboBox.getSelectionModel().getSelectedItem();
        Locale locale = Locale.ENGLISH;
        if (selection.contains("(nl)")) locale = new Locale("nl");
        else if (selection.contains("(ru)")) locale = new Locale("ru");
        else if (selection.contains("(sv)")) locale = new Locale("sv");
        else if (selection.contains("(au)")) locale = new Locale("en", "AU");
        resourceManager.setLocale(locale);
    }
}
