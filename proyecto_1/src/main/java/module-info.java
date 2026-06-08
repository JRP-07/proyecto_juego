module jrp.progra.proyecto_1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens jrp.progra.proyecto_1 to javafx.fxml;
    exports jrp.progra.proyecto_1;
}
