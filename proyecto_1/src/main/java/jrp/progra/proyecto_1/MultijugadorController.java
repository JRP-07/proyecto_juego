/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package jrp.progra.proyecto_1;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.application.*;

/**
 * FXML Controller class
 *
 * @author josep
 */
public class MultijugadorController implements Initializable {

    public Conexion conexion;
    private Puntaje puntos = new Puntaje();
    private int tiempo = 30;
    public Timeline cronometro;
    private Colores colores = new Colores();
    private ventana venta = new ventana();

    @FXML
    private Label puntajeLocal;
    @FXML
    private Label temporizadorLocal;
    @FXML
    private Label txtColor;
    @FXML
    private Button btnRojo;
    @FXML
    private Button btnAzul;
    @FXML
    private Button btnVerde;
    @FXML
    private Button btnAmarillo;
    @FXML
    private Label puntajeRemoto;
    @FXML
    private Label lblEstadoRemoto;
    @FXML
    private Label temporizadorLocal1;
    @FXML
    private Button Salir;
    @FXML
    private Button Reiniciar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conexion = Conexion.getInstance(); // Obtiene la instancia de la conexión

        // Configurar el callback para recibir puntos del oponente
        conexion.ponerPuntos(puntosOponente -> {
            Platform.runLater(() -> {
                puntajeRemoto.setText(String.valueOf(puntosOponente)); // Actualiza el puntaje del oponente
            });
        });

        // Manejar la desconexión
        conexion.setAlDesconectar(desconectado -> {
            Platform.runLater(() -> {
                lblEstadoRemoto.setText("Oponente desconectado"); // Muestra mensaje de desconexión
            });
        });

        cambio();
        tempo();
    }

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

    public void validar(String colorb) { // Valida si el color del botón coincide con el texto
        if (colorb.equals(txtColor.getText())) {
            puntos.aumentar();
        } else {
            puntos.reducir();
        }

        puntajeLocal.setText(String.valueOf(puntos.getValor()));
        conexion.enviarPuntos(puntos.getValor()); // Envía el puntaje al oponente

        cambio();
    }

    public void cambio() {
        txtColor.setText(colores.randomTexto());
        txtColor.setTextFill(Color.web(colores.randomColor()));
    }

    public void tempo() {
        tiempo = 30;
        temporizadorLocal.setText(String.valueOf(tiempo));

        cronometro = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    tiempo--;
                    temporizadorLocal.setText(String.valueOf(tiempo));

                    if (tiempo <= 0) {
                        cronometro.stop();
                        txtColor.setText("Tiempo Agotado");
                        txtColor.setTextFill(Color.web("#cd0000")); //
                        bloquearBotones();
                        venta.mensaje(puntos.getValor(), Integer.parseInt(puntajeRemoto.getText()));
                    }
                }));
        cronometro.setCycleCount(Timeline.INDEFINITE);
        cronometro.play();
    }

    private void bloquearBotones() {
        btnRojo.setDisable(true);
        btnAzul.setDisable(true);
        btnVerde.setDisable(true);
        btnAmarillo.setDisable(true);
    }

    private void desbloquearBotones() {
        btnRojo.setDisable(false);
        btnAzul.setDisable(false);
        btnVerde.setDisable(false);
        btnAmarillo.setDisable(false);
    }

    public void reiniciarPartida() {
        puntos.reiniciar();
        puntajeLocal.setText(String.valueOf(puntos.getValor()));
        conexion.enviarPuntos(puntos.getValor());
        
        desbloquearBotones();
        cambio();

        tiempo = 30;
        temporizadorLocal.setText("30");


        cronometro.play();

    }

    @FXML
    public void Salir(ActionEvent event) {
        conexion.cerrar();
        try {
            App.setRoot("menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Reinciar(ActionEvent event) {
        reiniciarPartida();
    }

}
