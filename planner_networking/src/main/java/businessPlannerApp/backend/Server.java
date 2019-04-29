package businessPlannerApp.backend;

import java.io.FileNotFoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lee.kendall
 * @author wesley murray
 */

public interface Server extends RemoteSubject {

	/**
	 * Adds a new department
	 *
	 * @param departmentName
	 * @param cookie
	 * @throws IllegalArgumentException
	 */
	void addDepartment(String departmentName, String cookie) throws IllegalArgumentException, RemoteException;

	/**
	 * Allows developers to add a new plan outline
	 *
	 * @param name
	 * @param plan
	 */
	void addPlanTemplate(String name, PlanFile plan) throws RemoteException;

	/**
	 * Adds new user to loginMap, generates new cookie for user and adds to
	 * cookieMap. Throws exception if user isn't an admin or the department doesn't
	 * exist.
	 *
	 * @param username
	 * @param password
	 * @param departmentName
	 * @param isAdmin
	 * @param cookie         of the admin
	 * @throws IllegalArgumentException
	 */
	void addUser(String username, String password, String departmentName, boolean isAdmin, String cookie)
			throws IllegalArgumentException, RemoteException;

	/**
	 * Sets whether or not a planFile is editable
	 *
	 * @param departmentName
	 * @param year
	 * @param canEdit
	 * @param cookie
	 * @throws IllegalArgumentException
	 */
	void flagPlan(String departmentName, String year, boolean canEdit, String cookie)
			throws IllegalArgumentException, RemoteException;

	/**
	 * @return the cookieMap
	 */
	ConcurrentHashMap<String, Account> getCookieMap() throws RemoteException;

	/**
	 * @return the departmentMap
	 */
	ConcurrentHashMap<String, Department> getDepartmentMap() throws RemoteException;

	/**
	 * @return the loginMap
	 */
	ConcurrentHashMap<String, Account> getLoginMap() throws RemoteException;

	/**
	 * Returns planFile object from the user's department given a year. Throws
	 * exception if that planFile doesn't exist.
	 *
	 * @param year
	 * @param cookie
	 * @return
	 * @throws IllegalArgumentException
	 */
	PlanFile getPlan(String year, String cookie) throws IllegalArgumentException, RemoteException;
	
	/**
	 * This method returns a representation of the edit history for a given plan
	 * @param year
	 * @param cookie
	 * @return
	 * @throws IllegalArgumentException
	 * @throws RemoteException
	 */
	Collection<PlanEdit> getEditHistory(String year,String cookie) throws IllegalArgumentException, RemoteException;
	
	/**
	 * This method returns a particular plan edit.
	 * @param year
	 * @param time
	 * @param cookie
	 * @return
	 * @throws IllegalArgumentException
	 * @throws RemoteException
	 */
	PlanFile getPlanEdit(String year,Timestamp time,String cookie) throws IllegalArgumentException, RemoteException;

	/**
	 * Returns a blank plan outline given a name. Throws exception if the plan
	 * outline doesn't exist.
	 *
	 * @param name
	 * @param cookie
	 * @return
	 * @throws IllegalArgumentException
	 */
	PlanFile getPlanOutline(String name, String cookie) throws IllegalArgumentException, RemoteException;

	/**
	 * @return the planTemplateMap
	 */
	ConcurrentHashMap<String, PlanFile> getPlanTemplateMap() throws RemoteException;

	/**
	 * Gets the username associated with account.
	 *
	 * @param cookie
	 * @return String username
	 * @throws IllegalArgumentException
	 * @throws RemoteException
	 */
	String getUsername(String cookie) throws IllegalArgumentException, RemoteException;

	/**
	 * @param cookie of user
	 * @return @return collection of planfiles associated with the client's
	 *         department
	 */
	Collection<PlanFile> listPlans(String cookie) throws RemoteException;

	/**
	 * @return collection of plan templates held by the server
	 */
	Collection<PlanFile> listPlanTemplates() throws RemoteException;

	/**
	 * Returns cookie associated with a particular account when the client logs in.
	 * Throws exception if the username is invalid.
	 *
	 * @param username
	 * @param password
	 * @return
	 * @throws IllegalArgumentException
	 */
	String logIn(String username, String password) throws IllegalArgumentException, RemoteException;

	/**
	 * Serializes server to xml
	 *
	 * @throws FileNotFoundException
	 */
	void save() throws RemoteException;

	/**
	 * Saves planFile to the user's department if that planFile is marked as
	 * editable. If not editable, an exception is thrown. An exception is also
	 * thrown if a newly created planFile is not assigned a year.
	 *
	 * @param plan
	 * @param cookie
	 * @throws IllegalArgumentException
	 */
	void savePlan(PlanFile plan, String cookie) throws IllegalArgumentException, RemoteException;

	/**
	 * @param cookieMap the cookieMap to set
	 */
	void setCookieMap(ConcurrentHashMap<String, Account> cookieMap) throws RemoteException;

	/**
	 * @param departmentMap the departmentMap to set
	 */
	void setDepartmentMap(ConcurrentHashMap<String, Department> departmentMap) throws RemoteException;

	/**
	 * @param loginMap the loginMap to set
	 */
	void setLoginMap(ConcurrentHashMap<String, Account> loginMap) throws RemoteException;

	/**
	 * @param planTemplateMap the planTemplateMap to set
	 */
	void setPlanTemplateMap(ConcurrentHashMap<String, PlanFile> planTemplateMap) throws RemoteException;
}
