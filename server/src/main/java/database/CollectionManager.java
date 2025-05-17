package database;

import authorization_lib.JwtUtil;
import exceptions.user_exceptions.NotCreatorException;
import exceptions.database_exception.UnsuccesfulDeletionException;
import exceptions.database_exception.UnsuccesfulInsertException;
import exceptions.database_exception.UnsuccesfulUpdateException;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.KeyTakenException;
import exceptions.user_exceptions.NameTakenException;
import exceptions.user_exceptions.WrongKeyException;
import goods.Request;
import goods.Response;
import handler.Translator;
import logging.LogUtil;
import main_objects.StudyGroup;


import java.time.LocalDateTime;
import java.util.*;

/**
 * The `CollectionManager` class manages the collection of `StudyGroup` objects.
 * It provides methods for interacting with the collection, such as adding, removing, updating, and displaying elements.
 * It also handles data persistence using the `DatabaseManager`.
 */
public class CollectionManager {
    private final DatabaseManager databaseManager;
    private final LinkedHashMap<String, StudyGroup> collection = new LinkedHashMap<>();
    private final String startTime = LocalDateTime.now().toString();
    private final String baseNotices= "notices";
    /**
     * Constructs a `CollectionManager` object.
     *
     * @param databaseManager The `DatabaseManager` used to interact with the database.
     * @throws LogException If there is an error during the initialization process.
     */
    public CollectionManager(DatabaseManager databaseManager) throws LogException {
        this.databaseManager = databaseManager;
    }

    /**
     * Checks if the collection is empty.
     *
     * @param request The request object.
     * @return A `Response` object indicating that the collection is empty, or `null` if it is not empty.
     */
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

    /**
     * Checks if the collection contains a key.
     *
     * @param request The request object containing the key to check.
     * @return `true` if the collection contains the key; `false` otherwise.
     */
    private boolean checkKey(Request request) {
        String key = request.getArguments().get(0);
        return collection.containsKey(key);
    }

    /**
     * Checks if a creator matches the creator of a `StudyGroup` in the collection.
     *
     * @param creator   The creator to check.
     * @param searchKey The key of the `StudyGroup` to check.
     * @return `true` if the creator matches; `false` otherwise.
     */
    private boolean checkCreator(String creator, String searchKey) {
        String creatorFromCollection = collection.get(searchKey).getCreator();
        return creator.equals(creatorFromCollection);
    }

    /**
     * Provides information about the collection.
     *
     * @param request The request object.
     * @return A `Response` object containing information about the collection, such as its type, initialization date, and number of elements.
     */
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


    /**
     * Inserts a new `StudyGroup` into the collection.
     *
     * @param request The request object containing the `StudyGroup` to insert.
     * @return A `Response` object indicating the success of the insertion.
     * @throws KeyTakenException          If the key is already taken.
     * @throws NameTakenException         If there is a name conflict.
     * @throws LogException               If there is an error during the insertion process.
     * @throws UnsuccesfulInsertException If the insertion into the database fails.
     */
    public Response insert(Request request) throws KeyTakenException, NameTakenException, LogException, UnsuccesfulInsertException {
        String search_key = request.getArguments().get(0);
        StudyGroup studyGroup = request.getStudyGroup();

        boolean checkKey = checkKey(request);
        if (checkKey) {
            throw new KeyTakenException();
        }
        studyGroup.setSearchKey(search_key);
        studyGroup.setCreator(JwtUtil.getUsername(request.getToken()));
        databaseManager.insertStudyGroup(studyGroup);
        synchronized (collection) {
            collection.put(search_key, request.getStudyGroup());
        }
        return new Response(new ArrayList<>(), null, request.getRemoteAddress(), true);
    }

    /**
     * Displays the elements of the collection.
     *
     * @param request The request object.
     * @return A `Response` object containing the elements of the collection.
     */
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

    /**
     * Clears the collection.
     *
     * @param request The request object.
     * @return A `Response` object indicating the success of the clearing operation.
     * @throws LogException                 If there is an error during the clearing process.
     * @throws UnsuccesfulDeletionException If the deletion from the database fails.
     */
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

    /**
     * Removes an element from the collection by its key.
     *
     * @param request The request object containing the key of the element to remove.
     * @return A `Response` object indicating the success of the removal operation.
     * @throws WrongKeyException            If the key does not exist.
     * @throws UnsuccesfulDeletionException If the deletion from the database fails.
     * @throws NotCreatorException          If the user is not the creator of the element.
     * @throws LogException                 If there is an error during the removal process.
     */
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

    /**
     * Removes elements from the collection if they are greater than a given value.
     *
     * @param request The request object containing the value to compare against.
     * @return A `Response` object indicating the number of elements removed.
     * @throws LogException If there is an error during the removal process.
     */
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

    /**
     * Updates an element in the collection.
     *
     * @param request The request object containing the updated element.
     * @return A `Response` object indicating the success of the update operation.
     * @throws WrongKeyException          If the key does not exist.
     * @throws LogException               If there is an error during the update process.
     * @throws NotCreatorException        If the user is not the creator of the element.
     * @throws UnsuccesfulUpdateException If the update in the database fails.
     */
    public Response update(Request request) throws WrongKeyException, LogException, NotCreatorException, UnsuccesfulUpdateException {
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
            databaseManager.updateStudyGroup(studyGroup);
            synchronized (collection) {
                collection.remove(searchKey);
                collection.put(searchKey, studyGroup);
                notice.add("The element with key " + searchKey + " was successfully updated");
            }
            return new Response(notice, null, request.getRemoteAddress(), true);
        }
        return response;
    }

    /**
     * Replaces an element in the collection if it is lower than a given value.
     *
     * @param request The request object containing the element to replace with.
     * @return A `Response` object indicating the success of the replacement operation.
     * @throws WrongKeyException            If the key does not exist.
     * @throws NotCreatorException          If the user is not the creator of the element.
     * @throws LogException                 If there is an error during the replacement process.
     * @throws UnsuccesfulInsertException   If the insertion into the database fails.
     * @throws UnsuccesfulDeletionException If the deletion from the database fails.
     */
    public Response replace_if_lower(Request request) throws WrongKeyException, NotCreatorException, LogException, UnsuccesfulInsertException, UnsuccesfulDeletionException {
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
                databaseManager.replaceIfLower(studyGroup);
                synchronized (collection) {
                    if (collection.get(searchKey).compareTo(studyGroup) < 0 && collection.get(searchKey).getCreator().equals(creatorFromReq)) {
                        collection.replace(searchKey, studyGroup);
                        notice.add("The element with key " + searchKey + " was replaced");
                    } else {
                        notice.add("The element with key " + searchKey + " was not replaced");
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

    /**
     * Displays the elements of the collection in descending order.
     *
     * @param request The request object.
     * @return A `Response` object containing the elements of the collection in descending order.
     */
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

    /**
     * Displays the semester enum field in descending order.
     *
     * @param request The request object.
     * @return A `Response` object containing the semester enum field in descending order.
     */
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


    /**
     * Uploads data from the database into the collection.
     *
     * @throws LogException If there is an error during the data upload process.
     */
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
