/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package jrp.progra.proyecto_1;

import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.*;
import javafx.event.ActionEvent;

/**
 * FXML Controller class
 *
 * @author josep
 */
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // agregar();
        randomTexto();
        randomColor();

    }

    static int puntos = 0;
    static float tiempo = 0;

    static int tiempoinicio = 30;
    static Timer cronometro = new Timer();

    // public static void agregar() {

    // for (int index = 0; index < 4; index++) {
    // colors.add(colores[index]);
    // }
    // }

    static Random r = new Random();
    // static ArrayList<String> colors = new ArrayList<String>();
    static String colores[] = { "Rojo", "Azul", "Verde", "Amarillo" };
    static String numcolor[] = { "#FF3333", "#3333FF", "#33FF33", "#FFFF00" };

    public void randomTexto() {
        // Collections.shuffle(colors);
        // System.out.println("Color aleatorio:" + colors);
        int c = r.nextInt(colores.length);
        // System.out.println("gato" + c);
        // String valor = colors.get(2);
        color.setText(colores[c]);

        // for (String valorr : colors) {llll
        // System.out.println("Color aleatorio:" + colors);
        // }
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

    @FXML
    private void reinio(ActionEvent event) {
        randomTexto();
        randomColor();
        puntos = 0;
        puntaje.setText("0");
    }


    public static void bloquear() {
        boton1.setDisabled(True);
    }

    @FXML
    private void clickRojo(ActionEvent event) {
        voidValidar("Rojo");
    }

    @FXML
    private void clickAzul(ActionEvent event) {
        voidValidar("Azul");
    }

    @FXML
    private void clickVerde(ActionEvent event) {
        voidValidar("Verde");
    }

    @FXML
    private void clickAmarillo(ActionEvent event) {
        voidValidar("Amarillo");
    }

    public void voidValidar(String colorb) {
        if (colorb == "Azul" && color.getText() == "Azul") {
            puntos += 1;
        } else if (colorb == "Verde" && color.getText() == "Verde") {
            puntos += 1;
        } else if (colorb == "Rojo" && color.getText() == "Rojo") {
            puntos += 1;
        } else if (colorb == "Amarillo" && color.getText() == "Amarillo") {
            puntos += 1;
        } else {
            puntos -= 1;
        }
        String points = "" + puntos;
        puntaje.setText(points);
        cambiarC();
    }

    public void finalizar(){
        if(puntos<0){
            bloquear();
            System.out.println("Perdiste");
        }
    }
}
