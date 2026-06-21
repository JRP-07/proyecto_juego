package jrp.progra.proyecto_1;

import java.net.*;
import java.util.function.Consumer;
import java.io.*;

public class Conexion {
    private static Conexion instance; // Instancia Singleton
    
    public static Conexion getInstance() { // Método para obtener la instancia Singleton
        if (instance == null) {
            instance = new Conexion();
        }
        return instance;
    }

    private Conexion() {}

    private Socket soc = null; // Socket para conexión
    private ServerSocket servs = null; // ServerSocket para servidor
    private PrintWriter op; // Salida
    private BufferedReader ip; // Entrada
    public boolean conectado = false; // Estado de la conexión

    private Consumer<Integer> PuntoRecibido; // Callback para puntos recibidos
    private Consumer<Boolean> alDesconectar; // Callback para manejar desconexiones

    public void iniciar() throws IOException { 
        System.out.println("Configurando flujos de datos...");
        this.op = new PrintWriter(this.soc.getOutputStream(), true); // Inicia salida
        this.ip = new BufferedReader(new InputStreamReader(this.soc.getInputStream())); // Inicia entrada
        this.conectado = true; // Marca como conectado
        System.out.println("Conexión establecida (conectado = true)");

        new Thread(() -> { // Hilo para escuchar mensajes entrantes
            try {
                String mensaje;
                // El ciclo se rompe si el rival cierra el juego 
                while (conectado && (mensaje = ip.readLine()) != null) { 
                    try {
                        System.out.println("Mensaje recibido: " + mensaje);
                        int puntosO = Integer.parseInt(mensaje); 
                        if (PuntoRecibido != null) {
                            PuntoRecibido.accept(puntosO); // Llama al callback con los puntos del oponente
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("No se recibieron datos validos: " + mensaje);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error de conexión o rival desconectado: " + e.getMessage());
            } finally {
                conectado = false; // Marca como desconectado
                System.out.println("Se ha perdido la conexión con el rival.");
                if (alDesconectar != null) {
                    alDesconectar.accept(true); // Llama al callback de desconexión
                }
                cerrar(); 
            }
        }).start();
    }

    public void iServer(int puerto) { // Inicia el servidor para esperar conexiones
        new Thread(() -> {
            try {
                this.servs = new ServerSocket(puerto); // Crea un ServerSocket en el puerto 
                System.out.println("Esperando al oponente en el puerto " + puerto + "...");
                this.soc = servs.accept(); // Espera y acepta una conexión entrante
                System.out.println("¡Oponente conectado!");
                iniciar(); // Inicia la comunicación
            } catch (IOException e) {
                // Si nosotros mismos cerramos el servidor, lanzará excepción aquí
                System.out.println("Servidor de escucha detenido o fallido: " + e.getMessage());
            }
        }).start();
    }   

    public void iCliente(String ip, int puerto) { 
        new Thread(() -> {
            try {
                this.soc = new Socket(ip, puerto); // Intenta conectar al servidor
                System.out.println("¡Conectado al servidor!");
                iniciar(); // Inicia la comunicación
            } catch (IOException e) {
                System.err.println("Error al conectar: " + e.getMessage());
                // Si falla al intentar conectar, también avisamos a la interfaz
                if (alDesconectar != null) {
                    alDesconectar.accept(true); // Llama al callback de desconexión
                }
            }
        }).start();
    }

    public void enviarPuntos(int puntos) { // Envía los puntos al oponente
        if (this.conectado && op != null) {
            op.println(puntos);
        }
    }

    public void ponerPuntos(Consumer<Integer> callback) { // Establece el callback para cuando se reciben puntos
        this.PuntoRecibido = callback;
    }

    public void setAlDesconectar(Consumer<Boolean> callback) {
        this.alDesconectar = callback;
    }

    public void cerrar() { // Cierra todos los recursos de la conexión
        try {
            conectado = false;
            if (op != null) op.close(); // Cierra la salida
            if (ip != null) ip.close(); // Cierra la entrada
            if (soc != null && !soc.isClosed()) soc.close(); // Cierra el socket
            
            if (servs != null && !servs.isClosed()) servs.close(); // Cierra el ServerSocket
        } catch (IOException e) {
            System.err.println("Error cerrando sockets: " + e.getMessage());
        }
    }

    public boolean isConectado() { // Devuelve true si la conexión está activa
        return conectado;
    }
}