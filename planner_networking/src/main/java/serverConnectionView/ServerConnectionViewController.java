package serverConnectionView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import application.Main;
import javafx.scene.control.TextField;

public class ServerConnectionViewController
{
	Main application;
	TextField ipAddress;
	TextField port;
	
	void connect()
	{
		try
		{
			application.getModel().connectToServer(ipAddress.getText(), Integer.parseInt(port.getText()));
			application.showLoginView();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	public void setApplication(Main application)
	{
		this.application = application;
	}
}