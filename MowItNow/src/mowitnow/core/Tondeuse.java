/**
 * 
 */
package mowitnow.core;

import java.awt.Point;

/**
 * @author Y.Tchirikov
 *
 * Tondeuse à gazon automatique pour {@link Pelouse}.
 */
public class Tondeuse {
	private Point position;
	private Orientation orientation;
	
	/**
	 * Retourne la position actuelle
	 * @return
	 */
	public Point getPosition() {
		return position;
	}
	/**
	 * Mettre la nouvelle position
	 * @param position
	 */
	public void setPosition(Point position) {
		if(position == null)
			throw new IllegalArgumentException("la position est vide");
		this.position = position;
	}
	/**
	 * Retourne l'orientation actuelle
	 * @return
	 */
	public Orientation getOrientation() {
		return orientation;
	}
	/**
	 * Mettre la nouvelle orientation
	 * @param orientation
	 */
	public void setOrientation(Orientation orientation) {
		if(orientation == null)
			throw new IllegalArgumentException("l'orientation est vide");
		this.orientation = orientation;
	}
	
	/**
	 * Pivoter la tondeuse de 90° à gauche 
	 */
	public void toLeft() {
		switch (orientation) {
			case N:
				orientation = Orientation.E;
				break;
			case E:
				orientation = Orientation.S;
				break;
			case S:
				orientation = Orientation.W;
				break;
			case W:
				orientation = Orientation.N;
				break;
		}
	}
	
	/**
	 * Pivoter la tondeuse de 90° à droite
	 */
	public void toRight() {
		switch (orientation) {
			case N:
				orientation = Orientation.W;
				break;
			case W:
				orientation = Orientation.S;
				break;
			case S:
				orientation = Orientation.E;
				break;
			case E:
				orientation = Orientation.N;
				break;
		}
	}
	
	/**
	 * Retourne la position et l'orientation actuelles
	 * @return
	 */
	public String getCurrentPosition() {
		return position.toString()+" "+orientation.toString();
	}
	
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" "+getCurrentPosition();
	}
	
	/**
	 * L'instance par défaut : position (0,0) et orientation Nord
	 */
	public Tondeuse() {
		this(new Point(0,0), Orientation.N);
	}
	/**
	 * L'instance avec la position et orientation définis
	 * @param position
	 * @param orientation
	 */
	public Tondeuse(Point position, Orientation orientation) {
		setPosition(position);
		setOrientation(orientation);
	}
}
