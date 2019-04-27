package businessPlannerApp.backend;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * @author Courtney and Jack
 * @author wesley and lee.
 */
public abstract class Plan implements Serializable// extends UnicastRemoteObject
{
	private static final long serialVersionUID = 1538776243780396317L;
	private ArrayList<String> defaultNodes = new ArrayList<>();
	private String name;
	private PlanSection root;

	/**
	 * @throws RemoteException
	 */
	public Plan() throws RemoteException {
		this.defaultNodes = new ArrayList<>();
		this.setDefaultStrings();
		this.addDefaultNodes();
	}

	/**
	 * This class builds default template based on string array
	 *
	 * @throws RemoteException
	 */
	protected void addDefaultNodes() throws RemoteException {
		this.root = new PlanSection(null, this.defaultNodes.get(0), null, null);
		final PlanSection newParent = new PlanSection(this.root, this.defaultNodes.get(1), null, null);
		this.root.addChild(newParent);
		this.addNode(newParent);
	}

	/**
	 * @param parent
	 * @return
	 */
	abstract public boolean addNode(PlanSection parent) throws RemoteException, IllegalArgumentException;

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
		final Plan other = (Plan) obj;
		if (this.defaultNodes == null) {
			if (other.defaultNodes != null) return false;
		} else if (!this.defaultNodes.equals(other.defaultNodes)) return false;
		if (this.name == null) {
			if (other.name != null) return false;
		} else if (!this.name.equals(other.name)) return false;
		if (this.root == null) {
			if (other.root != null) return false;
		} else if (!this.root.testEquals(other.root)) return false;
		return true;
	}

	/**
	 * @return the defaultNodes
	 */
	public ArrayList<String> getDefaultNodes() { return this.defaultNodes; }

	/**
	 * returns a list of default node strings
	 *
	 * @return ArrayList list of default node strings
	 */
	public ArrayList<String> getList() { return this.defaultNodes; }

	/**
	 * returns a String name of plan
	 *
	 * @return String strings of plan name
	 */
	public String getName() { return this.name; }

	/**
	 * returns the root node
	 *
	 * @return Node root node
	 */
	public PlanSection getRoot() { return this.root; }

	protected int index_depth(PlanSection node) {
		if (node.getParent() == null) return 0;
		else return this.index_depth(node.getParent()) + 1;

	}

	/**
	 * @param Node
	 * @return
	 */
	abstract public boolean removeNode(PlanSection Node) throws IllegalArgumentException;

	/**
	 * @param defaultNodes the defaultNodes to set
	 */
	public void setDefaultNodes(ArrayList<String> defaultNodes) { this.defaultNodes = defaultNodes; }

	// creates string array node hierarchy in subclass
	abstract protected void setDefaultStrings();

	/**
	 * Takes a String name and sets name of plan
	 *
	 * @param name name to set as plan name
	 */
	public void setName(String name) { this.name = name; }

	/**
	 * Takes a Node node and String data Sets data for the node
	 *
	 * @param node node to set data for
	 * @param data data to set in node
	 */
	public void setNodeData(PlanSection node, String data) { node.setData(data); }

	/**
	 * @param root the root to set
	 */
	public void setRoot(PlanSection root) { this.root = root; }

}
