package businessPlannerApp.frontend.planViews;

import javafx.fxml.FXML;

/**
 * @author Wesley Murray Outline for a generic comment controller
 */
public interface CommentController {

	/**
	 * This method updates the comments displayed based on the current section given
	 * section
	 */
	void changeSection();

	/**
	 * Method that allows the user to create their own comment.
	 */
	@FXML
	void createComment();

	/**
	 * This method hides the comments display
	 */
	@FXML
	void hideComments();

	/**
	 * This method creates a popup that displays a given comment. This popup also
	 * allows the user to resolve a comment. Once resolved a comment will be hidden.
	 */
	@FXML
	void viewComment();
}
