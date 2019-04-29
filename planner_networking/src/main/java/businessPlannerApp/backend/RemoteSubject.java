package businessPlannerApp.backend;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteSubject extends Remote {
	/**
	 * Add on observer for the subject to update.
	 * @param obs
	 * @throws RemoteException
	 */
	void addObserver(RemoteObserver obs) throws RemoteException;
	
	/**
	 * Removes an observer from subject.
	 * @param obs
	 * @throws RemoteException
	 */
	void deleteObserver(RemoteObserver obs) throws RemoteException;
}
