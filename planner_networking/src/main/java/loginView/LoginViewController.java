package loginView;

import java.rmi.RemoteException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import software_masters.model.PlannerModel;

public class LoginViewController
{
	Main application;
	@FXML TextField usernameField;
	@FXML TextField passWordField;
	
	
	@FXML
    void login(ActionEvent event) 
	{
		try {
			application.getModel().login(usernameField.getText(), passWordField.getText());
			application.showPlanSelectionView();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	void setApplication(Main application)
	{
		this.application = application;
	}
}	
	
	
