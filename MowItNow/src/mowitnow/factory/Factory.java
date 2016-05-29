/**
 * 
 */
package mowitnow.factory;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

import mowitnow.core.Commande;
import mowitnow.core.Orientation;
import mowitnow.core.Pelouse;
import mowitnow.core.Task;
import mowitnow.core.Tondeuse;

/**
 * @author Y.Tchirikov
 *
 * IO bibliotheque
 */
public class Factory {
	
	/**
	 * Lire le flux & init la pelouse avec les intructions
	 * @param reader	Flux des instructions
	 * @return	{@link Pelouse} & des {@link Task}s
	 * @throws IOException
	 */
	public static Map.Entry<Pelouse, ArrayList<Task>> readStream(Reader reader)
		throws Exception
	{
		if(reader == null)
			throw new IllegalArgumentException("le flux est vide");
		
		String line = null;
		BufferedReader bReader = null;
		try {
			bReader = new BufferedReader(reader);
			line = bReader.readLine();
			if(line == null)
				throw new Exception("le fichier est incorrect : 1ere ligne");
			String[] arr = line.split(" ");
			if(arr.length != 2)
				throw new Exception("le fichier est incorrect : taille de la pelouse. ligne = ["+line+"]");
			Pelouse pelouse = null;
			try { 
				pelouse = new Pelouse(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
			}
			catch(Exception e) {
				throw new Exception("le fichier est incorrect : taille de la pelouse. ligne = ["+line+"]", e);
			}
			
			ArrayList<Task> tasks = new ArrayList<Task>();
			Tondeuse tondeuse = null;
			ArrayList<Commande> commandes = null;
			Commande commande = null;
			while((line = bReader.readLine()) != null) {
				if(tondeuse == null) {
					arr = line.split(" ");
					if(arr.length != 3)
						throw new Exception("le fichier est incorrect : position de la tondeuse. ligne = ["+line+"]");
					
					tondeuse = new Tondeuse(
							new Point(Integer.parseInt(arr[0]), Integer.parseInt(arr[1])),
							Orientation.valueOf(arr[2])
							);
				}
				else {
					if(commandes == null)
						commandes = new ArrayList<Commande>();
					
					for(char c : line.toCharArray()) {
						commande = Commande.valueOf(""+c);
						if(commande == null)
							throw new Exception("le fichier est incorrect : instruction de la tondeuse. ligne = ["+line+"]");
						commandes.add(commande);
					}
					
					tasks.add( new Task(tondeuse, commandes) );
					
					tondeuse = null;
					commandes = null;
				}
			}
			
			return new AbstractMap.SimpleEntry<Pelouse, ArrayList<Task>>(pelouse, tasks);
		}
		finally {
			if(bReader != null) {
				try { bReader.close(); }
				catch(Exception e){}
			}
		}
	}
}
