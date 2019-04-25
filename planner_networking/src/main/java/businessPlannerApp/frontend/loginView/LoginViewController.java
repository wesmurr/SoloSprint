package businessPlannerApp.frontend.loginView;

import java.rmi.RemoteException;

import businessPlannerApp.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginViewController
{
	Main application;
	@FXML
	TextField usernameField;
	@FXML
	TextField passWordField;

	@FXML
	void login(ActionEvent event)
	{
		try
		{
			application.getModel().login(usernameField.getText(), passWordField.getText());
			application.showPlanSelectionView();
		}
		catch (IllegalArgumentException e)
		{
			application.sendError("invalid credentials");
		}
		catch (RemoteException e)
		{
			application.sendError("cannot connect to server");
		}
	}

	public void setApplication(Main application)
	{
		this.application = application;
	}
}
