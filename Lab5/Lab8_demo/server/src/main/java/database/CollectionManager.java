package database;

import authorization_lib.JwtUtil;
import exceptions.user_exceptions.*;
import exceptions.database_exception.UnsuccesfulDeletionException;
import exceptions.database_exception.UnsuccesfulInsertException;
import exceptions.database_exception.UnsuccesfulUpdateException;
import exceptions.log_exceptions.LogException;
import goods.Request;
import goods.Response;
import handler.Translator;
import logging.LogUtil;
import main_objects.Coordinates;
import main_objects.StudyGroup;


import java.time.LocalDateTime;
import java.util.*;

public class CollectionManager {
    private final DatabaseManager databaseManager;
    private final LinkedHashMap<String, StudyGroup> collection = new LinkedHashMap<>();
    private final String startTime = LocalDateTime.now().toString();
    private final String baseNotices= "notices";

    public CollectionManager(DatabaseManager databaseManager) throws LogException {
        this.databaseManager = databaseManager;
    }

    private Response checkEmpty(Request request) {
        synchronized (collection) {
            if (collection.isEmpty()) {
                List<String> notice = new ArrayList<>();
                notice.add(Translator.getString(baseNotices, "empty.collection", request.getLocale()));
                return new Response(notice, null, request.getRemoteAddress(), true);
            } else {
                return null;
            }
        }
    }

    private boolean checkKey(Request request) {
        String key = request.getArguments().get(0);
        return collection.containsKey(key);
    }

    private boolean checkCreator(String creator, String searchKey) {
        String creatorFromCollection = collection.get(searchKey).getCreator();
        return creator.equals(creatorFromCollection);
    }

    private boolean checkCoordinate(StudyGroup studyGroup) {
        Coordinates coordinates = studyGroup.getCoordinates();
        String searchKey = studyGroup.getSearchKey();
        synchronized (collection) {
            for(StudyGroup element: collection.values()) {
                Coordinates coordinatesFromCollection = element.getCoordinates();
                if(coordinatesFromCollection.equals(coordinates) && !element.getSearchKey().equals(searchKey)) return false;
            }
        }
        return true;
    }

    public Response info(Request request) {
        Response response = null;
        List<String> notice = new ArrayList<>();
        List<String> results = new ArrayList<>();
        String type = Translator.getString(baseNotices, "type.ds", request.getLocale());
        String moment = Translator.getString(baseNotices, "moment.ds", request.getLocale()) + " " + startTime;
        String numOfElements= Translator.getString(baseNotices, "numOfElements.ds", request.getLocale());
        results.add(type);
        results.add(moment);
        synchronized (collection) {
            results.add(numOfElements + " " + collection.size());
        }
        response = new Response(notice, results, request.getRemoteAddress());
        return response;
    }


    public Response insert(Request request) throws KeyTakenException, NameTakenException, LogException, UnsuccesfulInsertException, UniqueCoordinateException {
        String search_key = request.getArguments().get(0);
        StudyGroup studyGroup = request.getStudyGroup();

        boolean checkKey = checkKey(request);
        if (checkKey) {
            throw new KeyTakenException();
        }
        studyGroup.setSearchKey(search_key);
        studyGroup.setCreator(JwtUtil.getUsername(request.getToken()));
        if(!checkCoordinate(studyGroup)) throw new UniqueCoordinateException();
        databaseManager.insertStudyGroup(studyGroup);
        synchronized (collection) {
            collection.put(search_key, request.getStudyGroup());
        }
        return new Response(new ArrayList<>(), null, request.getRemoteAddress(), true);
    }

    public Response show(Request request) {
        List<String> notice = new ArrayList<>();
        List<StudyGroup> studyGroups = new ArrayList<>();
        Response response = checkEmpty(request);
        if (response == null) {
            synchronized (collection) {
                collection.forEach((key, studyGroup) -> {
                    studyGroups.add(studyGroup);
                });
            }
            response = new Response(notice, null, request.getRemoteAddress());
            response.setStudyGroups(studyGroups);
            return response;
        }
        return response;
    }

