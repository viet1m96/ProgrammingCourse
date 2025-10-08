package goods;

import java.io.Serial;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;

public class Response implements Serializable {
    @Serial
    private final static long serialVersionUID = 2L;

    private List<String> notice;
    private List<String> result;
    private SocketAddress clientAddress;

    public Response(List<String> notice, List<String> result, SocketAddress clientAddress) {
        this.notice = notice;
        this.result = result;
        this.clientAddress = clientAddress;
    }

    public void addNotice(String notice) {
        this.notice.add(notice);
    }

    public List<String> getNotice() {
        return notice;
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

}
