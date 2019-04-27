package software_masters.gui_test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testfx.api.FxAssert.verifyThat;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import businessPlannerApp.backend.PlanSection;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;

/**
 * @author software masters
 */
class PlanEditViewTest extends GuiTestBase {

	String addButtonID = "#addSectionButton";
	String backID = "#backToPlansButton";
	String dataFieldID = "#dataField";
	String deleteButtonID = "#deleteSectionButton";
	String logoutID = "#logoutButton";
	String nameFieldID = "#nameField";
	String saveID = "#saveButton";
	String treeViewID = "#treeView";
	String yearFieldID = "#yearField";
	String yearLabelID = "#yearLabel";

	/**
	 * Helper method that verifies plan branch structure is displayed
	 */
	private void checkBranch() {
		final String[] names = { "Mission", "Goal", "Learning Objective", "Assessment Process", "Results" };
		this.checkBranch(names);
	}

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
	 * Test that default values exist.
	 */
	@Test
	void defaultTest() {
		this.clickOn("Connect");
		this.getToPlanEditView("2019");
		this.checkBranch();
		this.clickOn("Mission");
		this.verify(this.addButtonID, "Add Section");
		this.verify(this.deleteButtonID, "Delete Section");
		this.verify(this.saveID, "Save");
		this.verify(this.backID, "Back to plans");
		this.verify(this.yearLabelID, "Year");
		this.verify(this.logoutID, "Log Out");
		verifyThat(this.treeViewID,
				(TreeView<PlanSection> tree) -> { return tree.getRoot().getValue().getName().equals("Mission"); });
		this.checkPage("Mission", "", "2019");
	}

	/**
	 * moves from login screen to the plan edit window in the gui
	 */
	protected void getToPlanEditView(String item) {
		((TextField) this.find("#usernameField")).setText("user");
		((TextField) this.find("#passwordField")).setText("user");
		this.clickOn("#loginButton");
		this.clickOn(this.find(item));
	}

	/**
	 * Tests that add section does not work when a section cannot be duplicated.
	 */
	private void invalidAddSection() {
		this.clickOn("Mission");
		this.clickOn(this.addButtonID);
		this.checkPopupMsg("Cannot add a section of this type");
		this.clickOn("OK");
		/*
		 * if addsection is called anyway a null pointer exception will occur because
		 * mission's parent is null As long as we do not get a null pointer exception
		 * then we know the client did not attempt to add a section.
		 */
		this.checkPage("Mission", "");
	}

	/**
	 * This verifies the delete section button is working properly.
	 */
	private void invalidDeleteSection() {
		this.clickOn(this.find("Mission"));
		this.clickOn(this.deleteButtonID);
		this.checkPopupMsg(
				"Are you sure you want to delete this section and all dependencies?" + "They cannot be recovered.");
		this.clickOn(this.find("Delete"));
		this.checkPopupMsg("Cannot delete this section");
		this.clickOn("OK");

		this.clickOn(this.find("Mission"));
		this.clickOn(this.deleteButtonID);
		this.checkPopupMsg(
				"Are you sure you want to delete this section and all dependencies?" + "They cannot be recovered.");
		this.clickOn(this.find("Don't Delete"));
		this.checkPage("Mission", "");

		this.clickOn("Goal");
		this.clickOn(this.deleteButtonID);
		this.checkPopupMsg(
				"Are you sure you want to delete this section and all dependencies?" + "They cannot be recovered.");
		this.clickOn(this.find("Delete"));
		this.checkPopupMsg("Cannot delete this section");
		this.clickOn("OK");
	}

	/**
	 * method that coordinates order of gui test.
	 *
	 * @throws Exception
	 */
	@Test
	void testAddSection() throws Exception {
		// independent section
		this.clickOn("Connect");
		this.getToPlanEditView("2019");
		this.checkBranch();
		this.validAddSection();
		this.invalidAddSection();
		this.clickOn(this.saveID);
	}

	/**
	 * Verifies the back to plan button works as advertised. Handles if changes have
	 * been made or not.
	 */
	@Test
	void testBackToPlans() {
		this.clickOn("#connectButton");
		this.getToPlanEditView("2019");
		this.checkBranch();

		// test that unsaved changes produce popup
		// cancel case
		this.clickOn("Mission");
		this.clickOn(this.dataFieldID);
		this.write("mission edit");
		this.clickOn(this.backID);
		this.checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		this.clickOn("Cancel");
		this.checkPage("Mission", "mission edit");
		// do not save changes, "no" case
		this.clickOn(this.backID);
		this.checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		this.clickOn("No");
		this.clickOn(this.find("2019"));
		this.checkPage("Mission", "");
		// save changes, "yes" case
		this.clickOn(this.dataFieldID);
		this.write("mission edit");
		this.clickOn(this.backID);
		this.checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		this.clickOn("Yes");
		this.clickOn(this.find("2019"));
		this.checkPage("Mission", "mission edit");

		// verify no changes doesn't create popup
		this.doubleClickOn("Mission");
		this.clickOn("Goal");
		this.checkPage("Goal", "");
		this.clickOn(this.backID);
		this.clickOn(this.find("2019"));
		this.clickOn("Mission");
		((TextField) this.find("mission edit")).setText("");
		this.clickOn(this.saveID);
	}

