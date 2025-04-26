module pl.kk.quizmon {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens pl.kk.quizmon to javafx.fxml;
    exports pl.kk.quizmon;
}