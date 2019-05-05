package software_masters.gui_test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import businessPlannerApp.backend.PlanEdit;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;

class EditHistoryTest extends PlanEditViewTest {
	protected final String toggleEdits = "#editsToggle";
	protected final String editsList = "#editsList";
	/**
	 * moves from login screen to the plan edit window in the gui
	 */
	@Override
	protected void getToPlanEditView(String item) {
		super.getToPlanEditView(item);
		clickOn(this.toggleEdits);
	}
	
	/**
	 * Verifies the user can easily show and hide list of edit history.
	 */
	@Test
	public void toggleViewsTest() {
		//navigate to edit history view
		clickOn("Connect");
		getToPlanEditView("2019");
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
		getToPlanEditView("2019");
		//open second window
		this.setUpBeforeEachTest();
		//navigate to edit history view
		clickOn("Connect");
		getToPlanEditView("2019");
		clickOn("Mission");
		((TextField) find(this.dataFieldID)).setText("Observer Edit");
		clickOn(saveID);
//		closeCurrentWindow();
		
		fail("unable to open two apps simultaneously at the moment.");
		
		//get listview
		ListView<PlanEdit> editHist=(ListView<PlanEdit>) find(this.editsList);
		editHist.getSelectionModel().select(0);
		sleep(4000);
		
	}
}
