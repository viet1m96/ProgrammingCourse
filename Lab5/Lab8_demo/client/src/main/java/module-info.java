module demo.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires org.apache.logging.log4j;
    requires common;
    requires javafx.swing;


    opens gui.sign_in_up to javafx.fxml;
    opens gui.starters to javafx.fxml;
    opens gui.working_session to javafx.fxml;
    exports gui.sign_in_up;
    exports gui.starters;
    exports gui.working_session;
    exports gui.utilities.buttons;
    exports gui.working_session.std_grp_controllers;
    opens gui.working_session.std_grp_controllers to javafx.fxml;
    exports gui.utilities.command_util;
    exports gui.utilities.tools;
}