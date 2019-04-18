/**
 * 
 */
package software_masters.gui_test;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import application.Main;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

/**
 * @author lee.kendall
 *
 */
class LoginTest extends ApplicationTest {

	/**
	 * @throws java.lang.Exception
	 */
	private void setUp() {
		try {
			ApplicationTest.launch(Main.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	private void afterEachTest() {
		try {
			FxToolkit.hideStage();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		release(new KeyCode[] {});
		release(new MouseButton[] {});
	}

	
	/**
	 * Main test which calls other tests in sequential order
	 */
	@Test
	void mainTest() {
		setUp();
		testDefaultValues();
		
		afterEachTest();
	}
	
	/**
	 * 
	 */
	private void testDefaultValues()
	{
		verifyThat("#usernameLabel", (Label label)-> {return label.getText().equals("Username");});
		verifyThat("passwordLabel", (Label label)-> {return label.getText().equals("Password");});
	}
	

}
