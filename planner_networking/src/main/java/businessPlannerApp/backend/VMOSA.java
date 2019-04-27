package businessPlannerApp.backend;

import java.rmi.RemoteException;

/**
 * @author Courtney and Jack
 * @author wesley and lee.
 */
public class VMOSA extends Plan {
	private static final long serialVersionUID = 8514352878071159404L;

	/**
	 * @throws RemoteException
	 */
	public VMOSA() throws RemoteException { super(); }

	/**
	 * Takes a Node parent and returns a boolean True if added
	 *
	 * @param parent parent node of node that needs to be added
	 * @return boolean true if added
	 * @throws RemoteException
	 */
	@Override
	public boolean addNode(PlanSection parent) throws RemoteException, IllegalArgumentException {
		if (parent == null) throw new IllegalArgumentException("Cannot add to this parent");
		else if (parent.getName().equals("Vision")) throw new IllegalArgumentException("Cannot add to this parent");
		else {

			for (int i = this.index_depth(parent) + 1; i < this.getList().size(); i++) {

				final PlanSection newNode = new PlanSection(parent, this.getList().get(i), null, null);

				parent.addChild(newNode);
				parent = newNode;

			}
			return true;
		}
	}

	/**
	 * Takes a Node nodeRemove and returns a boolean true if added
	 *
	 * @param nodeRemove node to be removed
	 * @return boolean true is removed
	 */
	@Override
	public boolean removeNode(PlanSection nodeRemove) throws IllegalArgumentException

	{
		if (nodeRemove == null) throw new IllegalArgumentException("Cannot remove this node");
		else if (nodeRemove.getParent() == null) throw new IllegalArgumentException("Cannot remove this node");
		else if (nodeRemove.getName().equals(this.getRoot().getName())
				|| (nodeRemove.getParent().getChildren().size() == 1))
			throw new IllegalArgumentException("Cannot remove this node");
		else {
			nodeRemove.getParent().removeChild(nodeRemove);
			nodeRemove.setParent(null);

			return true;

		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see software_masters.planner_networking.Plan#setDefaultStrings()
	 */
	@Override
	protected void setDefaultStrings() {
		this.getList().add("Vision");
		this.getList().add("Mission");
		this.getList().add("Objective");
		this.getList().add("Strategy");
		this.getList().add("Action Plan");
	}
}
