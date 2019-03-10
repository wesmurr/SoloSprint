package software_masters.planner_networking;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
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
	private ConcurrentHashMap<String, Account> loginMap = new ConcurrentHashMap<String, Account>();
	private ConcurrentHashMap<String, Account> cookieMap = new ConcurrentHashMap<String, Account>();
	private ConcurrentHashMap<String, Department> departmentMap = new ConcurrentHashMap<String, Department>();
	private ConcurrentHashMap<String, PlanFile> planTemplateMap = new ConcurrentHashMap<String, PlanFile>();
	
	/**Initializes server with default objects listed above for testing
	 * 
	 */
	public Server() 
	{
		Department dpt = new Department();
		this.departmentMap.put("default", dpt);
		
		Account admin = new Account("admin","0", dpt, true);
		Account user = new Account("user","1", dpt, false);
		this.loginMap.put("admin", admin);
		this.loginMap.put("user", user);
		this.cookieMap.put("0", admin);
		this.cookieMap.put("1", user);

		Plan plan = new Centre();
		plan.setName("Centre_Plan_1");
		PlanFile planfile = new PlanFile("2019", true,plan);
		dpt.addPlan("2019", planfile);
		
		Plan defaultCentre = new Centre();
		Plan defaultVMOSA = new VMOSA();
		this.planTemplateMap.put("Centre", new PlanFile(null, true, defaultCentre));
		this.planTemplateMap.put("VMOSA", new PlanFile(null, true, defaultVMOSA));
		

	}
	
	/**
	 * 
	 * Returns cookie associated with a particular account when the client logs in. Throws exception if the username is invalid.
	 * @param username
	 * @param password
	 * @return
	 * @throws IllegalArgumentException
	 */
	public String logIn(String username, String password) throws IllegalArgumentException
	{
		if(!this.loginMap.contains(username))//checks username is valid
		{
			throw new IllegalArgumentException("Invalid username and/or password");

		}
		Account userAccount = this.loginMap.get(username);
		
		String cookie = userAccount.testCredentials(password);
		return cookie;
	}
	
	/**
	 * Returns planFile object from the user's department given a year. Throws exception if that planFile doesn't exist.
	 * @param year
	 * @param cookie
	 * @return
	 * @throws IllegalArgumentException
	 */
	public PlanFile getPlan(String year, String cookie) throws IllegalArgumentException
	{
		cookieChecker(cookie);//checks that cookie is valid
		
		Account userAccount = this.cookieMap.get(cookie);
		Department department = userAccount.getDepartment();
		if (!department.containsPlan(year))
		{
			throw new IllegalArgumentException("Plan doesn't exist within your department");

		}
		return department.getPlan(year);
	}
	
	/**
	 * Returns a blank plan outline given a name. Throws exception if the plan outline doesn't exist.
	 * @param name
	 * @param cookie
	 * @return
	 * @throws IllegalArgumentException
	 */
	public PlanFile getPlanOutline(String name, String cookie) throws IllegalArgumentException
	{
		cookieChecker(cookie);//checks that cookie is valid

		if (!this.planTemplateMap.contains(name))//checks plan template exists
		{
			throw new IllegalArgumentException("Plan outline doesn't exist");

		}
		return this.planTemplateMap.get(name);
		
	}
	
	/**
	 * Saves planFile to the user's department if that planFile is marked as editable. 
	 * If not editable, an exception is thrown. An exception is also thrown if a newly
	 * created planFile is not assigned a year.
	 * 
	 * @param plan
	 * @param cookie
	 * @throws IllegalArgumentException
	 */
	public void savePlan(PlanFile plan, String cookie) throws IllegalArgumentException
	{
		cookieChecker(cookie);//checks that cookie is valid
		
		if(plan.getYear() == null)//checks planFile is given a year
		{
			throw new IllegalArgumentException("This planFile needs a year!");
		}
		
		Account userAccount = this.cookieMap.get(cookie);
		Department dept = userAccount.getDepartment();
		
		if(dept.containsPlan(plan.getYear()))
		{
			PlanFile oldPlan = dept.getPlan(plan.getYear());
			if(!oldPlan.isCanEdit())//checks planFile is editable
			{
				throw new IllegalArgumentException("Not allowed to edit this plan");
			}
			
		}
		dept.addPlan(plan.getYear(), plan);

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
		cookieChecker(cookie);//checks that cookie is valid and that user is admin
		adminChecker(cookie);
		
		departmentChecker(departmentName);
		
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
	
	/**
	 * Sets whether or not a planFile is editable
	 * @param departmentName
	 * @param year
	 * @param canEdit
	 * @param cookie
	 * @throws IllegalArgumentException
	 */
	public void flagPlan(String departmentName, String year, boolean canEdit, String cookie) throws IllegalArgumentException
	{
		cookieChecker(cookie);//checks that cookie is valid and that user is admin
		adminChecker(cookie);
		departmentChecker(departmentName);
		
		Department dept = this.departmentMap.get(departmentName);
		if(!dept.containsPlan(year))
		{
			throw new IllegalArgumentException("Plan doesn't exist");

		}

		dept.getPlan(year).setCanEdit(canEdit);
	}
	
	/**
	 * Adds a new department
	 * @param departmentName
	 * @param cookie
	 * @throws IllegalArgumentException
	 */
	public void addDepartment(String departmentName, String cookie) throws IllegalArgumentException
	{
		cookieChecker(cookie);//checks that cookie is valid and that user is admin
		adminChecker(cookie);
		
		this.departmentMap.put(departmentName, new Department());

	}
	
	/**
	 * Allows developers to add a new plan outline
	 * @param name
	 * @param plan
	 */
	public void addPlanTemplate(String name,PlanFile plan)
	{
		this.planTemplateMap.put(name, plan);
	}
	
	/**
	 * Loads server from xml, called in main
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Server load() throws FileNotFoundException
	{
		String filepath = "PlannerServer.serv";
		XMLDecoder decoder = null;
		decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filepath)));
		Server server = (Server) decoder.readObject();
		decoder.close();
		return server;
}
	
	/**
	 * Serializes server to xml
	 * @throws FileNotFoundException
	 */
	public void save() 
	{
		String filename = "PlannerServer.serv";
		XMLEncoder encoder = null;
		try
		{
			encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
		} 
		catch (FileNotFoundException fileNotFound)
		{
			System.out.println("ERROR: While Creating or Opening the File " + filename);
		}
		encoder.writeObject(this);
		encoder.close();

	}
	
	/**
	 * Checks that the client is logged in with a valid cookie
	 * @param cookie
	 * @throws IllegalArgumentException
	 */
	private void cookieChecker(String cookie) throws IllegalArgumentException
	{
		if (!this.cookieMap.contains(cookie))
		{
			throw new IllegalArgumentException("Need to log in");

		}
		
	}
	
	/**
	 * Checks that the user is an admin
	 * @param cookie
	 * @throws IllegalArgumentException
	 */
	private void adminChecker(String cookie) throws IllegalArgumentException
	{
		if (!this.cookieMap.get(cookie).isAdmin())//Checks that user is admin
		{
			throw new IllegalArgumentException("You're not an admin");
		}
	}
	
	/**
	 * Checks department is valid
	 * @param name
	 * @throws IllegalArgumentException
	 */
	private void departmentChecker(String name) throws IllegalArgumentException
	{
		if (!this.departmentMap.contains(name))
		{
			throw new IllegalArgumentException("Deparment doesn't exist");
		}
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

	public static void main(String[] args) throws RemoteException
	{
		Server server;
		try {
			server = Server.load();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		Server stub = (Server)UnicastRemoteObject.exportObject(server, 0);
		Registry registry = LocateRegistry.getRegistry();
		try
		{
			registry.bind("PlannerServer", stub);
		} catch (java.rmi.AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
