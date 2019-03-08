package software_masters.planner_networking;


import static org.junit.jupiter.api.Assertions.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Lee Kendall
 * @author Wes Murray
 *
 *Verifies that client methods work correctly
 */
public class ClientTest {
	
	static Server testServer;
	static Client testClient;

	/**
	 * @throws Exception
	 * Sets up RMI registry, ensures that a server is pulled from the registry, and sets up a client. The server and client are used for subsequent tests.
	 */ 
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		{
			Registry registry;
			try
			{
				registry = LocateRegistry.createRegistry(1099);
				Server server = new Server();
				Server stub = (Server)UnicastRemoteObject.exportObject(server, 0);
				registry.rebind("server",stub);
				testServer = (Server)registry.lookup("server");
				testClient = new Client(testServer);
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	

	/**Verifies that the login method works by returning a valid cookie from a valid login.
	 * The server is initialized with two accounts - an Admin(Username admin, password admin, cookie 0) and a normal user (Username user, password user, cookie 1)
	 * The server is also initialized with one department - default
	 * The default department has a default plan file - (year: "2019", candEdit: true, Plan null)
	 */
	@Test
	public void testLogin() {
		
		//Checks invalid cases
		assertThrows(IllegalArgumentException.class, () -> testClient.login("invalidUsername", "invalidPassword"));
		assertThrows(IllegalArgumentException.class, () -> testClient.login("user", "invalidPassword"));
		assertThrows(IllegalArgumentException.class, () -> testClient.login("invalidUsername", "user"));
		
		//Checks valid logins
		assertEquals("1", testClient.login("user", "user"));
		assertEquals("1",testClient.getCookie());
		assertEquals("0", testClient.login("admin", "admin"));
	}
		

	/**Verifies addUser method works and that only admins can call it
	 * 
	 */
	@Test
	public void testAddUser() {
		
		//tests non-admin addUser
		testClient.login("user", "user");
		assertThrows(IllegalArgumentException.class, () -> testClient.addUser("newUser", "newUser", "default", false));
		
		//tests admin can add new account. 
		testClient.login("admin", "admin");
		testClient.addUser("newUser", "newUser", "default", false);
		
		//test if account was actually created and correct department was added
		ConcurrentHashMap<String, Account> loginMap = testServer.getLoginMap();
		assertTrue(loginMap.containsKey("newUser"));
		ConcurrentHashMap<String, Department> departmentMap = testServer.getDepartmentMap();
		assertEquals(departmentMap.get("default"),loginMap.get("newUser").getDepartment());

		
		//Verifies an exception is thrown when admins attempt to add a user with a non-existent department
		testClient.login("admin", "admin");
		assertThrows(IllegalArgumentException.class, () -> testClient.addUser("anotherUser", "anotherUser", "invalidDepartment", false));

	}
	
	/**
	 * Verifies addDepartment method works and that only admins can call it
	 */
	@Test
	public void testAddDepartment() {
		//tests non-admin addDepartment
		testClient.login("user", "user");
		assertThrows(IllegalArgumentException.class, () -> testClient.addDepartment("newDepartment"));
		
		//tests admin can add new department. 
		testClient.login("admin", "admin");
		testClient.addDepartment("newDepartment");
		
		//verifies the department object was created and added to hash
		ConcurrentHashMap<String, Department> departmentMap = testServer.getDepartmentMap();
		assertTrue(departmentMap.containsKey("newDepartment"));

	}
	
	@Test
	public void testFlagPlan() {
		//tests non-admin flagFile
		testClient.login("user", "user");
		assertThrows(IllegalArgumentException.class, () -> testClient.flagPlan("default","2019",false));
		
		//tests admin can flag file. 
		testClient.login("admin", "admin");
		testClient.flagPlan("default","2019",true);
		ConcurrentHashMap<String, Department> departmentMap = testServer.getDepartmentMap();
		PlanFile file=departmentMap.get("default").getPlan("2019");
		assertTrue(file.isCanEdit());
		
		//tests exception is thrown if try to flag invalid file. 
		assertThrows(IllegalArgumentException.class, () -> testClient.flagPlan("default","2000",true));

	}
	
	@Test
	public void testGetPlan() {
		//plan does not exist throws exception 
		testClient.login("user", "user");
		assertThrows(IllegalArgumentException.class, () -> testClient.getPlan("2000"));
				
		//otherwise ok
		ConcurrentHashMap<String, Department> departmentMap = testServer.getDepartmentMap();
		
		
		
	}

	@Test
	public void testGetPlanOutline() {
		fail("Not yet implemented");
	}

	@Test
	public void testPushFile() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddBranch() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveBranch() {
		fail("Not yet implemented");
	}

	@Test
	public void testEditData() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetData() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetYear() {
		fail("Not yet implemented");
	}

}
