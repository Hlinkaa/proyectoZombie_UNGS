package juego;

public class Colisiones {

    // Método para verificar si una bala toca a un zombie
	public static boolean hayColision(Bala b, Zombie z) {
        double dx = b.getX() - z.getX();
        double dy = b.getY() - z.getY();
        double distancia = Math.sqrt(dx * dx + dy * dy);
        return distancia < (b.getAncho() / 2 + z.getDiametro() / 2);
    }
}
