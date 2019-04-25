package software_masters.gui_test;

import static org.testfx.api.FxAssert.verifyThat;

import org.junit.jupiter.api.Test;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.scene.Node;

/**
 * @author software masters
 */
class ReadOnlyTest extends GuiTestBase {
	/**
	 * method that coordinates order of gui test.
	 * 
	 * @throws Exception
	 */
	@Test
	void mainTest() {
		clickOn("Connect");
		clickOn("#loginButton");
		clickOn("2020 Read Only");
		testDefaultValues();
		testNavigation();
		testCannotEdit();
		testBackToPlans();
		testLogout();
	}

	/**
	 * Verifies labels, buttons, and textfields are initialized with intended text.
	 */
	private void testDefaultValues() {

		verifyThat("#backToPlansButton", LabeledMatchers.hasText("Back to plans"));
		verifyThat("#readOnlyLabel", LabeledMatchers.hasText("Local View Only - Cannot Save Changes"));
		verifyThat("#logoutButton", LabeledMatchers.hasText("Log Out"));
		verifyThat("#yearLabel", LabeledMatchers.hasText("Year"));
		checkPage("Vision", "My Vision is to...", "2020");
	}

	/**
	 * Verifies that title field is repopulated with correct section name when the
	 * user clicks on a new section in the treeview.
	 */
	private void testNavigation() {
		doubleClickOn("Vision");
		doubleClickOn("Mission");
		clickOn("Mission");
		verifyField("#titleField", "Mission");
		verifyField("#contentField", "My Mission is to...");
		String[] names = { "Objective", "Strategy", "Action Plan" };
		checkBranch(names);
		clickOn("Mission");
		checkPage("Mission", "My Mission is to...");
	}

	/**
	 * Verifies user cannot edit any of the text fields in read only mode.
	 */
	private void testCannotEdit() {
		clickOn("#yearField");
		write("test");
		clickOn("#titleField");
		write("test");
		clickOn("#contentField");
		write("test");
		checkPage("Mission", "My Mission is to...", "2020");
	}

	/**
	 * Verifies the back to plans button works by checking for the presence of the
	 * select plan label in the plan selection window
	 */
	private void testBackToPlans() {
		clickOn("#backToPlansButton");
		verify("#selectPlanLabel", "Select Plan");
		clickOn((Node) find("2020 Read Only"));
	}

	/**
	 * Verifies the logout button works by checking for the presence of the username
	 * label in the login window
	 */
	private void testLogout() {
		clickOn("#logoutButton");
		verify("#usernameLabel", "Username");
	}

	/**
	 * Helper method that verifies plan branch structure is displayed
	 */
	private void checkBranch(String[] names) {
		for (String name : names) {
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
		verifyField("#titleField", name);
		verifyField("#contentField", content);
	}

	/**
	 * Helper method that checks the content displayed for a given node based on
	 * provided string. This version also checks the year field.
	 * 
	 * @param name
	 * @param content
	 */
	private void checkPage(String name, String content, String year) {
		verifyField("#yearField", year);
		checkPage(name, content);
	}

}
