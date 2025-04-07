package database;

import authorization_lib.JwtUtil;
import exceptions.database_exception.*;
import exceptions.log_exceptions.EnvNotExistsException;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.KeyTakenException;
import exceptions.user_exceptions.WrongKeyException;
import goods.Request;
import goods.Response;
import main_objects.StudyGroup;
import printer_options.RainbowPrinter;

import java.time.LocalDateTime;
import java.util.*;

public class CollectionManager {
    private final StudyGroupUploader studyGroupUploader;
    private final StudyGroupPusher studyGroupPusher;
    private final StudyGroupRemover studyGroupRemover;
    private final StudyGroupUpdater studyGroupUpdater;
    private final StudyGroupBasicUtil studyGroupBasicUtil;
    private final LinkedHashMap<String, StudyGroup> collection = new LinkedHashMap<>();
    private final String startTime = LocalDateTime.now().toString();

    public CollectionManager() throws LogException {
        studyGroupUploader = new StudyGroupUploader();
        studyGroupUploader.init();
        studyGroupPusher = new StudyGroupPusher();
        studyGroupPusher.init();
        studyGroupRemover = new StudyGroupRemover();
        studyGroupRemover.init();
        studyGroupUpdater = new StudyGroupUpdater();
        studyGroupUpdater.init();
        studyGroupBasicUtil = new StudyGroupBasicUtil();
        studyGroupBasicUtil.init();
    }

    private Response checkEmpty(Request request) {
        synchronized (collection) {
            if(collection.isEmpty()) {
                List<String> notice = new ArrayList<>();
                notice.add("The collection is empty");
                return new Response(notice, null, request.getRemoteAddress());
            } else {
                return null;
            }
        }
    }

    public Response info(Request request) {
        Response response = null;
        List<String> notice = new ArrayList<>();
        List<String> results = new ArrayList<>();
        results.add("Type of DS: LinkedHashMap");
        results.add("Moment of Initialization: " + startTime);
        synchronized(collection) {
            results.add("Number of elements: " + collection.size());
        }
        response = new Response(notice, results, request.getRemoteAddress());
        return response;
    }


    public Response insert(Request request) throws KeyTakenException, LogException, UnsuccesfulInsertException {
        String search_key = request.getArguments().get(0);
        StudyGroup studyGroup = request.getStudyGroup();
        studyGroup.setSearchKey(search_key);
        studyGroup.setCreator(JwtUtil.getUsername(request.getToken()));
        studyGroupBasicUtil.checkUsedSearchKey(search_key);
        studyGroupPusher.insertStudyGroup(studyGroup);
        synchronized (collection) {
            collection.put(search_key, request.getStudyGroup());
        }
        return new Response(new ArrayList<>(), null, request.getRemoteAddress());
    }

    public Response show(Request request) {
        List<String> notice = new ArrayList<>();
        List<String> results = new ArrayList<>();
        Response response = checkEmpty(request);
        if(response == null) {
            synchronized(collection) {
                collection.forEach((key, studyGroup) -> {
                    StringBuilder res = studyGroup.getTheGroup();
                    results.add(res.toString());
                });
            }
            return new Response(notice, results, request.getRemoteAddress());
        }
        return response;
    }

    public Response clear(Request request) throws LogException, UnsuccesfulDeletionException {
        Response response = checkEmpty(request);
        if(response == null) {
            String creator = JwtUtil.getUsername(request.getToken());
            studyGroupRemover.clearByCreator(creator);
            synchronized (collection) {
                collection.entrySet().removeIf(entry -> entry.getValue().getCreator().equals(creator));
            }
            return new Response(new ArrayList<>(), null, request.getRemoteAddress());
        }
        return response;
    }

    public Response remove_key(Request request) throws WrongKeyException, UnsuccesfulDeletionException, NotCreatorException, LogException {
        Response response = checkEmpty(request);
        if(response == null) {
            String searchKey = request.getArguments().get(0);
            String creator = JwtUtil.getUsername(request.getToken());
            studyGroupBasicUtil.checkKeyExists(searchKey);
            studyGroupBasicUtil.checkCreator(searchKey, creator);
            studyGroupRemover.removeKey(searchKey);
            synchronized (collection) {
                collection.remove(searchKey);
            }
            List<String> notice = new ArrayList<>();
            notice.add("The search key " + searchKey + " was deleted!");
            return new Response(notice, null, request.getRemoteAddress());
        }
        return response;
    }

