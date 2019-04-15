package application;

import serverConnectionView.*;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loginView.LoginViewController;
import planEditView.PlanEditViewController;
import planReadOnlyView.PlanReadOnlyViewController;
import planSelectionView.PlanSelectionViewController;
import software_masters.model.PlannerModel;

/**
 * @author lee.kendall
 *
 */
public class Main extends Application {

	/**
	 * Initializes the server connection window and includes methods for changing the window to display a new view
	 * @param args
	 */
	
	PlannerModel model;
	Stage primaryStage;
	Parent mainView;
	public static void main(String[] args) 
	{
		launch(args);
	}

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 * Initializes server connection view
	 */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.model=new PlannerModel();
		
		this.showConnectToServer();
	}
	

	/**
	 * Shows the connect to server window
	 */
	public void showConnectToServer()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../serverConnectionView/serverConnectionView.fxml"));
		
		try {
			mainView = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ServerConnectionViewController cont = loader.getController();
		cont.setApplication(this); // Allows controller to access showPlanSelectionView
		
		Scene s = new Scene(mainView);
		primaryStage.setScene(s);
		primaryStage.show();
	}
	
	/**
	 * Shows the login view window
	 */
	public void showLoginView()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../loginView/loginView.fxml"));
		
		try {
			mainView = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LoginViewController cont = loader.getController();
		cont.setApplication(this); // Allows controller to access showPlanSelectionView
		
		Scene s = new Scene(mainView);
		primaryStage.setScene(s);
		primaryStage.show();
	}
	
	/**Shows the plan selection view
	 * 
	 */
	public void showPlanSelectionView()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../planSelectionView/planSelectionView.fxml"));
		
		try {
			mainView = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		PlanSelectionViewController cont = loader.getController();
		cont.setApplication(this); // Allows controller to access showPlanEditView and showPlanReadOnlyView
		
		Scene s = new Scene(mainView);
		primaryStage.setScene(s);
		primaryStage.show();		
		
	}
	
	/**
	 * Shows the plan edit view
	 */
	public void showPlanEditView()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../planEditView/planEditView.fxml"));
		
		try {
			mainView = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		PlanEditViewController cont = loader.getController();
		cont.setApplication(this); // Allows controller to access showPlanSelectionView and showLoginView
		
		Scene s = new Scene(mainView);
		primaryStage.setScene(s);
		primaryStage.show();
	}
	
	/**
	 * Shows the plan read-only view
	 */
	public void showPlanReadOnlyView()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../planReadOnlyView/planReadOnlyView.fxml"));
		
		try {
			mainView = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		PlanReadOnlyViewController cont = loader.getController();
		cont.setApplication(this); // Allows controller to access showPlanSelectionView and showLoginView
		
		Scene s = new Scene(mainView);
		primaryStage.setScene(s);
		primaryStage.show();
	}

	/**
	 * @return the model
	 */
	public PlannerModel getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(PlannerModel model) {
		this.model = model;
	}
	
	/**
	 * This method helps to pop up error message happens when controller is operating the model
	 * For example, delete a node that is not allowed to be deleted
	 * @param message error message from wrong operation on the model
	 */
	public void sendError(String message) 
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Warning Dialog");
		alert.setHeaderText(message);
		alert.setContentText(null);
		alert.showAndWait();
	}
	

}
