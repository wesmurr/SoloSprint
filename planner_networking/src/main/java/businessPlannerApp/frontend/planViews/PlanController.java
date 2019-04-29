package businessPlannerApp.frontend.planViews;

import businessPlannerApp.Main;
import businessPlannerApp.backend.PlanSection;
import businessPlannerApp.frontend.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class PlanController extends ViewController {
	protected String selfPath = "";
	@FXML
	protected ToggleButton commentsToggle;
	@FXML
	protected ToggleButton editsToggle;
	@FXML
	protected TextField dataField;
	@FXML
	protected TextField nameField;
	@FXML
	protected TreeView<PlanSection> treeView;
	@FXML
	protected TextField yearField;

	/**
	 * Handles navigating back to plans
	 */
	public void backToPlans() {
		this.model.backToPlans();
		this.app.showPlanSelectionView();
	}

	/**
	 * @param root build the treeview start from root node of business plan
	 * @return
	 */
	private TreeItem<PlanSection> convertTree(PlanSection root) {
		final TreeItem<PlanSection> newRoot = new TreeItem<>(root);
		for (int i = 0; i < root.getChildren().size(); i++)
			newRoot.getChildren().add(convertTree(root.getChildren().get(i)));
		return newRoot;
	}

	/**
	 * @return the selfPath
	 */
	public String getSelfPath() { return this.selfPath; }

	/**
	 * handles logging out
	 */
	public void logout() {
		this.model.logout();
		this.app.showLoginView();
	}

	/**
	 * Initializes the year, name, and data text fields.
	 */
	protected void populateFields() {
		this.yearField.setText(this.model.getCurrPlanFile().getYear());
		this.nameField.setText(this.model.getCurrNode().getName());
		this.dataField.setText(this.model.getCurrNode().getData());
	}

	/**
	 * Initialize view
	 *
	 * @param this.app
	 */
	@Override
	public void setApplication(Main app) {
		super.setApplication(app);
		setTreeView();
	}

	/**
	 * @param selfPath the selfPath to set
	 */
	public void setSelfPath(String selfPath) { this.selfPath = selfPath; }

	/**
	 * Filling the treeview with nodes from business plan
	 */
	protected void setTreeView() {
		this.treeView.setRoot(convertTree(this.model.getCurrPlanFile().getPlan().getRoot()));
		this.treeView.getSelectionModel().select(this.treeView.getRoot());
		this.model.setCurrNode(this.model.getCurrPlanFile().getPlan().getRoot());
		this.treeView.refresh();
		populateFields();
	}
	
	/**
	 * Update observer states.
	 */
	public void update() {
		
	}

}
