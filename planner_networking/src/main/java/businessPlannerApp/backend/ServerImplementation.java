package businessPlannerApp.backend;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lee Kendall
 * @author Wesley Murray
 */

public class ServerImplementation implements Server {
	private ConcurrentHashMap<String, Account> cookieMap = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, Department> departmentMap = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, Account> loginMap = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, PlanFile> planTemplateMap = new ConcurrentHashMap<>();
	
	/**
	 * Initializes server with default objects for testing purposes.
	 */
	public ServerImplementation() throws RemoteException {
		//create default department
		Department dpt = new Department();
		this.departmentMap.put("default", dpt);
		//create default accounts
		final Account admin = new Account("admin", "admin", "0", dpt, true);
		final Account user = new Account("user", "user", "1", dpt, false);
		this.loginMap.put("admin", admin);
		this.loginMap.put("user", user);
		this.cookieMap.put("0", admin);
		this.cookieMap.put("1", user);
		//create default plans and add comments
		Plan plan = new Centre();
		plan.setName("Centre_Plan_1");
		plan.getRoot().addComment(new Comment("Testing  default comment display", "user"));
		plan.getRoot().addComment(new Comment("mission comment", "user"));
		plan.getRoot().getChildren().get(0).addComment(new Comment("goal comment", "user"));
		plan.getRoot().addComment(new Comment("Testing  default comment display", "admin"));
		Comment temp = new Comment("no show", "user");
		temp.setResolved(true);
		plan.getRoot().addComment(temp);
		
		Plan plan2 = new VMOSA();
		plan2.setName("VMOSA_Plan_1");
		plan2.getRoot().setData("My Vision is to...");
		plan2.getRoot().getChildren().get(0).setData("My Mission is to...");
		plan2.getRoot().addComment(new Comment("Testing  default comment display", "user"));
		plan2.getRoot().addComment(new Comment("vision comment", "user"));
		plan2.getRoot().getChildren().get(0).addComment(new Comment("mission comment", "user"));
		plan2.getRoot().addComment(new Comment("Testing  default comment display", "admin"));
		plan2.getRoot().addComment(temp);
		
		//encapsulate plans into edit objects
		PlanFile planfile = new PlanFile("2019", true, plan);
		PlanEdit planEdit=new PlanEdit("user",new Timestamp(1000),planfile);
		PlanHistory planHistory=new PlanHistory(planfile.getYear(),planfile.isCanEdit());
		planHistory.addPlanEdit(planEdit);
		
		PlanFile planfile2 = new PlanFile("2020", false, plan2);
		PlanEdit planEdit2=new PlanEdit("user",new Timestamp(1000),planfile2);
		PlanHistory planHistory2=new PlanHistory(planfile2.getYear(),planfile2.isCanEdit());
		planHistory2.addPlanEdit(planEdit2);
		
		//add history object to department
		dpt.addPlanHistory(planHistory.getYear(), planHistory);
		dpt.addPlanHistory(planHistory2.getYear(), planHistory2);

		Plan defaultCentre = new Centre();
		Plan defaultVMOSA = new VMOSA();
		this.planTemplateMap.put("Centre", new PlanFile("", true, defaultCentre));
		this.planTemplateMap.put("VMOSA", new PlanFile("", true, defaultVMOSA));

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * software_masters.planner_networking.Server#addDepartment(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void addDepartment(String departmentName, String cookie) {
		cookieChecker(cookie);// checks that cookie is valid and that user is admin
		adminChecker(cookie);

		this.departmentMap.put(departmentName, new Department());

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * software_masters.planner_networking.Server#addPlanTemplate(java.lang.String,
	 * software_masters.planner_networking.PlanFile)
	 */
	@Override
	public void addPlanTemplate(String name, PlanFile plan) { this.planTemplateMap.put(name, plan); }

	/*
	 * (non-Javadoc)
	 *
	 * @see software_masters.planner_networking.Server#addUser(java.lang.String,
	 * java.lang.String, java.lang.String, boolean, java.lang.String)
	 */
	@Override
	public void addUser(String username, String password, String departmentName, boolean isAdmin, String cookie) {
		cookieChecker(cookie);// checks that cookie is valid and that user is admin
		adminChecker(cookie);

		departmentChecker(departmentName);

		final String newCookie = cookieMaker();
		final Department newDept = this.departmentMap.get(departmentName);
		final Account newAccount = new Account(username, password, newCookie, newDept, isAdmin);
		this.loginMap.put(username, newAccount);
		this.cookieMap.put(newAccount.getCookie(), newAccount);

	}

	/**
	 * Checks that the user is an admin
	 *
	 * @param cookie
	 * @throws IllegalArgumentException
	 */
	private void adminChecker(String cookie) {
		if (!this.cookieMap.get(cookie).isAdmin()) throw new IllegalArgumentException("You're not an admin");
	}

