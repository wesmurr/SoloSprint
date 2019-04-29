package businessPlannerApp.frontend.planViews;

import java.rmi.RemoteException;
import java.util.Optional;

import businessPlannerApp.Main;
import businessPlannerApp.backend.PlanSection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;

public class EditController extends PlanController {

	boolean isPushed;

	/**
	 * Add a branch at the same level as current selected node to the business plan
	 */
	@FXML
	public void addSection() {
		try {
			changeSection();
			this.model.addBranch();
			setTreeView();
			this.isPushed = false;
		} catch (final RemoteException e) {
			this.app.sendError("Cannot connect to server");
		} catch (final IllegalArgumentException e) {
			this.app.sendError("Cannot add a section of this type");
		}

	}

	/**
	 * Change the view back to planSelectionView
	 */
	@Override
	@FXML
	public void backToPlans() {
		// need to ask users if they want to push

		changeSection();
		if (this.isPushed) super.backToPlans();
		else warningToSaveBackToPlans();
	}

	/**
	 * Change the nameField and dataField to the content stored in current node
	 *
	 * @param item
	 */
	@FXML
	public void changeSection() {
		final boolean isChanged = !(this.nameField.getText().equals(this.model.getCurrNode().getName())
				&& this.dataField.getText().equals(this.model.getCurrNode().getData())
				&& this.yearField.getText().equals(this.model.getCurrPlanFile().getYear()));

		final TreeItem<PlanSection> item = this.treeView.getSelectionModel().getSelectedItem();
		this.model.editName(this.nameField.getText());
		this.model.editData(this.dataField.getText());
		this.model.setCurrNode(item.getValue());
		this.nameField.setText(this.model.getCurrNode().getName());
		this.dataField.setText(this.model.getCurrNode().getData());
		this.treeView.refresh();

		if (isChanged) this.isPushed = false;

	}

	/**
	 * Delete the current selected node in the treeview. Shows popup asking user if
	 * they actually wish to delete.
	 */
	@FXML
	public void deleteSection() {
		final Alert alert = new Alert(AlertType.CONFIRMATION);
		final String message = "Are you sure you want to delete this section and all dependencies?"
				+ "They cannot be recovered.";
		alert.setContentText(message);
		final ButtonType okButton = new ButtonType("Delete");
		final ButtonType noButton = new ButtonType("Don't Delete");
		alert.getButtonTypes().setAll(okButton, noButton);
		final Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == okButton) try {
			changeSection();
			this.model.removeBranch();
			setTreeView();
			this.isPushed = false;
		} catch (final IllegalArgumentException e) {
			this.app.sendError("Cannot delete this section");
		}
		else if (result.get() == noButton) {}

	}

	/**
	 * @return the isPushed
	 */
	public boolean isPushed() { return this.isPushed; }

	/**
	 * Log out the current account on the server
	 */
	@Override
	@FXML
	public void logout() {
		// need to ask users if they want to push
		changeSection();
		if (this.isPushed) super.logout();
		else warningToSaveLogout();
	}

	/**
	 * Push the current business plan to the server
	 */
	@FXML
	public boolean push() {
		try {
			// set the year to which the user want
			// This allow the user to decide which year they want to edit
			// at editing time
			changeSection();
			this.model.getCurrPlanFile().setYear(this.yearField.getText());
			this.model.pushPlan(this.model.getCurrPlanFile());
			this.isPushed = true;
		} catch (final NumberFormatException e) {
			this.app.sendError("Invalid Year");
			return false;
		} catch (final IllegalArgumentException e) {
			this.app.sendError("Cannot save changes to this plan");
			return false;
		} catch (final RemoteException e) {
			this.app.sendError("Cannot connect to server");
			return false;
		}
		return true;
	}

	/**
	 * Initialize view
	 *
	 * @param this.app
	 */
	@Override
	public void setApplication(Main app) {
		super.setApplication(app);
		this.isPushed = true;
	}

	/**
	 * This method handles showing comments.
	 */
	@FXML
	public void showComments() {
		changeSection();
		this.app.showPlanViewExtension(this.selfPath.replace("View.fxml", "CommentsView.fxml"));
	}
	
	/**
	 * This method handles listing edit history
	 */
	@FXML
	public void showEditHistory() {
		changeSection();
		this.app.showPlanViewExtension(this.selfPath.replace("View.fxml", "HistoryView.fxml"));
	}

	/**
	 * Asks user if they want to save unsaved changes before leaving the plan edit
	 * view window for the back to plans button
	 *
	 * @return result of button press
	 */
	public void warningToSaveBackToPlans() {
		final Alert alert = new Alert(AlertType.CONFIRMATION);
		final String message = "You have unsaved changes. Do you wish to save before exiting?";
		alert.setContentText(message);
		final ButtonType okButton = new ButtonType("Yes");
		final ButtonType noButton = new ButtonType("No");
		final ButtonType cancelButton = new ButtonType("Cancel");
		alert.getButtonTypes().setAll(okButton, noButton, cancelButton);
		final Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == okButton) {
			if (push()) super.backToPlans();
		} else if (result.get() == noButton) super.backToPlans();
		else if (result.get() == cancelButton) {}
	}

	/**
	 * Asks user if they want to save unsaved changes before leaving the plan edit
	 * view window for the logout button
	 *
	 * @return result of button press
	 */
	public void warningToSaveLogout() {
		final Alert alert = new Alert(AlertType.CONFIRMATION);
		final String message = "You have unsaved changes. Do you wish to save before exiting?";
		alert.setContentText(message);
		final ButtonType okButton = new ButtonType("Yes");
		final ButtonType noButton = new ButtonType("No");
		final ButtonType cancelButton = new ButtonType("Cancel");
		alert.getButtonTypes().setAll(okButton, noButton, cancelButton);
		final Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == okButton) {
			if (push()) super.logout();
		} else if (result.get() == noButton) super.logout();
		else if (result.get() == cancelButton) {}
	}
}
