package jrp.progra.proyecto_1;

import java.net.*;
import java.util.function.Consumer;
import java.io.*;

public class Conexion {
    private Socket soc = null;
    private ServerSocket servs = null;
    private PrintWriter op;
    private BufferedReader ip;
    public boolean conectado = false;

    private Consumer<Integer> PuntoRecibido;

    public void iniciar() throws IOException{
        this.op=new PrintWriter(this.soc.getOutputStream(), true);
        this.ip=new BufferedReader(new InputStreamReader(this.soc.getInputStream()));
        this.conectado=true;

        new Thread(() -> {
            try{
                String mensaje;
                while (conectado && (mensaje = ip.readLine()) != null) {
                    try{
                        System.out.println("Mensaje recibido: " + mensaje);
                        int puntosO=Integer.parseInt(mensaje);
                        if(PuntoRecibido!=null){
                            PuntoRecibido.accept(puntosO);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("No se recibieron datos validos: "+ mensaje);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error de conexión: " + e.getMessage());
                conectado = false;
            }
        }).start();
    }

    public void iServer(int puerto) throws IOException{
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Esperando al oponente...");
            this.soc = serverSocket.accept(); // Aquí el programa se detiene hasta que alguien se conecte
            iniciar();
        }    
    }   

    public void iCliente(String ip, int puerto) throws IOException{
        this.soc= new Socket(ip, puerto);
        iniciar();
    }

    public void enviarPuntos(int puntos){
        if(this.conectado && op!=null){
            op.println(puntos);
        }
    }

    public void ponerPuntos(Consumer<Integer> callback){
        this.PuntoRecibido =callback;
    }

    public void cerrar(){
        try{
            conectado=false;
            if(soc !=null) soc.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean isConectado() {
        return conectado;
    }
    

}
