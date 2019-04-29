package businessPlannerApp.frontend.ComparisonViews;

import java.rmi.RemoteException;
import businessPlannerApp.Main;
import businessPlannerApp.backend.PlanFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class ComparePlansController extends CompareController {
	
	@FXML
	protected ComboBox<PlanFile> altSelectPlanMenu;
	
	@Override
	public void setApplication(Main app) {
		super.setApplication(app);
		updateSelectPlanMenu();
	}
	
	/**
	 * List possible plans to compare current plan with
	 */
	@FXML
	public void updateSelectPlanMenu() {
		ObservableList<PlanFile> items = null;
		try {
			items = FXCollections.observableArrayList(this.model.listPlans());
		} catch (final RemoteException e) {
			this.app.showConnectToServer();
		}
		this.altSelectPlanMenu.setItems(items);
	}
	
	/**
	 * Updates the fields for the alternate plan.
	 */
	@Override
	public void populateAltPlan() {
		String year=this.altSelectPlanMenu.getValue().getYear();
		if(year==null) {
			return;
		}
		try {
			this.model.getAltPlan(year);
		} catch (IllegalArgumentException e) {
			System.out.println("You fucked up the plan selection menu");
		} catch (RemoteException e) {
			System.out.println("Failed to connect to server");
			this.app.showConnectToServer();
		}
		setAltTreeView();
	}
	

}
