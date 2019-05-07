package software_masters.gui_test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;

class ComparePlansViewTest extends CompareViewTest {
	private String compareToID="#compareToButton";
	private String selectPlanID="#altSelectPlanMenu";
	
	@Override
	protected void getToPlanEditView(String item) {
		super.getToPlanEditView(item);
		clickOn(this.compareToID);
		clickOn(this.selectPlanID);
		ComboBox menu=(ComboBox) find(this.selectPlanID);
		menu.getSelectionModel().select(menu.getItems().get(0));
	}
	
	/**
	 * test to verify valid initialization
	 */
	@Test
	void defaultTest() { 
		//edits allowed
		getToPlanEditView("2019");
		super.defaultButtonTest();
		assertThrows(NoSuchElementException.class, () -> find("Restore"));
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

}
