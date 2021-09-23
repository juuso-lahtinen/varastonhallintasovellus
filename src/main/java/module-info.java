module r13.javafx.Varastonhallinta {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.persistence;
    requires org.hibernate.orm.core;
	requires javafx.base;
	requires javafx.graphics;

    opens r13.javafx.Varastonhallinta to javafx.fxml, org.hibernate.orm.core;
    exports r13.javafx.Varastonhallinta;
    exports r13.javafx.Varastonhallinta.models;
    opens r13.javafx.Varastonhallinta.models to javafx.fxml, org.hibernate.orm.core;
}
