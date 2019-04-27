/**
 *
 */
package software_masters.backend_test;

import static org.junit.Assert.assertEquals;

import java.rmi.RemoteException;

import org.junit.Test;

import businessPlannerApp.backend.Centre;
import businessPlannerApp.backend.Plan;
import businessPlannerApp.backend.PlanSection;

/**
 * @author Courtney and Jack
 */
public class CentreTest {

	// test invalid arguments
	@Test
	public void invalidArguments() throws RemoteException {
		// make plan and set pointer to root
		final Plan CentrePlan2 = new Centre();
		final PlanSection r = CentrePlan2.getRoot();
		// try to remove root
		try {
			CentrePlan2.removeNode(r);
		} catch (final IllegalArgumentException e) {
			e.getMessage();
		}
		// try to add a mission node
		try {
			CentrePlan2.addNode(r);
		} catch (final IllegalArgumentException e) {
			e.getMessage();
		}
	}

	@Test
	public void test() throws RemoteException {
		// make a new Centre plan
		final Plan CentrePlan = new Centre();

		// print out strings in the list
		for (int i = 0; i < CentrePlan.getList().size(); i++) System.out.println(CentrePlan.getList().get(i));

		// get root node
		final PlanSection rootNode = CentrePlan.getRoot();
		final PlanSection goal = rootNode.getChildren().get(0);
		final PlanSection learn = goal.getChildren().get(0);
		final PlanSection assess = learn.getChildren().get(0);
		final PlanSection res = assess.getChildren().get(0);

		assertEquals("Mission", rootNode.getName());
		assertEquals("Goal", goal.getName());
		assertEquals("Learning Objective", learn.getName());
		assertEquals("Assessment Process", assess.getName());
		assertEquals("Results", res.getName());

		// try to add mission again and check to see that it wasn't added
		assertEquals(false, rootNode.getChildren().isEmpty());
		// add goal, and following, nodes
		CentrePlan.addNode(rootNode);
		assertEquals(2, rootNode.getChildren().size());
		final PlanSection goalNode = rootNode.getChildren().get(0);
		final PlanSection learn2 = goalNode.getChildren().get(0);
		final PlanSection assess2 = learn2.getChildren().get(0);
		final PlanSection res2 = assess2.getChildren().get(0);

		assertEquals("Goal", goalNode.getName());
		assertEquals("Learning Objective", learn2.getName());
		assertEquals("Assessment Process", assess2.getName());
		assertEquals("Results", res2.getName());

		// remove a goal node
		final PlanSection rm = rootNode.getChildren().get(0);
		// check to see if removed
		CentrePlan.removeNode(rm);
		assertEquals(1, rootNode.getChildren().size());
		// set goal data and check it
		goalNode.setData("hello");
		assertEquals("hello", goalNode.getData());

	}

}
