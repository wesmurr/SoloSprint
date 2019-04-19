package software_masters.gui_test;

import org.junit.jupiter.api.Test;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import software_masters.planner_networking.Node;
import static org.testfx.api.FxAssert.verifyThat;
class PlanEditViewTest extends GuiTestBase
{

	String addButtonID = "#addSectionButton";
	String deleteButtonID = "#deleteSectionButton";
	String saveID = "#saveButton";
	String backID = "#backToPlanButton";
	String yearLabelID = "#yearLabel";
	String yearFieldID = "#editYearField";
	String logoutID = "#logoutButton";
	String treeViewID = "#treeView";
	String nameFieldID = "#nameField";
	String dataFieldID = "#dataField";
	
	
	
	@Test
	void test()
	{
		getToPlanEditView();
		defaultTest();
		validAddSection();
		validDeleteSection();
	}
	
	private void getToPlanEditView()
	{
		clickOn("#loginButton");
		clickOn((javafx.scene.Node) find("2019"));
		
		
	}
	private void defaultTest()
	{
		verifyThat(addButtonID, (Button button) -> {return button.getText().equals("Add Section");});
		verifyThat(deleteButtonID, (Button button) -> {return button.getText().equals("Delete Section");});
		verifyThat(saveID, (Button button) -> {return button.getText().equals("Save");});
		verifyThat(backID, (Button button) -> {return button.getText().equals("Back to plans");});
		verifyThat(yearLabelID, (Label label) -> {return label.getText().equals("Year");});
		verifyThat(yearFieldID, (TextField textField) -> {return textField.getText().equals( "2019");});
		verifyThat(logoutID, (Button button) -> {return button.getText().equals("Log Out");});
		verifyThat(treeViewID, (TreeView<Node> tree) -> {return tree.getRoot().getValue().getName().equals("Mission");});	
	}
	
	private void validAddSection()
	{
		TreeView<Node> tree = find(treeViewID);
		expand(tree.getRoot());
		sleep(1000);
		clickOn((javafx.scene.Node)find("Goal"));
		clickOn(addButtonID);
		verifyThat(treeViewID, (TreeView<Node> treeview) -> {
			
			if(treeview.getRoot().getChildren().size() != 2)
				return false;
			
			for(TreeItem<Node> node: treeview.getRoot().getChildren())
			{
				if (!node.getValue().getName().equals("Goal"))
					return false;
			}
			return true;
		});
	}
	
	private void validDeleteSection()
	{
		TreeView<Node> tree = find(treeViewID);
		expand(tree.getRoot());
		sleep(1000);
		clickOn((javafx.scene.Node)find("Goal"));
		clickOn(deleteButtonID);
		
		clickOn((javafx.scene.Node)find("Delete"));
		verifyThat(treeViewID, (TreeView<Node> treeview) -> {
			
			if(treeview.getRoot().getChildren().size() != 1)
				return false;
			
			for(TreeItem<Node> node: treeview.getRoot().getChildren())
			{
				if (!node.getValue().getName().equals("Goal"))
					return false;
			}
			return true;
		});
	}
	
	
	
	
	
	
	
	
	private void expand(TreeItem<Node> root)
	{
		root.setExpanded(true);
		for(TreeItem<Node> item : root.getChildren())
			expand(item);
	}
	
	
}
