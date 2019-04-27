package software_masters.gui_test;

import static org.testfx.api.FxAssert.verifyThat;

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
			this.doubleClickOn(name);
			this.checkPage(name, "");
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
		this.verifyField(this.nameFieldID, name);
		this.verifyField(this.dataFieldID, content);
	}

	/**
	 * Helper method that checks the content displayed for a given node based on
	 * provided string. This version also checks the year field.
	 *
	 * @param name
	 * @param content
	 */
	private void checkPage(String name, String content, String year) {
		this.verifyField(this.yearFieldID, year);
		this.checkPage(name, content);
	}

	/**
	 * moves from login screen to the plan edit window in the gui
	 */
	protected void getToPlanEditView(String item) {
		this.clickOn("Connect");
		((TextField) this.find("#usernameField")).setText("user");
		((TextField) this.find("#passwordField")).setText("user");
		this.clickOn("#loginButton");
		this.clickOn(this.find(item));
	}

	/**
	 * method that coordinates order of gui test.
	 *
	 * @throws Exception
	 */
	@Test
	void mainTest() {
		this.getToPlanEditView("2020 Read Only");
		this.testDefaultValues();
		this.testNavigation();
		this.testCannotEdit();
		this.testBackToPlans();
		this.testLogout();
	}

	/**
	 * Verifies the back to plans button works by checking for the presence of the
	 * select plan label in the plan selection window
	 */
	private void testBackToPlans() {
		this.clickOn(this.backID);
		this.verify("#selectPlanLabel", "Select Plan");
		this.clickOn(this.find("2020 Read Only"));
	}

	/**
	 * Verifies user cannot edit any of the text fields in read only mode.
	 */
	private void testCannotEdit() {
		this.clickOn(this.yearFieldID);
		this.write("test");
		this.clickOn(this.nameFieldID);
		this.write("test");
		this.clickOn(this.dataFieldID);
		this.write("test");
		this.checkPage("Mission", "My Mission is to...", "2020");
	}

	/**
	 * Verifies labels, buttons, and textfields are initialized with intended text.
	 */
	private void testDefaultValues() {
		verifyThat(this.backID, LabeledMatchers.hasText("Back to plans"));
		verifyThat(this.readOnlyLabel, LabeledMatchers.hasText("Local View Only - Cannot Save Changes"));
		verifyThat(this.logoutID, LabeledMatchers.hasText("Log Out"));
		verifyThat(this.yearLabelID, LabeledMatchers.hasText("Year"));
		this.checkPage("Vision", "My Vision is to...", "2020");
	}

	/**
	 * Verifies the logout button works by checking for the presence of the username
	 * label in the login window. There should not be a save popup.
	 */
	private void testLogout() {
		this.clickOn(this.logoutID);
		this.verify("#usernameLabel", "Username");
	}

	/**
	 * Verifies that title field is repopulated with correct section name when the
	 * user clicks on a new section in the treeview.
	 */
	private void testNavigation() {
		this.doubleClickOn("Vision");
		this.doubleClickOn("Mission");
		this.clickOn("Mission");
		this.checkPage("Mission", "My Mission is to...");
		final String[] names = { "Objective", "Strategy", "Action Plan" };
		this.checkBranch(names);
		this.clickOn("Mission");
		this.checkPage("Mission", "My Mission is to...");
	}

}
