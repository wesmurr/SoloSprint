package businessPlannerApp.backend;

import java.io.Serializable;
import java.sql.Timestamp;

public class PlanEdit implements Serializable{
	private static final long serialVersionUID = -807032099652757915L;
	private String username;
	private Timestamp timestamp;
	private PlanFile planFile;
	
	/**
	 * Initialized all attributes based on provide values.
	 * @param username
	 * @param timestamp
	 * @param planFile
	 */
	public PlanEdit(String username, Timestamp timestamp, PlanFile planFile) {
		this.username = username;
		this.timestamp = timestamp;
		this.planFile = planFile;
	}
	
	/**
	 * XML serialization constructor.
	 */
	public PlanEdit() {
		this.username = null;
		this.timestamp = null;
		this.planFile = null;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() { return username; }
	/**
	 * @return the timestamp
	 */
	public Timestamp getTimestamp() { return timestamp; }
	/**
	 * @return the planFile
	 */
	public PlanFile getPlanFile() { return planFile; }
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) { this.username = username; }
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
	/**
	 * @param planFile the planFile to set
	 */
	public void setPlanFile(PlanFile planFile) { this.planFile = planFile; }
	
	@Override
	public String toString() { return username + "\n"+timestamp.toString(); }
	

}
