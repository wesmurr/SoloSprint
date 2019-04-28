package businessPlannerApp.frontend.planSelectionView;

import java.rmi.RemoteException;

import businessPlannerApp.Main;
import businessPlannerApp.backend.PlanFile;
import businessPlannerApp.frontend.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 * @author lee.kendall MVC Controller for the plan selection view
 */
public class PlanSelectionViewController extends ViewController {

	@FXML
	private ListView<PlanFile> departmentPlanList;

	@FXML
	private ListView<PlanFile> planTemplateList;

	/**
	 * This method generates a list view of plans associated with client department
	 *
	 * @return
	 */
	public void genPlansList() {
		ObservableList<PlanFile> items = null;
		try {
			items = FXCollections.observableArrayList(this.app.getModel().listPlans());
		} catch (final RemoteException e) {
			this.app.showConnectToServer();
		}
		this.departmentPlanList.setItems(items);

	}

	/**
	 * This method generates a list view of developer templates
	 *
	 * @return
	 */
	public void genTemplateList() {
		ObservableList<PlanFile> items = null;
		try {
			items = FXCollections.observableArrayList(this.app.getModel().listPlanTemplates());
		} catch (final RemoteException e) {
			this.app.showConnectToServer();
		}
		this.planTemplateList.setItems(items);

	}

	/**
	 * Resets client's cookie and planFile, displays the login window
	 *
	 * @param event Action
	 */
	@FXML
	public void Logout(ActionEvent event) {
		this.app.getModel().setCookie(null);
		this.app.getModel().setCurrNode(null);
		this.app.getModel().setCurrPlanFile(null);
		this.app.showLoginView();
	}

	/**
	 * This method lists all the plans associated with the user's department
	 */
	@FXML
	public void openPlan() {
		final PlanFile selected = this.departmentPlanList.getSelectionModel().getSelectedItem();
		if (selected == null) return;
		try {
			this.app.getModel().getPlan(selected.getYear());
		} catch (final IllegalArgumentException e) {
			System.out.println("Invalid plan template");
			this.app.showPlanSelectionView();
			return;
		} catch (final RemoteException e) {
			this.app.showConnectToServer();
			return;
		}

		if (selected.isCanEdit()) {
			this.app.showPlanEditView();
			return;
		} else {
			this.app.showPlanReadOnlyView();
			return;
		}
	}

	/**
	 * This method lists all the plan templates.
	 */
	@FXML
	public void openPlanTemplate() {
		final PlanFile selected = this.planTemplateList.getSelectionModel().getSelectedItem();
		if (selected == null) return;
		try {
			this.app.getModel().getPlanOutline(selected.getYear());
		} catch (final IllegalArgumentException e) {
			System.out.println("Invalid plan template");
			this.app.showPlanSelectionView();
			return;
		} catch (final RemoteException e) {
			this.app.showConnectToServer();
			return;
		}
		this.app.showPlanEditView();
		return;
	}

	/**
	 * Allows controller to access showPlanEditView, showPlanReadOnlyView, and the
	 * showLoginView methods in the main application. Also populates the listviews
	 * with genTemplateList and genPlansList
	 *
	 * @param app main application
	 */
	@Override
	public void setApplication(Main app) {
		super.setApplication(app);
		genPlansList();
		genTemplateList();
	}

}
