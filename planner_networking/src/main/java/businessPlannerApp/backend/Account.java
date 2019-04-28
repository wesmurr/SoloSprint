/**
 *
 */
package businessPlannerApp.backend;

/**
 * @author lee kendall
 * @author wesley murray
 */
public class Account {

	private String cookie;
	private Department department;
	private boolean isAdmin;
	private String password;
	/**
	 * Object keeps track of each user's password, cookie, department, and admin
	 * state.
	 */
	private String username;

	/**
	 * Default constructor for serialization
	 */
	public Account() {
		this.username = null;
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
	public Account(String username, String password, String cookie, Department department, boolean isAdmin) {
		this.username = username;
		this.password = password;
		this.cookie = cookie;
		this.department = department;
		this.isAdmin = isAdmin;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (this.getClass() != obj.getClass()) return false;
		final Account other = (Account) obj;
		if (this.cookie == null) {
			if (other.cookie != null) return false;
		} else if (!this.cookie.equals(other.cookie)) return false;
		if (this.department == null) {
			if (other.department != null) return false;
		} else if (!this.department.equals(other.department)) return false;
		if (this.isAdmin != other.isAdmin) return false;
		if (this.password == null) {
			if (other.password != null) return false;
		} else if (!this.password.equals(other.password)) return false;
		return true;
	}

	/**
	 * @return the cookie
	 */
	public String getCookie() { return this.cookie; }

	/**
	 * @return the department
	 */
	public Department getDepartment() { return this.department; }

	/**
	 * @return the password
	 */
	public String getPassword() { return this.password; }

	/**
	 * @return the username
	 */
	public String getUsername() { return this.username; }

	/**
	 * @return the isAdmin
	 */
	public boolean isAdmin() { return this.isAdmin; }

	/**
	 * @param isAdmin the isAdmin to set
	 */
	public void setAdmin(boolean isAdmin) { this.isAdmin = isAdmin; }

	/**
	 * @param cookie the cookie to set
	 */
	public void setCookie(String cookie) { this.cookie = cookie; }

	/**
	 * @param department the department to set
	 */
	public void setDepartment(Department department) { this.department = department; }

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) { this.password = password; }

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) { this.username = username; }

	/**
	 * Checks if the passed password string corresponds to the account's password,
	 * and returns the account's cookie during login. If the password is invalid, an
	 * exception is thrown.
	 *
	 * @param password
	 * @return String cookie
	 * @throws IllegalArgumentException
	 */
	public String testCredentials(String password) throws IllegalArgumentException // returns cookie
	{
		if (getPassword().equals(password)) return this.cookie;
		throw new IllegalArgumentException("Invalid username and/or password");
	}

}
