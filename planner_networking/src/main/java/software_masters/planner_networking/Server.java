/**
 * 
 */
package software_masters.planner_networking;

import java.io.FileNotFoundException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lee.kendalll
 *
 */

public class Server {

	/**
	 * 
	 */
	private ConcurrentHashMap<String, Account> loginMap;
	private ConcurrentHashMap<String, Account> cookieMap;
	private ConcurrentHashMap<String, Department> departmentMap;
	private ConcurrentHashMap<String, Plan> planTemplateMap;
	
	public Server() 
	{
		// TODO Auto-generated constructor stub
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
	
	public static void main(String[] args)
	{

	}
}
