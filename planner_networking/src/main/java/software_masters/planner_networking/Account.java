/**
 * 
 */
package software_masters.planner_networking;

/**
 * @author lee kendall
 * @author wesley murray
 *
 */
public class Account {

	/**
	 * Object keeps track of each user's password, cookie, department, and admin state.
	 */
	private String password;
	private String cookie;
	private Department department;
	private boolean isAdmin;
	
	/**
	 * Default constructor for serialization
	 */
	public Account() 
	{
		this.password = null;
		this.cookie = null;
		this.department = null;
		this.isAdmin = false;
	}
	
	/**
	 * @param password
	 * @param cookie
	 * @param department
	 * @param isAdmin
	 */
	public Account(String password, String cookie, Department department, boolean isAdmin) {
		super();
		this.password = password;
		this.cookie = cookie;
		this.department = department;
		this.isAdmin = isAdmin;
	}

	/**
	 * Checks if the passed password string corresponds to the account's password, and returns the account's cookie during login. If the password is invalid,
	 * an exception is thrown.
	 * 
	 * @param password
	 * @return String cookie
	 * @throws IllegalArgumentException
	 */
	public String testCredentials(String password) throws IllegalArgumentException //returns cookie
	{
		if (this.getPassword() == password)
		{
			return cookie;
		}
		throw new IllegalArgumentException("Invalid username and/or password");
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
