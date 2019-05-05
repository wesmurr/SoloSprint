package businessPlannerApp.frontend.ComparisonViews;

import java.rmi.RemoteException;

import businessPlannerApp.Main;
import javafx.fxml.FXML;

public class CompareEditsController extends CompareController {
	
	/**
	 * Connect controller to view.
	 */
	@Override
	public void setApplication(Main app) {
		super.setApplication(app);
		populateAltPlan();
	}

	/**
	 * Populates the comparison view with selected edit.
	 */
	@Override
	public void populateAltPlan() {
		this.setAltTreeView();
		this.compareTrees2();
		this.altTreeView.refresh();
		this.treeView.refresh();
		this.populateAltFields();
	}
	
	/**
	 * Replaces the active planfile with old version.
	 */
	@FXML
	public void restore() {
		this.model.setCurrPlanFile(this.model.getAltCurrPlanFile());
		this.model.setCurrNode(this.model.getAltCurrNode());
		try {
			this.model.pushPlan(this.model.getAltCurrPlanFile());
		} catch (IllegalArgumentException e) {
			this.app.sendError("Cannot edit this plan.");
		} catch (RemoteException e) {
			System.out.println("Unable to connect to server.");
			this.app.showConnectToServer();
		}
		super.setAltTreeView();
		super.setTreeView();
	}

}
