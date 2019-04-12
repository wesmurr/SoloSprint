package loginView;



import java.rmi.RemoteException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import software_masters.model.PlannerModel;

public class LoginViewController
{
	PlannerModel model;
	@FXML TextField usernameField;
	@FXML TextField passWordField;
	public LoginViewController(PlannerModel model) 
	{
		this.model = model;
	}
	
	@FXML
    void login(ActionEvent event) 
	{
		try {
			model.login(usernameField.getText(), passWordField.getText());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	
	
}