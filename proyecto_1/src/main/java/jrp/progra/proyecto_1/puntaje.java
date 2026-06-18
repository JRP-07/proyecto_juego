package jrp.progra.proyecto_1;

/**
 * Clase que gestiona la puntuación del jugador.
 * Permite aumentar, reducir y reiniciar el marcador.
 */
public class Puntaje {
    private int valor;

    public Puntaje() {
        this.valor = 0;
    }

    /**
     * Incrementa la puntuación en 1 punto.
     */
    public void aumentar() {
        this.valor++;
    }

    /**
     * Reduce la puntuación en 1 punto, asegurando que no sea menor a 0.
     */
    public void reducir() {
        if (this.valor > 0) {
            this.valor--;
        }
    }

    /**
     * Reinicia la puntuación a cero.
     */
    public void reiniciar() {
        this.valor = 0;
    }

    /**
     * @return El valor actual de la puntuación.
     */
    public int getValor() {
        return valor;
    }
}
