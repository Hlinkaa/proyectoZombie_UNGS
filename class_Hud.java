package juego;

import java.awt.Color;
import entorno.Entorno;

public class altoHUD {
    private int alto;
    private int ancho;

    public altoHUD(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo(ancho / 2, alto / 2, ancho, alto, 0, Color.black);

        int anchoCasilla = 100;
        int altoCasilla = 100;
        int margenIzquierdo = 100;
        Color[] coloresSelector = { Color.pink, Color.pink.darker(), Color.pink.darker().darker() };

        for (int i = 0; i < coloresSelector.length; i++) {
            int x = margenIzquierdo + i * (anchoCasilla + 10);
            int y = altoCasilla / 2;
            entorno.dibujarRectangulo(x, y, anchoCasilla, altoCasilla, 0, coloresSelector[i]);
        }
    }

    public int getAlto() {
        return alto;
    }
}
