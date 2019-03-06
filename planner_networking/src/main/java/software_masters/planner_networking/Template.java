/**
 * 
 */
package software_masters.planner_networking;

import java.beans.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * @author Courtney and Jack
 *
 */
public class Template
{
	
	// list of available templates
	public ArrayList<String> templates;
	// list of current plans
	public ArrayList<Plan> plans;
	
	//constructor
	/**
	 * Takes a list of templates and list of plans and makes a new template
	 * @param ArrayList templates list of template strings
	 * @param ArrayList plans list of Plan plans
	 * 
	 */
    public Template(ArrayList<String> templates, ArrayList<Plan> plans)
    {
    	this.templates = templates;
    	this.plans = plans;

    }
    
    //constructor, no parameters
    public Template()
    {
    	this.templates = new ArrayList<String>();
    	this.plans = new ArrayList<Plan>();
    	templates.clear();
    	addTemplates();
    }
    
    
    // when templates are added when the constructor is called
    // need to be changed when there is a new plan added
    private void addTemplates()
    {
    	templates.add("VMOSA");
    	templates.add("CENTRE");
    	templates.add("IOWAST");
    }
    
    
    // uses if stmts in order to get the plan
    // makes a plan from list of templates, sets given name to new plan's name
    // if template does not exist, exception is thrown
    // stores in Plans list after
    // only other place where existing code need to be changed
    
    /**
     * Takes a String templateName and planTitle
     * If the template exists, makes a new plan and set name to given name
	 * @param templateName name of template that needs to be used
	 * @param planTitle name to set as plan name
	 */
    public void makePlan(String templateName, String planTitle)
    {
    	templateName = templateName.toUpperCase();
    	
    	if (templateName == "VMOSA")
    	{
    		Plan Vmosa = new VMOSA();
    		plans.add(Vmosa);
    		Vmosa.setName(planTitle);
    		System.out.println("Vmosa Plan Added");
    		
    	}
    	else if (templateName == "CENTRE")
    	{
    		Plan Centre = new Centre();
    		plans.add(Centre);
    		Centre.setName(planTitle);
    		System.out.println("Centre Plan Added");
    	}
    	else if (templateName == "IOWAST")
    	{
    		Plan IowaST = new IowaState();
    		plans.add(IowaST);
    		IowaST.setName(planTitle);
    		System.out.println("Iowa State Plan Added");
    	}
    	else
    	{
    		throw new IllegalArgumentException("Plan does not exist");
    	}
    }
    
  
    // allows access to Plans stored in the list
    // if there is not a plan with the given title, exception is thrown
    // assumes that person organizing knows which plan is which
    
    /**
     * Takes a String title name and returns the plan with that
     * title if there is a plan with that title name
	 * @param titleName name of plan
	 * @return Plan plan that has specified title
	 * 
	 */
   public Plan getPlan(String titleName)
    {
	   for (int i = 0; i < plans.size(); i++)
	   {
		   if (plans.get(i).getName() == titleName)
		   {
			   return plans.get(i);
		   }
	   }
	   throw new IllegalArgumentException("No plan with this title exist");
    }
   
   
   // encodes plans to an xml file
   // know the plan you want to encode
   
   /**
    * Take a Plan plan and a String fileName
    * puts an XML file for the plan in the file given
	 * @param plan plan to be encoded
	 * @param fileName file to store XML
	 * 
	 */
	public void encode(Plan plan, String fileName)
	{
		XMLEncoder encoder=null;
		try
		{
			encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)));
		}
		catch(FileNotFoundException fileNotFound)
		{
			System.out.println("ERROR: While Creating or Opening the File dvd.xml");
		}
		encoder.writeObject(plan);
		encoder.close();
	}
	
	
	// takes a fileName and returns the plan object to you
	/**
	 * Takes an XML file and decodes it,
	 * returns a Plan
	 * @param fileName XML file
	 * @return Plan plan that was decoded
	 * 
	 */
	public Plan decode(String fileName)
	{
		XMLDecoder decoder=null;
		try 
		{
			decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(fileName)));
		} 
			catch (FileNotFoundException e) 
		{
				System.out.println("ERROR: File dvd.xml not found");
		}
		Plan plan = (Plan)decoder.readObject();
		
		return plan;
		
	}
}
