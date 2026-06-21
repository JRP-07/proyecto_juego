package jrp.progra.proyecto_1;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;

public class ConexionMenuController {

    @FXML
    private TextField txtIp;
    @FXML
    private Label lblEstado;

    private Conexion conexion = Conexion.getInstance();
    private final int PUERTO = 12345;

    @FXML
    private void handleCrearServidor() {
        lblEstado.setText("Iniciando servidor... espera al oponente.");
        conexion.iServer(PUERTO);
        
        // Hilo para monitorear cuando se conecte el oponente y cambiar de pantalla
        new Thread(() -> {
            while (!conexion.isConectado()) {
                try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
            }
            Platform.runLater(() -> {
                try {
                    App.setRoot("multijugador");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }).start();
    }

    @FXML
    private void handleUnirse() {
        String ip = txtIp.getText();
        if (ip == null || ip.isEmpty()) {
            lblEstado.setText("Por favor, ingresa una IP.");
            return;
        }

        lblEstado.setText("Intentando conectar...");
        conexion.iCliente(ip, PUERTO);

        // Hilo para monitorear la conexión exitosa
        new Thread(() -> {
            int intentos = 0;
            while (!conexion.isConectado() && intentos < 20) { // Timeout de 10 segundos
                try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
                intentos++;
            }
            
            Platform.runLater(() -> {
                if (conexion.isConectado()) {
                    try {
                        App.setRoot("multijugador");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    lblEstado.setText("Error: No se pudo conectar al servidor.");
                }
            });
        }).start();
    }

    @FXML
    private void volverMenu(ActionEvent event) {
        try {
            App.setRoot("menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
