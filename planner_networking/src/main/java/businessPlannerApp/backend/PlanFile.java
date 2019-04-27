package businessPlannerApp.backend;

import java.io.Serializable;

/**
 * @author lee.kendall
 * @author wesley murray
 */
public class PlanFile implements Serializable {

	private static final long serialVersionUID = 8679415216780269027L;
	private boolean canEdit;
	private Plan plan;
	private String year;

	/**
	 * Default constructor for serialization
	 */
	public PlanFile() {
		this.year = null;
		this.canEdit = false;
		this.plan = null;
	}

	/**
	 * @param year
	 * @param canEdit
	 * @param plan
	 */
	public PlanFile(String year, boolean canEdit, Plan plan) {
		this.year = year;
		this.canEdit = canEdit;
		this.plan = plan;
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
		final PlanFile other = (PlanFile) obj;
		if (this.canEdit != other.canEdit) return false;
		if (this.plan == null) {
			if (other.plan != null) return false;
		} else if (!this.plan.equals(other.plan)) return false;
		if (this.year == null) {
			if (other.year != null) return false;
		} else if (!this.year.equals(other.year)) return false;
		return true;
	}

	/**
	 * @return the plan
	 */
	public Plan getPlan() { return this.plan; }

	/**
	 * @return the year
	 */
	public String getYear() { return this.year; }

	/**
	 * @return the canEdit
	 */
	public boolean isCanEdit() { return this.canEdit; }

	/**
	 * @param canEdit the canEdit to set
	 */
	public void setCanEdit(boolean canEdit) { this.canEdit = canEdit; }

	/**
	 * @param plan the plan to set
	 */
	public void setPlan(Plan plan) { this.plan = plan; }

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) { this.year = year; }

	@Override
	public String toString() {
		if (this.isCanEdit()) return this.year;
		else return this.year + " Read Only";

	}

}
