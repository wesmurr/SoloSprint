/**
 * 
 */
package application;

import java.rmi.RemoteException;

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
			ServerImplementation server = new ServerImplementation();
			model = new PlannerModel(server);
			showLoginView();
		}
		catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}

}
