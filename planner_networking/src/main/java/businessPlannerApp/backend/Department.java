package businessPlannerApp.backend;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lee.kendall
 * @author wesley murray
 */

public class Department {


	/**
	 * Object which stores the PlanHistorys of a particular department based on year
	 */
	private ConcurrentHashMap<String, PlanHistory> planHistoryMap;

	
	public Department() { this.planHistoryMap = new ConcurrentHashMap<>(); }

	/**
	 * Adds new PlanHistory to department hash with a corresponding year. Overwrites
	 * old PlanHistory if a PlanHistory with the passed year already exists.
	 * @param year of PlanHistory to be added
	 * @param plan
	 */
	public void addPlanHistory(String year, PlanHistory plan) { this.planHistoryMap.put(year, plan); }

	/**
	 * Checks if the plan exists within this department
	 * @param year
	 * @return
	 */
	public boolean containsPlan(String year) { return this.planHistoryMap.containsKey(year); }


	/**
	 * Retrieves PlanHistory from department based on year
	 * @param year
	 * @return plan corresponding to the passed year
	 * @throws IllegalArgumentException
	 */
	public PlanHistory getPlanHistory(String year) throws IllegalArgumentException {
		if (this.planHistoryMap.containsKey(year)) return this.planHistoryMap.get(year);
		throw new IllegalArgumentException("A plan with this year doesn't exist in this department");
	}


	/**
	 * Removes PlanHistory from department hash given a year
	 * @param year of PlanHistory to be removed
	 */
	public void removePlan(String year) { this.planHistoryMap.remove(year); }
	
	/**
	 * @return the planHistoryMap
	 */
	public ConcurrentHashMap<String, PlanHistory> getPlanHistoryMap() { return this.planHistoryMap; }

	/**
	 * @param planHistoryMap the planHistoryMap to set
	 */
	public void setPlanHistoryMap(ConcurrentHashMap<String, PlanHistory> planHistoryMap) { this.planHistoryMap = planHistoryMap; }

	/**
	 * @param <K>
	 * @param <V>
	 * @param map1
	 * @param map2
	 * @return
	 */
	private static <K, V> boolean hashesEqual(ConcurrentHashMap<K, V> map1, ConcurrentHashMap<K, V> map2) {
		for (final Enumeration<K> keyList = map1.keys(); keyList.hasMoreElements();) {
			final K key = keyList.nextElement();
			if (!map1.containsKey(key)) return false;
			if (!map2.containsKey(key)) return false;
			if (!map1.get(key).equals(map2.get(key))) return false;
		}
		return true;
	}
	

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.planHistoryMap == null) ? 0 : this.planHistoryMap.hashCode());
		return result;
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
		final Department other = (Department) obj;
		if (this.planHistoryMap == null) {
			if (other.planHistoryMap != null) return false;
		} else if (!Department.<String, PlanHistory>hashesEqual(this.planHistoryMap, other.planHistoryMap)) return false;
		return true;
	}
}
