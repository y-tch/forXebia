package mowitnow;

import mowitnow.ui.Frame;

/**
 * @author Y.Tchirikov
 *
 * Lancer simple MowItNow interface
 */
public class MowItNowUI {

	public static void main(String[] args) {
		Frame frame = new Frame();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.toFront();
	}

}