	/**
	 * Verifies the close button works as advertised. Handles if changes have been
	 * made or not.
	 *
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@Test
	void testClose() throws Exception {
		this.clickOn("#connectButton");
		this.getToPlanEditView("2019");
		this.checkBranch();
		// test that unsaved changes produce popup
		// cancel case
		this.clickOn("Mission");
		this.clickOn(this.dataFieldID);
		this.write("mission edit");
		this.closeCurrentWindow();
		this.checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		this.clickOn("Cancel");
		this.checkPage("Mission", "mission edit");
		// do not save changes, "no" case
		this.closeCurrentWindow();
		this.checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		this.clickOn("No");
		this.setUpBeforeEachTest();
		this.clickOn("Connect");
		this.getToPlanEditView("2019");
		this.checkPage("Mission", "");
		// save changes, "yes" case
		this.clickOn(this.dataFieldID);
		this.write("mission edit");
		this.closeCurrentWindow();
		this.checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		this.clickOn("Yes");
		this.setUpBeforeEachTest();
		this.clickOn("Connect");
		this.getToPlanEditView("2019");
		this.clickOn(this.find("2019"));
		this.checkPage("Mission", "mission edit");

		// verify no changes doesn't create popup
		this.doubleClickOn("Mission");
		this.clickOn("Goal");
		this.checkPage("Goal", "");
		this.closeCurrentWindow();
		this.setUpBeforeEachTest();
		this.clickOn("Connect");
		this.getToPlanEditView("2019");
		this.clickOn(this.find("2019"));
		this.clickOn("Mission");
		((TextField) this.find("mission edit")).setText("");
		this.clickOn(this.saveID);
	}

	@Test
	void testDeleteSection() throws Exception {
		// independent section
		this.clickOn("Connect");
		this.getToPlanEditView("2019");
		this.validDeleteSection();
		this.invalidDeleteSection();
		this.clickOn(this.saveID);
	}

	/**
	 * Verifies the logout button works as advertised. Handles if changes have been
	 * made or not.
	 */
	@Test
	void testLogout() {
		this.clickOn("#connectButton");
		this.getToPlanEditView("2019");
		this.checkBranch();
		// cancel case
		this.clickOn("Mission");
		this.clickOn(this.dataFieldID);
		this.write("mission edit");
		this.clickOn(this.logoutID);
		this.checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		this.clickOn("Cancel");
		this.checkPage("Mission", "mission edit");
		// do not save changes, "no" case
		this.clickOn(this.logoutID);
		this.checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		this.clickOn("No");
		this.getToPlanEditView("2019");
		this.checkPage("Mission", "");
		// save changes, "yes" case
		this.clickOn(this.dataFieldID);
		this.write("mission edit");
		this.clickOn(this.logoutID);
		this.checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		this.clickOn("Yes");
		this.getToPlanEditView("2019");
		this.checkPage("Mission", "mission edit");

		// verify no changes doesn't create popup
		this.doubleClickOn("Mission");
		this.clickOn("Goal");
		this.checkPage("Goal", "");
		this.clickOn(this.logoutID);
		this.getToPlanEditView("2019");
		this.clickOn("Mission");
		((TextField) this.find("mission edit")).setText("");
		this.clickOn(this.saveID);
	}

