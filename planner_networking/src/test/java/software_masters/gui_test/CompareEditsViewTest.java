package software_masters.gui_test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import businessPlannerApp.backend.PlanEdit;
import businessPlannerApp.backend.PlanSection;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

class CompareEditsViewTest extends CompareViewTest {
	private String restoreID="#restoreButton";
	
	/* uses super class method to navigate to comparison view for edit history
	 * (non-Javadoc)
	 * @see software_masters.gui_test.CompareViewTest#getToView(java.lang.String)
	 */
	@Override
	protected void getToView(String item) {
		super.getToView(item);
		clickOn(this.toggleEdits);
		clickOn("user\n1969-12-31 19:00:01.0");
	}
	
	/**
	 * test to verify valid initialization
	 */
	@Test
	void defaultTest() { 
		//edits allowed
		getToView("2019");
		super.defaultButtonTest();
		find("Restore");
		assertEquals("2019",((TextField) find("#yearField")).getText());
		this.verify(planLabelID, "Original Plan");
		this.verify(yearLabelID, "Year");
		this.verify(altPlanLabelID, "Plan Edit");
		this.afterEachTest();
		//read only
		this.setUpBeforeEachTest();
		getToView("2020 Read Only");
		super.defaultButtonTest();
		assertThrows(NoSuchElementException.class, () -> find("Restore"));
		assertEquals("2020",((TextField) find("#yearField")).getText());
		this.verify(planLabelID, "Original Plan");
		this.verify(yearLabelID, "Year");
		this.verify(altPlanLabelID, "Plan Edit");
	}
	
	/**
	 * verifies the user can restore a plan to an old version
	 */
	@Test
	void testRestore() {
		//try restoring valid plan
		getToView("2019");
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
		getToView("2020 Read Only");
		assertThrows(NoSuchElementException.class, () -> find("Restore"));
	}
	
	/**
	 * Makes sure the algorithm for comparing two plans is working.
	 */
	@Test
	void autoComparisonTest() {
		getToView("2019");
		TreeItem<PlanEdit> temp=((TreeView<PlanEdit>) find(this.altTreeViewID)).getRoot();
		assertNotNull(temp.getGraphic());
		temp=((TreeView<PlanEdit>) find(this.treeViewID)).getRoot();
		assertNotNull(temp.getGraphic());
		
		clickOn(backID);
		doubleClickOn("Mission");
		clickOn("Goal");
		clickOn("#addSectionButton");
		doubleClickOn("Mission");
		clickOn(this.toggleEdits);
		clickOn("user\n1969-12-31 19:00:01.0");
		
		TreeItem<PlanSection> section=((TreeView) find(this.treeViewID)).getRoot();
		section=section.getChildren().get(1);
		assertNotNull(section.getGraphic());
		while (section.getChildren().size()>0) {
			section=section.getChildren().get(0);
			assertNotNull(section.getGraphic());
		}
		
		
	}
	
	/* (non-Javadoc)
	 * @see software_masters.gui_test.CompareViewTest#testCannotEditAlternatePlan()
	 */
	@Test
	void testCannotEditAlternatePlan() {
		super.testCannotEditAlternatePlan();
	}
	
	/* (non-Javadoc)
	 * @see software_masters.gui_test.CompareViewTest#backTest()
	 */
	@Test
	void backTest() {
		super.backTest();
	}

}
