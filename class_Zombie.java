package juego;

import java.awt.Color;
import entorno.Entorno;

//Esta clase es la de los zombies, los hago moverse y los dibujo
public class Zombie {
    private double x;
    private double y;
    private int diametro;
    private Color color;
    private int vida = 100 ; // Vida el zombi

    //Constructor del zombie
    public Zombie(double x, double y, int diametro, Color color, int vida) {
        this.x = x;
        this.y = y;
        this.diametro = diametro;
        this.color = color;
        this.vida = vida;
    }

    //Lo dibujo en pantalla
    public void dibujar(Entorno entorno) {
        entorno.dibujarCirculo(x, y, diametro, color);
//         Dibujo una barra de vida arriba del zombie
//        entorno.dibujarCirculo(x, y - diametro / 1.5, diametro, 5, Color.red);
//        entorno.dibujarCirculo(x - (diametro - (vida / 2)) / 2, y - diametro / 1.5, vida, 5, Color.green);
  }
    
    public void restarVida(int cantidad) {
        vida -= cantidad;}
    public int getVida() {
        return vida;
    }
    
    //Cuando recibe daño, le bajo la vida
    public void recibirDanio(int d) {
        vida -= d;
        if (vida < 0) {
            vida = 0;
        }
    }
    
	 //Hace que el zombie avance hacia la izquierda
	    public void moverIzq() {
	        x -= 0.5; 	//Velocidad del movimiento
	    }
	//Reviso si el zombie ya se murió
    public boolean estaMuerto() {
        return vida <= 0;
    }

    // Gets y Sets
    public double getX() { return x; }
    public void setX(double nuevoX) { this.x = nuevoX; }
    public double getY() { return y; }
    public int getDiametro() { return diametro; }
    }
