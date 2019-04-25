package software_masters.gui_test;

import org.junit.jupiter.api.Test;

import businessPlannerApp.backend.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testfx.api.FxAssert.verifyThat;

import java.util.NoSuchElementException;

/**
 * @author software masters
 */
class PlanEditViewTest extends GuiTestBase {

	String addButtonID = "#addSectionButton";
	String deleteButtonID = "#deleteSectionButton";
	String saveID = "#saveButton";
	String backID = "#backToPlansButton";
	String yearLabelID = "#yearLabel";
	String yearFieldID = "#editYearField";
	String logoutID = "#logoutButton";
	String treeViewID = "#treeView";
	String nameFieldID = "#nameField";
	String dataFieldID = "#dataField";

	/**
	 * moves from login screen to the plan edit window in the gui
	 */
	private void getToPlanEditView(String item) {
		((TextField) find("#usernameField")).setText("user");
		((TextField) find("#passwordField")).setText("user");
		clickOn("#loginButton");
		clickOn((javafx.scene.Node) find(item));

	}

	/**
	 * Test that default values exist.
	 */
	@Test
	void defaultTest() {
		clickOn("Connect");
		getToPlanEditView("2019");
		checkBranch();
		clickOn("Mission");
		verify(addButtonID, "Add Section");
		verify(deleteButtonID, "Delete Section");
		verify(saveID, "Save");
		verify(backID, "Back to plans");
		verify(yearLabelID, "Year");
		verify(logoutID, "Log Out");
		verifyThat(treeViewID,
				(TreeView<Node> tree) -> { return tree.getRoot().getValue().getName().equals("Mission"); });
		checkPage("Mission", "", "2019");
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
		checkBranch();
		validAddSection();
		invalidAddSection();
		clickOn(saveID);
	}

	/**
	 * Tests that add section is executed properly
	 */
	private void validAddSection() {
		clickOn((javafx.scene.Node) find("Goal"));
		((TextField) find(nameFieldID)).setText("Go 1");
		((TextField) find(dataFieldID)).setText("Go 1 content");
		clickOn(addButtonID);

		verifyThat(treeViewID, (TreeView<Node> treeview) -> {
			if (treeview.getRoot().getChildren().size() != 2) return false;
			return true;
		});

		doubleClickOn("Mission");
		clickOn((javafx.scene.Node) find("Goal")); // will fail if a goal section was not created
		checkPage("Goal", "");

		clickOn((javafx.scene.Node) find("Go 1"));
		checkPage("Go 1", "Go 1 content");
	}

	/**
	 * Tests that add section does not work when a section cannot be duplicated.
	 */
	private void invalidAddSection() {
		clickOn("Mission");
		clickOn(addButtonID);
		checkPopupMsg("Cannot add a section of this type");
		clickOn("OK");
		/*
		 * if addsection is called anyway a null pointer exception will occur because
		 * mission's parent is null As long as we do not get a null pointer exception
		 * then we know the client did not attempt to add a section.
		 */
		checkPage("Mission", "");
	}

	@Test
	void testDeleteSection() throws Exception {
		// independent section
		clickOn("Connect");
		getToPlanEditView("2019");
		validDeleteSection();
		invalidDeleteSection();
		clickOn(saveID);
	}

	/**
	 * This verifies the delete section button is working properly.
	 */
	private void validDeleteSection() {
		doubleClickOn("Mission");
		clickOn((javafx.scene.Node) find("Go 1"));
		clickOn(deleteButtonID);
		checkPopupMsg(
				"Are you sure you want to delete this section and all dependencies?" + "They cannot be recovered.");
		clickOn((javafx.scene.Node) find("Don't Delete"));
		checkPage("Go 1", "Go 1 content");

		clickOn(deleteButtonID);
		checkPopupMsg(
				"Are you sure you want to delete this section and all dependencies?" + "They cannot be recovered.");
		clickOn((javafx.scene.Node) find("Delete"));
		checkBranch();
		verifyThat(treeViewID, (TreeView<Node> treeview) -> {
			if (treeview.getRoot().getChildren().size() != 1) return false;
			return true;
		});
	}

