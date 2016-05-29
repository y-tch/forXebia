/**
 * 
 */
package mowitnow.test;

import static org.junit.Assert.fail;

import org.junit.Test;

import mowitnow.controller.Controller;

/**
 * @author Y.Tchirikov
 *
 * Test automatisé
 */
public class TestController {

	/**
	 * Test method for {@link mowitnow.controller.Controller#execute(java.lang.String)}.
	 */
	@Test
	public final void testExecuteString() {
		Controller controller = new Controller();
		try {
			controller.execute("5 5\n"
					+"1 2 N\n"
					+"GAGAGAGAA\n"
					+"3 3 E\n"
					+"AADAADADDA");
		}
		catch(Exception e) {
			fail("Test controller fail :\n"+e);
		}
		finally {
			if(controller != null) {
				try { controller.clean(); }
				catch(Exception e) { }
			}
		}
	}

}
