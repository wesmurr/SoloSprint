/**
 * 
 */
package software_masters.planner_networking;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lee.kendall
 *
 */

public class Server {

	/**
	 * 
	 */
	private ConcurrentHashMap<String, Account> loginMap;
	private ConcurrentHashMap<String, Account> cookieMap;
	private ConcurrentHashMap<String, Department> departmentMap;
	
	public Server() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public String logIn(String username, String password)
	{
		return null;
	}
	
	public PlanFile getPlan(String year, String cookie)
	{
		return null;
	}
	
	public PlanFile getPlanOutline(String name, String cookie)
	{
		return null;
	}
	
	public boolean pushFile(PlanFile plan, String cookie)
	{
		return false;
	}
	
	public boolean addUser(String username, String password, String departmentName, boolean isAdmin, String cookie)
	{
		return false;
	}
	
	public boolean flagPlan(String departmentName, String year, String cookie)
	{
		return false;
	}
	
	public boolean addDepartment(String departmentName, String cookie)
	{
		return false;
	}


}
