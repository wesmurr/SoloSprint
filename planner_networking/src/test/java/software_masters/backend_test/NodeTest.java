package software_masters.backend_test;

import static org.junit.Assert.assertEquals;

import java.rmi.RemoteException;

import org.junit.Test;

import businessPlannerApp.backend.PlanSection;

public class NodeTest {

	@Test
	public void test() throws RemoteException {
		// make node +
		// add child +
		// remove child
		// get name, data +
		// set name, data +
		// get child when no child

		// make tree node and test data methods
		final PlanSection tree = new PlanSection(null, "TreeNode", null, null);
		assertEquals("TreeNode", tree.getName());
		tree.setName("Tree");
		assertEquals("Tree", tree.getName());

		tree.setData("Desciption of mission goals.");
		assertEquals("Desciption of mission goals.", tree.getData());
		tree.setData("Description");
		assertEquals("Description", tree.getData());
		assertEquals(null, tree.getParent());
		assertEquals(true, tree.getChildren().isEmpty());

		// make child nodes for tree, test addChild and getParent
		final PlanSection n1 = new PlanSection(tree, "Vision", null, null);
		tree.addChild(n1);
		assertEquals(tree, n1.getParent());
		assertEquals("Vision", n1.getName());
		assertEquals(true, tree.getChildren().contains(n1));

		final PlanSection n2 = new PlanSection(tree, "node", null, null);
		tree.addChild(n2);
		assertEquals(true, tree.getChildren().contains(n2));
		assertEquals(tree, n2.getParent());

		// add child to n2
		final PlanSection n3 = new PlanSection(n2, "node3", null, null);
		n2.addChild(n3);
		assertEquals(true, n2.getChildren().contains(n3));
		assertEquals(n2, n3.getParent());

		// add child to n3
		final PlanSection n4 = new PlanSection(n3, "node3", null, null);
		n3.addChild(n4);

		// get grandparent
		assertEquals(n2, n4.getParent().getParent());

		// remove child from tree with no other children
		tree.removeChild(n1);
		assertEquals(false, tree.getChildren().contains(n1));

		// remove child that has children
		tree.removeChild(n2);
		assertEquals(false, tree.getChildren().contains(n2));

	}

}
