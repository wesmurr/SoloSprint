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
	
	
	Node currNode;
	boolean isPushed;
	
	
	public void setApplication(Main application) {
		this.application = application;
		model = this.application.getModel();
		setTreeView();
		setTreeItemAction();
		isPushed = true;
	}
	
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
	
	@FXML
	public void logOut() {
		//need to ask users if they want to push
		
		currNode = null;
		model.setCookie(null);
		model.setCurrNode(null);
		model.setCurrPlanFile(null);
		model.setYear(null);
	}
	
	@FXML
	public void backToPlans() {
		//need to ask users if they want to push
		
		model.setCurrNode(null);
		model.setCurrPlanFile(null);
		model.setYear(null);
		application.showPlanSelectionView();
	}
	
	@FXML 
	public void push() {
		try {
			model.pushPlan(model.getCurrPlanFile());
			isPushed = true;
		} catch (IllegalArgumentException e) {
			application.sendError(e.toString());
		} catch (RemoteException e) {
			application.sendError(e.toString());
		}
	}
	
	private void setTreeView()
	{
		treeView.setRoot(convertTree(model.getCurrPlanFile().getPlan().getRoot()));
	}
	
	/**
	 * Helper method for changing tree*/
	private void setCurrNode() {
		currNode = treeView.getSelectionModel().getSelectedItem().getValue();
	}
	
	private TreeItem<Node> convertTree(Node root) {
		TreeItem<Node> newRoot = new TreeItem<Node>(root);
		for (int i = 0; i < root.getChildren().size(); i++) {
			newRoot.getChildren().add(convertTree(root.getChildren().get(i)));
		}
		return newRoot;
	}
	
	
	private void setTreeItemAction()
	{
		treeView.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue)->
		{
			saveAction();
			changeSection(newValue);
		});
	}
	
	private void changeSection(TreeItem<Node> item)
	{
		currNode = item.getValue();
		nameField.setText(currNode.getName());
		dataField.setText(currNode.getData());
	}
	
	private void saveAction()
	{
		currNode.setName(nameField.getText());
		currNode.setData(dataField.getText());
		isPushed = false;
	}
}