package software_masters.gui_test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import businessPlannerApp.backend.PlanEdit;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.control.ListView;

class EditHistoryTest extends PlanEditViewTest {
	protected final String toggleEdits = "#editsToggle";
	protected final String editsList = "#editList";
	private String altDataFieldID="#altDataField";
	/**
	 * moves from login screen to the plan edit window in the gui
	 */
	@Override
	protected void getToView(String item) {
		super.getToView(item);
		clickOn(this.toggleEdits);
	}
	
	/**
	 * Verifies the user can easily show and hide list of edit history.
	 */
	@Test
	public void toggleViewsTest() {
		//navigate to edit history view
		clickOn("Connect");
		getToView("2019");
		//make sure correct buttons appear in history view
		find("HideEdits");
		assertThrows(NoSuchElementException.class, () -> find("ShowEdits"));
		//make history view is hidden properly
		clickOn(this.toggleEdits);
		find("ShowEdits");
		assertThrows(NoSuchElementException.class, () -> find("HideEdits"));
		//make sure correct plan edit view buttons are displayed
		defaultButtonTest();
	}
	
	/**
	 * Verifies the view associated with a plan updates when any user makes an edit to
	 * this plan.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testObserver() {
		//navigate to view showing edit history of plan
		clickOn("Connect");
		getToView("2019");
		//select first edit from list view
		clickOn(this.editsList);
		type(KeyCode.DOWN);
		clickOn(((ListView)find(this.editsList)).getSelectionModel().getSelectedItem().toString());
		assertFalse(((TextField) find(this.altDataFieldID)).getText().contains("Observer Edit"));
		clickOn("Back");
		//edit plan and save
		clickOn(this.toggleEdits);
		clickOn("Mission");
		((TextField) find(this.dataFieldID)).setText("Observer Edit");
		clickOn(saveID);
		//select first edit and make sure text was changed
		clickOn(this.editsList);
		type(KeyCode.DOWN);
		type(KeyCode.UP);
		clickOn(((ListView)find(this.editsList)).getSelectionModel().getSelectedItem().toString());
		assertTrue(((TextField) find(this.altDataFieldID)).getText().contains("Observer Edit"));
	}
}
