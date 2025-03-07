package goods;

import printer_options.RainbowPrinter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {
    @Serial
    private final static long serialVersionUID = 2L;

    private List<String> notice;
    private List<String> result;

    public Response(List<String> notice, List<String> result) {
        this.notice = notice;
        this.result = result;
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

}
