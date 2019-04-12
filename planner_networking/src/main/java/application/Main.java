/**
 * 
 */
package application;

import serverConnectionView.*;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
	BorderPane mainView;
	public static void main(String[] args) 
	{
		launch(args);
	}

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 * Initializes server connection view
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("serverConnectionView/serverConnectionView.fxml"));
		mainView = loader.load();
		
		ServerConnectionViewController cont = loader.getController();
		cont.setApplication(this);
		
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
		loader.setLocation(Main.class.getResource("loginView/loginView.fxml"));
		
		try {
			mainView.setCenter(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
		LoginViewController cont = loader.getController();
		cont.setApplication(this); // Allows controller to access showPlanSelectionView
		
		
	}
	
	/**Shows the plan selection view
	 * 
	 */
	public void showPlanSelectionView()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("planSelectionView/planSelectionView.fxml"));
		
		try {
			mainView.setCenter(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
		PlanSelectionViewController cont = loader.getController();
		cont.setApplication(this); // Allows controller to access showPlanEditView and showPlanReadOnlyView
	}
	
	/**
	 * Shows the plan edit view
	 */
	public void showPlanEditView()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("planEditView/planEditView.fxml"));
		
		try {
			mainView.setCenter(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
		PlanEditViewController cont = loader.getController();
		cont.setApplication(this); // Allows controller to access showPlanSelectionView and showLoginView
	}
	
	/**
	 * Shows the plan read-only view
	 */
	public void showPlanReadOnlyView()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("planReadOnlyView/planReadOnlyView.fxml"));
		
		try {
			mainView.setCenter(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
		PlanReadOnlyViewController cont = loader.getController();
		cont.setApplication(this); // Allows controller to access showPlanSelectionView and showLoginView
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
}
