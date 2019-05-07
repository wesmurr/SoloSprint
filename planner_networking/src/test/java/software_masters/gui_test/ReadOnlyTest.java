package software_masters.gui_test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testfx.api.FxAssert.verifyThat;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.scene.control.TextField;

/**
 * @author software masters
 */
class ReadOnlyTest extends GuiTestBase {
	String addButtonID = "#addSectionButton";
	String backID = "#backToPlansButton";
	String dataFieldID = "#dataField";
	String deleteButtonID = "#deleteSectionButton";
	String logoutID = "#logoutButton";
	String nameFieldID = "#nameField";
	String readOnlyLabel = "#readOnlyLabel";
	String saveID = "#saveButton";
	String treeViewID = "#treeView";
	String yearFieldID = "#yearField";
	String yearLabelID = "#yearLabel";

	/**
	 * Helper method that verifies plan branch structure is displayed
	 */
	private void checkBranch(String[] names) {
		for (final String name : names) {
			doubleClickOn(name);
			checkPage(name, "");
		}
	}

	/**
	 * Helper method that checks the content displayed for a given node based on
	 * provided string.
	 *
	 * @param name
	 * @param content
	 */
	private void checkPage(String name, String content) {
		verifyField(this.nameFieldID, name);
		verifyField(this.dataFieldID, content);
	}

	/**
	 * Helper method that checks the content displayed for a given node based on
	 * provided string. This version also checks the year field.
	 *
	 * @param name
	 * @param content
	 */
	private void checkPage(String name, String content, String year) {
		verifyField(this.yearFieldID, year);
		checkPage(name, content);
	}

	/**
	 * moves from login screen to the plan edit window in the gui
	 */
	protected void getToView(String item) {
		clickOn("Connect");
		((TextField) find("#usernameField")).setText("user");
		((TextField) find("#passwordField")).setText("user");
		clickOn("#loginButton");
		clickOn(item);
	}

	/**
	 * method that coordinates order of gui test.
	 *
	 * @throws Exception
	 */
	@Test
	void mainTest() {
		getToView("2020 Read Only");
		testDefaultValues();
		testNavigation();
		testCannotEdit();
		testBackToPlans();
		testLogout();
	}

	/**
	 * Verifies the back to plans button works by checking for the presence of the
	 * select plan label in the plan selection window
	 */
	private void testBackToPlans() {
		clickOn(this.backID);
		verify("#selectPlanLabel", "Select Plan");
		clickOn("2020 Read Only");
	}

	/**
	 * Verifies user cannot edit any of the text fields in read only mode.
	 */
	private void testCannotEdit() {
		clickOn(this.yearFieldID);
		write("test");
		clickOn(this.nameFieldID);
		write("test");
		clickOn(this.dataFieldID);
		write("test");
		checkPage("Mission", "My Mission is to...", "2020");
	}

	/**
	 * Verifies labels, buttons, and textfields are initialized with intended text.
	 */
	private void testDefaultValues() {
		defaultItemsTest();
		checkPage("Vision", "My Vision is to...", "2020");
	}
	
	/**
	 * Helper method to make sure the correct window is initialized.
	 */
	protected void defaultItemsTest() {
		verifyThat(this.backID, LabeledMatchers.hasText("Back to plans"));
		verifyThat(this.readOnlyLabel, LabeledMatchers.hasText("Local View Only - Cannot Save Changes"));
		verifyThat(this.logoutID, LabeledMatchers.hasText("Log Out"));
		verifyThat(this.yearLabelID, LabeledMatchers.hasText("Year"));
		assertThrows(NoSuchElementException.class, () -> find("Save"));
	}

	/**
	 * Verifies the logout button works by checking for the presence of the username
	 * label in the login window. There should not be a save popup.
	 */
	private void testLogout() {
		clickOn(this.logoutID);
		verify("#usernameLabel", "Username");
	}

	/**
	 * Verifies that title field is repopulated with correct section name when the
	 * user clicks on a new section in the treeview.
	 */
	private void testNavigation() {
		doubleClickOn("Vision");
		doubleClickOn("Mission");
		clickOn("Mission");
		checkPage("Mission", "My Mission is to...");
		final String[] names = { "Objective", "Strategy", "Action Plan" };
		checkBranch(names);
		clickOn("Mission");
		checkPage("Mission", "My Mission is to...");
	}

}
