package handler;

import java.util.Locale;
import java.util.ResourceBundle;

public class Translator {
    public static String getString(String baseName, String key, Locale locale) {
        return ResourceBundle.getBundle(baseName, locale).getString(key);
    }
}
