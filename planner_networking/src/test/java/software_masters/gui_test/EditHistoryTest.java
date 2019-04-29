package software_masters.gui_test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

class EditHistoryTest extends PlanEditViewTest {
	private final String toggleEdits = "#editsToggle";
	/**
	 * moves from login screen to the plan edit window in the gui
	 */
	@Override
	protected void getToPlanEditView(String item) {
		super.getToPlanEditView(item);
		clickOn(this.toggleEdits);
	}
	
	@Test
	public void toggleViewsTest() {
		clickOn("Connect");
		getToPlanEditView("2019");
		find("HideEdits");
		assertThrows(NoSuchElementException.class, () -> find("ShowEdits"));
		clickOn(this.toggleEdits);
		find("ShowEdits");
		assertThrows(NoSuchElementException.class, () -> find("HideEdits"));
		defaultButtonTest();
		
	}
}
