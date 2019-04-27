package businessPlannerApp;

import java.io.IOException;
import java.util.Optional;

import businessPlannerApp.backend.model.PlannerModel;
import businessPlannerApp.frontend.ViewController;
import businessPlannerApp.frontend.loginView.LoginViewController;
import businessPlannerApp.frontend.planSelectionView.PlanSelectionViewController;
import businessPlannerApp.frontend.planViews.EditController;
import businessPlannerApp.frontend.serverConnectionView.ServerConnectionViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author lee.kendall
 */
public class Main extends Application {

	public static void main(String[] args) { launch(args); }

	Parent mainView;
	/**
	 * Initializes the server connection window and includes methods for changing
	 * the window to display a new view
	 *
	 * @param args
	 */

	PlannerModel model;

	Stage primaryStage;

	/**
	 * Handles the exit without saving popup
	 *
	 * @param cont plan edit view controller
	 */
	private void closeWindow(EditController cont) {
		final Alert alert = new Alert(AlertType.CONFIRMATION);
		final String message = "You have unsaved changes. Do you wish to save before exiting?";
		alert.setContentText(message);
		final ButtonType okButton = new ButtonType("Yes");
		final ButtonType noButton = new ButtonType("No");
		final ButtonType cancelButton = new ButtonType("Cancel");
		alert.getButtonTypes().setAll(okButton, noButton, cancelButton);
		final Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == okButton) {
			if (cont.push()) this.primaryStage.close();
		} else if (result.get() == noButton) this.primaryStage.close();

	}

	/**
	 * @return the model
	 */
	public PlannerModel getModel() { return this.model; }

	/**
	 * Generalizes the process for loading a controller.
	 *
	 * @param <T>
	 * @param cont
	 * @param filepath
	 */
	private <T extends ViewController> void loadController(T cont, String filepath) {
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(filepath));

		try {
			this.mainView = loader.load();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		cont = loader.getController();
		cont.setApplication(this);
	}

	/**
	 * This method helps to pop up error message happens when controller is
	 * operating the model For example, delete a node that is not allowed to be
	 * deleted
	 *
	 * @param message error message from wrong operation on the model
	 */
	public void sendError(String message) {
		final Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Warning Dialog");
		alert.setHeaderText(message);
		alert.setContentText(null);
		// alert.getButtonTypes().get(0).

		alert.showAndWait();
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(PlannerModel model) { this.model = model; }

	/**
	 * Helper method for adding stage to window.
	 */
	private void setupDisplay() {
		final Scene s = new Scene(this.mainView);
		this.primaryStage.setScene(s);
		this.primaryStage.show();
		this.primaryStage.sizeToScene();
	}

	/**
	 * Shows the connect to server window
	 */
	public void showConnectToServer() {
		final ServerConnectionViewController cont = null;
		this.loadController(cont, "frontend/serverConnectionView/serverConnectionView.fxml");
		this.primaryStage.setOnCloseRequest((WindowEvent e) -> { this.primaryStage.close(); });
		this.setupDisplay();
	}

	/**
	 * Shows the login view window
	 */
	public void showLoginView() {
		final LoginViewController cont = null;
		this.loadController(cont, "frontend/loginView/loginView.fxml");
		this.primaryStage.setOnCloseRequest((WindowEvent e) -> { this.primaryStage.close(); });
		this.setupDisplay();
	}

	/**
	 * Shows the plan edit view
	 */
	public void showPlanEditView() { this.showPlanView("frontend/planViews/EditView.fxml"); }

	/**
	 * Shows the plan read-only view
	 */
	public void showPlanReadOnlyView() { this.showPlanView("frontend/planViews/ReadOnlyView.fxml"); }

	/**
	 * Shows the plan selection view
	 */
	public void showPlanSelectionView() {
		final PlanSelectionViewController cont = null;
		this.loadController(cont, "frontend/planSelectionView/planSelectionView.fxml");
		this.primaryStage.setOnCloseRequest((WindowEvent e) -> { this.primaryStage.close(); });
		this.setupDisplay();
	}

	/**
	 * Show plan edit view based provided path.
	 *
	 * @param filepath
	 */
	public <T extends EditController> void showPlanView(String filepath) {
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(filepath));

		try {
			this.mainView = loader.load();
		} catch (final IOException e) {
			System.out.println("failed to load view: " + filepath);
			return;
		}
		final T cont = loader.getController();
		cont.setSelfPath(filepath);
		cont.setCommentsPath(filepath.replace("View.fxml", "CommentsView.fxml"));
		cont.setApplication(this); // Allows controller to access showPlanSelectionView and showLoginView

		this.primaryStage.setOnCloseRequest((WindowEvent e) -> {
			e.consume();
			cont.changeSection();
			if (!cont.isPushed()) this.closeWindow(cont);
			else this.primaryStage.close();
		});

		this.setupDisplay();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javafx.application.Application#start(javafx.stage.Stage) Initializes
	 * server connection view
	 */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.model = new PlannerModel();

		this.showConnectToServer();
	}

}
