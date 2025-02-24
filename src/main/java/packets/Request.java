package packets;

import main_objects.StudyGroup;

/**
 * The {@code Request} class represents a request containing an argument and a {@link StudyGroup} object.
 * It is used to encapsulate the data needed for various operations on the collection of study groups.
 */
public class Request {
    private String argument;
    private StudyGroup studyGroup;

    /**
     * Constructs a new {@code Request} object with the specified argument and {@link StudyGroup}.
     *
     * @param argument   The argument for the request.
     * @param studyGroup The {@link StudyGroup} object associated with the request.
     */
    public Request(String argument, StudyGroup studyGroup) {
        this.argument = argument;
        this.studyGroup = studyGroup;
    }

    /**
     * Returns the argument of the request.
     *
     * @return The argument of the request.
     */
    public String getArgument() {
        return argument;
    }

    /**
     * Returns the {@link StudyGroup} object associated with the request.
     *
     * @return The {@link StudyGroup} object associated with the request.
     */
    public StudyGroup getStudyGroup() {
        return studyGroup;
    }
}