    public Response clear(Request request) throws LogException, UnsuccesfulDeletionException {
        Response response = checkEmpty(request);
        if (response == null) {
            String creator = JwtUtil.getUsername(request.getToken());
            Integer rowDeleted = databaseManager.clearByCreator(creator);
            synchronized (collection) {
                collection.entrySet().removeIf(entry -> entry.getValue().getCreator().equals(creator));
            }
            List<String> notice = new ArrayList<>();
            notice.add(rowDeleted + " " + Translator.getString(baseNotices, "removed.element", request.getLocale()));
            return new Response(notice, null, request.getRemoteAddress(), true);
        }
        return response;
    }

    public Response remove_key(Request request) throws WrongKeyException, UnsuccesfulDeletionException, NotCreatorException, LogException {
        Response response = checkEmpty(request);
        if (response == null) {
            String searchKey = request.getArguments().get(0);
            if (!checkKey(request)) throw new WrongKeyException();
            String creatorFromReq = JwtUtil.getUsername(request.getToken());
            if (!checkCreator(creatorFromReq, searchKey)) throw new NotCreatorException();
            databaseManager.removeByKey(searchKey);
            synchronized (collection) {
                collection.remove(searchKey);
            }
            List<String> notice = new ArrayList<>();
            notice.add("The search key " + searchKey + " was deleted!");
            return new Response(notice, null, request.getRemoteAddress(), true);
        }
        return response;
    }

    public Response remove_greater(Request request) throws LogException {
        Response response = checkEmpty(request);
        if (response == null) {
            Long criteria = request.getStudyGroup().getStudentsCount();
            String creator = JwtUtil.getUsername(request.getToken());
            long numOfRemoved = databaseManager.removeIfGreater(creator, criteria);
            synchronized (collection) {
                collection.entrySet().removeIf(entry -> entry.getValue().getCreator().equals(creator) && entry.getValue().getStudentsCount() > criteria);
            }
            List<String> notices = new ArrayList<>();
            notices.add(numOfRemoved + " " + Translator.getString(baseNotices, "removed.element", request.getLocale()));
            return new Response(notices, null, request.getRemoteAddress(), true);
        }
        return response;
    }

    public Response update(Request request) throws WrongKeyException, LogException, NotCreatorException, UnsuccesfulUpdateException, UniqueCoordinateException {
        Response response = checkEmpty(request);
        if (response == null) {
            String searchKey = request.getArguments().get(0);
            StudyGroup studyGroup = request.getStudyGroup();
            String creatorFromReq = JwtUtil.getUsername(request.getToken());
            if (!checkKey(request)) throw new WrongKeyException();
            studyGroup.setSearchKey(searchKey);
            if (!checkCreator(creatorFromReq, searchKey)) throw new NotCreatorException();
            String creator = JwtUtil.getUsername(request.getToken());
            studyGroup.setCreator(creator);
            Integer id;
            Integer adminId;
            synchronized (collection) {
                id = collection.get(searchKey).getId();
                adminId = collection.get(searchKey).getGroupAdmin().getId();
            }
            studyGroup.setId(id);
            studyGroup.getGroupAdmin().setId(adminId);
            List<String> notice = new ArrayList<>();
            if(!checkCoordinate(studyGroup)) throw new UniqueCoordinateException();
            databaseManager.updateStudyGroup(studyGroup);
            synchronized (collection) {
                collection.remove(searchKey);
                collection.put(searchKey, studyGroup);
                notice.add(Translator.getString(baseNotices, "element.key", request.getLocale()) + " " + searchKey + " " + Translator.getString(baseNotices, "successful.update", request.getLocale()));
            }
            return new Response(notice, null, request.getRemoteAddress(), true);
        }
        return response;
    }

