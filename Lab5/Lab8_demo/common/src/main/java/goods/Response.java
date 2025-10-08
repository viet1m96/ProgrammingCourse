package goods;

import main_objects.Instruction;
import main_objects.StudyGroup;

import java.io.Serial;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class Response implements Serializable {
    @Serial
    private final static long serialVersionUID = 2L;

    private List<String> notice;
    private List<String> result;
    private List<StudyGroup> studyGroups;
    private List<Instruction> instructions;
    private SocketAddress clientAddress;
    private Boolean success;

    public Response() {}
    public Response(List<String> notice, List<String> result, SocketAddress clientAddress) {
        this.notice = notice;
        this.result = result;
        this.clientAddress = clientAddress;
    }
    public Response(List<String> notice, List<String> result, SocketAddress clientAddress, boolean success) {
        this.notice = notice;
        this.result = result;
        this.clientAddress = clientAddress;
        this.success = success;
    }
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
    public void addNotice(String notice) {
        this.notice.add(notice);
    }

    public List<String> getNotice() {
        return notice;
    }

    public List<StudyGroup> getStudyGroups() {
        return studyGroups;
    }
    public void addStudyGroup(StudyGroup studyGroup) {
        if (this.studyGroups != null) {
            this.studyGroups.add(studyGroup);
        } else {
            this.studyGroups = new ArrayList<>();
            this.studyGroups.add(studyGroup);
        }
    }
    public void setStudyGroups(List<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;
    }

    public List<String> getResult() {
        return result;
    }

    public void setClientAddress(InetSocketAddress clientAddress) {
        this.clientAddress = clientAddress;
    }

    public SocketAddress getClientAddress() {
        return clientAddress;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }
    public List<Instruction> getInstructions() {
        return instructions;
    }

}
