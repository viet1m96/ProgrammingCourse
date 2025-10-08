package gui.history;

import javafx.scene.Scene;

import java.util.Stack;

public class SceneHistory {
    private static final Stack<Scene> history = new Stack<>();

    public static void pushScene(Scene scene) {
        history.push(scene);
    }

    public static Scene popScene() {
        return !history.isEmpty() ? history.pop() : null;
    }
}
