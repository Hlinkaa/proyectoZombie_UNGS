package juego;

import java.awt.Color;
import entorno.Entorno;

public class Plantas {
    private int x;
    private int y;
    private int diametro;
    private Color color;

    public Plantas(int x, int y) {
        this.x = x;
        this.y = y;
        this.diametro = 50;
        this.color = Color.blue; //Color de la planta
    }

    public void dibujar(Entorno e) {
        e.dibujarCirculo(x, y, diametro, color);
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
