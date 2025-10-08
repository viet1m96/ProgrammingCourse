package file_processor.logic;


import gui.utilities.command_util.DynamicPopUpUtil;
import gui.utilities.tools.ResourceManager;
import gui.utilities.tools.TextUpdater;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class RecursionController {
    private final LinkedHashMap<String, Integer> cyclePath = new LinkedHashMap<>();

    private final String baseStarter = "/il8n/Starters";
    public LinkedHashMap<String, String> getNewTrace(LinkedHashMap<String, String> currentTrace, String currentFile, String nextFile) {
        List<String> cycle = getCyclePath(currentTrace, currentFile, nextFile);
        List<String> needRemovingVertices = new ArrayList<>();
        for (int i = 1; i < cycle.size() - 1; i++) {
            needRemovingVertices.add(cycle.get(i));
        }
        LinkedHashMap<String, String> newTrace = new LinkedHashMap<>();
        currentTrace.forEach((key, value) -> {
            if (!needRemovingVertices.contains(key)) {
                newTrace.put(key, value);
            }
        });
        return newTrace;
    }

    public boolean stopOrNot(LinkedHashMap<String, String> currentTrace, String currentFile, String nextFile) {
        List<String> cycle = getCyclePath(currentTrace, currentFile, nextFile);
        String trace = String.join("\n", cycle);
        if (cyclePath.get(trace) <= 0) {
            cyclePath.remove(trace);
            return true;
        } else {
            cyclePath.put(trace, cyclePath.get(trace) - 1);
        }
        return false;
    }

    private List<String> getCyclePath(LinkedHashMap<String, String> currentTrace, String currentFile, String nextFile) {
        List<String> trace = new ArrayList<>();
        String currentInTrace = currentFile;
        while (currentInTrace != null && !currentInTrace.equals(nextFile)) {
            trace.add(currentInTrace);
            currentInTrace = currentTrace.get(currentInTrace);
        }
        trace.add(currentInTrace);
        Collections.reverse(trace);
        trace.add(nextFile);

        return trace;
    }

    public boolean isRecursion(LinkedHashMap<String, String> currentTrace, String currentFile, String nextFile) {
        return currentTrace.containsKey(nextFile);
    }


    public boolean isFirstTimeDetected(LinkedHashMap<String, String> currentTrace, String currentFile, String nextFile) {
        List<String> cycle = getCyclePath(currentTrace, currentFile, nextFile);
        String trace = String.join("\n", cycle);
        return !cyclePath.containsKey(trace);
    }

    public Integer askAction(LinkedHashMap<String, String> currentTrace, String currentFile, String nextFile, ResourceManager resourceManager) {
        List<String> cycle = getCyclePath(currentTrace, currentFile, nextFile);
        String trace = String.join("\n", cycle);
        Stage stage = DynamicPopUpUtil.releaseBasicPopUp();
        stage.setOnCloseRequest(Event::consume);

        Label label = new Label(resourceManager.getString(baseStarter, "cycle.detected"));
        Label labelCycle = new Label(trace);
        labelCycle.setWrapText(true);

        TextField iterationField = new TextField();
        iterationField.setPromptText(resourceManager.getString(baseStarter, "iteration"));

        Text conditionLabel = new Text(resourceManager.getString(baseStarter, "iteration.condition"));

        Button continueButton = new Button(resourceManager.getString(baseStarter, "continue.button"));
        continueButton.setDefaultButton(true);
        continueButton.setDisable(true);

        BooleanProperty isValidInput = new SimpleBooleanProperty(false);

        iterationField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int iteration = Integer.parseInt(newValue);
                if (iteration >= 0 && iteration <= 100) {
                    isValidInput.set(true);
                } else {
                    isValidInput.set(false);
                }
            } catch (NumberFormatException e) {
                isValidInput.set(false);
            }
            TextUpdater.updateTextColor(conditionLabel, isValidInput.get());
        });

        continueButton.disableProperty().bind(isValidInput.not());
        continueButton.setOnAction(event -> {
            int iteration = Integer.parseInt(iterationField.getText());
            if(iteration > 0) cyclePath.put(trace, Integer.parseInt(iterationField.getText()) - 1);
            stage.close();
        });
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, labelCycle, iterationField, conditionLabel, continueButton);

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.showAndWait();
        if(Integer.parseInt(iterationField.getText()) == 0) return null;
        return Integer.parseInt(iterationField.getText());
    }


}
