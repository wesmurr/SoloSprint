package software_masters.backend_test;

import static org.junit.Assert.assertEquals;

import java.rmi.RemoteException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import businessPlannerApp.backend.IowaState;
import businessPlannerApp.backend.Plan;
import businessPlannerApp.backend.PlanFile;
import businessPlannerApp.backend.Server;
import businessPlannerApp.backend.ServerImplementation;

/**
 * @author Wesley Murray
 * @author Lee Kendall This class verifies that server is working properly.
 *         initialized with two accounts - an Admin(Username: admin, password:
 *         admin, cookie: 0) and a normal user (Username: user, password: user,
 *         cookie: 1) initialized with one department - (name: default) The
 *         default department has a default plan file - (year: "2019", candEdit:
 *         true, Plan Centre_Plan_1) planTemplateMap is initialized with VMOSA
 *         and Centre
 */
public class ServerTest {

	static Server testServer;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception { testServer = new ServerImplementation(); }

	@AfterClass
	public static void tearDownAfterClass() throws Exception {}

	/**
	 * This method tests that a new type of business plan can be added to server by
	 * a developer
	 *
	 * @throws RemoteException
	 */
	@Test
	public void testAddPlanTemplate() throws RemoteException {
		final Plan Iowa_State = new IowaState();
		final PlanFile Iowa_test = new PlanFile(null, true, Iowa_State);
		testServer.addPlanTemplate("IowaState", Iowa_test);
		final PlanFile other = testServer.getPlanOutline("IowaState", "1");
		assertEquals(Iowa_test, other);
	}

	/**
	 * Tests that the server can be saved to xml and reloaded correctly
	 *
	 * @throws Exception
	 */
	@Test
	public void testSerialization() throws Exception {
		testServer.save();
		final Server temp = ServerImplementation.load();
		final Server temp2=testServer;
		assertEquals(testServer, temp);

	}

}
