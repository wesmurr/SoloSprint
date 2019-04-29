package businessPlannerApp.backend;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteObserver extends Remote {
	/**
	 * Method for updating the state of the observer.
	 * @param updatemsg
	 * @throws RemoteException
	 */
	void update() throws RemoteException;
}
