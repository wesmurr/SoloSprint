package software_masters.gui_test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import businessPlannerApp.backend.PlanSection;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;

class ComparePlansViewTest extends CompareViewTest {
	private String compareToID="#compareToButton";
	private String selectPlanID="#altSelectPlanMenu";
	
	/* uses super class method to navigate to the plan comparison view
	 * (non-Javadoc)
	 * @see software_masters.gui_test.CompareViewTest#getToView(java.lang.String)
	 */
	@Override
	protected void getToView(String item) {
		super.getToView(item);
		clickOn(this.compareToID);
		clickOn(this.selectPlanID);
		type(KeyCode.DOWN);
		type(KeyCode.ENTER);
		sleep(500);
	}
	
	/**
	 * test to verify valid initialization
	 */
	@Test
	void defaultTest() { 
		//edits allowed
		getToView("2019");
		super.defaultButtonTest();
		assertThrows(NoSuchElementException.class, () -> find("Restore"));
		assertEquals("2019",((TextField) find("#yearField")).getText());
		this.verify(planLabelID, "Original Plan");
		this.verify(yearLabelID, "Year");
		this.verify(altPlanLabelID, "Alternate Plan");
		this.afterEachTest();
		//read only
		this.setUpBeforeEachTest();
		getToView("2020 Read Only");
		super.defaultButtonTest();
		assertThrows(NoSuchElementException.class, () -> find("Restore"));
		assertEquals("2020",((TextField) find("#yearField")).getText());
		this.verify(planLabelID, "Original Plan");
		this.verify(yearLabelID, "Year");
		this.verify(altPlanLabelID, "Alternate Plan");
	}
	
	/**
	 * test navigation through alternate plan tree
	 */
	@Test
	void alternatePlanNavigationTest() { 
		String[] names={"Mission","Goal", "Learning Objective", "Assessment Process", "Results"};
		getToView("2020 Read Only");
		checkAltBranch(names);
	}
	
	/**
	 * tests the algorithm for automatically comparing two plans
	 */
	@Test
	void autoComparisonTest() {
		String[] names={"Mission","Goal", "Learning Objective", "Assessment Process", "Results"};
		String[] names1={"Vision","Mission","Objective","Strategy","Action Plan"};
		getToView("2019");
		for(String name: names) {
			doubleClickOn(name);
			TreeItem<PlanSection> item=((TreeView<PlanSection>) find(this.altTreeViewID)).getSelectionModel().getSelectedItem();
			assertNull(item.getGraphic());
		}
		this.afterEachTest();
		
		this.setUpBeforeEachTest();
		getToView("2020 Read Only");
		for(String name: names) {
			doubleClickOn(name);
			TreeItem<PlanSection> item=((TreeView<PlanSection>) find(this.altTreeViewID)).getSelectionModel().getSelectedItem();
			assertNotNull(item.getGraphic());
		}
		for(String name: names1) {
			doubleClickOn(name);
			TreeItem<PlanSection> item=((TreeView<PlanSection>) find(this.altTreeViewID)).getSelectionModel().getSelectedItem();
			assertNotNull(item.getGraphic());
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
