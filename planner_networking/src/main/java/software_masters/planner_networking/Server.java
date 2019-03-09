package software_masters.planner_networking;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.rmi.Remote;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lee Kendall
 * @author Wesley Murray
 * 
 * This class is the server for our business planner application
 *
 * initialized with two accounts - an Admin(Username: admin, password: admin, cookie: 0) and a normal user (Username: user, password: user, cookie: 1)
 * initialized with one department - (name: default)
 * The default department has a default plan file - (year: "2019", candEdit: true, Plan Centre_Plan_1)
 * planTemplateMap is initialized with VMOSA and Centre
 */

public class Server implements Remote, Serializable {

	private static final long serialVersionUID = 1L;
	private ConcurrentHashMap<String, Account> loginMap;
	private ConcurrentHashMap<String, Account> cookieMap;
	private ConcurrentHashMap<String, Department> departmentMap;
	private ConcurrentHashMap<String, PlanFile> planTemplateMap;
	
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
	
	/**
	 * Adds new user to loginMap, generates new cookie for user and adds to cookieMap. Throws exception if user isn't
	 * an admin or the department doesn't exist.
	 * 
	 * @param username
	 * @param password
	 * @param departmentName
	 * @param isAdmin
	 * @param cookie of the admin
	 * @throws IllegalArgumentException
	 */
	public void addUser(String username, String password, String departmentName, boolean isAdmin, String cookie) throws IllegalArgumentException
	{
		if (!this.cookieMap.get(cookie).isAdmin())
		{
			throw new IllegalArgumentException("You're not an admin");
		}
		
		if (!this.departmentMap.contains(departmentName))
		{
			throw new IllegalArgumentException("Deparment doesn't exist");
		}
		
		String newCookie = cookieMaker();
		Department newDept = this.departmentMap.get(departmentName);
		Account newAccount = new Account(password, newCookie, newDept, isAdmin);
		this.loginMap.put(username, newAccount);
		this.cookieMap.put(cookie, newAccount);
		
	}
	
	/**
	 * Helper method to randomly generate a 25-character cookie. This method regenerates a cookie if it already exists in the cookieMap. 
	 * @return String cookie
	 */
	private String cookieMaker()
	{
	    int leftLimit = 33; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 25;
	    Random random = new Random();
	    String generatedString;
	    StringBuilder buffer = new StringBuilder(targetStringLength);
	    for (int i = 0; i < targetStringLength; i++) {
	        int randomLimitedInt = leftLimit + (int) 
	          (random.nextFloat() * (rightLimit - leftLimit + 1));
	        buffer.append((char) randomLimitedInt);
	    }
	    generatedString = buffer.toString();
	    
	    while(this.cookieMap.containsKey(generatedString))
	    {
		    buffer = new StringBuilder(targetStringLength);
		    for (int i = 0; i < targetStringLength; i++) {
		        int randomLimitedInt = leftLimit + (int) 
		          (random.nextFloat() * (rightLimit - leftLimit + 1));
		        buffer.append((char) randomLimitedInt);
		    	}
	    generatedString = buffer.toString();
	    }
		return generatedString;
	}
	
	public void flagPlan(String departmentName, String year, boolean canEdit, String cookie) throws IllegalArgumentException
	{
	}
	
	public void addDepartment(String departmentName, String cookie) throws IllegalArgumentException
	{
	}
	
	public void addPlanTemplate(String name,PlanFile plan) throws IllegalArgumentException
	{
		
	}
	
	public static Server load() throws FileNotFoundException
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
	public ConcurrentHashMap<String, PlanFile> getPlanTemplateMap() {
		return planTemplateMap;
	}

	/**
	 * @param planTemplateMap the planTemplateMap to set
	 */
	public void setPlanTemplateMap(ConcurrentHashMap<String, PlanFile> planTemplateMap) {
		this.planTemplateMap = planTemplateMap;
	}

	public static void main(String[] args)
	{

	}
}
