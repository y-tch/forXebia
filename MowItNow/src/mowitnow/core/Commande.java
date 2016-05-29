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
	 * Pivoter la tondeuse de 90� � droite sans d�placer
	 */
	D,
	/**
	 * Pivoter la tondeuse de 90� � gauche sans d�placer
	 */
	G,
	/**
	 * Avance la tondeuse sans modifier son orientation.
	 */
	A;
}
