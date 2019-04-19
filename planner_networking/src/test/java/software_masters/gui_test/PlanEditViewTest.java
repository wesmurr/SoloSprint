package software_masters.gui_test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import software_masters.planner_networking.Node;


import static org.testfx.api.FxAssert.verifyThat;
class PlanEditViewTest extends ApplicationTest
{

	String addButtonID = "addSectionButton";
	String deleteButtonID = "deleteSectionButton";
	String saveID = "saveButton";
	String backID = "backToPlanButton";
	String yearLabelID = "yearLabel";
	String yearFieldID = "yearField";
	String logoutID = "logoutButton";
	String treeViewID = "treeView";
	String nameFieldID = "nameField";
	String dataFieldID = "dataField";
	
	
	@BeforeAll
	public static void setUp() throws Exception
	{
		ApplicationTest.launch(null);
	}
	
	@Test
	void test()
	{
		
	}

	private void defaultTest()
	{
		verifyThat(addButtonID, (Button button) -> {return button.getText().equals("Add Section");});
		verifyThat(deleteButtonID, (Button button) -> {return button.getText().equals("Delete Section");});
		verifyThat(saveID, (Button button) -> {return button.getText().equals("Save");});
		verifyThat(backID, (Button button) -> {return button.getText().equals("Back to plans");});
		verifyThat(yearLabelID, (Label label) -> {return label.getText().equals("Year");});
		verifyThat(yearFieldID, (TextField textField) -> {return textField.getText() != null;});
		verifyThat(logoutID, (Button button) -> {return button.getText().equals("Log Out");});
		verifyThat(treeViewID, (TreeView<Node>, treeView) -> {treeView.getRoot();});
		
		
	}
}
