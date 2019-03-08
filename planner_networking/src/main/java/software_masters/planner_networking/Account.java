/**
 * 
 */
package software_masters.planner_networking;

/**
 * @author lee kendalll
 * @author wesley murray
 *
 */
public class Account {

	/**
	 * 
	 */
	private String password;
	private String cookie;
	private Department department;
	private boolean isAdmin;
	
	public Account(String password, Department department, boolean isAdmin) 
	{
		
	}
	
	public String testCredentials(String password) throws IllegalArgumentException //returns cookie
	{
		return null;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
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
	 * @return the department
	 */
	public Department getDepartment()
	{
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(Department department)
	{
		this.department = department;
	}

	/**
	 * @return the isAdmin
	 */
	public boolean isAdmin()
	{
		return isAdmin;
	}

	/**
	 * @param isAdmin the isAdmin to set
	 */
	public void setAdmin(boolean isAdmin)
	{
		this.isAdmin = isAdmin;
	}

}
