package jrp.progra.proyecto_1;

import java.util.Random;

/**
 * Clase encargada de gestionar la lógica de los colores del juego.
 * Proporciona nombres de colores y sus códigos hexadecimales correspondientes.
 */
public class Colores {
    private Random r = new Random();
    private String colores[] = { "Rojo", "Azul", "Verde", "Amarillo" };
    private String numcolor[] = { "#FF3333", "#3333FF", "#33FF33", "#FFFF00" };

    /**
     * Genera un nombre de color aleatorio de la lista.
     * @return String con el nombre del color.
     */
    public String randomTexto(){
        return colores[r.nextInt(colores.length)];
    }

    /**
     * Genera un código hexadecimal de color aleatorio de la lista.
     * @return String con el código hexadecimal del color.
     */
    public String randomColor(){
        return numcolor[r.nextInt(numcolor.length)];
    }
}
