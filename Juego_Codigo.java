package juego;

import entorno.Herramientas;
import entorno.Entorno;
import entorno.InterfaceJuego;
import java.awt.Color;
import java.util.Random;

public class Juego extends InterfaceJuego {
    // Objetos principales
    private Entorno entorno;
    private Zombie[] zombies;
    private Regalos[] regalos;
    private Plantas[] plantas;
    private Bala[] balas;
    private Cuadricula cuadricula;
    private altoHUD alto_HUD;
    private Random random;

    // Configuración general del campo
    private final int filas = 5;
    private final int columnas = 10;
    private final int anchoCelda = 100;
    private final int altoCelda = 100;
    private final int ALTO_HUD = 100; // parte superior

    // HUD de la planta																								---MOVER A ALTOHUD---
    private int xPlantaHUD = 100;
    private int yPlantaHUD = (ALTO_HUD / 2);
    private boolean plantaSeleccionada = false;
    int anchoPlanta = 80;	
    int altoPlanta = 80;

    // Constructor
    public Juego() {
        this.entorno = new Entorno(this, "Plantas contra Grinchs Zombies", 1000, 600);
        this.random = new Random();

        this.alto_HUD = new altoHUD(entorno.ancho(), ALTO_HUD);
        this.cuadricula = new Cuadricula(filas, columnas, anchoCelda, altoCelda, ALTO_HUD);
        this.plantas = new Plantas[filas * columnas];
        this.balas = new Bala[100]; // Hasta 100 balas activas
        this.zombies = generarZombies();
        this.regalos = generarRegalos();

        //Posicion del selector de plantas
        xPlantaHUD = 100; 	
        yPlantaHUD = alto_HUD.getAlto() / 2;

        //Musica
        try {
            Herramientas.loop("Loonboon.aiff");
        } catch (Exception e) {
            System.out.println("Error al reproducir música: " + e.getMessage());
        }

        this.entorno.iniciar();
    }

//												-----INICIO DEL TICK-----
    public void tick() {
        // Dibuja el campo y el HUD
        cuadricula.dibujar(entorno);
        alto_HUD.dibujar(entorno);

        // Planta del HUD
        entorno.dibujarRectangulo(xPlantaHUD, yPlantaHUD, anchoPlanta, altoPlanta, 0, Color.GREEN.darker());

        manejarClicks();

        // Si estoy arrastrando la planta																		---PASAR A PLANTAS O CREAR NUEVA CLASE CARTAS---
        if (plantaSeleccionada) {
            int mx = entorno.mouseX();
            int my = entorno.mouseY();
            entorno.dibujarRectangulo(mx, my, 80, 80, 0, new Color(0, 255, 0, 100));
        }

        dibujarPlantas();				//Dibija la planta una vez colocada en el tablero
        dispararBalasDesdePlantas(); 	//Dibuja las balas 
        actualizarBalas();  			//Acá se manejan los choques
        actualizarZombies();
        dibujarRegalos();
    }

    // Hace que las plantas disparen cada tanto																	---PASAR A BALAS, PLANTAS O HACER CLASE NUEVA---
    private void dispararBalasDesdePlantas() {
        for (Plantas p : plantas) {
            if (p != null) {
                if (Math.random() < 0.01) { // Probabilidad de disparo
                    agregarBala(new Bala(p.getX() + 30, p.getY()));
                }
            }
        }
    }

    //Agrega una nueva bala si hay lugar																		---PASAR A BALAS---
    private void agregarBala(Bala nuevaBala) {
        for (int i = 0; i < balas.length; i++) {
            if (balas[i] == null) {
                balas[i] = nuevaBala;
                break;
            }
        }
    }

