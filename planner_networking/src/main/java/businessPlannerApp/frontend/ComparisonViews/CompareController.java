package businessPlannerApp.frontend.ComparisonViews;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import businessPlannerApp.Main;
import businessPlannerApp.backend.PlanSection;
import businessPlannerApp.backend.model.ComparisonModel;
import businessPlannerApp.frontend.planViews.EditController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class CompareController extends EditController {
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
		this.model.getAltCurrNode().setName(this.altNameField.getText());
		this.model.getAltCurrNode().setData(this.altDataField.getText());
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
	public void populateAltPlan() {
		this.compareTrees();
		this.altTreeView.refresh();
		this.treeView.refresh();
	}
	
	/**
	 * Navigate back from comparison view to plan edit view.
	 */
	@FXML
	public void back() {
		this.model.getCurrPlanFile().setYear(this.yearField.getText());
		changeSection();
		CompareController cont=(CompareController) this.model.getController();
		this.app.showPlanView();
		((EditController) this.model.getController()).setPushed(cont.isPushed);
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
		populateAltFields();
	}
	
	/**
	 * method that uses breadth first search to compare two trees.
	 * add a node graphic to treeitems to indicate there are any differences.
	 */
	protected void compareTrees() {
		Label diffLabel=null;
		//create queue for parsing tree using breadth first search
		Queue<TreeItem<PlanSection>> mainPlan=new LinkedList<>();
		Queue<TreeItem<PlanSection>> altPlan=new LinkedList<>();
		altPlan.add(this.altTreeView.getRoot());
		mainPlan.add(this.treeView.getRoot());
		//track current tree item
		TreeItem<PlanSection> altCurrent=null;
		TreeItem<PlanSection> mainCurrent=null;

		int mainSize=0,altSize=0;
		while (!mainPlan.isEmpty() || !altPlan.isEmpty()) {
			mainSize=0;altSize=0;
			//get corresponding items from queue
			altCurrent=altPlan.remove();
			mainCurrent=mainPlan.remove();
			if (altCurrent==null && mainCurrent==null) {}
			else if(altCurrent==null) {
				//create indicator for differences
				diffLabel=new Label("X");
				diffLabel.setTextFill(Color.DARKRED);
				mainCurrent.setGraphic(diffLabel);
			}
			else if(mainCurrent==null) {
				//create indicator for differences
				diffLabel=new Label("X");
				diffLabel.setTextFill(Color.DARKRED);
				altCurrent.setGraphic(diffLabel);
			}
			//check is the corresponding items are equal. if they are not, change view to indicate
			else if (!altCurrent.getValue().getData().equals(mainCurrent.getValue().getData())
					|| !altCurrent.getValue().getName().equals(mainCurrent.getValue().getName())) {
				//create indicator for differences
				diffLabel=new Label("X");
				diffLabel.setTextFill(Color.DARKRED);
				mainCurrent.setGraphic(diffLabel);
				diffLabel=new Label("X");
				diffLabel.setTextFill(Color.DARKRED);
				altCurrent.setGraphic(diffLabel);
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
