package network;


import java.util.LinkedList;
import java.util.Queue;

public class CommandHistory {
    private final Queue<String> history;

    public CommandHistory() {
        history = new LinkedList<>();
    }

    public void addHistory(String command) {
        if (history.size() < 12) {
            history.add(command);
        } else {
            history.poll();
            history.add(command);
        }
    }

    public void printHistory() {
        history.forEach(System.out::println);
    }
}
