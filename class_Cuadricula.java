package juego;

import java.awt.Color;
import entorno.Entorno;

public class Cuadricula {
    private int filas;
    private int columnas;
    private int anchoCelda;
    private int altoCelda;
    private int altoHUD;
    private Color[][] colores;

    public Cuadricula(int filas, int columnas, int anchoCelda, int altoCelda, int altoHUD) {
        this.filas = filas;
        this.columnas = columnas;
        this.anchoCelda = anchoCelda;
        this.altoCelda = altoCelda;
        this.altoHUD = altoHUD;
        this.colores = new Color[filas][columnas];
    }

    public void dibujar(Entorno entorno) {
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                int x = c * anchoCelda + anchoCelda / 2;
                int y = f * altoCelda + altoCelda / 2 + altoHUD;
                Color[] paleta = { Color.green.darker().darker().darker(), Color.green.darker().darker() };
                colores[f][c] = paleta[(f + c) % paleta.length];
                entorno.dibujarRectangulo(x, y, anchoCelda - 4, altoCelda - 4, 0, colores[f][c]);
            }
        }
    }

    public int getFilaDesdeMouse(int mouseY) {
        return (mouseY - altoHUD) / altoCelda;
    }

    public int getColumnaDesdeMouse(int mouseX) {
        return mouseX / anchoCelda;
    }

    public boolean dentroDeCampo(int mouseX, int mouseY) {
        return mouseY > altoHUD;
    }
}
