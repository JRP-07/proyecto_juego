/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package jrp.progra.proyecto_1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author josep
 */
public class MultijugadorController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clickRojo(ActionEvent event) {
    }

    @FXML
    private void clickAzul(ActionEvent event) {
    }

    @FXML
    private void clickVerde(ActionEvent event) {
    }

    @FXML
    private void clickAmarillo(ActionEvent event) {
    }

    @FXML
    private void handleAbandonar(ActionEvent event) {
    }

    
}
