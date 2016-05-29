/**
 * 
 */
package mowitnow.core;

/**
 * @author Y.Tchirikov
 *
 * Une commande pour pivoter ou avancer la {@link Tondeuse}
 */
public enum Commande {
	/**
	 * Pivoter la tondeuse de 90° à droite sans déplacer
	 */
	D,
	/**
	 * Pivoter la tondeuse de 90° à gauche sans déplacer
	 */
	G,
	/**
	 * Avance la tondeuse sans modifier son orientation.
	 */
	A;
}
