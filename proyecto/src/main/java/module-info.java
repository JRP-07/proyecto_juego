module jrp.progra.proyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;

    opens jrp.progra.proyecto to javafx.fxml;
    exports jrp.progra.proyecto;
}
