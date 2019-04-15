package planEditView;

import java.rmi.RemoteException;
import java.util.Optional;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
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
	
	boolean isPushed;
	
	
	/** Let controller to know view 
	 * @param application
	 */
	public void setApplication(Main application) {
		this.application = application;
		model = this.application.getModel();
		setTreeView();
		isPushed = true;
	}
	
	/**
	 * Delete the current selected node in the treeview
	 */
	@FXML
	public void deleteSection() {
		try {
			this.changeSection();
			model.removeBranch();
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
		try {
			this.changeSection();
			model.addBranch();
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
		
		if (this.isPushed)
		{
			model.setCookie(null);
			model.setCurrNode(null);
			model.setCurrPlanFile(null);
			application.showLoginView();
		}
		else
		{
			this.warningToSave();
		}
	}
	
	/**
	 *	Change the view back to planSelectionView 
	 */
	@FXML
	public void backToPlans() {
		//need to ask users if they want to push
		
		model.setCurrNode(null);
		model.setCurrPlanFile(null);
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
			changeSection();
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
		treeView.getSelectionModel().select(treeView.getRoot());
		model.setCurrNode(model.getCurrPlanFile().getPlan().getRoot());
		treeView.refresh();
		populateFields();
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
	 * Change the nameField and dataField to the content stored in current node
	 * @param item
	 */
	@FXML
	private void changeSection()
	{
		TreeItem<Node> item = treeView.getSelectionModel().getSelectedItem();
		model.editName(nameField.getText());
		model.editData(dataField.getText());
		model.setCurrNode(item.getValue());
		nameField.setText(model.getCurrNode().getName());
		dataField.setText(model.getCurrNode().getData());
		treeView.refresh();
		isPushed = false;
	}
	

	/**Initializes the year, name, and data text fields.
	 * 
	 */
	private void populateFields()
	{
		yearField.setText(model.getCurrPlanFile().getYear());
		nameField.setText(model.getCurrNode().getName());
		dataField.setText(model.getCurrNode().getData());
	}
	
	/**
	 * Asks user if they want to save unsaved changes before leaving the plan edit view window
	 * @return result of button press
	 */
	public void warningToSave()
	{
	Alert alert = new Alert(AlertType.CONFIRMATION);
	String message = "You have unsaved changes. Do you wish to save before exiting?";
	alert.setContentText(message);
	ButtonType okButton = new ButtonType("Yes");
	ButtonType noButton = new ButtonType("No");
	ButtonType cancelButton = new ButtonType("Cancel");
	alert.getButtonTypes().setAll(okButton,noButton,cancelButton);
	Optional<ButtonType> result = alert.showAndWait();
	if (result.get() == okButton)
	{
			this.push();
			model.setCookie(null);
			model.setCurrNode(null);
			model.setCurrPlanFile(null);
			application.showLoginView();
		
	}
	else if (result.get() == noButton) {
		model.setCookie(null);
		model.setCurrNode(null);
		model.setCurrPlanFile(null);
		application.showLoginView();

	}
	else if (result.get() == cancelButton) {
	}
	}
}