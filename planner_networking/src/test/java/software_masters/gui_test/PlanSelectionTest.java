package software_masters.gui_test;

import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.scene.control.TextField;

/**
 * @author software masters
 */
class PlanSelectionTest extends GuiTestBase {

	/**
	 * Main test which calls other tests in sequential order
	 */
	@Test
	void mainTest() {
		clickOn("Connect");
		((TextField) find("#usernameField")).setText("user");
		((TextField) find("#passwordField")).setText("user");
		clickOn("#loginButton");
		testDefaultValues();
		testLogout();
		testSelectPlanTemplate();
		testSelectEditablePlan();
		testSelectReadOnlyPlan();
	}

	/**
	 * Verifies labels are initialized with intended text.
	 */
	private void testDefaultValues() {
		verify("#selectPlanLabel", "Select Plan");
		verify("#planTemplatesLabel", "Plan Templates");
		verify("#departmentPlansLabel", "Department Plans");
		FxAssert.verifyThat("#logoutButton", LabeledMatchers.hasText("Logout"));
	}

	/**
	 * Verifies that logout button works by clicking on button and checking for the
	 * presence of the username label in the login window.
	 */
	private void testLogout() {
		clickOn("#logoutButton");
		verify("#usernameLabel", "Username");
		clickOn("#loginButton");
	}

	/**
	 * Verifies the user can click on an editable plan template from the plan
	 * templates list view and view the plan edit window.
	 */
	private void testSelectEditablePlan() {
		clickOn("2019");
		FxAssert.verifyThat("#saveButton", LabeledMatchers.hasText("Save"));
		clickOn("#backToPlansButton");
	}

	/**
	 * Verifies the user can click on a plan template from the plan templates list
	 * view and view the plan edit window.
	 */
	private void testSelectPlanTemplate() {
		clickOn("VMOSA");
		FxAssert.verifyThat("#saveButton", LabeledMatchers.hasText("Save"));
		clickOn("#backToPlansButton");
	}

	/**
	 * Verifies the user can click on an editable plan template from the plan
	 * templates list view and view the plan edit window.
	 */
	private void testSelectReadOnlyPlan() {
		clickOn("2020 Read Only");
		FxAssert.verifyThat("#readOnlyLabel", LabeledMatchers.hasText("Local View Only - Cannot Save Changes"));
		clickOn("#backToPlansButton");
	}

}