	/**
	 * This verifies the delete section button is working properly.
	 */
	private void invalidDeleteSection() {
		clickOn((javafx.scene.Node) find("Mission"));
		clickOn(deleteButtonID);
		checkPopupMsg(
				"Are you sure you want to delete this section and all dependencies?" + "They cannot be recovered.");
		clickOn((javafx.scene.Node) find("Delete"));
		checkPopupMsg("Cannot delete this section");
		clickOn("OK");

		clickOn((javafx.scene.Node) find("Mission"));
		clickOn(deleteButtonID);
		checkPopupMsg(
				"Are you sure you want to delete this section and all dependencies?" + "They cannot be recovered.");
		clickOn((javafx.scene.Node) find("Don't Delete"));
		checkPage("Mission", "");

		clickOn("Goal");
		clickOn(deleteButtonID);
		checkPopupMsg(
				"Are you sure you want to delete this section and all dependencies?" + "They cannot be recovered.");
		clickOn((javafx.scene.Node) find("Delete"));
		checkPopupMsg("Cannot delete this section");
		clickOn("OK");
	}

	/**
	 * This method makes sure you can open a blank template and edit it
	 */
	@Test
	void testTemplate() {
		clickOn("#connectButton");
		getToPlanEditView("VMOSA");
		String[] names = { "Vision", "Mission", "Objective", "Strategy", "Action Plan" };
		checkBranch(names);
		clickOn(saveID);
		clickOn("OK");
		((TextField) find(yearFieldID)).setText("2023");
		clickOn(backID);
		clickOn("Yes");
		clickOn("2023");
		checkBranch(names);
	}

	/**
	 * This method verifies the save button works. We don't expect the warning popup
	 * to show because we exit right after clicking the save button.
	 */
	@Test
	void testSave() {
		clickOn("#connectButton");
		getToPlanEditView("2019");
		checkBranch();
		clickOn("Mission");
		clickOn(dataFieldID);
		write("mission edit");
		clickOn(nameFieldID);
		write(" name field edit");
		clickOn("Goal");
		clickOn(nameFieldID);
		write(" original");
		clickOn(addButtonID);
		clickOn(yearFieldID);
		write("0");
		clickOn(saveID);
		clickOn(backID);
		clickOn("20190");

		doubleClickOn("Mission name field edit"); // checks that tree view changed in response to name field edit
		checkPage("Mission name field edit", "mission edit", "20190");

		String[] names = { "Goal", "Learning Objective", "Assessment Process", "Results" };
		checkBranch(names);
		clickOn("Goal original");
		String[] names2 = { "Goal original", "Learning Objective", "Assessment Process", "Results" };
		checkBranch(names2);

		clickOn("Goal original");
		clickOn(deleteButtonID);
		clickOn("Delete");
		clickOn(saveID);
		clickOn(backID);
		clickOn("20190");
		doubleClickOn("Mission name field edit");
		checkBranch(names);
		assertThrows(NoSuchElementException.class, () -> find("Goal original"));
	}

	/**
	 * Tests the save warning popup is displayed in response to all types of edits
	 */
	@Test
	void testSavePopup() {
		clickOn("#connectButton");
		getToPlanEditView("2019");
		checkBranch();

		// Tests data field, name field, and year field
		testSavePopupHelp(yearFieldID);
		testSavePopupHelp(dataFieldID);
		testSavePopupHelp(nameFieldID);

		// Tests add section
		clickOn("Goal");
		clickOn(addButtonID);
		clickOn(logoutID);
		checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		clickOn("Cancel");
		clickOn(saveID);

		// Tests delete
		doubleClickOn("Mission1"); // This also tests that treeview labels change in response to name field edits
		clickOn("Goal");
		clickOn(deleteButtonID);
		clickOn("Delete");
		clickOn(logoutID);
		checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		clickOn("Cancel");
		clickOn(saveID);

		clickOn(logoutID);
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
		clickOn(logoutID);
		checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		clickOn("Cancel");
		clickOn(saveID);
	}

