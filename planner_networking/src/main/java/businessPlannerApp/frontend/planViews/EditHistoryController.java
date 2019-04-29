package businessPlannerApp.frontend.planViews;

import java.rmi.RemoteException;
import java.util.List;

import businessPlannerApp.Main;
import businessPlannerApp.backend.PlanEdit;
import businessPlannerApp.backend.PlanFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class EditHistoryController extends EditController implements HistoryController {

	@FXML
	ListView<PlanEdit> editList;
	
	/**
	 * Let controller to know view
	 * @param app
	 */
	@Override
	public void setApplication(Main app) {
		super.setApplication(app);
		setListView();
	}

	/**
	 * update list view
	 */
	private void setListView() {
		ObservableList<PlanEdit> items = null;
		try {
			items = FXCollections.observableList((List<PlanEdit>) this.model.getEditHistory());
		} catch (IllegalArgumentException e) {
			System.out.println("You messed up the code for getting edit history");
		} catch (RemoteException e) {
			System.out.println("unable to connect to server");
			app.showConnectToServer();
		}
		this.editList.setItems(items);
	}
	
	@Override
	public void changeSection() {
		super.changeSection();
		this.editList.refresh();
	}
	
	@Override
	public void viewEdit() {
		PlanEdit selected=this.editList.getSelectionModel().getSelectedItem();
		PlanFile edit=null;
		try {
			edit=this.model.getPlanEdit(selected.getTimestamp());
		} catch (IllegalArgumentException e) {
			System.out.println("You messed up your management of the time stamps");
		} catch (RemoteException e) {
			app.showConnectToServer();
		}
		System.out.println("view for edits incomplete.");
	}

	@Override
	public void update() {
		setListView();
		this.editList.refresh();
	}

	@Override
	public void hideEdits() {
		super.changeSection();
		this.app.showPlanView();
	}
	
	

}
