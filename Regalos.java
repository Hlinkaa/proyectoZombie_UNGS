package juego;

import java.awt.Color;
import entorno.Entorno;

public class Regalos {
    // Atributos principales del regalo
    private int x;
    private int y;
    private int diametro;
    private Color color;

    // Constructor: crea un nuevo regalo con posición, tamaño y color
    public Regalos(int x, int y, int d, Color c) {
        this.x = x;
        this.y = y;
        this.diametro = d;
        this.color = c;
    }

    // Dibuja al regalo como un círculo en el entorno
    public void dibujar(Entorno e) {
        e.dibujarCirculo(x, y, diametro, color);
    }

    // Devuelve la posición X del regalo
    public int getX() {        
        return this.x;
    }

    // Cambia la posición X (solo si no sale del borde izquierdo)
    public void setX(int valor) {
        if (valor >= 0) {
            this.x = valor;            
        }
    }

    // Devuelve la posición Y del regalo
    public int getY() {
        return y;
    }

    // Cambia la posición Y
    public void setY(int y) {
        this.y = y;
    }

    // Devuelve el tamaño (diámetro)
    public int getDiametro() {
        return diametro;
    }

    // Cambia el tamaño
    public void setDiametro(int diametro) {
        this.diametro = diametro;
    }

    // Devuelve el color actual del regalo
    public Color getColor() {
        return color;
    }

    // Cambia el color (ahora usa el color que se pasa por parámetro)
    public void setColor(Color color) {
        this.color = color;
    }

    // Verifica si el regalo se salió de la pantalla por abajo
    public boolean colisionaConEntornoPorAbajo(Entorno e) {
        return this.y - this.diametro / 2 > e.alto();
    }
}
