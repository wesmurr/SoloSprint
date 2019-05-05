package software_masters.gui_test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javafx.scene.control.TextField;

/**
 * @author Wesley Murray
 *
 */
class CompareViewTest extends GuiTestBase {
	protected final String toggleEdits = "#editsToggle";
	private final String editsList = "#editsList";
	private final String planLabelID = "#planLabel";
	protected final String altPlanLabelID = "#altPlanLabel";
	private final String backID = "#backToPlansButton";
	private final String dataFieldID = "#dataField";
	private final String logoutID = "#logoutButton";
	private final String nameFieldID = "#nameField";
	private final String saveID = "#saveButton";
	private final String treeViewID = "#treeView";
	private final String yearFieldID = "#yearField";
	private final String yearLabelID = "#yearLabel";
	
	/**
	 * moves from connect to server screen to plan edit view
	 */
	protected void getToPlanEditView(String item) {
		clickOn("Connect");
		((TextField) find("#usernameField")).setText("user");
		((TextField) find("#passwordField")).setText("user");
		clickOn("#loginButton");
		clickOn(item);
	}
	
	/**
	 * helper method that checks that expected buttons appear
	 */
	protected void defaultButtonTest() {
		find("Back");
		find("Log Out");
	}
	
	
	/**
	 * test to verify valid initialization
	 */
	void defaultTest() { 
		getToPlanEditView("2019");
		defaultButtonTest();
		assertEquals("2019",((TextField) find("#yearField")).getText());
		this.verify(planLabelID, "Original Plan");
		this.verify(yearLabelID, "Year");
	}
	
	/**
	 * test back button
	 */
	@Test
	void backTest() { 
		//test back with no changes
		//test back button after changes have been made
	}
	
	/**
	 * make sure changes are handled on close
	 */
	@Test
	void testClose() {
		//test close when no changes occur
		//test close when changes have occurred. need popup
	}
	
	/**
	 * make sure you cannot edit the alternate plan items
	 */
	@Test
	void testCannotEditAlternatePlan() {
		//try to add text to alternate plan
	}
	
	/**
	 * makes sure the user can navigate both plan trees
	 */
	@Test
	void testPlanNavigation() {
		//make sure the user can change sections
	}
	
	/**
	 * verifies difference detection is working
	 */
	@Test
	void testDifferenceDetection() {
		//make sure the user can change sections
	}
	
	
}
