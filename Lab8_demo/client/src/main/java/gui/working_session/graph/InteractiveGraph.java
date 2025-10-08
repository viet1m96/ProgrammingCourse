package gui.working_session.graph;

import gui.utilities.tools.ResourceManager;
import gui.working_session.std_grp_controllers.ChoicesCreator;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.control.ContextMenu;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import main_objects.StdGroupUltimate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class InteractiveGraph extends Pane {

    private final String linkGroup = "/home/cun/IdeaProjects/Lab8_demo/client/src/main/resources/images/test2.png";
    private double scale = 1.0;
    private double translateX = 0;
    private double translateY = 0;
    private double startDragX, startDragY;
    private final ChoicesCreator choicesCreator = new ChoicesCreator();
    private ResourceManager resourceManager;
    private ContextMenu contextMenu;
    private Canvas canvas = new Canvas();
    private final Group graphGroup = new Group();
    private HashMap<String, StdGroupUltimate> curGroups = new HashMap<>();
    private final HashMap<String, ImageView> groupViews = new HashMap<>();

    public InteractiveGraph(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        initialize();
    }

    private void initialize() {
        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());
        this.getChildren().addAll(canvas, graphGroup);

        canvas.widthProperty().addListener((obs, oldVal, newVal) -> redrawCanvas());
        canvas.heightProperty().addListener((obs, oldVal, newVal) -> redrawCanvas());

        this.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal.doubleValue() == 0 && newVal.doubleValue() > 0) {
                translateX = newVal.doubleValue() / 2;
                redrawCanvas();
            }
        });
        this.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal.doubleValue() == 0 && newVal.doubleValue() > 0) {
                translateY = newVal.doubleValue() / 2;
                redrawCanvas();
            }
        });

        setupMouseHandlers();
    }


    private void setupMouseHandlers() {
        this.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                startDragX = event.getX();
                startDragY = event.getY();
                hideContextMenu();
            }
        });

        this.setOnMouseDragged(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                translateX += event.getX() - startDragX;
                translateY += event.getY() - startDragY;
                startDragX = event.getX();
                startDragY = event.getY();
                updateTransforms();
            }
        });

        this.setOnScroll(event -> {
            double delta = event.getDeltaY();
            double zoomFactor = 1.05;
            double minScale = 0.1, maxScale = 10.0;
            double oldScale = scale;

            if (delta < 0) {
                if (scale < maxScale) {
                    scale *= zoomFactor;
                    if (scale > maxScale) scale = maxScale;
                }
            } else if (delta > 0) {

                if (scale > minScale) {
                    scale /= zoomFactor;
                    if (scale < minScale) scale = minScale;
                }
            }

            double mouseX = event.getX();
            double mouseY = event.getY();

            translateX = mouseX - (mouseX - translateX) * (scale / oldScale);
            translateY = mouseY - (mouseY - translateY) * (scale / oldScale);

            updateTransforms();
            event.consume();
        });
    }

    private void updateTransforms() {
        redrawCanvas();
        updateGraphGroupPositions();
    }

    private void updateGraphGroupPositions() {
        List<Node> nodesSnapshot = new ArrayList<>(graphGroup.getChildren());
        for (Node node : nodesSnapshot) {
            if (node instanceof ImageView imageView) {
                StdGroupUltimate group = (StdGroupUltimate) imageView.getUserData();
                setNewPosition(imageView, group);
            }
        }
    }

    private void setNewPosition(ImageView imageView, StdGroupUltimate group) {
        imageView.setUserData(group);
        imageView.toFront();
        imageView.setPickOnBounds(true);
        double size = group.getStudent_count() * scale;
        double px = translateX + group.getGroup_x() * scale - size / 2;
        double py = translateY - group.getGroup_y() * scale - size / 2;
        imageView.setLayoutX(px);
        imageView.setLayoutY(py);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
    }

    private void setEventOnElement(ImageView imageView, StdGroupUltimate group) {
        imageView.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.SECONDARY) {
                StdGroupUltimate clickedGroup = (StdGroupUltimate) imageView.getUserData();
                if(clickedGroup == null) {
                    hideContextMenu();
                    return;
                }
                contextMenu = choicesCreator.createContextMenu(clickedGroup, resourceManager, this);
                contextMenu.show(canvas, event.getScreenX(), event.getScreenY());
                event.consume();
            }
        });
    }

    private void setUpElement(StdGroupUltimate group) {
        curGroups.put(group.getSearch_key(), group);
        ImageView imageView = ImageFactory.createImage(linkGroup, group.getCreator());
        setNewPosition(imageView, group);
        setEventOnElement(imageView, group);
        graphGroup.getChildren().add(imageView);
        groupViews.put(group.getSearch_key(), imageView);
    }


    public void updateGraphGroups(List<StdGroupUltimate> groups)  {
        HashMap<String, StdGroupUltimate> nextGroups = new HashMap<>();
        for (StdGroupUltimate group : groups) {
            nextGroups.put(group.getSearch_key(), group);
            if (!curGroups.containsKey(group.getSearch_key())) {
                setUpElement(group);
            } else {
                StdGroupUltimate curGroup = curGroups.get(group.getSearch_key());
                ImageView imageView = groupViews.get(group.getSearch_key());
                Integer xOld = curGroup.getGroup_x();
                Integer yOld = curGroup.getGroup_y();
                Integer xNew = group.getGroup_x();
                Integer yNew = group.getGroup_y();
                Long countOld = curGroup.getStudent_count();
                Long countNew = group.getStudent_count();

                boolean positionChanged = !Objects.equals(xOld, xNew) || !Objects.equals(yOld, yNew);
                boolean countChanged = !Objects.equals(countOld, countNew);

                if (positionChanged || countChanged) {
                    animateMoveAndSize(imageView, curGroup, group);
                } else {
                    setNewPosition(imageView, group);
                }
                imageView.setUserData(group);
                curGroups.put(group.getSearch_key(), group);
            }
        }
        List<String> removeKeys = new ArrayList<>();
        for (String k : curGroups.keySet()) {
            if (!nextGroups.containsKey(k)) {
                removeKeys.add(k);
            }
        }
        for (String k : removeKeys) {
            Node view = groupViews.remove(k);
            if (view != null) {
                javafx.application.Platform.runLater(() -> graphGroup.getChildren().remove(view));
            }
            curGroups.remove(k);
        }
    }


    private void animateMoveAndSize(ImageView imageView, StdGroupUltimate from, StdGroupUltimate to) {
        final long startTime = System.nanoTime();
        final double durationSec = 5;

        final double startStudentCount = from.getStudent_count();
        final double endStudentCount = to.getStudent_count();
        final int fromX = from.getGroup_x(), fromY = from.getGroup_y();
        final int toX = to.getGroup_x(), toY = to.getGroup_y();

        javafx.animation.AnimationTimer timer = new javafx.animation.AnimationTimer() {
            @Override
            public void handle(long now) {
                double elapsedSec = (now - startTime) / 1_000_000_000.0;
                double progress = Math.min(elapsedSec / durationSec, 1.0);

                double curScale = scale;
                double curTransX = translateX;
                double curTransY = translateY;

                double curCount = startStudentCount + (endStudentCount - startStudentCount) * progress;
                double curX = fromX + (toX - fromX) * progress;
                double curY = fromY + (toY - fromY) * progress;

                double size = curCount * curScale;
                double px = curTransX + curX * curScale - size / 2;
                double py = curTransY - curY * curScale - size / 2;

                imageView.setLayoutX(px);
                imageView.setLayoutY(py);
                imageView.setFitWidth(size);
                imageView.setFitHeight(size);

                if (progress >= 1.0) {
                    this.stop();
                    setNewPosition(imageView, to);
                }
            }
        };
        timer.start();
    }


    private void hideContextMenu() {
        if (contextMenu != null && contextMenu.isShowing()) {
            contextMenu.hide();
            contextMenu = null;
        }
    }

    private void redrawCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        double width = canvas.getWidth();
        double height = canvas.getHeight();

        double xMin = (0 - translateX) / scale;
        double xMax = (width - translateX) / scale;
        double yMax = (translateY) / scale;
        double yMin = (translateY - height) / scale;


        gc.setStroke(Color.LIGHTGRAY);
        double gridStep = 50;

        double startX = Math.floor(xMin / gridStep) * gridStep;
        double startY = Math.floor(yMin / gridStep) * gridStep;

        for (double x = startX; x <= xMax; x += gridStep) {
            double px = translateX + x * scale;
            gc.strokeLine(px, 0, px, height);
        }
        for (double y = startY; y <= yMax; y += gridStep) {
            double py = translateY - y * scale;
            gc.strokeLine(0, py, width, py);
        }
        gc.setStroke(Color.BLACK);
        double axisX = translateX;
        if (axisX >= 0 && axisX <= width)
            gc.strokeLine(axisX, 0, axisX, height);

        double axisY = translateY;
        if (axisY >= 0 && axisY <= height)
            gc.strokeLine(0, axisY, width, axisY);
    }

}
