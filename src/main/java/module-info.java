module pl.kk.quizmon {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.logging;
    requires org.apache.commons.configuration2;
    requires java.desktop;

    opens pl.kk.quizmon to javafx.fxml;
    exports pl.kk.quizmon;
    exports pl.kk.quizmon.controllers;
    opens pl.kk.quizmon.controllers to javafx.fxml;
}