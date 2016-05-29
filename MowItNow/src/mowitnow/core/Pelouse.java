/**
 * 
 */
package mowitnow.core;

/**
 * @author Y.Tchirikov
 * 
 * La pelouse est un surface rectangulaire divisée en grille. 
 * Coin <b>inférieur gauche</b> est supposées (0,0)
 */
public class Pelouse {
	private int width;
	private int height;
	
	/**
	 * Retourne hauteur de la pelouse
	 * @return
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * Retourne largeur de la pelouse
	 * @return
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Vérifier si la position est dans le perimetre de la pelouse
	 * @param x La position X
	 * @param y La position Y
	 * @return <code>true</code> si la position est correct. <code>false</code> si la position
	 * est en dehors de la pelouse
	 */
	public boolean contains(int x, int y) {
		return x >= 0 && width >= x && y >= 0 && height >= y;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" ["+width+"x"+height+"]";
	}
	
	/**
	 * Créer l'instance avec la taille définie (coin inférieur gauche est supposées 0,0)
	 * @param width
	 * @param height
	 */
	public Pelouse(int width, int height) {
		this.width = width;
		this.height = height;
	}
}
