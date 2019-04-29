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
	 * Test that default values exist.
	 */
	@Test
	void defaultTest() {
		clickOn("Connect");
		getToPlanEditView("2019");
		defaultButtonTest();
		this.checkBranch();
		clickOn("Mission");
		verifyThat(this.treeViewID,
				(TreeView<PlanSection> tree) -> { return tree.getRoot().getValue().getName().equals("Mission"); });
		checkPage("Mission", "", "2019");
	}
	
	/**
	 * Helper method to verify correct buttons are displayed
	 */
	protected void defaultButtonTest() {
		verify(this.addButtonID, "Add Section");
		verify(this.deleteButtonID, "Delete Section");
		verify(this.saveID, "Save");
		verify(this.backID, "Back to plans");
		verify(this.yearLabelID, "Year");
		verify(this.logoutID, "Log Out");
	}

	/**
	 * moves from login screen to the plan edit window in the gui
	 */
	protected void getToPlanEditView(String item) {
		((TextField) find("#usernameField")).setText("user");
		((TextField) find("#passwordField")).setText("user");
		clickOn("#loginButton");
		clickOn(item);
	}

	/**
	 * Tests that add section does not work when a section cannot be duplicated.
	 */
	private void invalidAddSection() {
		clickOn("Mission");
		clickOn(this.addButtonID);
		checkPopupMsg("Cannot add a section of this type");
		clickOn("OK");
		/*
		 * if addsection is called anyway a null pointer exception will occur because
		 * mission's parent is null As long as we do not get a null pointer exception
		 * then we know the client did not attempt to add a section.
		 */
		checkPage("Mission", "");
	}

	/**
	 * This verifies the delete section button is working properly.
	 */
	private void invalidDeleteSection() {
		clickOn("Mission");
		clickOn(this.deleteButtonID);
		checkPopupMsg(
				"Are you sure you want to delete this section and all dependencies?" + "They cannot be recovered.");
		clickOn("Delete");
		checkPopupMsg("Cannot delete this section");
		clickOn("OK");

		clickOn("Mission");
		clickOn(this.deleteButtonID);
		checkPopupMsg(
				"Are you sure you want to delete this section and all dependencies?" + "They cannot be recovered.");
		clickOn("Don't Delete");
		checkPage("Mission", "");

		clickOn("Goal");
		clickOn(this.deleteButtonID);
		checkPopupMsg(
				"Are you sure you want to delete this section and all dependencies?" + "They cannot be recovered.");
		clickOn("Delete");
		checkPopupMsg("Cannot delete this section");
		clickOn("OK");
	}

	/**
	 * method that coordinates order of gui test.
	 *
	 * @throws Exception
	 */
	@Test
	void testAddSection() throws Exception {
		// independent section
		clickOn("Connect");
		getToPlanEditView("2019");
		this.checkBranch();
		validAddSection();
		invalidAddSection();
		clickOn(this.saveID);
	}

	/**
	 * Verifies the back to plan button works as advertised. Handles if changes have
	 * been made or not.
	 */
	@Test
	void testBackToPlans() {
		clickOn("#connectButton");
		getToPlanEditView("2019");
		this.checkBranch();

		// test that unsaved changes produce popup
		// cancel case
		clickOn("Mission");
		clickOn(this.dataFieldID);
		write("mission edit");
		clickOn(this.backID);
		checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		clickOn("Cancel");
		checkPage("Mission", "mission edit");
		// do not save changes, "no" case
		clickOn(this.backID);
		checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		clickOn("No");
		clickOn("2019");
		checkPage("Mission", "");
		// save changes, "yes" case
		clickOn(this.dataFieldID);
		write("mission edit");
		clickOn(this.backID);
		checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		clickOn("Yes");
		clickOn("2019");
		checkPage("Mission", "mission edit");

		// verify no changes doesn't create popup
		doubleClickOn("Mission");
		clickOn("Goal");
		checkPage("Goal", "");
		clickOn(this.backID);
		clickOn("2019");
		clickOn("Mission");
		((TextField) find("mission edit")).setText("");
		clickOn(this.saveID);
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
		clickOn("#connectButton");
		getToPlanEditView("2019");
		this.checkBranch();
		// test that unsaved changes produce popup
		// cancel case
		clickOn("Mission");
		clickOn(this.dataFieldID);
		write("mission edit");
		closeCurrentWindow();
		checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		clickOn("Cancel");
		checkPage("Mission", "mission edit");
		// do not save changes, "no" case
		closeCurrentWindow();
		checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		clickOn("No");
		setUpBeforeEachTest();
		clickOn("Connect");
		getToPlanEditView("2019");
		checkPage("Mission", "");
		// save changes, "yes" case
		clickOn(this.dataFieldID);
		write("mission edit");
		closeCurrentWindow();
		checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		clickOn("Yes");
		setUpBeforeEachTest();
		clickOn("Connect");
		getToPlanEditView("2019");
		clickOn("2019");
		checkPage("Mission", "mission edit");

		// verify no changes doesn't create popup
		doubleClickOn("Mission");
		clickOn("Goal");
		checkPage("Goal", "");
		closeCurrentWindow();
		setUpBeforeEachTest();
		clickOn("Connect");
		getToPlanEditView("2019");
		clickOn("2019");
		clickOn("Mission");
		((TextField) find("mission edit")).setText("");
		clickOn(this.saveID);
	}

	@Test
	void testDeleteSection() throws Exception {
		// independent section
		clickOn("Connect");
		getToPlanEditView("2019");
		validDeleteSection();
		invalidDeleteSection();
		clickOn(this.saveID);
	}

	/**
	 * Verifies the logout button works as advertised. Handles if changes have been
	 * made or not.
	 */
	@Test
	void testLogout() {
		clickOn("#connectButton");
		getToPlanEditView("2019");
		this.checkBranch();
		// cancel case
		clickOn("Mission");
		clickOn(this.dataFieldID);
		write("mission edit");
		clickOn(this.logoutID);
		checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		clickOn("Cancel");
		checkPage("Mission", "mission edit");
		// do not save changes, "no" case
		clickOn(this.logoutID);
		checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		clickOn("No");
		getToPlanEditView("2019");
		checkPage("Mission", "");
		// save changes, "yes" case
		clickOn(this.dataFieldID);
		write("mission edit");
		clickOn(this.logoutID);
		checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		clickOn("Yes");
		getToPlanEditView("2019");
		checkPage("Mission", "mission edit");

		// verify no changes doesn't create popup
		doubleClickOn("Mission");
		clickOn("Goal");
		checkPage("Goal", "");
		clickOn(this.logoutID);
		getToPlanEditView("2019");
		clickOn("Mission");
		((TextField) find("mission edit")).setText("");
		clickOn(this.saveID);
	}

	/**
	 * This method verifies the save button works. We don't expect the warning popup
	 * to show because we exit right after clicking the save button.
	 */
	@Test
	void testSave() {
		clickOn("#connectButton");
		getToPlanEditView("2019");
		this.checkBranch();
		clickOn("Mission");
		clickOn(this.dataFieldID);
		write("mission edit");
		clickOn(this.nameFieldID);
		write(" name field edit");
		clickOn("Goal");
		clickOn(this.nameFieldID);
		write(" original");
		clickOn(this.addButtonID);
		clickOn(this.yearFieldID);
		write("0");
		clickOn(this.saveID);
		clickOn(this.backID);
		clickOn("20190");

		doubleClickOn("Mission name field edit"); // checks that tree view changed in response to name field edit
		checkPage("Mission name field edit", "mission edit", "20190");

		final String[] names = { "Goal", "Learning Objective", "Assessment Process", "Results" };
		this.checkBranch(names);
		clickOn("Goal original");
		final String[] names2 = { "Goal original", "Learning Objective", "Assessment Process", "Results" };
		this.checkBranch(names2);

		clickOn("Goal original");
		clickOn(this.deleteButtonID);
		clickOn("Delete");
		clickOn(this.saveID);
		clickOn(this.backID);
		clickOn("20190");
		doubleClickOn("Mission name field edit");
		this.checkBranch(names);
		assertThrows(NoSuchElementException.class, () -> find("Goal original"));
	}

	/**
	 * Tests the save warning popup is displayed in response to all types of edits
	 */
	@Test
	void testSavePopup() {
		clickOn("#connectButton");
		getToPlanEditView("2019");
		this.checkBranch();

		// Tests data field, name field, and year field
		testSavePopupHelp(this.yearFieldID);
		testSavePopupHelp(this.dataFieldID);
		testSavePopupHelp(this.nameFieldID);

		// Tests add section
		clickOn("Goal");
		clickOn(this.addButtonID);
		clickOn(this.logoutID);
		checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		clickOn("Cancel");
		clickOn(this.saveID);

		// Tests delete
		doubleClickOn("Mission1"); // This also tests that treeview labels change in response to name field edits
		clickOn("Goal");
		clickOn(this.deleteButtonID);
		clickOn("Delete");
		clickOn(this.logoutID);
		checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		clickOn("Cancel");
		clickOn(this.saveID);

		clickOn(this.logoutID);
		getToPlanEditView("2019");
	}

	/**
	 * Helper method to handle checking save popup is displayed in response to
	 * different kinds of changes
	 *
	 * @param ID
	 */
	private void testSavePopupHelp(String ID) {

		clickOn("Mission");
		clickOn(ID);
		write("1");
		clickOn(this.logoutID);
		checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		clickOn("Cancel");
		clickOn(this.saveID);
	}

	/**
	 * This method makes sure you can open a blank template and edit it
	 */
	@Test
	void testTemplate() {
		clickOn("#connectButton");
		getToPlanEditView("VMOSA");
		final String[] names = { "Vision", "Mission", "Objective", "Strategy", "Action Plan" };
		this.checkBranch(names);
		clickOn(this.saveID);
		clickOn("OK");
		((TextField) find(this.yearFieldID)).setText("2023");
		clickOn(this.backID);
		clickOn("Yes");
		clickOn("2023");
		this.checkBranch(names);
	}

	/**
	 * Tests that add section is executed properly
	 */
	private void validAddSection() {
		clickOn("Goal");
		((TextField) find(this.nameFieldID)).setText("Go 1");
		((TextField) find(this.dataFieldID)).setText("Go 1 content");
		clickOn(this.addButtonID);

		verifyThat(this.treeViewID, (TreeView<PlanSection> treeview) -> {
			if (treeview.getRoot().getChildren().size() != 2) return false;
			return true;
		});

		doubleClickOn("Mission");
		clickOn("Goal"); // will fail if a goal section was not created
		checkPage("Goal", "");

		clickOn("Go 1");
		checkPage("Go 1", "Go 1 content");
	}

	/**
	 * This verifies the delete section button is working properly.
	 */
	private void validDeleteSection() {
		doubleClickOn("Mission");
		clickOn("Go 1");
		clickOn(this.deleteButtonID);
		checkPopupMsg(
				"Are you sure you want to delete this section and all dependencies?" + "They cannot be recovered.");
		clickOn("Don't Delete");
		checkPage("Go 1", "Go 1 content");

		clickOn(this.deleteButtonID);
		checkPopupMsg(
				"Are you sure you want to delete this section and all dependencies?" + "They cannot be recovered.");
		clickOn("Delete");
		this.checkBranch();
		verifyThat(this.treeViewID, (TreeView<PlanSection> treeview) -> {
			if (treeview.getRoot().getChildren().size() != 1) return false;
			return true;
		});
	}

}
