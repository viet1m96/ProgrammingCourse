package file_processor.gui_file;

import gui.utilities.buttons.CloseButtonUtil;
import gui.utilities.command_util.DynamicPopUpUtil;
import gui.utilities.tools.ResourceManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ResultRender {

    private VBox mainLayout;
    private ResourceManager resourceManager;
    private Stage stage;
    public ResultRender() {}

    public void initRenderStage(ResourceManager resourceManager, ActionEvent e, Node node)  {
        this.resourceManager = resourceManager;
        stage = DynamicPopUpUtil.releaseBasicPopUp();
        System.out.println(stage);
        System.out.println(1);
        Scene scene = initScrollScene();
        stage.setScene(scene);
        DynamicPopUpUtil.givePositionPopUp(stage, node, 1200, 800, -200, 0);

    }

    private Scene initScrollScene() {
        mainLayout = new VBox();
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setSpacing(20);

        ScrollPane scrollPane = new ScrollPane(mainLayout);
        AnchorPane root = new AnchorPane(scrollPane);
        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        bindSizeToParent(root, mainLayout);
        return new Scene(root, 1200, 500);
    }

    public void addResultVBox(VBox resultVBox) {
        Platform.runLater(() -> mainLayout.getChildren().add(resultVBox));
    }
    private void bindSizeToParent(AnchorPane parent, VBox child) {
        parent.widthProperty().addListener((obs, oldVal, newVal) -> {
            child.setPrefWidth(newVal.doubleValue());
        });
    }

}