    public Response remove_if_greater(Request request) throws LogException {
        Response response = checkEmpty(request);
        if(response == null) {
            Long criteria = request.getStudyGroup().getStudentsCount();
            String creator = JwtUtil.getUsername(request.getToken());
            long numOfRemoved = studyGroupRemover.removeIfGreater(creator, criteria);
            synchronized (collection) {
                collection.entrySet().removeIf(entry -> entry.getValue().getCreator().equals(creator) && entry.getValue().getStudentsCount() > criteria);
            }
            List<String> results = new ArrayList<>();
            results.add(numOfRemoved + " elements were removed from the collection");
            return new Response(new ArrayList<>(), results, request.getRemoteAddress());
        }
        return response;
    }

    public Response update(Request request) throws WrongKeyException, LogException, NotCreatorException, UnsuccesfulUpdateException {
        Response response = checkEmpty(request);
        if(response == null) {
            String searchKey = request.getArguments().get(0);
            String creator = JwtUtil.getUsername(request.getToken());
            StudyGroup studyGroup = request.getStudyGroup();
            studyGroup.setSearchKey(searchKey);
            List<String> notice = new ArrayList<>();
            studyGroupBasicUtil.checkKeyExists(searchKey);
            studyGroupBasicUtil.checkCreator(searchKey, creator);
            studyGroupUpdater.simpleUpdate(studyGroup);
            synchronized (collection) {
                collection.remove(searchKey);
                collection.put(searchKey, studyGroup);
                notice.add("The element with key " + searchKey + " was successfully updated");
            }
            return new Response(notice, null, request.getRemoteAddress());
        }
        return response;
    }

    public Response replace_if_lower(Request request) throws WrongKeyException,NotCreatorException, FailedConditionUpdateException, UnsuccesfulUpdateException, LogException {
        Response response = checkEmpty(request);
        if(response == null) {
            String searchKey = request.getArguments().get(0);
            String creator = JwtUtil.getUsername(request.getToken());
            StudyGroup studyGroup = request.getStudyGroup();
            studyGroup.setSearchKey(searchKey);
            List<String> notice = new ArrayList<>();
            studyGroupBasicUtil.checkKeyExists(searchKey);
            studyGroupBasicUtil.checkCreator(searchKey, creator);
            studyGroupUpdater.replaceIfLower(request.getStudyGroup());
            synchronized (collection) {
                if(collection.get(searchKey).compareTo(studyGroup) < 0 && collection.get(searchKey).getCreator().equals(creator)) {
                    collection.replace(searchKey, studyGroup);
                    notice.add("The element with key " + searchKey + " was replaced");
                } else {
                    notice.add("The element with key " + searchKey + " was not replaced");
                }
            }
            return new Response(notice, null, request.getRemoteAddress());
        }
        return response;
    }

    public Response print_ascending(Request request) {
        Response response = checkEmpty(request);
        if(response == null) {
            synchronized (collection) {
                List<String> result = new ArrayList<>();
                List<StudyGroup> elements = new ArrayList<>(collection.values().stream().toList());
                Collections.sort(elements);
                elements.forEach((studyGroup) -> {
                    String group = studyGroup.getTheGroup().toString();
                    result.add(group);
                });
                return new Response(new ArrayList<>(), result, request.getRemoteAddress());
            }
        }
        return response;
    }

    public Response print_descending(Request request) {
        Response response = checkEmpty(request);
        if(response == null) {
            synchronized (collection) {
                List<String> result = new ArrayList<>();
                List<StudyGroup> elements = new ArrayList<>(collection.values().stream().toList());
                elements.sort(Collections.reverseOrder());
                elements.forEach((studyGroup) -> {
                    String group = studyGroup.getTheGroup().toString();
                    result.add(group);
                });
                return new Response(new ArrayList<>(), result, request.getRemoteAddress());
            }
        }
        return response;
    }

    public Response print_field_descending_semester_enum(Request request) {
        Response response = checkEmpty(request);
        if(response == null) {
            synchronized (collection) {
                List<StudyGroup> temp_result = new ArrayList<>(collection.values().stream().toList());
                temp_result.sort(Comparator.comparing(StudyGroup::getSemesterEnum));
                Collections.reverse(temp_result);
                List<String> result = new ArrayList<>(temp_result.stream().map(studyGroup -> studyGroup.getTheGroup().toString()).toList());
                return new Response(new ArrayList<>(), result, request.getRemoteAddress());
            }
        }
        return response;
    }


    public void uploadData() throws LogException {
        List<StudyGroup> result = studyGroupUploader.loadStudyGroups();
        for (StudyGroup studyGroup : result) {
            collection.put(studyGroup.getSearchKey(), studyGroup);
        }
        RainbowPrinter.printInfo("Loaded " + collection.size() + " study groups");
    }

}
