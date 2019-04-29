package software_masters.gui_test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

class ReadOnlyCommentsTest extends ReadOnlyTest {
	private final String toggleComments = "#toggleCommentsButton";
	
	/**
	 * Verifies the program toggles between views well
	 */
	@Test
	public void toggleViewsTest() {
		getToPlanEditView("2020 Read Only");
		clickOn(this.toggleComments);
		find("ShowComments");
		assertThrows(NoSuchElementException.class, () -> find("HideComments"));
		defaultItemsTest();
		clickOn(this.toggleComments);
		find("HideComments");
		assertThrows(NoSuchElementException.class, () -> find("ShowComments"));
		clickOn(this.toggleComments);
	}

	/**
	 * Makes sure the expected comments are displayed. Makes sure hidden comments
	 * are not. This makes sure the listed comments update as the section is
	 * changed.
	 */
	@Test
	void commentUpdateWithSectionChange() {
		getToPlanEditView("2020 Read Only");
		clickOn(this.toggleComments);
		assertThrows(NoSuchElementException.class, () -> find("HideComments"));
		assertThrows(NoSuchElementException.class, () -> find("Save"));
		clickOn(this.toggleComments);
		find("user: Testing  default comment display");
		find("admin: Testing  default comment display");
		assertThrows(NoSuchElementException.class, () -> find("user: no show"));
		find("user: vision comment");
		doubleClickOn("Vision");
		sleep(2000);
		clickOn("Mission");
		find("user: mission comment");
		assertThrows(NoSuchElementException.class, () -> find("user: vision comment"));
	}

	/**
	 * Test that you cannot create a new plan.
	 */
	@Test
	void editablePlanTestNewComment() {
		getToPlanEditView("2020 Read Only");
		find("user: Testing  default comment display");
		find("admin: Testing  default comment display");
		sleep(2000);
		assertThrows(NoSuchElementException.class, () -> find("user: no show"));
		assertThrows(NoSuchElementException.class, () -> find("addComment"));
	}

	/**
	 * Test resolving a comment makes it hidden.
	 */
	@Test
	void editablePlanTestResolvedComment() {
		getToPlanEditView("2020 Read Only");
		clickOn("user: Testing  default comment display");
		assertThrows(NoSuchElementException.class, () -> find("Resolved"));
		clickOn("OK");
		verifyNoPopup(this.logoutID);
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
	private void verifyNoPopup(String ID) {
		clickOn(ID);
		assertThrows(NoSuchElementException.class, () -> find("Cancel"));
	}
}