    public Response replace_if_lower(Request request) throws WrongKeyException, NotCreatorException, LogException, UnsuccesfulInsertException, UnsuccesfulDeletionException, UniqueCoordinateException {
        Response response = checkEmpty(request);
        if (response == null) {
            String searchKey = request.getArguments().get(0);
            if (!checkKey(request)) throw new WrongKeyException();
            StudyGroup studyGroup = request.getStudyGroup();
            studyGroup.setSearchKey(searchKey);
            String creatorFromReq = JwtUtil.getUsername(request.getToken());
            if (!checkCreator(creatorFromReq, searchKey)) throw new NotCreatorException();
            studyGroup.setCreator(creatorFromReq);
            List<String> notice = new ArrayList<>();
            boolean replace = false;
            synchronized (collection) {
                Long studentsCount = collection.get(searchKey).getStudentsCount();
                if (studentsCount < studyGroup.getStudentsCount()) replace = true;
            }
            if (replace) {
                if(!checkCoordinate(studyGroup)) throw new UniqueCoordinateException();
                databaseManager.replaceIfLower(studyGroup);
                synchronized (collection) {
                    if (collection.get(searchKey).compareTo(studyGroup) < 0 && collection.get(searchKey).getCreator().equals(creatorFromReq)) {
                        collection.replace(searchKey, studyGroup);
                        notice.add(Translator.getString(baseNotices, "element.key", request.getLocale()) + " " + searchKey + " " + Translator.getString(baseNotices, "successful.replace", request.getLocale()));
                    } else {
                        notice.add(Translator.getString(baseNotices, "element.key", request.getLocale()) + " " + searchKey + " " + Translator.getString(baseNotices, "unsuccessful.replace", request.getLocale()));
                    }
                }
            }
            return new Response(notice, null, request.getRemoteAddress(), true);
        }
        return response;
    }


    public Response print_ascending(Request request) {
        Response response = checkEmpty(request);
        if (response == null) {
            synchronized (collection) {
                List<StudyGroup> elements = new ArrayList<>(collection.values().stream().toList());
                Collections.sort(elements);
                List<StudyGroup> result = new ArrayList<>(elements);
                response = new Response(new ArrayList<>(), null, request.getRemoteAddress(), true);
                response.setStudyGroups(result);
                return response;
            }
        }
        return response;
    }

    public Response print_descending(Request request) {
        Response response = checkEmpty(request);
        if (response == null) {
            synchronized (collection) {
                List<StudyGroup> elements = new ArrayList<>(collection.values().stream().toList());
                elements.sort(Collections.reverseOrder());
                List<StudyGroup> result = new ArrayList<>(elements);
                response = new Response(new ArrayList<>(), null, request.getRemoteAddress(), true);
                response.setStudyGroups(result);
                return response;
            }
        }
        return response;
    }

    public Response print_field_descending_semester_enum(Request request) {
        Response response = checkEmpty(request);
        if (response == null) {
            synchronized (collection) {
                List<StudyGroup> temp_result = new ArrayList<>(collection.values().stream().toList());
                temp_result.sort(Comparator.comparing(StudyGroup::getSemesterEnum));
                Collections.reverse(temp_result);
                List<StudyGroup> result = new ArrayList<>(temp_result);
                response = new Response(new ArrayList<>(), null, request.getRemoteAddress(), true);
                response.setStudyGroups(result);
                return response;
            }
        }
        return response;
    }


    public void uploadData() throws LogException {
        try {
            List<StudyGroup> result = databaseManager.uploadData();
            for (StudyGroup studyGroup : result) {
                collection.put(studyGroup.getSearchKey(), studyGroup);
            }
            System.out.println("Loaded " + collection.size() + " study groups");
        } catch (Exception e) {
            LogUtil.logTrace(e);
            throw new LogException("Data upload failed");
        }
    }




}
