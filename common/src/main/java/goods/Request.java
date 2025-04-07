package goods;



import main_objects.StudyGroup;

import java.io.Serial;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;


public class Request implements Serializable {
    @Serial
    private final static long serialVersionUID = 1L;
    private String cmd;
    private List<String> arguments;
    private StudyGroup studyGroup;
    private SocketAddress remoteAddress;
    private String token;
    public Request() {}
    public Request(String cmd, List<String> arguments, StudyGroup studyGroup, String token) {
        this.cmd = cmd;
        this.arguments = arguments;
        this.studyGroup = studyGroup;
        this.token = token;
    }
    public Request(String cmd, List<String> arguments, String token) {
        this.cmd = cmd;
        this.arguments = arguments;
        this.token = token;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(StudyGroup studyGroup) {
        this.studyGroup = studyGroup;
    }

    public SocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(SocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
