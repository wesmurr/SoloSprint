package businessPlannerApp.frontend.ComparisonViews;

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
	
	@Override
	public void setApplication(Main app) {
		super.setApplication(app);
		this.model=(ComparisonModel) super.model;
	}
	
	@FXML
	public void changeAltSection() {
		TreeItem<PlanSection> item = this.altTreeView.getSelectionModel().getSelectedItem();
		this.model.editName(this.altNameField.getText());
		this.model.editData(this.altDataField.getText());
		this.model.setAltCurrNode(item.getValue());
		this.altNameField.setText(this.model.getAltCurrNode().getName());
		this.altDataField.setText(this.model.getAltCurrNode().getData());
		this.altTreeView.refresh();
	}
	
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
	private void populateAltFields() {
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
}
