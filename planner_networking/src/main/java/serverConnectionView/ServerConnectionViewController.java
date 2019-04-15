package serverConnectionView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ServerConnectionViewController
{
	Main application;
	@FXML TextField ipAddress;
	@FXML TextField port;
	
	@FXML
	public void connect()
	{
		try
		{
			application.getModel().connectToServer(ipAddress.getText(), Integer.parseInt(port.getText()));
			application.showLoginView();
		}
			catch (NumberFormatException e)
		{
				application.sendError(e.toString());
		}
			catch (RemoteException e) {
			application.sendError(e.toString());
		} catch (NotBoundException e) {
			application.sendError(e.toString());
		}
	}
	
	public void setApplication(Main application)
	{
		this.application = application;
	}
	
	
}