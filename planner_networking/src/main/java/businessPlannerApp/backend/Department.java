package businessPlannerApp.backend;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lee.kendall
 * @author wesley murray
 */

public class Department {

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

	/**
	 * Object which stores the planFiles of a particular department based on year
	 */
	private ConcurrentHashMap<String, PlanFile> planFileMap;

	public Department() { this.planFileMap = new ConcurrentHashMap<>(); }

	/**
	 * Adds new planFile to department hash with a corresponding year. Overwrites
	 * old planFile if a planFile with the passed year already exists.
	 *
	 * @param year of planFile to be added
	 * @param plan
	 */
	public void addPlan(String year, PlanFile plan) { this.planFileMap.put(year, plan); }

	/**
	 * Checks if the plan exists within this department
	 *
	 * @param year
	 * @return
	 */
	public boolean containsPlan(String year) { return this.planFileMap.containsKey(year); }

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
		if (this.planFileMap == null) {
			if (other.planFileMap != null) return false;
		} else if (!Department.<String, PlanFile>hashesEqual(this.planFileMap, other.planFileMap)) return false;
		return true;
	}

	/**
	 * Retrieves planFile from department based on year
	 *
	 * @param year
	 * @return plan corresponding to the passed year
	 * @throws IllegalArgumentException
	 */
	public PlanFile getPlan(String year) throws IllegalArgumentException {
		if (this.planFileMap.containsKey(year)) return this.planFileMap.get(year);
		throw new IllegalArgumentException("A plan with this year doesn't exist in this department");
	}

	/**
	 * @return the planFileMap
	 */
	public ConcurrentHashMap<String, PlanFile> getPlanFileMap() { return this.planFileMap; }

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.planFileMap == null) ? 0 : this.planFileMap.hashCode());
		return result;
	}

	/**
	 * Removes planFile from department hash given a year
	 *
	 * @param year of planFile to be removed
	 */
	public void removePlan(String year) { this.planFileMap.remove(year); }

	/**
	 * @param planFileMap the planFileMap to set
	 */
	public void setPlanFileMap(ConcurrentHashMap<String, PlanFile> planFileMap) { this.planFileMap = planFileMap; }

}
