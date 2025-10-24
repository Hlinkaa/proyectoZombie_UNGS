package juego;

import entorno.Herramientas;
import java.awt.Color;
import entorno.Entorno;
import entorno.InterfaceJuego;
import java.util.Random;

public class Juego extends InterfaceJuego {
    // Objetos del juego
    private Entorno entorno;
    private Zombies[] zombie;
    private Regalo[] regalos;
    private Random random; 

    // Configuración de cuadrícula
    int filas = 6;
    int celdas = 12;
    int anchoCelda = 100;
    int altoCelda = 100;
    int altoHUD = 100;
    Color[][] colores = new Color[filas][celdas];

    // Constructor principal
    Juego() {
    	
    	try {
    	    String rutaMusica = "Loonboon.aiff"; // sin ruta absoluta

    	    System.out.println("Intentando reproducir: " + rutaMusica);
    	    Herramientas.loop(rutaMusica); // Reproduce en bucle

    	} catch (Exception e) {
    	    System.out.println("Error al intentar reproducir la música: " + e.getMessage());
    	}

        // Inicializa el entorno
        this.entorno = new Entorno(this, "Plantas contra Grinchs Zombies", 1200, 600);
        this.random = new Random();
     // Crea  zombie  
        int totalZombies = filas;  // un zombi por fila
        zombie = new Zombies[totalZombies];

        for (int i = 0; i < filas; i++) {
            // Calcula el centro exacto de la fila 'i'
            int x = random.nextInt(entorno.ancho());              // posición horizontal aleatoria
            int y = altoHUD + i * altoCelda + altoCelda / 2;      // centro de la fila
            zombie[i] = new Zombies(x, y, 70, Color.red.darker().darker());
        }
        // Crea regalos en todas las filas de la primera columna
        this.regalos = new Regalo[filas];
        for (int r = 0; r < filas; r++) {
            int xReg = anchoCelda / 2; // primera columna
            int yReg = r * altoCelda + altoCelda / 2 + altoHUD;
            this.regalos[r] = new Regalo(xReg, yReg, 60, Color.YELLOW);
        }

        // Inicia el entorno
        this.entorno.iniciar();
    }
    
    public void tick() {
        //Dibuja la cuadrícula del campo de juego
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < celdas; c++) {
                int x = c * anchoCelda + anchoCelda / 2;
                int y = f * altoCelda + altoCelda / 2 + altoHUD;

                Color[] paleta = {
                    Color.green.darker().darker().darker(),
                    Color.green.darker().darker()
                };
                colores[f][c] = paleta[(f + c) % paleta.length];

                entorno.dibujarRectangulo(x, y, anchoCelda - 4, altoCelda - 4, 0, colores[f][c]);
            }
        }

        // HUD superior
        entorno.dibujarRectangulo(entorno.ancho() / 2, altoHUD / 2, entorno.ancho(), altoHUD, 0, Color.black);

        // Selector de plantas
        int anchoCasilla = 100;
        int altoCasilla = 100;
        int margenIzquierdo = 50;
        Color[] coloresSelector = {
            Color.pink,
            Color.pink.darker(),
            Color.pink.darker().darker(),
        };

        for (int i = 0; i < coloresSelector.length; i++) {
            int x = margenIzquierdo + i * (anchoCasilla + 10);
            int y = altoCasilla / 2;
            entorno.dibujarRectangulo(x, y, anchoCasilla, altoCasilla, 0, coloresSelector[i]);
        }

        // Dibuja los regalos
        for (Regalo r : regalos) {
            r.dibujar(entorno);
        }

        // 🔸 Dibuja y mueve los zombis
        for (Zombies z : zombie) {
            if (z != null) {
                z.dibujar(entorno);
                z.moverIzq();
                if (z.getX() < -z.getDiametro() / 2)
                    z.setX(entorno.ancho() + z.getDiametro() / 2);
            }
        }
    }
    public static void main(String[] args) {
        Juego juego = new Juego();
    }

}
