package jrp.progra.proyecto_1;

import java.util.Random;

public class colores {
    static Random r = new Random();
    static String colores[] = { "Rojo", "Azul", "Verde", "Amarillo" };
    static String numcolor[] = { "#FF3333", "#3333FF", "#33FF33", "#FFFF00" };

    public String randomTexto(){
        return colores[r.nextInt(colores.length)];
    }

    public String randomColor(){
        return numcolor[r.nextInt(numcolor.length)];
    }

    
}
