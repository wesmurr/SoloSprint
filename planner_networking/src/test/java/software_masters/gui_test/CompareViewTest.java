package software_masters.gui_test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import javafx.scene.control.TextField;

/**
 * @author Wesley Murray
 *
 */
class CompareViewTest extends GuiTestBase {
	protected final String toggleEdits = "#editsToggle";
	private final String editsList = "#editsList";
	protected final String planLabelID = "#planLabel";
	protected final String altPlanLabelID = "#altPlanLabel";
	private final String backPlansID = "#backToPlansButton";
	protected final String backID = "#backButton";
	protected final String dataFieldID = "#dataField";
	protected final String logoutID = "#logoutButton";
	private final String nameFieldID = "#nameField";
	private final String saveID = "#saveButton";
	protected final String treeViewID = "#treeView";
	private final String yearFieldID = "#yearField";
	protected final String yearLabelID = "#yearLabel";
	private final String altNameFieldID = "#altNameField";
	private final String altDataFieldID = "#altDataField";
	protected final String altTreeViewID = "#altTreeView";
	
	/**
	 * Helper method that verifies plan branch structure is displayed for original plan
	 */
	protected void checkBranch() {
		final String[] names = { "Mission", "Goal", "Learning Objective", "Assessment Process", "Results" };
		this.checkBranch(names);
	}

	protected void checkBranch(String[] names) {
		for (final String name : names) {
			doubleClickOn(name);
			checkPage(name, "");
		}
	}

	/**
	 * Helper method that checks the content displayed for a given node for the original plan based on
	 * provided strings.
	 *
	 * @param name
	 * @param content
	 */
	protected void checkPage(String name, String content) {
		verifyField(this.nameFieldID, name);
		verifyField(this.dataFieldID, content);
	}

	
	/**
	 *  Helper method that checks the content displayed for a given node for the original plan based on
	 *  provided strings. This version also checks the year field.
	 *  
	 * @param name
	 * @param content
	 * @param year
	 */
	private void checkPage(String name, String content, String year) {
		verifyField(this.yearFieldID, year);
		checkPage(name, content);
	}
	
	/**
	 * Helper method that verifies alternate plan branch structure is ok
	 */
	protected void checkAltBranch(String[] names) {
		for (final String name : names) {
			doubleClickOn(name);
			checkAltPage(name, "");
		}
	}
	
	/**
	 * Helper method that checks the textfields in the alternate plan.
	 * @param name
	 * @param content
	 * @param year
	 */
	protected void checkAltPage(String name, String content) {
		verifyField(this.altNameFieldID,name);
		verifyField(this.altDataFieldID,content);
	}
	
	/**
	 * moves from connect to server screen to plan edit view
	 */
	protected void getToView(String item) {
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
		assertThrows(NoSuchElementException.class, () -> find("logout"));
	}
	
	/**
	 * test back button for exiting comparison views
	 */
	void backTest() { 
		//test back with no changes
		getToView("2019");
		clickOn(dataFieldID);
		verifyField(dataFieldID, "");
		clickOn(backID);
		verifyField(dataFieldID, "");
		find(saveID);
		clickOn(logoutID);
		assertThrows(NoSuchElementException.class, () -> find("Yes"));
		this.afterEachTest();
		
		//test back with changes
		this.setUpBeforeEachTest();
		getToView("2019");
		checkPage("Mission","","2019");
		clickOn(yearFieldID);
		write("edit");
		clickOn(nameFieldID);
		write("edit");
		clickOn(dataFieldID);
		write("edit");
		checkPage("Missionedit","edit","2019edit");
		clickOn(backID);
		clickOn(backPlansID);
		clickOn("No");
		this.afterEachTest();
		
		//test back in read only
		this.setUpBeforeEachTest();
		getToView("2020 Read Only");
		clickOn(backID);
		//verify opens read only view not edit view
		assertThrows(NoSuchElementException.class, () -> find(saveID));
		
	}
	
	/**
	 * make sure changes are handled on close
	 */
	@Test
	void testClose() {
		//test close when no changes occur
		getToView("2019");
		closeCurrentWindow();
		//test close when changes have occurred. need popup
		this.setUpBeforeEachTest();
		getToView("2019");
		clickOn(this.dataFieldID);
		write("test close");
		closeCurrentWindow();
		checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		clickOn("No");
		this.setUpBeforeEachTest();
	}
	
	/**
	 * make sure you cannot edit the alternate plan items
	 */
	void testCannotEditAlternatePlan() {
		//edit
		getToView("2019");
		clickOn(this.altNameFieldID);
		write("edit");
		clickOn(this.altDataFieldID);
		write("edit");
		assertFalse(((TextField) find(this.altNameFieldID)).getText().contains("edit"));
		assertFalse(((TextField) find(this.altDataFieldID)).getText().contains("edit"));
		this.afterEachTest();
		
		//read only
		this.setUpBeforeEachTest();
		getToView("2020 Read Only");
		clickOn(this.altNameFieldID);
		write("edit");
		clickOn(this.altDataFieldID);
		write("edit");
		assertFalse(((TextField) find(this.altNameFieldID)).getText().contains("edit"));
		assertFalse(((TextField) find(this.altDataFieldID)).getText().contains("edit"));
	}
	
	/**
	 * makes sure the user can navigate original plan trees
	 */
	@Test
	void testPlanNavigation() {
		//read only
		getToView("2020 Read Only");
		doubleClickOn("Vision");
		checkPage("Vision","My Vision is to...","2020");
		doubleClickOn("Mission");
		checkPage("Mission","My Mission is to...","2020");
		String[] names= {"Objective","Strategy","Action Plan"};
		this.checkBranch(names);
		this.afterEachTest();
		
		//editable plans
		this.setUpBeforeEachTest();
		getToView("2019");
		checkBranch();
		
	}
	
	/**
	 * make sure user cannot edit a read only plan
	 */
	@Test
	void testCannotEditReadOnlyPlan() {
		getToView("2020 Read Only");
		clickOn(yearFieldID);
		write("edit");
		clickOn(nameFieldID);
		write("edit");
		clickOn(dataFieldID);
		write("edit");
		//verify changes were not made
		checkPage("Vision","My Vision is to...","2020");
	}
	
	
	
	
}
