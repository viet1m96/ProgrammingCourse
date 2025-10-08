package gui.utilities.tools;

import java.util.*;
import javafx.beans.property.SimpleObjectProperty;


public class ResourceManager {
    private static ResourceManager instance;
    private static SimpleObjectProperty<Locale> currentLocale;
    private Map<String, ResourceBundle> resourceBundles = new HashMap<>();
    private Map<Localizable, String> controllerBaseNames = new HashMap<>();


    private ResourceManager() {
        currentLocale = new SimpleObjectProperty<>(Locale.ENGLISH);
        currentLocale.addListener((obs, oldVal, newVal) -> updateAllResourceBundles(newVal));
    }

    public static ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }

    private void updateAllResourceBundles(Locale locale) {
        for (String baseName : resourceBundles.keySet()) {
            resourceBundles.put(baseName, ResourceBundle.getBundle(baseName, locale));
        }
        notifyControllers();
    }

    public void updateResourceBundle(String baseName, Locale locale) {
        resourceBundles.put(baseName, ResourceBundle.getBundle(baseName, locale));
    }

    public String getString(String baseName, String key) {
        return resourceBundles.get(baseName).getString(key);
    }

    public void setLocale(Locale locale) {
        currentLocale.set(locale);
    }

    public static Locale getLocale() {
        return currentLocale.get();
    }

    public void registerController(Localizable controller, String baseName) {
        controllerBaseNames.put(controller, baseName);
        if (!resourceBundles.containsKey(baseName)) {
            updateResourceBundle(baseName, currentLocale.get());
        }
    }

    public void unregisterController(Localizable controller) {
        controllerBaseNames.remove(controller);
    }

    private void notifyControllers() {
        new HashMap<>(controllerBaseNames).forEach((controller, baseName) -> controller.updateLocalization());
    }

    public ResourceBundle getBundle(String baseName) {
        return resourceBundles.get(baseName);
    }
}