package packets;

import main_objects.StudyGroup;

public class Request {
    private String argument;
    private StudyGroup studyGroup;

    public Request(String argument, StudyGroup studyGroup) {
        this.argument = argument;
        this.studyGroup = studyGroup;
    }


    public String getArgument() {
        return argument;
    }
    public StudyGroup getStudyGroup() {
        return studyGroup;
    }
}
