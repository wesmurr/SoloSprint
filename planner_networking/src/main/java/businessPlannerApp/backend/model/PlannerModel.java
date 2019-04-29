package businessPlannerApp.backend.model;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import businessPlannerApp.backend.PlanEdit;
import businessPlannerApp.backend.PlanFile;
import businessPlannerApp.backend.PlanSection;
import businessPlannerApp.backend.RemoteObserver;
import businessPlannerApp.backend.Server;
import businessPlannerApp.frontend.planViews.PlanController;

/**
 * @author lee kendall and wesley murray
 */

public class PlannerModel implements RemoteObserver {

	/**
	 * This class represents the client which users interact with. It includes
	 * methods for retrieving and editing business plans, keeping track of the
	 * user's cookie after login.
	 */
	private String cookie;
	private PlanSection currNode;
	private PlanFile currPlanFile;
	private Server server;
	private PlanController controller;

	/**
	 * Default constructor.
	 */
	public PlannerModel() { this.server = null; }

	/**
	 * Sets the client's server.
	 *
	 * @param server
	 */
	public PlannerModel(Server server) { this.server = server; }

	/**
	 * Adds a new branch to the business plan tree if allowed
	 *
	 * @throws IllegalArgumentException
	 * @throws RemoteException
	 */
	public void addBranch() throws IllegalArgumentException, RemoteException {
		this.currPlanFile.getPlan().addNode(this.currNode.getParent());
	}

	/**
	 * Adds a new department
	 *
	 * @param departmentName
	 * @throws IllegalArgumentException
	 */
	public void addDepartment(String departmentName) throws IllegalArgumentException, RemoteException {
		this.server.addDepartment(departmentName, this.cookie);

	}

	/**
	 * Adds new user to loginMap, generates new cookie for user and adds to
	 * cookieMap. Throws exception if user isn't an admin or the department doesn't
	 * exist.
	 *
	 * @param username
	 * @param password
	 * @param departmentName
	 * @param isAdmin
	 * @throws IllegalArgumentException
	 */
	public void addUser(String username, String password, String departmentName, boolean isAdmin)
			throws IllegalArgumentException, RemoteException {
		this.server.addUser(username, password, departmentName, isAdmin, this.cookie);
	}

	/**
	 * Handle a navigating back to plans by resetting the attributes in client.
	 */
	public void backToPlans() {
		this.currNode = null;
		this.currPlanFile = null;
		releaseFile();
	}

	/**
	 * @param ip
	 * @param port
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public void connectToServer(String ip, int port) throws RemoteException, NotBoundException {
		String hostName = ip;
		Registry registry = LocateRegistry.getRegistry(hostName, port);
		Server stub = (Server) registry.lookup("PlannerServer");
		this.server = stub;
		
	}

	/**
	 * Sets the data held in the currently accessed node
	 *
	 * @param data
	 */
	public void editData(String data) { this.currNode.setData(data); }

	/**
	 * @param name to set node title to
	 */
	public void editName(String name) { this.currNode.setName(name); }

	/**
	 * Sets whether or not a planFile is editable
	 *
	 * @param departmentName
	 * @param year
	 * @param canEdit
	 * @throws IllegalArgumentException
	 */
	public void flagPlan(String departmentName, String year, boolean canEdit)
			throws IllegalArgumentException, RemoteException {
		this.server.flagPlan(departmentName, year, canEdit, this.cookie);

	}

	/**
	 * @return the cookie
	 */
	public String getCookie() { return this.cookie; }

	/**
	 * @return the currNode
	 */
	public PlanSection getCurrNode() { return this.currNode; }

	/**
	 * @return the currPlanFile
	 */
	public PlanFile getCurrPlanFile() { return this.currPlanFile; }

	/**
	 * @return the data associated with a node
	 */
	public String getData() { return this.currNode.getData(); }

	/**
	 * Returns planFile object from the user's department given a year. Throws
	 * exception if that planFile doesn't exist.
	 *
	 * @param year
	 * @return
	 * @throws IllegalArgumentException
	 */
	public void getPlan(String year) throws IllegalArgumentException, RemoteException {
		this.currPlanFile = this.server.getPlan(year, this.cookie);
		this.currNode = this.currPlanFile.getPlan().getRoot();
	}
	