	/**
	 * Verifies the logout button works as advertised. Handles if changes have been
	 * made or not.
	 */
	@Test
	void testLogout() {
		clickOn("#connectButton");
		getToPlanEditView("2019");
		checkBranch();
		// cancel case
		clickOn("Mission");
		clickOn(dataFieldID);
		write("mission edit");
		clickOn(logoutID);
		checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		clickOn("Cancel");
		checkPage("Mission", "mission edit");
		// do not save changes, "no" case
		clickOn(logoutID);
		checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		clickOn("No");
		getToPlanEditView("2019");
		checkPage("Mission", "");
		// save changes, "yes" case
		clickOn(dataFieldID);
		write("mission edit");
		clickOn(logoutID);
		checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		clickOn("Yes");
		getToPlanEditView("2019");
		checkPage("Mission", "mission edit");

		// verify no changes doesn't create popup
		doubleClickOn("Mission");
		clickOn("Goal");
		checkPage("Goal", "");
		clickOn(logoutID);
		getToPlanEditView("2019");
		clickOn("Mission");
		((TextField) find("mission edit")).setText("");
		clickOn(saveID);
	}

	/**
	 * Verifies the back to plan button works as advertised. Handles if changes have
	 * been made or not.
	 */
	@Test
	void testBackToPlans() {
		clickOn("#connectButton");
		getToPlanEditView("2019");
		checkBranch();

		// test that unsaved changes produce popup
		// cancel case
		clickOn("Mission");
		clickOn(dataFieldID);
		write("mission edit");
		clickOn(backID);
		checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		clickOn("Cancel");
		checkPage("Mission", "mission edit");
		// do not save changes, "no" case
		clickOn(backID);
		checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		clickOn("No");
		clickOn((javafx.scene.Node) find("2019"));
		checkPage("Mission", "");
		// save changes, "yes" case
		clickOn(dataFieldID);
		write("mission edit");
		clickOn(backID);
		checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		clickOn("Yes");
		clickOn((javafx.scene.Node) find("2019"));
		checkPage("Mission", "mission edit");

		// verify no changes doesn't create popup
		doubleClickOn("Mission");
		clickOn("Goal");
		checkPage("Goal", "");
		clickOn(backID);
		clickOn((javafx.scene.Node) find("2019"));
		clickOn("Mission");
		((TextField) find("mission edit")).setText("");
		clickOn(saveID);
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
		checkBranch();
		// test that unsaved changes produce popup
		// cancel case
		clickOn("Mission");
		clickOn(dataFieldID);
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
		clickOn(dataFieldID);
		write("mission edit");
		closeCurrentWindow();
		checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		clickOn("Yes");
		setUpBeforeEachTest();
		clickOn("Connect");
		getToPlanEditView("2019");
		clickOn((javafx.scene.Node) find("2019"));
		checkPage("Mission", "mission edit");

		// verify no changes doesn't create popup
		doubleClickOn("Mission");
		clickOn("Goal");
		checkPage("Goal", "");
		closeCurrentWindow();
		setUpBeforeEachTest();
		clickOn("Connect");
		getToPlanEditView("2019");
		clickOn((javafx.scene.Node) find("2019"));
		clickOn("Mission");
		((TextField) find("mission edit")).setText("");
		clickOn(saveID);
	}

	/**
	 * Helper method that verifies plan branch structure is displayed
	 */
	private void checkBranch() {
		String[] names = { "Mission", "Goal", "Learning Objective", "Assessment Process", "Results" };
		checkBranch(names);
	}

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
		verifyField(nameFieldID, name);
		verifyField(dataFieldID, content);
	}

	/**
	 * Helper method that checks the content displayed for a given node based on
	 * provided string. This version also checks the year field.
	 * 
	 * @param name
	 * @param content
	 */
	private void checkPage(String name, String content, String year) {
		verifyField(yearFieldID, year);
		checkPage(name, content);
	}

}
