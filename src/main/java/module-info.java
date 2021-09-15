module r13.javafx.Varastonhallinta {
    requires javafx.controls;
    requires javafx.fxml;

    opens r13.javafx.Varastonhallinta to javafx.fxml;
    exports r13.javafx.Varastonhallinta;
}
