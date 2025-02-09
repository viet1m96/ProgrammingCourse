package packets;

import main_objects.StudyGroup;

/**
 * The {@code Request} class represents a request packet containing an argument (a String) and a {@link StudyGroup} object.
 * It is used to encapsulate data sent between a client and a server.
 */
public class Request {
    private String argument;
    private StudyGroup studyGroup;

    /**
     * Constructs a new {@code Request} object with the specified argument and {@link StudyGroup}.
     *
     * @param argument   The argument for the request (e.g., a command name or a search query).  May be null.
     * @param studyGroup The {@link StudyGroup} object associated with the request. May be null.
     */
    public Request(String argument, StudyGroup studyGroup) {
        this.argument = argument;
        this.studyGroup = studyGroup;
    }


    /**
     * Returns the argument associated with the request.
     *
     * @return The argument of the request. May be null.
     */
    public String getArgument() {
        return argument;
    }

    /**
     * Returns the {@link StudyGroup} object associated with the request.
     *
     * @return The {@link StudyGroup} object. May be null.
     */
    public StudyGroup getStudyGroup() {
        return studyGroup;
    }
}