    //Mueve las balas, las dibuja y detecta colisiones															---PASAR A BALAS---
    private void actualizarBalas() {
        for (int i = 0; i < balas.length; i++) {
            Bala b = balas[i];
            if (b != null) {
                b.mover();
                b.dibujar(entorno);

                // Si la bala sale de la pantalla y las resetea													---PASAR A BALAS---
                if (b.estaFueraDePantalla(entorno.ancho())) {
                    balas[i] = null;
                    continue;
                }

                // Colisión bala con zombie																		---PASAR A ZOMBIE---
                for (int j = 0; j < zombies.length; j++) {
                    Zombie z = zombies[j];
                    if (z != null && Colisiones.hayColision(b, z)) {
                        balas[i] = null; // La bala desaparece
                        z.restarVida(20); // Laja vida

                        // Si el zombie muere, desaparece
                        if (z.getVida() <= 0) {
                            zombies[j] = null;
                        }
                    }
                }
            }
        }
    }

    //  Genera zombi																							---RESUMIR O PASAR A ZOMBIE---
    private Zombie[] generarZombies() {
        int total = 10;
        Zombie[] z = new Zombie[total];

        for (int i = 0; i < total; i++) {
            int fila = random.nextInt(filas);
            int x = entorno.ancho() + random.nextInt(500);
            int y = ALTO_HUD + fila * altoCelda + altoCelda / 2;
            Color colorZombie = new Color(150 + random.nextInt(100), 0, 0);
            z[i] = new Zombie(x, y, 70, colorZombie, 100);
        }
        return z;
    }

    // Regalos																									---PASAR A REGALOS---
    private Regalos[] generarRegalos() {
        Regalos[] r = new Regalos[filas];
        for (int i = 0; i < filas; i++) {
            int x = anchoCelda / 2;
            int y = i * altoCelda + altoCelda / 2 + ALTO_HUD;
            r[i] = new Regalos(x, y, 60, Color.YELLOW, 1);
        }
        return r;
    }

    //  Colocar plantas con el mouse
    private void manejarClicks() {
        if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
            int mouseX = entorno.mouseX();
            int mouseY = entorno.mouseY();

            if (mouseY < ALTO_HUD &&
                mouseX > xPlantaHUD - 40 && mouseX < xPlantaHUD + 40 &&
                mouseY > yPlantaHUD - 40 && mouseY < yPlantaHUD + 40) {
                plantaSeleccionada = true;

            } else if (plantaSeleccionada && cuadricula.dentroDeCampo(mouseX, mouseY)) {
                int fila = cuadricula.getFilaDesdeMouse(mouseY);
                int columna = cuadricula.getColumnaDesdeMouse(mouseX);
                int indice = fila * columnas + columna;

                if (plantas[indice] == null) {
                    int x = columna * anchoCelda + anchoCelda / 2;
                    int y = fila * altoCelda + altoCelda / 2 + ALTO_HUD;
                    plantas[indice] = new Plantas(x, y);
                    plantaSeleccionada = false;
                }
            }
        }
    }

    //  Dibuja las plantas									
    private void dibujarPlantas() {
        for (Plantas p : plantas) {
            if (p != null) {
                p.dibujar(entorno);
            }
        }
    }

    // Mueve los zombies												
    private void actualizarZombies() {
        for (Zombie z : zombies) {
            if (z != null) {
                z.dibujar(entorno);
                z.moverIzq();

                if (z.getX() < -z.getDiametro()) {
                    z.setX(entorno.ancho() + random.nextInt(400));
                }
            }
        }
    }

    private void dibujarRegalos() {
        for (Regalos r : regalos) {
            r.dibujar(entorno);
        }
    }

    public static void main(String[] args) {
        new Juego();
    }
}








/*
 * -----------------------------------------------------------TASK LIST------------------------------------------------------------------
 * 1- Barra de vida. 															--OBLIGATORIO
 * 2- Fotos. 																	--OPCIONAL
 * 3- Planta dispare solo cuando hay zombie (CAMBIAR - una vez por colision).	--OBLIGATORIO
 * 4- Acomodar codigos.															--OBLIGATORIO
 * 5- Ocupar la casilla de regalos para no plntar encima.						--OBLIGATORIO
 * 6- Colision de zombies con plantas y regalos.									--OBLIGATORIO / OPCIONAL
 * 7- Minutos, puntaje y temporizador comun y el de plantas (Menu).				--OBLIGATORIO
 * 8- Seleccion y movimiento de planta con caracteres WASD.						--OBLIGATORIO
 * -------------------------------------------------------------------------------------------------------------------------------------
 */
