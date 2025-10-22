package juego;

import java.awt.Color;
import entorno.Entorno;

public class Zombies {
    private int x, y, diametro;
    private Color color;
	private int length;

    public Zombies(int x, int y, int diametro, Color color) {
        this.x =  x;
        this.y = y;
        this.diametro = diametro;
        this.color = color;
    }

    public void dibujar(Entorno e) {
        e.dibujarCirculo(x, y, diametro, color);
    }

    public void moverIzq() {
        this.x -= 1; // movimiento lento hacia la izquierda
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getDiametro() { return diametro; }
    public void setX(int x) { this.x = x; }
}
