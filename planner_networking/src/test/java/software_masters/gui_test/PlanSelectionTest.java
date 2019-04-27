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
		this.clickOn("Connect");
		((TextField) this.find("#usernameField")).setText("user");
		((TextField) this.find("#passwordField")).setText("user");
		this.clickOn("#loginButton");
		this.testDefaultValues();
		this.testLogout();
		this.testSelectPlanTemplate();
		this.testSelectEditablePlan();
		this.testSelectReadOnlyPlan();
	}

	/**
	 * Verifies labels are initialized with intended text.
	 */
	private void testDefaultValues() {
		this.verify("#selectPlanLabel", "Select Plan");
		this.verify("#planTemplatesLabel", "Plan Templates");
		this.verify("#departmentPlansLabel", "Department Plans");
		FxAssert.verifyThat("#logoutButton", LabeledMatchers.hasText("Logout"));
	}

	/**
	 * Verifies that logout button works by clicking on button and checking for the
	 * presence of the username label in the login window.
	 */
	private void testLogout() {
		this.clickOn("#logoutButton");
		this.verify("#usernameLabel", "Username");
		this.clickOn("#loginButton");
	}

	/**
	 * Verifies the user can click on an editable plan template from the plan
	 * templates list view and view the plan edit window.
	 */
	private void testSelectEditablePlan() {
		this.clickOn(this.find("2019"));
		FxAssert.verifyThat("#saveButton", LabeledMatchers.hasText("Save"));
		this.clickOn("#backToPlansButton");
	}

	/**
	 * Verifies the user can click on a plan template from the plan templates list
	 * view and view the plan edit window.
	 */
	private void testSelectPlanTemplate() {
		this.clickOn(this.find("VMOSA"));
		FxAssert.verifyThat("#saveButton", LabeledMatchers.hasText("Save"));
		this.clickOn("#backToPlansButton");
	}

	/**
	 * Verifies the user can click on an editable plan template from the plan
	 * templates list view and view the plan edit window.
	 */
	private void testSelectReadOnlyPlan() {
		this.clickOn(this.find("2020 Read Only"));
		FxAssert.verifyThat("#readOnlyLabel", LabeledMatchers.hasText("Local View Only - Cannot Save Changes"));
		this.clickOn("#backToPlansButton");
	}

}
