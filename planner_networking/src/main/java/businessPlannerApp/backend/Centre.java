package businessPlannerApp.backend;

import java.rmi.RemoteException;

/**
 * @author Courtney and Jack
 * @author wesley and lee.
 */
public class Centre extends Plan {
	private static final long serialVersionUID = 8094008350302564337L;

	/**
	 * @throws RemoteException
	 */
	public Centre() throws RemoteException { super(); }

	/**
	 * Take a Node parent and adds the required children and returns a boolean true
	 * if added
	 *
	 * @param parent parent of node that needs to be added
	 * @return boolean true if added
	 */
	@Override
	public boolean addNode(PlanSection parent) throws RemoteException, IllegalArgumentException {
		if (parent == null) throw new IllegalArgumentException("Cannot add to this parent");
		else {
			for (int i = this.index_depth(parent) + 1; i < this.getList().size(); i++) {

				final PlanSection newNode = new PlanSection(parent, this.getList().get(i), null, null);

				parent.addChild(newNode);
				parent = newNode;

			}
			return true;
		}
	}

	// remove a node if it is allowed to be removed
	// cannot be removed if it is the only child of its parent
	// or if it is the root node
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

	// set strings for default stages Centre plan
	/*
	 * (non-Javadoc)
	 *
	 * @see software_masters.planner_networking.Plan#setDefaultStrings()
	 */
	@Override
	protected void setDefaultStrings() {
		this.getList().add("Mission");
		this.getList().add("Goal");
		this.getList().add("Learning Objective");
		this.getList().add("Assessment Process");
		this.getList().add("Results");
	}

}
