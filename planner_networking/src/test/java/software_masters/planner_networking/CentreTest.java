/**
 * 
 */
package software_masters.planner_networking;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Courtney and Jack
 *
 */
public class CentreTest
{
	
	@Test
	public void test()
	{
		try{
		// make a new Centre plan
		Plan CentrePlan = new Centre();
		
		//print out strings in the list
		for (int i = 0; i<CentrePlan.getList().size(); i++)
		{
			System.out.println(CentrePlan.getList().get(i));
		}
		
		//get root node
		Node rootNode = CentrePlan.getRoot();
		Node goal = rootNode.children.get(0);
		Node learn = goal.children.get(0);
		Node assess = learn.children.get(0);
		Node res = assess.children.get(0);
		
		assertEquals("Mission", rootNode.getName());
		assertEquals("Goal", goal.getName());
		assertEquals("Learning Objective", learn.getName());
		assertEquals("Assessment Process", assess.getName());
		assertEquals("Results", res.getName());
		
		
		// try to add mission again and check to see that it wasn't added
		assertEquals(false, CentrePlan.addNode(null));
		assertEquals(false, rootNode.children.isEmpty());
		//add goal, and following, nodes		
		//check to see if added
		assertEquals(true, CentrePlan.addNode(rootNode));
		assertEquals(2, rootNode.children.size());
		Node goalNode = rootNode.children.get(0);
		Node learn2 = goalNode.children.get(0);
		Node assess2 = learn2.children.get(0);
		Node res2 = assess2.children.get(0);
		
		assertEquals("Goal", goalNode.getName());
		assertEquals("Learning Objective", learn2.getName());
		assertEquals("Assessment Process", assess2.getName());
		assertEquals("Results", res2.getName());
		//try to remove root
		assertEquals(false, CentrePlan.removeNode(rootNode));
		
		//remove a goal node
		Node rm = rootNode.children.get(0);
		//check to see if removed
		assertEquals(true, CentrePlan.removeNode(rm));
		assertEquals(1, rootNode.children.size());
		//set goal data and check it
		goalNode.setData("hello");
		assertEquals("hello", goalNode.getData());
	
		// try to remove a learning objective
		Node rm2 = goalNode.children.get(0);
		
		assertEquals(false, CentrePlan.removeNode(rm2));
		

		}catch(IllegalArgumentException e)
		
		{
		  e.getMessage();
		}

		
	}
	//test invalid arguments
	@Test
	public void invalidArguments() 
	{
		//make plan and set pointer to root
		Plan CentrePlan2 = new IowaState();
		Node r = CentrePlan2.getRoot();
		//try to remove root
		try 
		{
			CentrePlan2.removeNode(r);
		    fail( "My method didn't throw when I expected it to" );
		} 
		catch (IllegalArgumentException e)
		{
			  e.getMessage();
		}
		//try to add a mission node
		try 
		{
			
			CentrePlan2.addNode(r);
		    fail( "My method didn't throw when I expected it to" );
		} 
		catch (IllegalArgumentException e)
		{
			  e.getMessage();
		}
	}
	
	public void main(String[] args)
	{
		test();
		invalidArguments();
	}

}
