/**
 * 
 */
package software_masters.planner_networking;

/**
 * @author lee kendall
 *
 */

public class Client {

	/**
	 * 
	 */
	private String cookie;
	private PlanFile currPlanFile;
	private Node currNode;
	private Server server;
	
	
	public Client(Server server) 
	{
		this.server = server;
	}
	
	public String login(String username, String password) throws IllegalArgumentException
	{
		return null;
	}
	
	public PlanFile getPlan(String year) throws IllegalArgumentException
	{
		return null;
	}
	
	public PlanFile getPlanOutline(String name) throws IllegalArgumentException
	{
		return null;
	}
	
	public void pushPlan(PlanFile plan) throws IllegalArgumentException
	{
	}
	
	public void addUser(String username, String password, String departmentName, boolean isAdmin) throws IllegalArgumentException
	{
	}
	
	public void flagPlan(String departmentName, String year, boolean canEdit) throws IllegalArgumentException
	{
	}
	
	public void addDepartment(String departmentName) throws IllegalArgumentException
	{
	}
	
	public void addBranch() throws IllegalArgumentException 
	{

	}
	
	public void removeBranch() throws IllegalArgumentException
	{
		
	}
	
	public void editData(String data)
	{
		
	}
	
	public String getData()
	{
		return null;
	}
	
	public void setYear()
	{
		
	}

	/**
	 * @return the cookie
	 */
	public String getCookie()
	{
		return cookie;
	}

	/**
	 * @param cookie the cookie to set
	 */
	public void setCookie(String cookie)
	{
		this.cookie = cookie;
	}

	/**
	 * @return the currPlanFile
	 */
	public PlanFile getCurrPlanFile()
	{
		return currPlanFile;
	}

	/**
	 * @param currPlanFile the currPlanFile to set
	 */
	public void setCurrPlanFile(PlanFile currPlanFile)
	{
		this.currPlanFile = currPlanFile;
	}

	/**
	 * @return the currNode
	 */
	public Node getCurrNode()
	{
		return currNode;
	}

	/**
	 * @param currNode the currNode to set
	 */
	public void setCurrNode(Node currNode)
	{
		this.currNode = currNode;
	}

	/**
	 * @return the server
	 */
	public Server getServer()
	{
		return server;
	}

	/**
	 * @param server the server to set
	 */
	public void setServer(Server server)
	{
		this.server = server;
	}
	
	

}
