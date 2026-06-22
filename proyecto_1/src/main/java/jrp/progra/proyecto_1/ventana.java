package jrp.progra.proyecto_1;

import java.io.IOException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ventana {
    public void mensaje(int puntaje){
        Stage mess= new Stage();
        mess.initModality(Modality.APPLICATION_MODAL);
        mess.setTitle("Fin del Juego");
        
        Label label1= new Label("Final del juego");
        label1.setStyle("-fx-text-fill:white; -fx-font-size:24px; -fx-font-weight:bold;");
        
        Label label2 = new Label("Su puntuacion es de: " + puntaje);
        label2.setStyle("-fx-text-fill:white;-fx-font-size:16px; -fx-font-weight:bold;");

        Button button2= new Button("Salir");
        button2.setStyle("-fx-background-color: #2776bf; -fx-font-color: white; -fx-font-weight-bold"); 
        button2.setOnAction(e->mess.close());

        VBox orden = new VBox(10);
        orden.getChildren().addAll(label1, label2, button2);
        orden.setStyle("-fx-background-color: #22345B; -fx-text-fill: white");
        orden.setAlignment(Pos.CENTER);

        Scene pantalla= new Scene(orden,300,150);
        mess.setScene(pantalla);
        mess.show();
    }

    public void mensaje(int puntajeL, int puntajeO){
        
        String res;
        if(puntajeL>puntajeO){
            res="Ganaste";
        }
        else if(puntajeL<puntajeO){
            res="Perdiste";
        }
        else{
            res="Empate";
        }
        
        Stage mess= new Stage();
        mess.initModality(Modality.APPLICATION_MODAL);
        mess.setTitle("Fin del Juego");

        Label lblRes = new Label(res);
        lblRes.setStyle("-fx-text-fill:white; -fx-font-size:24px; -fx-font-weight:bold;");
        
        Label lblPuntos = new Label("Tus puntos: "+ puntajeL + "| Puntos del Oponente: " +puntajeO);
        lblPuntos.setStyle("-fx-text-fill:white; -fx-font-size:24px; -fx-font-weight:bold;");


        Button btnSalir = new Button("Volver al Menú");
        btnSalir.setStyle("-fx-background-color: #2776bf; -fx-font-color: white; -fx-font-weight-bold"); 

        btnSalir.setOnAction(e -> {
            Conexion c = Conexion.getInstance();
            c.cerrar();
            mess.close();
            try{
                App.setRoot("menu");
            }
            catch(IOException x){
                x.printStackTrace();
            }
        });



        VBox orden = new VBox(10);
        orden.getChildren().addAll(lblRes, lblPuntos, btnSalir);
        orden.setAlignment(Pos.CENTER);

        Scene pantalla= new Scene(orden,300,150, Color.web("#22345B"));
        pantalla.setFill(Color.BLUE);
        mess.setScene(pantalla);
        mess.show();

    }
}