	/**
	 * Checks that the client is logged in with a valid cookie
	 *
	 * @param cookie
	 * @throws IllegalArgumentException
	 */
	private void cookieChecker(String cookie) {
		if (!this.cookieMap.containsKey(cookie)) throw new IllegalArgumentException("Need to log in");

	}

	/**
	 * Helper method to randomly generate a 25-character cookie. This method
	 * regenerates a cookie if it already exists in the cookieMap.
	 *
	 * @return String cookie
	 */
	private String cookieMaker() {
		final int leftLimit = 33; // letter 'a'
		final int rightLimit = 122; // letter 'z'
		final int targetStringLength = 25;
		final Random random = new Random();
		String generatedString;
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			final int randomLimitedInt = leftLimit + (int) (random.nextFloat() * ((rightLimit - leftLimit) + 1));
			buffer.append((char) randomLimitedInt);
		}
		generatedString = buffer.toString();

		while (this.cookieMap.containsKey(generatedString)) {
			buffer = new StringBuilder(targetStringLength);
			for (int i = 0; i < targetStringLength; i++) {
				final int randomLimitedInt = leftLimit + (int) (random.nextFloat() * ((rightLimit - leftLimit) + 1));
				buffer.append((char) randomLimitedInt);
			}
			generatedString = buffer.toString();
		}
		return generatedString;
	}

	/**
	 * Checks department is valid
	 *
	 * @param name
	 * @throws IllegalArgumentException
	 */
	private void departmentChecker(String name) {
		if (!this.departmentMap.containsKey(name)) throw new IllegalArgumentException("Deparment doesn't exist");
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see software_masters.planner_networking.Server#flagPlan(java.lang.String,
	 * java.lang.String, boolean, java.lang.String)
	 */
	@Override
	public void flagPlan(String departmentName, String year, boolean canEdit, String cookie) {
		cookieChecker(cookie);// checks that cookie is valid and that user is admin
		adminChecker(cookie);
		departmentChecker(departmentName);

		final Department dept = this.departmentMap.get(departmentName);
		if (!dept.containsPlan(year)) throw new IllegalArgumentException("Plan doesn't exist");

		dept.getPlanHistory(year).setCanEdit(canEdit);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see software_masters.planner_networking.Server#getCookieMap()
	 */
	@Override
	public ConcurrentHashMap<String, Account> getCookieMap() { return this.cookieMap; }

	/*
	 * (non-Javadoc)
	 *
	 * @see software_masters.planner_networking.Server#getDepartmentMap()
	 */
	@Override
	public ConcurrentHashMap<String, Department> getDepartmentMap() { return this.departmentMap; }

	/*
	 * (non-Javadoc)
	 *
	 * @see software_masters.planner_networking.Server#getLoginMap()
	 */
	@Override
	public ConcurrentHashMap<String, Account> getLoginMap() { return this.loginMap; }

	/*
	 * (non-Javadoc)
	 *
	 * @see software_masters.planner_networking.Server#getPlan(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public PlanFile getPlan(String year, String cookie) {
		cookieChecker(cookie);// checks that cookie is valid

		Account userAccount = this.cookieMap.get(cookie);
		Department department = userAccount.getDepartment();
		if (!department.containsPlan(year))
			throw new IllegalArgumentException("Plan doesn't exist within your department");
		PlanFile temp=department.getPlanHistory(year).getCurrentPlanEdit().getPlanFile();
		//ensure planfile attributes are up to date
		temp.setCanEdit(department.getPlanHistory(year).isCanEdit());
		temp.setYear(department.getPlanHistory(year).getYear());
		return temp;
	}
	
	@Override
	public Collection<PlanEdit> getEditHistory(String year, String cookie) { 
		cookieChecker(cookie);// checks that cookie is valid
		this.cookieMap.get(cookie).getDepartment().getPlanHistory(year);
		
		Collection<PlanEdit> collections = this.cookieMap.get(cookie).getDepartment().getPlanHistory(year).getEdits();
		LinkedList<PlanEdit> list = new LinkedList<>();
		PlanEdit temp;
		for (Iterator<PlanEdit> iter = collections.iterator(); iter.hasNext();) {
			temp = iter.next();
			list.add(new PlanEdit(temp.getUsername(), temp.getTimestamp(),null));
		}
		collections = list;
		return collections;
	}

	@Override
	public PlanFile getPlanEdit(String year,Timestamp time, String cookie) { 
		cookieChecker(cookie);// checks that cookie is valid
		PlanHistory hist=this.cookieMap.get(cookie).getDepartment().getPlanHistory(year);
		PlanFile file=hist.getEdit(time).getPlanFile();
		file.setCanEdit(hist.isCanEdit());
		file.setYear(hist.getYear());
		return file;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * software_masters.planner_networking.Server#getPlanOutline(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public PlanFile getPlanOutline(String name, String cookie) {
		cookieChecker(cookie);// checks that cookie is valid

		if (!this.planTemplateMap.containsKey(name)) throw new IllegalArgumentException("Plan outline doesn't exist");
		return this.planTemplateMap.get(name);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see software_masters.planner_networking.Server#getPlanTemplateMap()
	 */
	@Override
	public ConcurrentHashMap<String, PlanFile> getPlanTemplateMap() { return this.planTemplateMap; }

	@Override
	public String getUsername(String cookie) {
		cookieChecker(cookie);
		return this.cookieMap.get(cookie).getUsername();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see software_masters.planner_networking.Server#listPlans(java.lang.String)
	 */
	@Override
	public Collection<PlanFile> listPlans(String cookie) {
		cookieChecker(cookie);// checks that cookie is valid

		final Account userAccount = this.cookieMap.get(cookie);
		final Department department = userAccount.getDepartment();

		Collection<PlanHistory> collections = department.getPlanHistoryMap().values();
		final LinkedList<PlanFile> list = new LinkedList<>();
		PlanHistory temp;
		for (final Iterator<PlanHistory> iter = collections.iterator(); iter.hasNext();) {
			temp = iter.next();
			list.add(new PlanFile(temp.getYear(), temp.isCanEdit(), null));
		}
		return (Collection<PlanFile>) list;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see software_masters.planner_networking.Server#listPlanTemplates()
	 */
	@Override
	public Collection<PlanFile> listPlanTemplates() {
		Collection<PlanFile> collections = this.planTemplateMap.values();
		final LinkedList<PlanFile> list = new LinkedList<>();
		final Iterator<PlanFile> iter = collections.iterator();
		PlanFile temp;
		String name;
		for (final Enumeration<String> e = this.planTemplateMap.keys(); e.hasMoreElements();) {
			name = e.nextElement();
			temp = iter.next();
			list.add(new PlanFile(name, temp.isCanEdit(), null));
		}
		collections = list;
		return collections;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see software_masters.planner_networking.Server#logIn(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String logIn(String username, String password) {
		if (!this.loginMap.containsKey(username))
			throw new IllegalArgumentException("Invalid username and/or password");
		final Account userAccount = this.loginMap.get(username);

		final String cookie = userAccount.testCredentials(password);
		return cookie;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see software_masters.planner_networking.Server#savePlan(software_masters.
	 * planner_networking.PlanFile, java.lang.String)
	 */
	@Override
	public void savePlan(PlanFile plan, String cookie) {
		Integer.parseInt(plan.getYear());
		cookieChecker(cookie);// checks that cookie is valid

		if (plan.getYear() == null) throw new IllegalArgumentException("This planFile needs a year!");

		Account userAccount = this.cookieMap.get(cookie);
		Department dept = userAccount.getDepartment();

		Timestamp time=new Timestamp((new Date()).getTime());
		PlanEdit newEdit=new PlanEdit(this.cookieMap.get(cookie).getUsername(),time,plan);
		
		if (dept.containsPlan(plan.getYear())) {
			PlanHistory oldPlan = dept.getPlanHistory(plan.getYear());
			if (!oldPlan.isCanEdit()) throw new IllegalArgumentException("Not allowed to edit this plan");
			dept.getPlanHistory(plan.getYear()).addPlanEdit(newEdit);
			return;
		}
		
		PlanHistory newHistory=new PlanHistory(plan.getYear(),plan.isCanEdit());
		newHistory.addPlanEdit(newEdit);
		dept.addPlanHistory(newHistory.getYear(), newHistory);
		save();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * software_masters.planner_networking.Server#setCookieMap(java.util.concurrent.
	 * ConcurrentHashMap)
	 */
	@Override
	public void setCookieMap(ConcurrentHashMap<String, Account> cookieMap) { this.cookieMap = cookieMap; }

	/*
	 * (non-Javadoc)
	 *
	 * @see software_masters.planner_networking.Server#setDepartmentMap(java.util.
	 * concurrent.ConcurrentHashMap)
	 */
	@Override
	public void setDepartmentMap(ConcurrentHashMap<String, Department> departmentMap) {
		this.departmentMap = departmentMap;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * software_masters.planner_networking.Server#setLoginMap(java.util.concurrent.
	 * ConcurrentHashMap)
	 */
	@Override
	public void setLoginMap(ConcurrentHashMap<String, Account> loginMap) { this.loginMap = loginMap; }

	/*
	 * (non-Javadoc)
	 *
	 * @see software_masters.planner_networking.Server#setPlanTemplateMap(java.util.
	 * concurrent.ConcurrentHashMap)
	 */
	@Override
	public void setPlanTemplateMap(ConcurrentHashMap<String, PlanFile> planTemplateMap) {
		this.planTemplateMap = planTemplateMap;
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see software_masters.planner_networking.Server#save()
	 */
	@Override
	public void save() {
		final String filename = "PlannerServer.serv";
		XMLEncoder encoder = null;
		try {
			encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
		} catch (final FileNotFoundException fileNotFound) {
			System.out.println("ERROR: While Creating or Opening the File " + filename);
		}
		encoder.writeObject(this);
		encoder.close();

	}
	
////////////////////////////////////////////////Spawning Server with Singleton Pattern//////////////////////////////////////////////////////////	
	private static Server server = null;
	
	/**
	 * Helper static method that enforces a modified singleton pattern for program.
	 * Constructor is not private because XML serialization.
	 */
	public static void spawn() {
		if (server == null) {
			System.out.println("Loading Server from Memory");
			Registry registry = null;
			Server stub = null;
			try {
				server = new ServerImplementation();
				server.save();
				server = ServerImplementation.load();
				registry = LocateRegistry.createRegistry(1060);
				stub = (Server) UnicastRemoteObject.exportObject(server, 0);
				registry.bind("PlannerServer", stub);
			} catch (final RemoteException e) {
				System.out.println("Unable to create and bind to server using rmi.");
				System.exit(1);
			} catch (final AlreadyBoundException e) {
				System.out.println("Connecting to Existing Server");
			} catch (final FileNotFoundException e) {
				System.out.print("Cannot find file to load server from.");
				System.exit(1);
			}
			return;
		}
		System.out.println("Connecting to Existing Server");
	}

	/**
	 * Helper static method that allows us to use singleton pattern for testing.
	 * Ensures server being tested is an object with known values.
	 */
	public static void testSpawn() {
		if (server == null) {
			System.out.println("Starting Test Server");
			Registry registry = null;
			Server stub = null;
			try {
				server = new ServerImplementation();
				registry = LocateRegistry.createRegistry(1060);
				stub = (Server) UnicastRemoteObject.exportObject(server, 0);
				registry.bind("PlannerServer", stub);
			} catch (final RemoteException e) {
				System.out.println("Unable to create and bind to server using rmi.\n"
						+ "Likely a different server is running on this port");
				System.exit(0);
			} catch (final AlreadyBoundException e) {
				System.out.println("Connecting to Existing Server");
			}
			return;
		}
		System.out.println("Connecting to Existing Test Server");
	}
	
	/**
	 * Loads server from xml, called in main
	 *
	 * @return
	 * @throws FileNotFoundException
	 */
	public static ServerImplementation load() throws FileNotFoundException {
		final String filepath = "PlannerServer.serv";
		XMLDecoder decoder = null;
		decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filepath)));
		final ServerImplementation server = (ServerImplementation) decoder.readObject();
		decoder.close();
		return server;
	}
	
	/**
	 * Starts the server, allows clients to access it
	 *
	 * @param args
	 * @throws RemoteException
	 */
	public static void main(String[] args) throws RemoteException { ServerImplementation.spawn(); }

	
/////////////////////////////////////////////////Override Equality Methods for testing/////////////////////////////////////////////////////////////////////
	/**
	 * Compares two hashes for testing
	 *
	 * @param map1
	 * @param map2
	 * @return
	 */
	private static <K, V> boolean hashesEqual(ConcurrentHashMap<K, V> map1, ConcurrentHashMap<K, V> map2) {
		for (final Enumeration<K> keyList = map1.keys(); keyList.hasMoreElements();) {
			final K key = keyList.nextElement();
			if (!map1.containsKey(key)) return false;
			if (!map2.containsKey(key)) return false;
			if (!map1.get(key).equals(map2.get(key))) return false;
		}
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see software_masters.planner_networking.Server#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (this.getClass() != obj.getClass()) return false;
		final ServerImplementation other = (ServerImplementation) obj;
		if (this.cookieMap == null) {
			if (other.cookieMap != null) return false;
		} else if (!ServerImplementation.<String, Account>hashesEqual(this.cookieMap, other.cookieMap)) return false;
		if (this.departmentMap == null) {
			if (other.departmentMap != null) return false;
		} else if (!ServerImplementation.<String, Department>hashesEqual(this.departmentMap, other.departmentMap))
			return false;
		if (this.loginMap == null) {
			if (other.loginMap != null) return false;
		} else if (!ServerImplementation.<String, Account>hashesEqual(this.loginMap, other.loginMap)) return false;
		if (this.planTemplateMap == null) {
			if (other.planTemplateMap != null) return false;
		} else if (!ServerImplementation.<String, PlanFile>hashesEqual(this.planTemplateMap, other.planTemplateMap))
			return false;
		return true;
	}

}
