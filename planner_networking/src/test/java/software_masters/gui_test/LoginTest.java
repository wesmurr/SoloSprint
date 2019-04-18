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
import application.MockMain;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import software_masters.planner_networking.ServerImplementation;

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
			//ServerImplementation.main(null);
			ApplicationTest.launch(MockMain.class);
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
		testInvalidUser();
		testInvalidPassword();
		testInvalidUsernameAndPassword();
		testValidLogin();
		afterEachTest();
	}
	
	/**
	 * Helper method for grabbing nodes
	 * @param query
	 * @return
	 */
	public <T extends Node> T find(final String query) {
		
		return (T) lookup(query).queryAll().iterator().next();
	}
	
	
	/**
	 * Verifies that labels are populated with the intended text
	 */
	private void testDefaultValues()
	{
		verifyThat("#usernameLabel", (Label label)-> {return label.getText().equals("Username");});
		verifyThat("#passwordLabel", (Label label)-> {return label.getText().equals("Password");});
	}
	
	/**
	 * Verifies that the plan selection window is not displayed when invalid username is entered,
	 * by checking that the username label is still present
	 */
	private void testInvalidUser()
	{
		clickOn("#usernameField");
		write("INVALID USERNAME");
		clickOn("#loginButton");
		verifyThat("#usernameLabel", (Label label)-> {return label.getText().equals("Username");});
		clickOn("OK");
		TextField textfield = (TextField)find("#usernameField");
		textfield.setText("user");
		
	}
	/**
	 * Verifies that the plan selection window is not displayed when invalid password is entered,
	 * by checking that the username label is still present
	 */
	private void testInvalidPassword()
	{
		clickOn("#passwordField");
		write("INVALID PASSWORD");
		clickOn("#loginButton");
		verifyThat("#usernameLabel", (Label label)-> {return label.getText().equals("Username");});
		clickOn("OK");
		TextField textfield = (TextField)find("#passwordField");
		textfield.setText("user");
		
	}
	
	/**
	 * Verifies that the plan selection window is not displayed when invalid username and password is entered,
	 * by checking that the username label is still present
	 */
	private void testInvalidUsernameAndPassword()
	{
		clickOn("#passwordField");
		write("INVALID PASSWORD");
		clickOn("#usernameField");
		write("INVALID USERNAME");
		clickOn("#loginButton");
		verifyThat("#usernameLabel", (Label label)-> {return label.getText().equals("Username");});
		clickOn("OK");
		TextField textfield = (TextField)find("#passwordField");
		textfield.setText("user");
	    textfield = (TextField)find("#usernameField");
		textfield.setText("user");
		
	}
	
	/**
	 * Verifies that the plan selection window is displayed when a valid username and password is entered,
	 * by checking that the select plan label is present.
	 */
	private void testValidLogin()
	{
		clickOn("#loginButton");
		verifyThat("#selectPlanLabel", (Label label)-> {return label.getText().equals("Select Plan");});
		
	}
	

}
