package mowitnow;

import java.io.File;

import mowitnow.controller.Controller;

/**
 * @author Y.Tchirikov
 *
 * Lancer MowItNow
 */
public class MowItNow {

	public static void main(String[] args) {
		if(args.length > 0) {
			Controller controller = new Controller();
			try {
				String[] arr = args[0].split("[=]");
				if(arr.length == 2 && arr[0].equalsIgnoreCase("file"))
					controller.execute( new File(arr[1]) );
			}
			catch(Exception e) {
				System.err.println(e);
			}
			finally {
				if(controller != null) {
					try { controller.clean(); }
					catch(Exception e) { }
				}
			}
			return;
		}

		System.out.println("MowItNow -file=[le fichier d'entree]");
	}

}
