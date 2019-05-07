package software_masters.gui_test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import javafx.scene.control.TextField;

class CompareEditsViewTest extends CompareViewTest {
	private String restoreID="#restoreButton";
	
	@Override
	protected void getToPlanEditView(String item) {
		super.getToPlanEditView(item);
		clickOn(this.toggleEdits);
		clickOn("user\n1969-12-31 19:00:01.0");
	}
	
	/**
	 * test to verify valid initialization
	 */
	@Test
	void defaultTest() { 
		//edits allowed
		getToPlanEditView("2019");
		super.defaultButtonTest();
		find("Restore");
		assertEquals("2019",((TextField) find("#yearField")).getText());
		this.verify(planLabelID, "Original Plan");
		this.verify(yearLabelID, "Year");
		this.verify(altPlanLabelID, "Plan Edit");
		this.afterEachTest();
		//read only
		this.setUpBeforeEachTest();
		getToPlanEditView("2020 Read Only");
		super.defaultButtonTest();
		assertThrows(NoSuchElementException.class, () -> find("Restore"));
		assertEquals("2020",((TextField) find("#yearField")).getText());
		this.verify(planLabelID, "Original Plan");
		this.verify(yearLabelID, "Year");
		this.verify(altPlanLabelID, "Plan Edit");
	}
	
	/**
	 * make sure read only plans are handled
	 */
	@Test
	void testRestore() {
		//try restoring valid plan
		getToPlanEditView("2019");
		clickOn(dataFieldID);
		write("edit to disappear");
		clickOn(restoreID);
		verifyField(dataFieldID,"Old Edit");
		clickOn(backID);
		verifyField(dataFieldID,"Old Edit");
		clickOn(logoutID);
		clickOn("No");
		closeCurrentWindow();
		
		//try restoring a read only plan
		this.setUpBeforeEachTest();
		getToPlanEditView("2020 Read Only");
		assertThrows(NoSuchElementException.class, () -> find("Restore"));
	}

}
