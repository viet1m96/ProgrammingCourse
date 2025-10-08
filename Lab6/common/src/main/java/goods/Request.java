package goods;



import main_objects.StudyGroup;

import java.io.Serial;
import java.io.Serializable;
import java.net.SocketAddress;


public class Request implements Serializable {
    @Serial
    private final static long serialVersionUID = 1L;
    private String cmd;
    private String argument;
    private StudyGroup studyGroup;
    private SocketAddress remoteAddress;
    public Request(String cmd, String argument, StudyGroup studyGroup) {
        this.cmd = cmd;
        this.argument = argument;
        this.studyGroup = studyGroup;
    }

    public SocketAddress getRemoteAddress() {
        return remoteAddress;
    }
    public void setRemoteAddress(SocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }
    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(StudyGroup studyGroup) {
        this.studyGroup = studyGroup;
    }
}
