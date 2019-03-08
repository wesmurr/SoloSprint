/**
 * 
 */
package software_masters.planner_networking;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.rmi.Remote;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lee.kendalll
 *
 */

public class Server implements Remote, Serializable {

	/**
	 * 
	 */
	private ConcurrentHashMap<String, Account> loginMap;
	private ConcurrentHashMap<String, Account> cookieMap;
	private ConcurrentHashMap<String, Department> departmentMap;
	private ConcurrentHashMap<String, Plan> planTemplateMap;
	
	public Server() //Needs to create admin account Admin, admin cookie 0
	{
	}
	
	public String logIn(String username, String password) throws IllegalArgumentException
	{
		return null;
	}
	
	public PlanFile getPlan(String year, String cookie) throws IllegalArgumentException
	{
		return null;
	}
	
	public PlanFile getPlanOutline(String name, String cookie) throws IllegalArgumentException
	{
		return null;
	}
	
	public void saveFile(PlanFile plan, String cookie) throws IllegalArgumentException
	{
		
	}
	
	public void addUser(String username, String password, String departmentName, boolean isAdmin, String cookie) throws IllegalArgumentException
	{
	}
	
	public void flagPlan(String departmentName, String year, boolean canEdit, String cookie) throws IllegalArgumentException
	{
	}
	
	public void addDepartment(String departmentName, String cookie) throws IllegalArgumentException
	{
	}
	
	public void addPlanTemplate(String name,Plan plan) throws IllegalArgumentException
	{
		
	}
	
	public Server load() throws FileNotFoundException
	{
		return null;
	}
	
	public void save()
	{
		
	}
	
	/**
	 * @return the loginMap
	 */
	public ConcurrentHashMap<String, Account> getLoginMap() {
		return loginMap;
	}

	/**
	 * @param loginMap the loginMap to set
	 */
	public void setLoginMap(ConcurrentHashMap<String, Account> loginMap) {
		this.loginMap = loginMap;
	}

	/**
	 * @return the cookieMap
	 */
	public ConcurrentHashMap<String, Account> getCookieMap() {
		return cookieMap;
	}

	/**
	 * @param cookieMap the cookieMap to set
	 */
	public void setCookieMap(ConcurrentHashMap<String, Account> cookieMap) {
		this.cookieMap = cookieMap;
	}

	/**
	 * @return the departmentMap
	 */
	public ConcurrentHashMap<String, Department> getDepartmentMap() {
		return departmentMap;
	}

	/**
	 * @param departmentMap the departmentMap to set
	 */
	public void setDepartmentMap(ConcurrentHashMap<String, Department> departmentMap) {
		this.departmentMap = departmentMap;
	}

	/**
	 * @return the planTemplateMap
	 */
	public ConcurrentHashMap<String, Plan> getPlanTemplateMap() {
		return planTemplateMap;
	}

	/**
	 * @param planTemplateMap the planTemplateMap to set
	 */
	public void setPlanTemplateMap(ConcurrentHashMap<String, Plan> planTemplateMap) {
		this.planTemplateMap = planTemplateMap;
	}

	public static void main(String[] args)
	{

	}
}
