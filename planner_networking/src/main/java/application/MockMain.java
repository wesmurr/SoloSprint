/**
 * 
 */
package application;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ExportException;

import software_masters.model.PlannerModel;
import software_masters.planner_networking.ServerImplementation;

/**
 * @author lee.kendall
 * 
 * Mock of the Main class for testing purposes. This class only overrides the showConnectToServer method,
 * which passes a server object to the model instead of connecting to a running server. It also jumps directly
 * to the login window, skipping the server connection window.
 */
public class MockMain extends Main
{

	/* (non-Javadoc)
	 * @see application.Main#showConnectToServer()
	 * Creates a server object and passes it to the model, in imitation  of connecting to a server.
	 */
	@Override
	public void showConnectToServer()
	{
		try
		{
			ServerImplementation.main(null);
			model.connectToServer("127.0.0.1", 1060);
			showLoginView();
		}
		catch (ExportException e)
		{
			try
			{
				model.connectToServer("127.0.0.1", 1060);
			}
			catch (RemoteException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (NotBoundException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			showLoginView();
		}
		catch (RemoteException | NotBoundException e)
		{
			e.printStackTrace();
		}
	}

}
