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

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		PlanEdit other = (PlanEdit) obj;
		if (planFile == null) {
			if (other.planFile != null) return false;
		} else if (!planFile.equals(other.planFile)) return false;
		if (timestamp == null) {
			if (other.timestamp != null) return false;
		} else if (!timestamp.equals(other.timestamp)) return false;
		if (username == null) {
			if (other.username != null) return false;
		} else if (!username.equals(other.username)) return false;
		return true;
	}
	

}
