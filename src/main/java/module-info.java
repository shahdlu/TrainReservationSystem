module com.railway.trs {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    opens com.trs to javafx.fxml;
    opens com.trs.controllers to javafx.fxml;
    opens com.trs.modules to javafx.base;
    opens com.trs.modules.tickets to javafx.base;
    exports com.trs;
}