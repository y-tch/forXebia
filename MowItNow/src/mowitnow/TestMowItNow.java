package mowitnow;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import mowitnow.test.TestController;

/**
 * @author Y.Tchirikov
 *
 * Lancer test automatisé
 */
public class TestMowItNow {

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(TestController.class);
	    for (Failure failure : result.getFailures()) {
	      System.out.println(failure.toString());
	    }
	}

}
