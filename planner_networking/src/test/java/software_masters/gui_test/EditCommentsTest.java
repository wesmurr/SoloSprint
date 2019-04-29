package software_masters.gui_test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * @author Wesley Murray
 *
 *         This class extends PlanEditViewTest because all the original tests
 *         should still pass when comment list is displayed.
 *
 */
class EditCommentsTest extends PlanEditViewTest {
	private final String addComment = "#addCommentButton";
	private final String toggleComments = "#toggleCommentsButton";
	
	/**
	 * Verifies the program toggles between views well
	 */
	@Test
	public void toggleViewsTest() {
		clickOn("Connect");
		getToPlanEditView("2019");
		find("HideComments");
		assertThrows(NoSuchElementException.class, () -> find("ShowComments"));
		clickOn(this.toggleComments);
		find("ShowComments");
		assertThrows(NoSuchElementException.class, () -> find("HideComments"));
		defaultButtonTest();
	}

	/**
	 * Makes sure the expected comments are displayed. Makes sure hidden comments
	 * are not. This makes sure the listed comments update as the section is
	 * changed.
	 */
	@Test
	void commentUpdateWithSectionChange() {
		clickOn("Connect");
		getToPlanEditView("2019");
		sleep(2000);
		clickOn(this.toggleComments);
		assertThrows(NoSuchElementException.class, () -> find("HideComments"));
		assertThrows(NoSuchElementException.class, () -> find("AddComment"));
		clickOn(this.toggleComments);
		doubleClickOn("Mission");
		doubleClickOn("Goal");
		clickOn(this.addComment);
		write("goal comment");
		clickOn("OK");
		doubleClickOn("Learning Objective");
		clickOn(this.addComment);
		write("learning objective comment");
		clickOn("OK");
		
		clickOn("Mission");
		find("user: Testing  default comment display");
		find("admin: Testing  default comment display");
		assertThrows(NoSuchElementException.class, () -> find("user: no show"));
		find("user: mission comment");
		
		clickOn("Goal");
		find("user: goal comment");
		assertThrows(NoSuchElementException.class, () -> find("user: no show"));
		assertThrows(NoSuchElementException.class, () -> find("user: mission comment"));
		
		clickOn("Learning Objective");
		find("user: learning objective comment");
		assertThrows(NoSuchElementException.class, () -> find("user: no show"));
		assertThrows(NoSuchElementException.class, () -> find("user: mission comment"));
		assertThrows(NoSuchElementException.class, () -> find("user: goal comment"));
		
		clickOn(saveID);
	}

	/**
	 * Test adding a new comment to an editable plan.
	 */
	@Test
	void editablePlanTestNewComment() {
		clickOn("Connect");
		getToPlanEditView("2019");
		find("user: Testing  default comment display");
		find("admin: Testing  default comment display");
		assertThrows(NoSuchElementException.class, () -> find("user: no show"));
		clickOn(this.addComment);
		write("New Comment");
		clickOn("Cancel");
		assertThrows(NoSuchElementException.class, () -> find("user: New Comment"));
		clickOn(this.addComment);
		write("New Comment");
		clickOn("OK");
		find("user: New Comment");

		verifySavePopup(this.logoutID);
		verifySavePopup(this.backID);
		clickOn(this.saveID);
	}

	/**
	 * Test resolving a comment makes it hidden. Makes sure change was detected to
	 * allow save.
	 */
	@Test
	void editablePlanTestResolvedComment() {
		clickOn("Connect");
		getToPlanEditView("2019");
		clickOn("user: New Comment");
		clickOn("Resolved");
		assertThrows(NoSuchElementException.class, () -> find("user: New Comment"));

		verifySavePopup(this.logoutID);
		verifySavePopup(this.backID);
		clickOn(this.saveID);
	}

	/**
	 * moves from login screen to the plan edit window in the gui
	 */
	@Override
	protected void getToPlanEditView(String item) {
		super.getToPlanEditView(item);
		clickOn(this.toggleComments);
	}

	/**
	 * Helper method to ensure added comments are detected and saved is requested.
	 *
	 * @param ID
	 */
	private void verifySavePopup(String ID) {
		clickOn(ID);
		checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		clickOn("Cancel");
	}

}
