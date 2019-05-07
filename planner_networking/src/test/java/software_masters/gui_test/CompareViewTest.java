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
	private final String treeViewID = "#treeView";
	private final String yearFieldID = "#yearField";
	protected final String yearLabelID = "#yearLabel";
	private final String altNameFieldID = "#altNameField";
	private final String altDataFieldID = "#altDataField";
	
	/**
	 * Helper method that verifies plan branch structure is displayed
	 */
	protected void checkBranch() {
		final String[] names = { "Mission", "Goal", "Learning Objective", "Assessment Process", "Results" };
		this.checkBranch(names);
	}

	private void checkBranch(String[] names) {
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
	 * Helper method that checks the textfields in the alternate plans.
	 * @param name
	 * @param content
	 * @param year
	 */
	private void checkAltPage(String name, String content) {
		verifyField(this.altNameFieldID,name);
		verifyField(this.altDataFieldID,content);
	}
	
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
		assertThrows(NoSuchElementException.class, () -> find("logout"));
	}
	
	/**
	 * test back button
	 */
	@Test
	void backTest() { 
		//test back with no changes
		getToPlanEditView("2019");
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
		getToPlanEditView("2019");
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
		getToPlanEditView("2020 Read Only");
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
		getToPlanEditView("2019");
		closeCurrentWindow();
		//test close when changes have occurred. need popup
		this.setUpBeforeEachTest();
		getToPlanEditView("2019");
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
	@Test
	void testCannotEditAlternatePlan() {
		//edit
		getToPlanEditView("2019");
		clickOn(this.altNameFieldID);
		write("edit");
		clickOn(this.altDataFieldID);
		write("edit");
		checkAltPage("Mission","Old Edit");
		this.afterEachTest();
		
		//read only
		this.setUpBeforeEachTest();
		getToPlanEditView("2020 Read Only");
		clickOn(this.altNameFieldID);
		write("edit");
		clickOn(this.altDataFieldID);
		write("edit");
		checkAltPage("Vision","My Vision is to...");
	}
	
	/**
	 * makes sure the user can navigate both plan trees
	 */
	@Test
	void testPlanNavigation() {
		//read only
		getToPlanEditView("2020 Read Only");
		doubleClickOn("Vision");
		checkPage("Vision","My Vision is to...","2020");
		doubleClickOn("Mission");
		checkPage("Mission","My Mission is to...","2020");
		String[] names= {"Objective","Strategy","Action Plan"};
		this.checkBranch(names);
		this.afterEachTest();
		
		//editable plans
		this.setUpBeforeEachTest();
		getToPlanEditView("2019");
		checkBranch();
		
	}
	
	/**
	 * verifies difference detection is working
	 */
	@Test
	void testDifferenceDetection() {
		//make sure the user can change sections
	}
	
	/**
	 * make sure you cannot edit a read only plan
	 */
	@Test
	void testCannotEditReadOnlyPlan() {
		getToPlanEditView("2020 Read Only");
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
