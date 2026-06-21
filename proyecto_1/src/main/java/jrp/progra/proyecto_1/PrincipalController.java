/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package jrp.progra.proyecto_1;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

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
        // Inicio del Juego
        cambio();
        tempo();
    }
    private Colores colores = new Colores();
    private Puntaje puntos = new Puntaje();
    private ventana venta= new ventana();
    private int tiempo = 30;

    private Timeline cronometro;

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
        // reinicia el marcador y tiempo de la partida
        puntos.reiniciar();
        puntaje.setText(Integer.toString(puntos.getValor()));
        desbloquear();

        cambio();

        tiempo = 30;
        temporizador.setText(Integer.toString(tiempo));
        cronometro.playFromStart();
    }

    @FXML
    private void volverMenu(ActionEvent event) {
        cronometro.stop();
        try {
            App.setRoot("menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    /**
     * Cambia el texto y el color.
     */
    public void cambio() {
        color.setText(colores.randomTexto());
        color.setTextFill(Color.web(colores.randomColor()));
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

    /**
     * Valida si el color del botón presionado coincide con el texto mostrado.
     */
    public void validar(String colorb) {
        if (colorb.equals(color.getText())) {
            puntos.aumentar();
        } else {
            puntos.reducir();
        }
        puntaje.setText(Integer.toString(puntos.getValor()));
        
        if (puntos.getValor() <= 0 && !colorb.equals(color.getText())) {
            puntaje.setText("0");
            finalizar();
        }
        else {
            cambio();
        }
    }

    public void finalizar() {
        bloquear();
        cronometro.stop();
        color.setText("Perdiste");
        color.setTextFill(Color.web("#cd0000"));
        venta.mensaje(puntos.getValor());
    }

    public void tempo() {
        tiempo = 30;
        temporizador.setText(Integer.toString(tiempo));

        cronometro = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {

                    tiempo--;
                    temporizador.setText(Integer.toString(tiempo));

                    if (tiempo <= 0) {
                        venta.mensaje(puntos.getValor());
                        cronometro.stop();
                        bloquear();

                        color.setText("Tiempo Agotado");
                        color.setTextFill(Color.web("#cd0000"));
                    }
                }));
        cronometro.setCycleCount(Timeline.INDEFINITE);
        cronometro.play();
    }
}