/**
 * 
 */
package mowitnow.core;

import java.util.ArrayList;

/**
 * @author Y.Tchirikov
 * 
 * Définir une tache : une tondeuse avec ses instructions 
 */
public class Task {
	private Tondeuse tondeuse;
	private ArrayList<Commande> commandes;
	
	/**
	 * Retourne la tondeuse 
	 * @return {@link Tondeuse}
	 */
	public Tondeuse getTondeuse() {
		return tondeuse;
	}
	/**
	 * Mettre la tondeuse
	 * @param tondeuse
	 */
	public void setTondeuse(Tondeuse tondeuse) {
		if(tondeuse == null)
			throw new IllegalArgumentException("la tondeuse est vide");
		this.tondeuse = tondeuse;
	}
	
	/**
	 * Retourne la liste des instructions
	 * @return {@link Commande}
	 */
	public ArrayList<Commande> getCommandes() {
		return commandes;
	}
	/**
	 * Mettre les instructions
	 * @param commandes
	 */
	public void setCommandes(ArrayList<Commande> commandes) {
		if(commandes == null)
			throw new IllegalArgumentException("la liste des commandes est vide");
		this.commandes = commandes;
	}
	
	/**
	 * Créer une tache
	 * @param tondeuse
	 * @param commandes
	 */
	public Task(Tondeuse tondeuse, ArrayList<Commande> commandes) {
		setTondeuse(tondeuse);
		setCommandes(commandes);
	}
	
}
