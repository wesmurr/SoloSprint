/**
 * 
 */
package software_masters.gui_test;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import application.MockMain;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * @author lee.kendall
 *
 */
class PlanSelectionTest extends GuiTestBase
{
	
	/**
	 * 
	 * Main test which calls other tests in sequential order
	 */
	@Test
	void mainTest()
	{
		clickOn("#loginButton");
		testDefaultValues();
		testLogout();
		testSelectPlanTemplate();
	}
	
	
	/**
	 * Verifies labels are initialized with intended text.
	 */
	private void testDefaultValues() {
		
		verifyThat("#selectPlanLabel", (Label label) ->
		{
			return label.getText().equals("Select Plan");
		});
		verifyThat("#planTemplatesLabel", (Label label) ->
		{
			return label.getText().equals("Plan Templates");
		});
		verifyThat("#departmentPlansLabel", (Label label) ->
		{
			return label.getText().equals("Department Plans");
		});	
	}
	
	/**
	 * Verifies that logout button works by clicking on button and checking for the presence of the username
	 * label in the login window.
	 */
	private void testLogout()
	{
		clickOn("#logoutButton");
		verifyThat("#usernameLabel", (Label label) ->
		{
			return label.getText().equals("Username");
		});
		clickOn("#loginButton");
	}
	
	private void testSelectPlanTemplate() {
		
		clickOn((Node) find("VMOSA"));
		sleep(4000);
		
	}
	

}
