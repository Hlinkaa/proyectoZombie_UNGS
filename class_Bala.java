package juego;

import entorno.Entorno;
import java.awt.Color;

public class Bala {
    private double x;
    private double y;
    private int diametro = 30; //  Diámetro de la bala
    private int velocidad = 5;

    public Bala(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void mover() {
        x += velocidad; // La bala avanza hacia la derecha
    }

    public void dibujar(Entorno e) {
        e.dibujarCirculo(x, y, diametro, Color.orange); // Dibuja la bala
    }

    //  Métodos para colisiones
    public boolean estaFueraDePantalla(int anchoPantalla) {
        return x > anchoPantalla;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // Para colisiones usamos el diámetro como "ancho" y "alto"
    public int getAncho() {
        return diametro;
    }

    public int getAlto() {
        return diametro;
    }
}