	/**
	 * This method verifies the save button works. We don't expect the warning popup
	 * to show because we exit right after clicking the save button.
	 */
	@Test
	void testSave() {
		this.clickOn("#connectButton");
		this.getToPlanEditView("2019");
		this.checkBranch();
		this.clickOn("Mission");
		this.clickOn(this.dataFieldID);
		this.write("mission edit");
		this.clickOn(this.nameFieldID);
		this.write(" name field edit");
		this.clickOn("Goal");
		this.clickOn(this.nameFieldID);
		this.write(" original");
		this.clickOn(this.addButtonID);
		this.clickOn(this.yearFieldID);
		this.write("0");
		this.clickOn(this.saveID);
		this.clickOn(this.backID);
		this.clickOn("20190");

		this.doubleClickOn("Mission name field edit"); // checks that tree view changed in response to name field edit
		this.checkPage("Mission name field edit", "mission edit", "20190");

		final String[] names = { "Goal", "Learning Objective", "Assessment Process", "Results" };
		this.checkBranch(names);
		this.clickOn("Goal original");
		final String[] names2 = { "Goal original", "Learning Objective", "Assessment Process", "Results" };
		this.checkBranch(names2);

		this.clickOn("Goal original");
		this.clickOn(this.deleteButtonID);
		this.clickOn("Delete");
		this.clickOn(this.saveID);
		this.clickOn(this.backID);
		this.clickOn("20190");
		this.doubleClickOn("Mission name field edit");
		this.checkBranch(names);
		assertThrows(NoSuchElementException.class, () -> this.find("Goal original"));
	}

	/**
	 * Tests the save warning popup is displayed in response to all types of edits
	 */
	@Test
	void testSavePopup() {
		this.clickOn("#connectButton");
		this.getToPlanEditView("2019");
		this.checkBranch();

		// Tests data field, name field, and year field
		this.testSavePopupHelp(this.yearFieldID);
		this.testSavePopupHelp(this.dataFieldID);
		this.testSavePopupHelp(this.nameFieldID);

		// Tests add section
		this.clickOn("Goal");
		this.clickOn(this.addButtonID);
		this.clickOn(this.logoutID);
		this.checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		this.clickOn("Cancel");
		this.clickOn(this.saveID);

		// Tests delete
		this.doubleClickOn("Mission1"); // This also tests that treeview labels change in response to name field edits
		this.clickOn("Goal");
		this.clickOn(this.deleteButtonID);
		this.clickOn("Delete");
		this.clickOn(this.logoutID);
		this.checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		this.clickOn("Cancel");
		this.clickOn(this.saveID);

		this.clickOn(this.logoutID);
		this.getToPlanEditView("2019");
	}

	/**
	 * Helper method to handle checking save popup is displayed in response to
	 * different kinds of changes
	 *
	 * @param ID
	 */
	private void testSavePopupHelp(String ID) {

		this.clickOn("Mission");
		this.clickOn(ID);
		this.write("1");
		this.clickOn(this.logoutID);
		this.checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		this.clickOn("Cancel");
		this.clickOn(this.saveID);
	}

	/**
	 * This method makes sure you can open a blank template and edit it
	 */
	@Test
	void testTemplate() {
		this.clickOn("#connectButton");
		this.getToPlanEditView("VMOSA");
		final String[] names = { "Vision", "Mission", "Objective", "Strategy", "Action Plan" };
		this.checkBranch(names);
		this.clickOn(this.saveID);
		this.clickOn("OK");
		((TextField) this.find(this.yearFieldID)).setText("2023");
		this.clickOn(this.backID);
		this.clickOn("Yes");
		this.clickOn("2023");
		this.checkBranch(names);
	}

	/**
	 * Tests that add section is executed properly
	 */
	private void validAddSection() {
		this.clickOn(this.find("Goal"));
		((TextField) this.find(this.nameFieldID)).setText("Go 1");
		((TextField) this.find(this.dataFieldID)).setText("Go 1 content");
		this.clickOn(this.addButtonID);

		verifyThat(this.treeViewID, (TreeView<PlanSection> treeview) -> {
			if (treeview.getRoot().getChildren().size() != 2) return false;
			return true;
		});

		this.doubleClickOn("Mission");
		this.clickOn(this.find("Goal")); // will fail if a goal section was not created
		this.checkPage("Goal", "");

		this.clickOn(this.find("Go 1"));
		this.checkPage("Go 1", "Go 1 content");
	}

	/**
	 * This verifies the delete section button is working properly.
	 */
	private void validDeleteSection() {
		this.doubleClickOn("Mission");
		this.clickOn(this.find("Go 1"));
		this.clickOn(this.deleteButtonID);
		this.checkPopupMsg(
				"Are you sure you want to delete this section and all dependencies?" + "They cannot be recovered.");
		this.clickOn(this.find("Don't Delete"));
		this.checkPage("Go 1", "Go 1 content");

		this.clickOn(this.deleteButtonID);
		this.checkPopupMsg(
				"Are you sure you want to delete this section and all dependencies?" + "They cannot be recovered.");
		this.clickOn(this.find("Delete"));
		this.checkBranch();
		verifyThat(this.treeViewID, (TreeView<PlanSection> treeview) -> {
			if (treeview.getRoot().getChildren().size() != 1) return false;
			return true;
		});
	}

}
