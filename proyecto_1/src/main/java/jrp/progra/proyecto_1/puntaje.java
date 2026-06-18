package jrp.progra.proyecto_1;

public class puntaje {
    private int valor;

    public puntaje() {
        this.valor = 0;
    }

    public void aumentar() {
        this.valor++;
    }

    public void reducir() {
        if (this.valor > 0) {
            this.valor--;
        }
    }

    public void reiniciar() {
        this.valor = 0;
    }

    public int getValor() {
        return valor;
    }

}
