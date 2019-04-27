/**
 *
 */
package software_masters.backend_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.rmi.RemoteException;

import org.junit.Test;

import businessPlannerApp.backend.IowaState;
import businessPlannerApp.backend.Plan;
import businessPlannerApp.backend.PlanSection;

/**
 * @author Courtney and Jack
 */
public class IowaStateTest {
	// test invalid arguments
	@Test
	public void invalidArguments() throws RemoteException {
		// make plan and set pointer to root
		final Plan IowaStatePlan2 = new IowaState();
		final PlanSection r = IowaStatePlan2.getRoot();
		// try to remove root
		try {
			IowaStatePlan2.removeNode(r);
			fail("My method didn't throw when I expected it to");
		} catch (final IllegalArgumentException e) {
			e.getMessage();
		}
		// try to add a vision node
		try {

			IowaStatePlan2.addNode(r);
			fail("My method didn't throw when I expected it to");
		} catch (final IllegalArgumentException e) {
			e.getMessage();
		}

	}

	@Test
	public void test() throws RemoteException {
		// make a new VMOSA plan
		final Plan IowaStatePlan = new IowaState();

		// print out strings in the list
		for (int i = 0; i < IowaStatePlan.getList().size(); i++) System.out.println(IowaStatePlan.getList().get(i));

		// get root node
		final PlanSection rootNode = IowaStatePlan.getRoot();
		final PlanSection m = rootNode.getChildren().get(0);
		final PlanSection cv = m.getChildren().get(0);
		final PlanSection stra = cv.getChildren().get(0);
		final PlanSection goal = stra.getChildren().get(0);
		final PlanSection obj = goal.getChildren().get(0);
		final PlanSection act = obj.getChildren().get(0);
		final PlanSection assess = act.getChildren().get(0);

		assertEquals("Vision", rootNode.getName());
		assertEquals("Mission", m.getName());
		assertEquals("Core Value", cv.getName());
		assertEquals("Strategy", stra.getName());
		assertEquals("Goal", goal.getName());
		assertEquals("Objective", obj.getName());
		assertEquals("Action Plan", act.getName());
		assertEquals("Assessment", assess.getName());

		// try to add vision again and check to see that it wasn't added
		assertEquals(false, rootNode.getChildren().isEmpty());
		// add objective, and following, nodes
		// check if added
		final PlanSection missionNode = rootNode.getChildren().get(0);

		IowaStatePlan.addNode(missionNode);
		assertEquals(2, missionNode.getChildren().size());
		final PlanSection cv2 = missionNode.getChildren().get(0);
		final PlanSection stra2 = cv2.getChildren().get(0);
		final PlanSection goal2 = stra2.getChildren().get(0);
		final PlanSection obj2 = goal2.getChildren().get(0);
		final PlanSection act2 = obj2.getChildren().get(0);
		final PlanSection assess2 = act2.getChildren().get(0);

		assertEquals("Core Value", cv2.getName());
		assertEquals("Strategy", stra2.getName());
		assertEquals("Goal", goal2.getName());
		assertEquals("Objective", obj2.getName());
		assertEquals("Action Plan", act2.getName());
		assertEquals("Assessment", assess2.getName());

		// remove mission node and check if removed
		final PlanSection rm = missionNode.getChildren().get(0);
		IowaStatePlan.removeNode(rm);
		assertEquals(1, missionNode.getChildren().size());

		// set mission data and check
		missionNode.setData("hello");
		assertEquals("hello", missionNode.getData());

	}

}
