package database;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.KeyTakenException;
import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongKeyException;
import logging.LogUtil;
import goods.Request;
import goods.Response;
import handler.working_with_file.CSVReader;
import handler.working_with_file.CSVWriter;
import main_objects.StudyGroup;
import printer_options.RainbowPrinter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CollectionManager {

    private final LinkedHashMap<String, StudyGroup> collection = new LinkedHashMap<>();
    private final String startTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy"));
    private final String fileName;

    public CollectionManager(String fileName) {
        this.fileName = fileName;
    }

    public Response info() {
        Response response = null;
        List<String> notice = new ArrayList<>();
        List<String> results = new ArrayList<>();
        results.add("Type of DS: LinkedHashMap");
        results.add("Moment of Initialization: " + startTime);
        results.add("Number of elements: " + collection.size());
        response = new Response(notice, results);
        return response;
    }

    public Response clear() {
        Response response = null;
        collection.clear();
        List<String> notice = new ArrayList<>();
        response = new Response(notice,null);
        return response;
    }

    public Response insert(Request request) throws KeyTakenException {
        Response response = null;
        List<String> notice = new ArrayList<>();
        String key = request.getArgument();
        if(collection.containsKey(key)) {
            throw new KeyTakenException();
        } else {
            StudyGroup studyGroup = request.getStudyGroup();
            studyGroup.setId(Integer.parseInt(key));
            collection.put(key, studyGroup);
        }
        response = new Response(notice, null);
        return response;
    }

    public Response show() {
        Response response = null;
        List<String> notice = new ArrayList<>();
        if(collection.isEmpty()) {
            notice.add("The collection is empty");
            response = new Response(notice,null);
        } else {
            List<String> results = new ArrayList<>();
            collection.forEach((key, studyGroup) -> {
                StringBuilder res = studyGroup.getTheGroup();
                results.add(res.toString());
            });
            response = new Response(notice, results);
        }
        return response;
    }

    public Response update(Request request) throws WrongKeyException {
        Response response = null;
        List<String> notice = new ArrayList<>();
        String key = request.getArgument();
        if(!collection.containsKey(key)) {
            throw new WrongKeyException();
        } else {
            StudyGroup studyGroup = request.getStudyGroup();
            studyGroup.setId(Integer.parseInt(key));
            collection.remove(key);
            collection.put(key, studyGroup);
            notice.add("The element with key " + key + " was successfully updated");
        }
        response = new Response(notice, null);
        return response;
    }

    public Response remove_key(Request request) throws WrongKeyException {
        Response response = null;
        List<String> notice = new ArrayList<>();
        String key = request.getArgument();
        if(!collection.containsKey(key)) {
            throw new WrongKeyException();
        } else {
            collection.remove(key);
            notice.add("The element with key " + key + " was successfully removed");
        }
        response = new Response(notice, null);
        return response;
    }

    public Response replace_if_lower(Request request) throws WrongKeyException {
        Response response = null;
        List<String> notice = new ArrayList<>();
        String key = request.getArgument();
        if(!collection.containsKey(key)) {
            throw new WrongKeyException();
        } else {
            StudyGroup studyGroup = request.getStudyGroup();
            if(collection.get(key).compareTo(studyGroup) < 0) {
                collection.replace(key, studyGroup);
                notice.add("The element with key " + key + " was replaced");
            } else {
                notice.add("The element with key " + key + " was not replaced");
            }
        }
        response = new Response(notice, null);
        return response;
    }

    public Response remove_if_greater(Request request) {
        Response response = null;
        List<String> notice = new ArrayList<>();
        StudyGroup studyGroup = request.getStudyGroup();
        if(collection.isEmpty()) {
            notice.add("The collection is empty");
        } else {
            collection.forEach((key, group) -> {
                if(group.compareTo(studyGroup) < 0) {
                    collection.remove(key);
                    notice.add("The element with key " + key + " was removed");
                }
            });
        }
        response = new Response(notice, null);
        return response;
    }

    public Response print_ascending() {
        Response response = null;
        List<String> notice = new ArrayList<>();
        if(collection.isEmpty()) {
            notice.add("The collection is empty");
            response = new Response(notice,null);
        } else {
            List<String> result = new ArrayList<>();
            List<StudyGroup> elements = new ArrayList<>(collection.values().stream().toList());
            Collections.sort(elements);
            elements.forEach((studyGroup) -> {
                String group = studyGroup.getTheGroup().toString();
                result.add(group);
            });
            response = new Response(notice, result);
        }
        return response;
    }

    public Response print_descending() {
        Response response = null;
        List<String> notice = new ArrayList<>();
        if(collection.isEmpty()) {
            notice.add("The collection is empty");
            response = new Response(notice,null);
        } else {
            List<String> result = new ArrayList<>();
            List<StudyGroup> elements = new ArrayList<>(collection.values().stream().toList());
            elements.sort(Collections.reverseOrder());
            elements.forEach((studyGroup) -> {
                String group = studyGroup.getTheGroup().toString();
                result.add(group);
            });
            response = new Response(notice, result);
        }
        return response;
    }

    public Response print_field_descending_semester_enum() {
        Response response = null;
        List<StudyGroup> temp_result = new ArrayList<>(collection.values().stream().toList());
        temp_result.sort(Comparator.comparing(StudyGroup::getSemesterEnum));
        Collections.reverse(temp_result);
        List<String> result = new ArrayList<>(temp_result.stream().map(studyGroup -> studyGroup.getTheGroup().toString()).toList());
        response = new Response(new ArrayList<>(), result);
        return response;
    }

    public Response save() throws LogException {
        try {
            CSVWriter writer = new CSVWriter(fileName);
            for (String key : collection.keySet()) {
                writer.writeLines(collection.get(key).getAllFields());
            }
        } catch (IOException e) {
            RainbowPrinter.printError("There was an error during saving data.");
            LogUtil.logTrace(e);
            throw new LogException();
        }
        return null;
    }

    public Response uploadData() throws LogException {
        LogUtil.logInfo("The data is being uploaded...");
        try {
            CSVReader reader = new CSVReader();
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String str;
            int counter = 1;
            while ((str = br.readLine()) != null && !str.isEmpty()) {
                try {
                    StudyGroup studyGroup = reader.loadObj(str);
                    String key = studyGroup.getId().toString();
                    if (collection.containsKey(key)) {
                        throw new KeyTakenException();
                    } else {
                        collection.put(key, studyGroup);
                    }
                } catch (UserException e) {
                    RainbowPrinter.printCondition("-----");
                    RainbowPrinter.printError(e.toString());
                    LogUtil.logTrace(e);
                    RainbowPrinter.printError("Can not load the information of the group on the" + " line " + String.valueOf(counter));
                    LogUtil.logInfo("Can not load the information of the group on the" + " line " + String.valueOf(counter));
                    RainbowPrinter.printCondition("-----");
                }
                counter++;

            }
        } catch (IOException e) {
            RainbowPrinter.printError("There was an error during uploading data.");
            throw new LogException();
        }
        RainbowPrinter.printInfo("Loaded successfully " + collection.size() + " groups into the file.");
        return null;
    }

}
