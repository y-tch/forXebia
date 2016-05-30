/**
 * 
 */
package mowitnow.controller;

import java.awt.Point;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import mowitnow.core.Commande;
import mowitnow.core.Pelouse;
import mowitnow.core.Task;
import mowitnow.core.Tondeuse;
import mowitnow.factory.Factory;

/**
 * @author Y.Tchirikov
 *
 * G�rer le tondage de la {@link Pelouse} par N {@link Tondeuse}s.
 */
public class Controller {
	private Pelouse pelouse;
	private ArrayList<Task> tasks;
	
	/**
	 * Effectuer le tondage de la pelouse selon le texte d'entr�e.
	 * Imprimer dans la sortie par d�faut 
	 * @param text	Texte des instructions
	 * @throws Exception	Si probl�me de lecture
	 * @see #execute(String, PrintStream)
	 */
	public void execute(String text)
		throws IOException, Exception
	{
		execute(text, System.out);
	}
	
	/**
	 * Effectuer le tondage de la pelouse selon le texte d'entr�e.
	 * Imprimer dans la sortie d�finie.
	 * @param text	Texte des instructions
	 * @param stream	Sortie d'impression. Si <code>null</code> alors la sortie par d�faut 
	 * @throws IOException	Si probl�me de lecture
	 * @see #execute(Reader, PrintStream)
	 */
	public void execute(String text, PrintStream stream)
		throws IOException, Exception
	{
		if(text == null)
			throw new IllegalArgumentException("le texte est vide");
		
		StringReader reader = null;
		try {
			reader = new StringReader(text);
			execute(reader, stream);
		}
		finally {
			if(reader != null) {
				try { reader.close(); }
				catch(Exception e){}
			}
		}
	}
	
	/**
	 * Effectuer le tondage de la pelouse selon le fichier d'entr�e.
	 * Imprimer dans la sortie par d�faut.
	 * @param file	Fichier des instructions
	 * @throws IOException Si le fichier n'existe pas ou probl�me de lecture
	 * @see #execute(File, PrintStream)
	 */
	public void execute(File file)
		throws IOException, Exception
	{
		execute(file, System.out);
	}
	
	/**
	 * Effectuer le tondage de la pelouse selon le fichier d'entr�e.
	 * Imprimer dans la sortie d�finie.
	 * @param file	Fichier des instructions
	 * @param stream	Sortie d'impression. Si <code>null</code> alors la sortie par d�faut 
	 * @throws IOException Si le fichier n'existe pas ou probl�me de lecture
	 * @throws Exception Si le fichier est incorrect
	 * @see #execute(Reader, PrintStream)
	 */
	public void execute(File file, PrintStream stream)
		throws IOException, Exception
	{
		if(file == null)
			throw new IllegalArgumentException("le fichier est vide");
		if( !file.exists() )
			throw new IOException("le fichier est introuvable");
		
		// lire le fichier des params
		Reader reader = null;
		try {
			reader = new FileReader(file);
		
			execute(reader, stream);
		}
		finally {
			if(reader != null) {
				try { reader.close(); }
				catch(Exception e){}
			}
		}
	}
	
	/**
	 * Effectuer le tondage de la pelouse selon le flux d'entr�e.
	 * Imprimer dans la sortie d�finie.
	 * <br><code>Si la position apr�s mouvement est en dehors de la pelouse, 
	 * la tondeuse ne bouge pas, conserve son orientation et traite la commande suivante.
	 * </code><br> 
	 * @param reader	Flux d'entr�e
	 * @param stream	Sortie d'impression. Si <code>null</code> alors la sortie par d�faut 
	 * @throws Exception Si le contenu est incorrect
	 */
	private void execute(Reader reader, PrintStream stream)
		throws IOException, Exception
	{
		// lire le flux des params
		try {
			Map.Entry<Pelouse, ArrayList<Task>> job = Factory.readStream(reader);
		
			// init la pelouse et les tondeuses avec leurs commandes
			pelouse = job.getKey();
			tasks = job.getValue();
			
			stream.println("---");
			stream.println(new Date().toString());
			stream.println("init "+pelouse.toString());
			stream.println("Nb. des tondeuses � executer "+tasks.size());
			// lancer des tondeuses une apr�s autre
			Task task = null;
			for(int i=0; i < tasks.size(); i++) {
				task = tasks.get(i);
				stream.println();
				// le numero et la position initiale
				stream.println("tondeuse #"+i+". le lancement : "+task.getTondeuse().getCurrentPosition());
				// check la position
				if( pelouse.contains( task.getTondeuse().getPosition() ) ) {
					// bouger la tondeuse selon les commandes
					for(Commande c : task.getCommandes()) {
						stream.print("tondeuse #"+i+". "+c+" => ");
						if( Commande.A.equals(c) ) { // avancer dans la direction actuelle
							int x = task.getTondeuse().getPosition().x;
							int y = task.getTondeuse().getPosition().y;
							switch( task.getTondeuse().getOrientation() ) {
								case N :
									y++;
									break;
								case E :
									x++;
									break;
								case S :
									y--;
									break;
								case W :
									x--;
									break;
							}
							// mettre la nouvelle position
							task.getTondeuse().setPosition( new Point(x, y) );
							
							if( !pelouse.contains(x, y) ) { 
								// la tondeuse est en dehors. on l'arrete et passe � la suivante 
								stream.println(" !!! en dehors da la pelouse. l'arret (selon la sp�cification)");
								break;
							}
						}
						else { // pivoter la tondeuse
							if( Commande.D.equals(c) )
								task.getTondeuse().toRight();
							else
								task.getTondeuse().toLeft();
						}
						// la position finale de la tondeuse
						stream.println(task.getTondeuse().getCurrentPosition());
					}	
				} else { 
					// la tondeuse est en dehors. on l'arrete et passe � la suivante 
					stream.println(" !!! en dehors da la pelouse. l'arret (selon la sp�cification)");
					continue;
				}
				// la position finale de la tondeuse
				stream.println("tondeuse #"+i+". l'arret "+task.getTondeuse().getCurrentPosition());
			}
			
			stream.println("fin "+pelouse.toString());
		}
		finally {
			if(reader != null) {
				try { reader.close(); }
				catch(Exception e){}
			}
		}
	}
	
	/**
	 * Clean instance avant d�truire
	 */
	public void clean() {
		if(tasks != null) {
			tasks.clear();
			tasks = null;
		}
		pelouse = null;
	}
	
	@Override
	protected void finalize() throws Throwable {
		clean();
		super.finalize();
	}
	
	/**
	 * Cr�er un instance par d�faut
	 */
	public Controller() {
		
	}	
}
