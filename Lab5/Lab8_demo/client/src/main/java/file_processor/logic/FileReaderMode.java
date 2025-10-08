package file_processor.logic;

import enums.NeedInput;
import exceptions.network_exception.NetworkException;
import exceptions.user_exceptions.InputFormatException;
import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongCommandException;
import file_processor.gui_file.ResultRender;
import file_processor.gui_file.results.VboxOutput;
import goods.Request;
import goods.Response;
import gui.utilities.tools.AlertUtil;
import gui.utilities.tools.ResourceManager;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import main_objects.StudyGroup;
import network.Client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class FileReaderMode {

    private RecursionController recursionController;
    private ResultRender resultRender;
    private ResourceManager resourceManager;
    private final String baseStarters = "/il8n/Starters";
    public FileReaderMode(ResourceManager resourceManager, ResultRender resultRender) {
        this.resourceManager = resourceManager;
        this.resultRender = resultRender;
        recursionController = new RecursionController();
    }


    private void processFile(LinkedHashMap<String, String> currentTrace, String currentFile) throws IOException, UserException, NetworkException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(currentFile)));
        String inp;
        resultRender.addResultVBox(returnInfoVbox(resourceManager.getString(baseStarters, "current.file") + currentFile));
        while ((inp = getNextUserCommand(br)) != null) {
            String newCommand = inp.split(" ")[0];
            String newArgs;
            if(inp.split(" ").length > 1) {
                newArgs = inp.split(" ")[1];
            } else {
                newArgs = null;
            }
            List<String> arguments =  new ArrayList<>();
            arguments.add(newArgs);
            NeedInput needInput = CommandTypeClassifier.getNeedInput(newCommand);
            if(newCommand.equals("history")) {

                continue;
            }
            if(newCommand.equals("exit")) {
                System.exit(0);
                continue;
            }
            switch (needInput) {
                case NO_NEED_INPUT -> {
                    Request request = new Request(newCommand, arguments, Client.getToken());
                    Response response = Client.work(request);
                    VboxOutput vboxOutput = CommandVBoxClassifier.getVBox(newCommand);
                    VBox result = vboxOutput.returnResultAsVbox(resourceManager, newCommand, response);
                    resultRender.addResultVBox(result);
                }
                case NEED_INPUT -> {
                    if(!newCommand.equals("execute_script")) {
                        StudyGroup studyGroup = StdGroupBuilder.build(br);
                        Request request = new Request(newCommand, arguments, studyGroup, Client.getToken());
                        Response response = Client.work(request);
                        VboxOutput vboxOutput = CommandVBoxClassifier.getVBox(newCommand);
                        VBox result = vboxOutput.returnResultAsVbox(resourceManager, newCommand, response);
                        resultRender.addResultVBox(result);
                    } else {
                        if(recursionController.isRecursion(currentTrace, currentFile, newArgs)) {
                            if(recursionController.isFirstTimeDetected(currentTrace, currentFile, newArgs)) {
                                Integer times = recursionController.askAction(currentTrace, currentFile, newArgs, resourceManager);
                                if (times != null) {
                                    LinkedHashMap<String, String> newTrace = recursionController.getNewTrace(currentTrace, currentFile, newArgs);
                                    processFile(newTrace, newArgs);
                                }
                            } else {
                                if (!recursionController.stopOrNot(currentTrace, currentFile, newArgs)) {
                                    LinkedHashMap<String, String> newTrace = recursionController.getNewTrace(currentTrace, currentFile, newArgs);
                                    processFile(newTrace, newArgs);
                                }
                            }
                        } else {
                            currentTrace.put(newArgs, currentFile);
                            processFile(currentTrace, newArgs);
                        }
                    }
                }
            }


        }

    }

    private String getNextUserCommand(BufferedReader br) throws IOException, WrongCommandException, InputFormatException {
        String inp = br.readLine();
        if(inp == null || inp.isEmpty()) return null;
        inp = inp.trim();
        if(inp.split(" ").length > 2) throw new WrongCommandException();
        String command = inp.split(" ")[0];
        String arg;
        if(inp.split(" ").length < 2) {
            arg = null;
        } else {
            arg = inp.split(" ")[1];
        }
        System.out.println(command + " " + arg);
        if(!CommandArgClassifier.checkFormat(command, arg)) throw new InputFormatException();
        return inp;
    }


    public void mainProcess(String filePath) {
        LinkedHashMap<String, String> currentTrace = new LinkedHashMap<>();
        try {
            processFile(currentTrace, filePath);
        } catch (IOException | UserException | NetworkException e) {
            String content;
            if(resourceManager.getString(baseStarters, e.toString()) != null) {
                content = resourceManager.getString(baseStarters, e.toString());
            } else {
                content = e.toString();
            }
            resultRender.addResultVBox(returnInfoVbox(content));
        }
    }

    private VBox returnInfoVbox(String content) {
        VBox vbox = new VBox();
        Label label = new Label();
        label.setText(content);
        vbox.setAlignment(Pos.CENTER);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        vbox.getChildren().add(label);
        return vbox;
    }



}
