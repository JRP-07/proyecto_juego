package jrp.progra.proyecto_1;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ventana {
    public void messsaje(int puntaje){
        Stage mess= new Stage();
        mess.initModality(Modality.APPLICATION_MODAL);
        mess.setTitle("Fin del Juego");
        
        Label label1= new Label("Final del juego");
        Label label2 = new Label("Su puntiacion es de: " + puntaje);
        Button button2= new Button("Salir");
        button2.setOnAction(e->mess.close());

        VBox orden = new VBox(10);
        orden.getChildren().addAll(label1, label2, button2);
        orden.setAlignment(Pos.CENTER);

        Scene pantalla= new Scene(orden,300,150, Color.web("#22345B"));
        mess.setScene(pantalla);
        mess.show();
    }
}
