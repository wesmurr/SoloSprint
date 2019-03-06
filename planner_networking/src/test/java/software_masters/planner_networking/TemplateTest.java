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
public class TemplateTest
{

	@Test
	public void test()
	{
		try 
		{
			//make a template
			Template temp1 = new Template();
			//print out available templates
			System.out.println(temp1.templates);
			//make a Vmosa plan called VMOSA_2019 and check if made
			temp1.makePlan("VMOSA", "VMOSA_2019");
			assertEquals(false, temp1.plans.isEmpty());
			assertEquals("VMOSA_2019", temp1.plans.get(0).getName());
			
			// get the plan based on the name
			assertEquals(temp1.plans.get(0), temp1.getPlan("VMOSA_2019"));
			// encode XML
			temp1.encode(temp1.plans.get(0), "businessPlanXML.xml");
			// decode XML
			Plan p = temp1.decode("businessPlanXML.xml");
			// see if decoded plan p is the same plan that was encoded
			assertEquals("VMOSA_2019", p.getName());
			
			
			
		}
		catch(IllegalArgumentException e)
		{
		  e.getMessage();
		}		
	}
	
	//test invalid argument
	@Test
	public void invalidArguments() 
	{
		//make template
		Template temp2 = new Template();
		//try to make a plan with incorrect template name and plan name
		try 
		{
		    temp2.makePlan("hello", "people");
		    fail( "My method didn't throw when I expected it to" );
		} 
		catch (IllegalArgumentException e)
		{
			  e.getMessage();
		}
		//try to get plan with incorrect plan name
		try 
		{
		    temp2.getPlan("hello");
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
