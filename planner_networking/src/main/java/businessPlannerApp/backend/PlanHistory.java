package businessPlannerApp.backend;

import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.sql.Timestamp;

public class PlanHistory {
	
	private String year;
	private boolean canEdit;
	private Stack<PlanEdit> edits;
	private ConcurrentHashMap<Timestamp,PlanEdit> editMap;
	
	/**
	 * Initializes all attributes based on provided values.
	 * @param year
	 * @param canEdit
	 */
	public PlanHistory(String year, boolean canEdit) {
		this.year = year;
		this.canEdit = canEdit;
		this.edits = new Stack<>();
		this.editMap= new ConcurrentHashMap<>();
	}
	
	/**
	 * XML serialization constructor
	 */
	public PlanHistory() {
		this.year = null;
		this.canEdit = false;
		this.edits = null;
		this.editMap= new ConcurrentHashMap<>();
	}
	
	
	/**
	 * Returns the most recent plan edit object containing the most up to date plan.
	 * @return the most recent plan edit file
	 */
	public PlanEdit getCurrentPlanEdit() {
		return this.edits.peek();
	}
	
	/**
	 * Returns an edit to a plan based on its timestamp
	 * @param timestamp
	 * @return
	 */
	public PlanEdit getEdit(Timestamp timestamp) {
		PlanEdit temp=editMap.get(timestamp);
		temp.getPlanFile().setYear(year);
		temp.getPlanFile().setCanEdit(canEdit);
		return temp;
	}
	
	/**
	 * Add a plan edit to the history of edits.
	 * @param file
	 */
	public void addPlanEdit(PlanEdit file) {
		this.edits.add(file);
		this.editMap.put(file.getTimestamp(), file);
	}
	
	/**
	 * @return the year
	 */
	public String getYear() { return year; }
	/**
	 * @return the canEdit
	 */
	public boolean isCanEdit() { return canEdit; }
	/**
	 * @return the edits
	 */
	public Stack<PlanEdit> getEdits() { return edits; }
	/**
	 * @return the editMap
	 */
	public ConcurrentHashMap<Timestamp, PlanEdit> getEditMap() { return editMap; }
	/**
	 * @param year the year to set
	 */
	public void setYear(String year) { this.year = year; }
	/**
	 * @param canEdit the canEdit to set
	 */
	public void setCanEdit(boolean canEdit) { this.canEdit = canEdit; }
	/**
	 * @param edits the edits to set
	 */
	public void setEdits(Stack<PlanEdit> edits) { this.edits = edits; }
	/**
	 * @param editMap the editMap to set
	 */
	public void setEditMap(ConcurrentHashMap<Timestamp, PlanEdit> editMap) { this.editMap = editMap; }


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		PlanHistory other = (PlanHistory) obj;
		if (canEdit != other.canEdit) return false;
		if (editMap == null) {
			if (other.editMap != null) return false;
		} else if (!editMap.equals(other.editMap)) return false;
		if (edits == null) {
			if (other.edits != null) return false;
		} else if (!edits.equals(other.edits)) return false;
		if (year == null) {
			if (other.year != null) return false;
		} else if (!year.equals(other.year)) return false;
		return true;
	}

}
