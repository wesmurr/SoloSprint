/**
 * 
 */
package software_masters.planner_networking;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lee.kendall
 * @author wesley murray
 *
 */

public class Department {

	/**
	 * Object which stores the planFiles of a particular department associated with a year
	 * 
	 */
	private ConcurrentHashMap<String, PlanFile> planFileMap;
	
	public Department() 
	{
		planFileMap = new ConcurrentHashMap<String, PlanFile>();
	}
	
	/**
	 * Retrieves planFile from department based on year
	 * @param year
	 * @return plan corresponding to the passed year
	 * @throws IllegalArgumentException
	 */
	public PlanFile getPlan(String year) throws IllegalArgumentException
	{
		if(planFileMap.containsKey(year))
		{
		return planFileMap.get(year);
		}
		throw new IllegalArgumentException("A plan with this year doesn't exist in this department");
	}
	
	/**
	 * Adds new planFile to department hash with a corresponding year. Overwrites old planFile if a planFile with the passed year already exists.
	 * @param year of planFile to be added
	 * @param plan
	 */
	public void addPlan(String year, PlanFile plan)
	{
		planFileMap.put(year, plan);
	}
	
	/**
	 * Removes planFile from department hash given a year
	 * @param year of planFile to be removed
	 */
	public void removePlan(String year) 
	{
		planFileMap.remove(year);
	}
	
	/**
	 * Checks if the plan exists within this department
	 * @param year
	 * @return
	 */
	public boolean containsPlan(String year)
	{
		return this.planFileMap.containsKey(year);
	}

	/**
	 * @return the planFileMap
	 */
	public ConcurrentHashMap<String, PlanFile> getPlanFileMap()
	{
		return planFileMap;
	}

	/**
	 * @param planFileMap the planFileMap to set
	 */
	public void setPlanFileMap(ConcurrentHashMap<String, PlanFile> planFileMap)
	{
		this.planFileMap = planFileMap;
	}

}