	/**
	 * Gets a representation of the edit history associated with this plan.
	 * @return
	 * @throws IllegalArgumentException
	 * @throws RemoteException
	 */
	public Collection<PlanEdit> getEditHistory() throws IllegalArgumentException, RemoteException {
		return this.server.getEditHistory(this.currPlanFile.getYear(),this.cookie);
	}
	
	/**
	 * Returns a specific edit.
	 * @param timestamp
	 * @return
	 * @throws IllegalArgumentException
	 * @throws RemoteException
	 */
	public PlanFile getPlanEdit(Timestamp timestamp) throws IllegalArgumentException, RemoteException {
		return this.server.getPlanEdit(this.currPlanFile.getYear(),timestamp, this.cookie);
	}

	/**
	 * Returns a blank plan outline given a name. Throws exception if the plan
	 * outline doesn't exist.
	 *
	 * @param name
	 * @return
	 * @throws IllegalArgumentException
	 */
	public void getPlanOutline(String name) throws IllegalArgumentException, RemoteException {
		this.currPlanFile = this.server.getPlanOutline(name, this.cookie);
		this.currNode = this.currPlanFile.getPlan().getRoot();
	}

	/**
	 * @return the server
	 */
	public Server getServer() { return this.server; }

	/**
	 * Returns the username associated with this account.
	 *
	 * @return String username
	 * @throws RemoteException
	 * @throws IllegalArgumentException
	 */
	public String getUsername() throws IllegalArgumentException, RemoteException {
		return this.server.getUsername(this.cookie);
	}

	/**
	 * @return collection of planfiles associated with the client's department
	 */
	public Collection<PlanFile> listPlans() throws RemoteException { return this.server.listPlans(this.cookie); }

	/**
	 * @return collection of plan templates held by the server
	 */
	public Collection<PlanFile> listPlanTemplates() throws RemoteException {
		return this.server.listPlanTemplates();
	}

	/**
	 * Logs in, returns cookie
	 *
	 * @param username
	 * @param password
	 * @return
	 * @throws IllegalArgumentException
	 */
	public void login(String username, String password) throws IllegalArgumentException, RemoteException {
		this.currPlanFile = null;
		this.currNode = null;
		this.cookie = this.server.logIn(username, password);
	}

	/**
	 * Handle a logout by resetting the attributes in client.
	 */
	public void logout() {
		this.cookie = null;
		this.currNode = null;
		this.currPlanFile = null;
		releaseFile();
	}

	/**
	 * Saves planFile to the user's department if that planFile is marked as
	 * editable. If not editable, an exception is thrown. An exception is also
	 * thrown if a newly created planFile is not assigned a year.
	 *
	 * @param plan
	 * @throws IllegalArgumentException
	 */
	public void pushPlan(PlanFile plan) throws IllegalArgumentException, RemoteException {
		this.server.savePlan(plan, this.cookie);
	}

	private void releaseFile() {

	}

	/**
	 * Removes a branch from the business plan tree if at least one duplicate exists
	 *
	 * @throws IllegalArgumentException
	 */
	public void removeBranch() throws IllegalArgumentException {
		final PlanSection temp = this.currNode.getParent();
		this.currPlanFile.getPlan().removeNode(this.currNode);
		this.currNode = temp.getChildren().get(0);
	}

	/**
	 * @param cookie the cookie to set
	 */
	public void setCookie(String cookie) { this.cookie = cookie; }

	/**
	 * @param currNode the currNode to set
	 */
	public void setCurrNode(PlanSection currNode) { this.currNode = currNode; }

	/**
	 * @param currPlanFile the currPlanFile to set
	 */
	public void setCurrPlanFile(PlanFile currPlanFile) { this.currPlanFile = currPlanFile; }

	/**
	 * @param server the server to set
	 */
	public void setServer(Server server) { this.server = server; }

	/**
	 * @param year
	 */
	public void setYear(String year) { this.currPlanFile.setYear(year); }

	/**
	 * @return the controller
	 */
	public PlanController getController() { return controller; }

	/**
	 * @param controller the controller to set
	 */
	public void setController(PlanController controller) { this.controller = controller; }

	@Override
	public void updateEditHistory(){
		this.controller.update();
	}

}
