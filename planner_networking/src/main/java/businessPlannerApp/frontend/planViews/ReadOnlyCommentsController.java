package businessPlannerApp.frontend.planViews;

import businessPlannerApp.backend.Comment;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * @author Wesley Murray
 */
public class ReadOnlyCommentsController extends EditCommentsController {

	/**
	 * This method called by the observer pattern to update the controller and view.
	 */
	@Override
	public void update() {
		setListView();
	}
	
	/**
	 * displays the comment in a window.
	 */
	@Override
	public void viewComment() {
		final Comment comment = this.commentList.getSelectionModel().getSelectedItem();
		if (comment == null) return;
		final Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText(comment.getContent());
		final ButtonType okButton = new ButtonType("OK");
		alert.getButtonTypes().setAll(okButton);
		alert.showAndWait();
	}

}
