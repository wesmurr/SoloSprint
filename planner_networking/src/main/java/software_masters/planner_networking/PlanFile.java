/**
 * 
 */
package software_masters.planner_networking;

/**
 * @author lee.kendalll
 *
 */
public class PlanFile {

	private String year;
	private Department department;
	private boolean canEdit;
	private Plan plan;
	
	/**
	 * @param year
	 * @param department
	 * @param canEdit
	 * @param plan
	 */
	public PlanFile(String year, Department department, boolean canEdit, Plan plan) 
	{
		this.year = year;
		this.department = department;
		this.canEdit = canEdit;
		this.plan = plan;
	}

	/**
	 * @return the year
	 */
	public String getYear()
	{
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year)
	{
		this.year = year;
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
	 * @return the canEdit
	 */
	public boolean isCanEdit()
	{
		return canEdit;
	}

	/**
	 * @param canEdit the canEdit to set
	 */
	public void setCanEdit(boolean canEdit)
	{
		this.canEdit = canEdit;
	}

	/**
	 * @return the plan
	 */
	public Plan getPlan()
	{
		return plan;
	}

	/**
	 * @param plan the plan to set
	 */
	public void setPlan(Plan plan)
	{
		this.plan = plan;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlanFile other = (PlanFile) obj;
		if (canEdit != other.canEdit)
			return false;
		if (department == null)
		{
			if (other.department != null)
				return false;
		} 
		if (plan == null)
		{
			if (other.plan != null)
				return false;
		} else if (!plan.equals(other.plan))
			return false;
		if (year == null)
		{
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}

}
