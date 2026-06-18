/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package jrp.progra.proyecto_1;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PrincipalController implements Initializable {

    @FXML
    private Label txt;
    @FXML
    private Label color;
    @FXML
    private Button boton1;
    @FXML
    private Button boton2;
    @FXML
    private Button boton3;
    @FXML
    private Button boton4;
    @FXML
    private Button reinicio;
    @FXML
    private Label puntaje;
    @FXML
    private Label temporizador;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        randomTexto();
        randomColor();

        tempo();
    }

    int puntos = 0;

    private int tiempo = 30;

    private Timeline cronometro;

    Random r = new Random();
    static String colores[] = { "Rojo", "Azul", "Verde", "Amarillo" };
    static String numcolor[] = { "#FF3333", "#3333FF", "#33FF33", "#FFFF00" };

    @FXML
    private void clickRojo(ActionEvent event) {
        validar("Rojo");
    }

    @FXML
    private void clickAzul(ActionEvent event) {
        validar("Azul");
    }

    @FXML
    private void clickVerde(ActionEvent event) {
        validar("Verde");
    }

    @FXML
    private void clickAmarillo(ActionEvent event) {
        validar("Amarillo");
    }

    @FXML
    private void reinicio(ActionEvent event) {
        puntos = 0;
        puntaje.setText("0");
        desbloquear();

        randomTexto();
        randomColor();

        tiempo = 30;
        temporizador.setText(Integer.toString(tiempo));
        cronometro.playFromStart();
    }

    public void randomTexto() {
        int c = r.nextInt(colores.length);
        color.setText(colores[c]);
    }

    public void randomColor() {
        int c = r.nextInt(numcolor.length);
        System.out.println("Color2: " + c);
        color.setTextFill(Color.web(numcolor[c]));
    }

    public void cambiarC() {
        randomColor();
        randomTexto();
    }

    public void bloquear() {
        boton1.setDisable(true);
        boton2.setDisable(true);
        boton3.setDisable(true);
        boton4.setDisable(true);
    }

    public void desbloquear() {
        boton1.setDisable(false);
        boton2.setDisable(false);
        boton3.setDisable(false);
        boton4.setDisable(false);
    }

    public void validar(String colorb) {
        if (colorb.equals("Azul") && color.getText().equals("Azul")) {
            puntos += 1;
        } else if (colorb.equals("Verde") && color.getText().equals("Verde")) {
            puntos += 1;
        } else if (colorb.equals("Rojo") && color.getText().equals("Rojo")) {
            puntos += 1;
        } else if (colorb.equals("Amarillo") && color.getText().equals("Amarillo")) {
            puntos += 1;
        } else {
            puntos -= 1;
        }
        puntaje.setText(Integer.toString(puntos));
        
        if (puntos < 0) {
            puntos=0;
            puntaje.setText("0");
            finalizar();
        } else {
            cambiarC();
        }
    }

    public void finalizar() {
        bloquear();
        cronometro.stop();
        color.setText("Perdiste");
        color.setTextFill(Color.web("#cd0000"));
        mensaje();
    }

    public void tempo() {
        tiempo = 30;
        temporizador.setText(Integer.toString(tiempo));

        cronometro = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {

                    tiempo--;
                    temporizador.setText(Integer.toString(tiempo));

                    if (tiempo <= 0) {
                        mensaje();
                        cronometro.stop();
                        bloquear();

                        color.setText("Tiempo Agotado");
                        color.setTextFill(Color.web("#cd0000"));
                    }
                }));
        cronometro.setCycleCount(Timeline.INDEFINITE);
        cronometro.play();
    }

    public void mensaje(){
        Stage men= new Stage();
        men.initModality(Modality.APPLICATION_MODAL);
        men.setTitle("Fin del Juego");
        
        Label label1= new Label("Final del juego");
        Label label2 = new Label("Su puntiacion es de: " + puntos);
        Button button2= new Button("Salir");
        button2.setOnAction(e->men.close());


        VBox orden = new VBox(10);
        orden.getChildren().addAll(label1, label2, button2);
        orden.setAlignment(Pos.CENTER);

        Scene pantalla= new Scene(orden,300,150);
        pantalla.setFill(Color.BLUE);
        men.setScene(pantalla);
        men.show();
    }

}