package network;

import java.util.LinkedList;
import java.util.Queue;

public class CommandHistory {
    private static final Queue<String> history = new LinkedList<>();


    public static void addHistory(String command) {
        if (history.size() < 12) {
            history.add(command);
        } else {
            history.poll();
            history.add(command);
        }
    }

    public static Queue<String> getHistory() {
        return history;
    }

}
