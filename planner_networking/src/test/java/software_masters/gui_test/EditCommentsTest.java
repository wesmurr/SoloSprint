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
	 * Makes sure the expected comments are displayed. Makes sure hidden comments
	 * are not. This makes sure the listed comments update as the section is
	 * changed.
	 */
	@Test
	void commentUpdateWithSectionChange() {
		this.clickOn("Connect");
		this.getToPlanEditView("2019");
		this.sleep(20000);
		this.find("user: Testing  default comment display");
		this.find("admin: Testing  default comment display");
		assertThrows(NoSuchElementException.class, () -> this.find("user: no show"));
		this.find("user: mission comment");
		this.doubleClickOn("Mission");
		this.clickOn("Goal");
		this.sleep(20000);
		this.find("user: goal comment");
		assertThrows(NoSuchElementException.class, () -> this.find("user: mission comment"));
	}

	/**
	 * Test adding a new comment to an editable plan.
	 */
	@Test
	void editablePlanTestNewComment() {
		this.clickOn("Connect");
		this.getToPlanEditView("2019");
		this.find("user: Testing  default comment display");
		this.find("admin: Testing  default comment display");
		assertThrows(NoSuchElementException.class, () -> this.find("user: no show"));
		this.clickOn(this.addComment);
		this.write("New Comment");
		this.clickOn("Cancel");
		assertThrows(NoSuchElementException.class, () -> this.find("user: New Comment"));
		this.clickOn(this.addComment);
		this.write("New Comment");
		this.clickOn("OK");
		this.find("user: New Comment");

		this.verifySavePopup(this.logoutID);
		this.verifySavePopup(this.backID);
		this.clickOn(this.saveID);
	}

	/**
	 * Test resolving a comment makes it hidden. Makes sure change was detected to
	 * allow save.
	 */
	@Test
	void editablePlanTestResolvedComment() {
		this.clickOn("Connect");
		this.getToPlanEditView("2019");
		this.clickOn("user: New Comment");
		this.clickOn("Resolved");
		assertThrows(NoSuchElementException.class, () -> this.find("user: New Comment"));

		this.verifySavePopup(this.logoutID);
		this.verifySavePopup(this.backID);
		this.clickOn(this.saveID);
	}

	/**
	 * moves from login screen to the plan edit window in the gui
	 */
	@Override
	protected void getToPlanEditView(String item) {
		super.getToPlanEditView(item);
		this.clickOn(this.toggleComments);
	}

	/**
	 * Helper method to ensure added comments are detected and saved is requested.
	 *
	 * @param ID
	 */
	private void verifySavePopup(String ID) {
		this.clickOn(ID);
		this.checkPopupMsg("You have unsaved changes. Do you wish to save before exiting?");
		this.clickOn("Cancel");
	}

}
