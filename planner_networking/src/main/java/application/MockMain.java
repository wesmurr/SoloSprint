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
 */
public class MockMain extends Main {

	@Override
	public void showConnectToServer()
	{
		try {
			ServerImplementation server = new ServerImplementation();
			model = new PlannerModel(server);
			showLoginView();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	
}
