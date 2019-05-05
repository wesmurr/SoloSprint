package businessPlannerApp.frontend.ComparisonViews;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import businessPlannerApp.Main;
import businessPlannerApp.backend.PlanSection;
import businessPlannerApp.backend.model.ComparisonModel;
import businessPlannerApp.frontend.planViews.EditController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public abstract class CompareController extends EditController {
	protected ComparisonModel model;
	@FXML
	protected TextField altDataField;
	@FXML
	protected TextField altNameField;
	@FXML
	protected TreeView<PlanSection> altTreeView;
	
	/**
	 * Connect controller to view.
	 */
	@Override
	public void setApplication(Main app) {
		super.setApplication(app);
		this.model=(ComparisonModel) super.model;
	}
	
	/**
	 * This method handles changing between sections for the alternate plan being compared to the original.
	 */
	@FXML
	public void changeAltSection() {
		TreeItem<PlanSection> item = this.altTreeView.getSelectionModel().getSelectedItem();
		this.model.editName(this.altNameField.getText());
		this.model.editData(this.altDataField.getText());
		this.model.setAltCurrNode(item.getValue());
		this.altNameField.setText(this.model.getAltCurrNode().getName());
		this.altDataField.setText(this.model.getAltCurrNode().getData());
		this.altTreeView.refresh();
		populateAltFields();
	}
	
	/**
	 * populates fields representing the alternate plan.
	 */
	@FXML
	abstract public void populateAltPlan();
	
	/**
	 * Navigate back from comparison view to plan edit view.
	 */
	@FXML
	public void back() {
		changeSection();
		this.app.showPlanView();
	}
	
	/**
	 * Initializes the year, name, and data text fields.
	 */
	protected void populateAltFields() {
		this.altNameField.setText(this.model.getAltCurrNode().getName());
		this.altDataField.setText(this.model.getAltCurrNode().getData());
	}
	
	/**
	 * Filling the treeview with nodes from business plan
	 */
	protected void setAltTreeView() {
		this.altTreeView.setRoot(convertTree(this.model.getAltCurrPlanFile().getPlan().getRoot()));
		this.altTreeView.getSelectionModel().select(this.altTreeView.getRoot());
		this.model.setAltCurrNode(this.model.getAltCurrPlanFile().getPlan().getRoot());
		this.altTreeView.refresh();
		populateAltFields();
	}
	
	/**
	 * Search trees for differences using iterative deepening depth first search.
	 */
	private void compareTrees() {
		Stack<TreeItem> mainPlan=new Stack<>();
		Stack<TreeItem> altPlan=new Stack<>();
		TreeItem altCurrent=this.altTreeView.getRoot();
		TreeItem mainCurrent=this.treeView.getRoot();
		
		altPlan.add(this.altTreeView.getRoot());
		mainPlan.add(this.treeView.getRoot());
		int depth=0;
		int breadth=0;
		int size=0;
		
		while (!mainPlan.isEmpty() && !altPlan.isEmpty()){
			if (!altCurrent.getValue().equals(mainCurrent.getValue())) {
				altCurrent.getGraphic().setStyle("-fx-background-color: slateblue;");
				mainCurrent.getGraphic().setStyle("-fx-background-color: slateblue;");
			}
			
			size=mainCurrent.getChildren().size();
			if (altCurrent.getChildren().size()>mainCurrent.getChildren().size())
				size=altCurrent.getChildren().size();
			
			
		}
	}
	
	protected void compareTrees2() {
		Queue<TreeItem<PlanSection>> mainPlan=new LinkedList<>();
		Queue<TreeItem<PlanSection>> altPlan=new LinkedList<>();
		altPlan.add(this.altTreeView.getRoot());
		mainPlan.add(this.treeView.getRoot());
		TreeItem<PlanSection> altCurrent=this.altTreeView.getRoot();
		TreeItem<PlanSection> mainCurrent=this.treeView.getRoot();
		int mainSize=0,altSize=0,maxSize=0,minSize=0;
		
		while (!mainPlan.isEmpty() || !altPlan.isEmpty()) {
			//get corresponding items from queue
			altCurrent=altPlan.remove();
			mainCurrent=mainPlan.remove();
			
			if (altCurrent==null && mainCurrent==null) {
				
			}
			else if(altCurrent==null) {
				mainCurrent.getGraphic().setStyle("-fx-background-color: slateblue;");
			}
			else if(mainCurrent==null) {
				altCurrent.getGraphic().setStyle("-fx-background-color: slateblue;");
			}
			//check is the corresponding items are equal. if they are not, change view to indicate
			else if (!altCurrent.getValue().equals(mainCurrent.getValue())) {
				altCurrent.getGraphic().setStyle("-fx-background-color: slateblue;");
				mainCurrent.getGraphic().setStyle("-fx-background-color: slateblue;");
			}
			
			//since tree structures will be significantly different, the algorithm must be able to handle fillers. In this
			//case I used null to fill gaps between the two trees
			if (altCurrent!=null) {
				altCurrent.getChildren().forEach(child -> {altPlan.add((TreeItem<PlanSection>) child);});
				altSize=altCurrent.getChildren().size();
			}
			if (mainCurrent!=null) {
				mainCurrent.getChildren().forEach(child -> {mainPlan.add((TreeItem<PlanSection>) child);});
				mainSize=mainCurrent.getChildren().size();
			}
			//use null to handle nodes that appear in one tree but not the other.
			if (altSize>mainSize) {
				for(int i=mainSize;i<altSize;i++)
					mainPlan.add(null);
			}else {
				for(int i=altSize;i<mainSize;i++)
					altPlan.add(null);
			}
			
		}
		
	}
	
	
}
