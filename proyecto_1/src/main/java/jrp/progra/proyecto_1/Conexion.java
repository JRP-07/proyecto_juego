package jrp.progra.proyecto_1;

import java.net.*;
import java.util.function.Consumer;
import java.io.*;

public class Conexion {
    private static Conexion instance;
    
    public static Conexion getInstance() {
        if (instance == null) {
            instance = new Conexion();
        }
        return instance;
    }

    private Conexion() {}

    private Socket soc = null;
    private ServerSocket servs = null;
    private PrintWriter op;
    private BufferedReader ip;
    public boolean conectado = false;

    private Consumer<Integer> PuntoRecibido;
    private Consumer<Boolean> alDesconectar; // Callback para manejar desconexiones

    public void iniciar() throws IOException {
        System.out.println("Configurando flujos de datos...");
        this.op = new PrintWriter(this.soc.getOutputStream(), true);
        this.ip = new BufferedReader(new InputStreamReader(this.soc.getInputStream()));
        this.conectado = true;
        System.out.println("Conexión establecida (conectado = true)");

        new Thread(() -> {
            try {
                String mensaje;
                // El ciclo se rompe si el rival cierra el juego (readLine devuelve null)
                while (conectado && (mensaje = ip.readLine()) != null) {
                    try {
                        System.out.println("Mensaje recibido: " + mensaje);
                        int puntosO = Integer.parseInt(mensaje);
                        if (PuntoRecibido != null) {
                            PuntoRecibido.accept(puntosO);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("No se recibieron datos validos: " + mensaje);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error de conexión o rival desconectado: " + e.getMessage());
            } finally {
                // Si el hilo termina (por error o desconexión), cerramos de forma segura
                conectado = false;
                System.out.println("Se ha perdido la conexión con el rival.");
                if (alDesconectar != null) {
                    alDesconectar.accept(true);
                }
                cerrar();
            }
        }).start();
    }

    public void iServer(int puerto) {
        new Thread(() -> {
            try {
                // CORRECCIÓN: Usar la variable global servs en lugar del try-with-resources
                // Esto nos permite cerrarlo usando el método cerrar() si queremos cancelar la espera.
                this.servs = new ServerSocket(puerto);
                System.out.println("Esperando al oponente en el puerto " + puerto + "...");
                this.soc = servs.accept(); 
                System.out.println("¡Oponente conectado!");
                iniciar();
            } catch (IOException e) {
                // Si nosotros mismos cerramos el servidor, lanzará excepción aquí
                System.out.println("Servidor de escucha detenido o fallido: " + e.getMessage());
            }
        }).start();
    }   

    public void iCliente(String ip, int puerto) {
        new Thread(() -> {
            try {
                this.soc = new Socket(ip, puerto);
                System.out.println("¡Conectado al servidor!");
                iniciar();
            } catch (IOException e) {
                System.err.println("Error al conectar: " + e.getMessage());
                // Si falla al intentar conectar, también avisamos a la interfaz
                if (alDesconectar != null) {
                    alDesconectar.accept(true);
                }
            }
        }).start();
    }

    public void enviarPuntos(int puntos) {
        if (this.conectado && op != null) {
            op.println(puntos);
        }
    }

    public void ponerPuntos(Consumer<Integer> callback) {
        this.PuntoRecibido = callback;
    }

    // Nuevo método para definir qué hacer en JavaFX cuando se cae la red
    public void setAlDesconectar(Consumer<Boolean> callback) {
        this.alDesconectar = callback;
    }

    public void cerrar() {
        try {
            conectado = false;
            if (op != null) op.close();
            if (ip != null) ip.close();
            if (soc != null && !soc.isClosed()) soc.close();
            
            // CORRECCIÓN: Ahora podemos cerrar el ServerSocket correctamente
            if (servs != null && !servs.isClosed()) servs.close(); 
        } catch (IOException e) {
            System.err.println("Error cerrando sockets: " + e.getMessage());
        }
    }

    public boolean isConectado() {
        return conectado;
    }
}