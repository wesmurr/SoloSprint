package software_masters.gui_test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javafx.scene.control.TextField;

class CompareEditsViewTest extends CompareViewTest {
	
	@Override
	protected void getToPlanEditView(String item) {
		super.getToPlanEditView(item);
		clickOn(this.toggleEdits);
		clickOn("user\n1969-12-31 19:00:01.0");
	}
	
	@Override
	protected void defaultButtonTest() {
		super.defaultButtonTest();
		find("Restore");
	}
	
	/**
	 * test to verify valid initialization
	 */
	@Test
	void defaultTest() { 
		super.defaultTest();
		this.verify(altPlanLabelID, "Plan Edit");
	}
	
	/**
	 * make sure read only plans are handled
	 */
	@Test
	void testRestore() {
		//try restoring valid plan
		//try restoring a read only plan
	}

}
