package software_masters.gui_test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

class ReadOnlyCommentsTest extends ReadOnlyTest {
	private final String toggleComments = "#toggleCommentsButton";

	/**
	 * Makes sure the expected comments are displayed. Makes sure hidden comments
	 * are not. This makes sure the listed comments update as the section is
	 * changed.
	 */
	@Test
	void commentUpdateWithSectionChange() {
		this.getToPlanEditView("2020 Read Only");
		this.find("user: Testing  default comment display");
		this.find("admin: Testing  default comment display");
		assertThrows(NoSuchElementException.class, () -> this.find("user: no show"));
		this.find("user: vision comment");
		this.doubleClickOn("Vision");
		this.sleep(2000);
		this.clickOn("Mission");
		this.find("user: mission comment");
		assertThrows(NoSuchElementException.class, () -> this.find("user: vision comment"));
	}

	/**
	 * Test that you cannot create a new plan.
	 */
	@Test
	void editablePlanTestNewComment() {
		this.getToPlanEditView("2020 Read Only");
		this.find("user: Testing  default comment display");
		this.find("admin: Testing  default comment display");
		this.sleep(2000);
		assertThrows(NoSuchElementException.class, () -> this.find("user: no show"));
		assertThrows(NoSuchElementException.class, () -> this.find("addComment"));
	}

	/**
	 * Test resolving a comment makes it hidden.
	 */
	@Test
	void editablePlanTestResolvedComment() {
		this.getToPlanEditView("2020 Read Only");
		this.clickOn("user: Testing  default comment display");
		assertThrows(NoSuchElementException.class, () -> this.find("Resolved"));
		this.clickOn("OK");
		this.verifyNoPopup(this.logoutID);
		this.verifyNoPopup(this.backID);
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
	private void verifyNoPopup(String ID) {
		this.clickOn(ID);
		assertThrows(NoSuchElementException.class, () -> this.find("Cancel"));
	}
}
