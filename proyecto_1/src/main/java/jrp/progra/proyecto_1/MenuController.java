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

/**
 * FXML Controller class
 *
 * @author josep
 */
public class MenuController implements Initializable {


    @FXML
    private Button btnIndividual;
    @FXML
    private Button btnMultijugador;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void jugarIndividual(ActionEvent event) throws IOException{
        App.setRoot("principal");
    }

    @FXML
    private void jugarMultijugador(ActionEvent event) throws IOException{
        App.setRoot("conexion_menu");
    }

}
