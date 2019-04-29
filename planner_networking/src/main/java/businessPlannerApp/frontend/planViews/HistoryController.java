package businessPlannerApp.frontend.planViews;

import javafx.fxml.FXML;

public interface HistoryController {
	/**
	 * This method updates the comments displayed based on the current section given
	 * section
	 */
	void changeSection();

	/**
	 * This method creates a popup that displays a given comment. This popup also
	 * allows the user to resolve a comment. Once resolved a comment will be hidden.
	 */
	@FXML
	void viewEdit();
	
	/**
	 * This method allows the user to hide the list of edits.
	 */
	@FXML
	void hideEdits();
	
	/**
	 * This method is for observer pattern when it is implemented.
	 */
	void update();
}
