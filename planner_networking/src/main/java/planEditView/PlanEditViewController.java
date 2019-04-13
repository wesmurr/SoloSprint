package planEditView;

import java.rmi.RemoteException;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import software_masters.model.PlannerModel;
import software_masters.planner_networking.Node;


public class PlanEditViewController
{
	
	Main application;
	PlannerModel model;
	@FXML TreeView<Node> treeView;
	@FXML TextField nameField;
	@FXML TextField dataField;
	@FXML TextField yearField;
	
	Node currNode;
	boolean isPushed;
	
	
	/** Let controller to know view 
	 * @param application
	 */
	public void setApplication(Main application) {
		this.application = application;
		model = this.application.getModel();
		setTreeView();
		setTreeItemAction();
		isPushed = true;
	}
	
	/**
	 * Delete the current selected node in the treeview
	 */
	@FXML
	public void deleteSection() {
		setCurrNode();
		try {
			model.getCurrPlanFile().getPlan().removeNode(currNode);
			setTreeView();
			isPushed = false;
		} catch (IllegalArgumentException e) {
			application.sendError(e.toString());
		}
		
	}
	
	/**
	 * Add a branch at the same level as current selected node to the business plan 
	 */
	@FXML
	public void addSection() {
		setCurrNode();
		try {
			model.getCurrPlanFile().getPlan().addNode(currNode.getParent());
			setTreeView();
			isPushed = false;
		} catch (RemoteException e) {
			application.sendError(e.toString());
		} catch (IllegalArgumentException e) {
			application.sendError(e.toString());
		}
		
	}
	
	
	/**
	 *  Log out the current account on the server
	 */
	@FXML
	public void logOut() {
		//need to ask users if they want to push
		
		currNode = null;
		model.setCookie(null);
		model.setCurrNode(null);
		model.setCurrPlanFile(null);
		model.setYear(null);
	}
	
	/**
	 *	Change the view back to planSelectionView 
	 */
	@FXML
	public void backToPlans() {
		//need to ask users if they want to push
		
		model.setCurrNode(null);
		model.setCurrPlanFile(null);
		model.setYear(null);
		application.showPlanSelectionView();
	}
	
	/**
	 * Push the current business plan to the server
	 */
	@FXML 
	public void push() {
		try {
			//set the year to which the user want 
			//This allow the user to decide which year they want to edit
			// at editing time
			model.getCurrPlanFile().setYear(yearField.getText());
			
			model.pushPlan(model.getCurrPlanFile());
			isPushed = true;
		} catch (IllegalArgumentException e) {
			application.sendError(e.toString());
		} catch (RemoteException e) {
			application.sendError(e.toString());
		}
	}
	
	
	/**
	 * Filling the treeview with nodes from business plan 
	 */
	private void setTreeView()
	{
		treeView.setRoot(convertTree(model.getCurrPlanFile().getPlan().getRoot()));
	}
	
	
	/**
	 * @param root build the treeview start from root node of business plan
	 * @return
	 */
	private TreeItem<Node> convertTree(Node root) {
		TreeItem<Node> newRoot = new TreeItem<Node>(root);
		for (int i = 0; i < root.getChildren().size(); i++) {
			newRoot.getChildren().add(convertTree(root.getChildren().get(i)));
		}
		return newRoot;
	}
	
	/**
	 * Helper method for changing tree*/
	private void setCurrNode() {
		currNode = treeView.getSelectionModel().getSelectedItem().getValue();
	}
	
	
	
	
	/**
	 * set the action when the user click another node
	 * saving the current section is done automatically
	 */
	private void setTreeItemAction()
	{
		treeView.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue)->
		{
			saveAction();
			changeSection(newValue);
		});
	}
	
	/**
	 * Change the nameField and dataField to the content stored in current node
	 * @param item
	 */
	private void changeSection(TreeItem<Node> item)
	{
		currNode = item.getValue();
		nameField.setText(currNode.getName());
		dataField.setText(currNode.getData());
	}
	
	/**
	 * save the content edited in nameField and dataField to the node
	 */
	private void saveAction()
	{
		currNode.setName(nameField.getText());
		currNode.setData(dataField.getText());
		isPushed = false;
	}
}