package businessPlannerApp.backend;

import java.io.Serializable;

/**
 * @author Wesley Murray This class represents comments a user may post to a
 *         plan.
 */
public class Comment implements Serializable {

	private static final long serialVersionUID = 607819104769662255L;
	private String content = "";
	private boolean resolved = false;
	private String username = "default";

	public Comment() { this("", "default"); }

	public Comment(String content, String username) {
		this.content = content;
		this.username = username;
	}

	/**
	 * @return the content
	 */
	public String getContent() { return this.content; }

	/**
	 * @return the username
	 */
	public String getUsername() { return this.username; }

	/**
	 * @return the resolved
	 */
	public boolean isResolved() { return this.resolved; }

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) { this.content = content; }

	/**
	 * @param resolved the resolved to set
	 */
	public void setResolved(boolean resolved) { this.resolved = resolved; }

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) { this.username = username; }

	@Override
	public String toString() { return this.username + ": " + this.content; }

}
