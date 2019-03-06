/**
 * 
 */
package software_masters.planner_networking;

import static org.junit.Assert.*;
//import java.util.ArrayList;

import org.junit.Test;


/**
 * @author Courtney and Jack
 *
 */
public class VMOSATest
{

	@Test
	public void test()
	{
		try{
		// make a new VMOSA plan
		VMOSA VMOSAPlan = new VMOSA();
		
		//print out strings in the list
		for (int i = 0; i<VMOSAPlan.getList().size(); i++)
		{
			System.out.println(VMOSAPlan.getList().get(i));
		}
		
		
		//get root node
		Node rootNode = VMOSAPlan.getRoot();
		Node missionNode = rootNode.children.get(0);
		Node objNode = missionNode.children.get(0);
		Node stratNode = objNode.children.get(0);
		Node ActNode = stratNode.children.get(0);
		Node assessNode = ActNode.children.get(0);
		
		//see that all nodes were added after root
		assertEquals("Vision", rootNode.getName());
		assertEquals("Mission", missionNode.getName());
		assertEquals("Objective", objNode.getName());
		assertEquals("Strategy", stratNode.getName());
		assertEquals("Action Plan", ActNode.getName());
		assertEquals("Assessment", assessNode.getName());
		
		// try to add vision again and check to see that it wasn't added
		assertEquals(false, VMOSAPlan.addNode(rootNode));
		assertEquals(false, rootNode.children.isEmpty());

		//add objective, and following, nodes
		// check to see if added
		
		assertEquals(true, VMOSAPlan.addNode(missionNode));
		assertEquals(2, missionNode.children.size());
		Node obj2 = missionNode.children.get(1);
		Node strat2 = obj2.children.get(0);
		Node act2 = strat2.children.get(0);
		Node assess2 = act2.children.get(0);
		
		assertEquals("Objective", obj2.getName());
		assertEquals("Strategy", strat2.getName());
		assertEquals("Action Plan", act2.getName());
		assertEquals("Assessment", assess2.getName());
		
		//try to remove root node and mission node
		assertEquals(false, VMOSAPlan.removeNode(rootNode));
		assertEquals(false, VMOSAPlan.removeNode(missionNode));
		
		//set pointer to an objective node and remove it
		// check to see if removed
		Node rm = missionNode.children.get(0);
		assertEquals(true, VMOSAPlan.removeNode(rm));
		assertEquals(1, missionNode.children.size());
		
		//set data of mission node and check it
		missionNode.setData("hello");
		assertEquals("hello", missionNode.getData());
		
		//try to remove a strategy node
		// only one so is not removed
		Node rm2 = missionNode.children.get(0).children.get(0);
		assertEquals(false, VMOSAPlan.removeNode(rm2));
	
		}
		//catch any exceptions
		catch(IllegalArgumentException e)
		
		{
		  e.getMessage();
		}

	}
	
	
	// test invalid arguments
	@Test
	public void invalidArguments() 
	{
		//make a plan and set pointer to root
		Plan VMOSAPlan2 = new IowaState();
		Node r = VMOSAPlan2.getRoot();
		//try to remove root
		try 
		{
			VMOSAPlan2.removeNode(r);
		    fail( "My method didn't throw when I expected it to" );
		} 
		catch (IllegalArgumentException e)
		{
			  e.getMessage();
		}
		//try to add a vision node
		try 
		{
			
			VMOSAPlan2.addNode(r);
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
	}


}