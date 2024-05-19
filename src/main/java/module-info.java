module com.example.pingpong {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.pingpong to javafx.fxml;
    exports com.example.pingpong;
}