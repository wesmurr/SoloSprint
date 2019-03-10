/**
 * 
 */
package software_masters.planner_networking;

import java.util.ArrayList;

/**
 * @author Courtney and Jack
 *
 */
public abstract class Plan
{
	// name of plan, set by client
	public String name;
	// list of default nodes
	public ArrayList<String> defaultNodes = new ArrayList<String>(); 
	//pointer to top of IowaState plan tree
	public Node root;
	
	//abstract methods addNode, removeNode, getRoot, getList 
	//   to be implemented in concrete classes
	
	abstract public boolean addNode(Node parent);
	
	abstract public boolean removeNode(Node Node);
	
	abstract public Node getRoot();
	
	abstract public ArrayList<String> getList();

	abstract public String getName();

	abstract public void setName(String name);
	
	//set data for the given node
	/**
	 * Takes a Node node and String data
	 * Sets data for the node
	 * @param node node to set data for
	 * @param data data to set in node
	 * 
	 */
	public void setNodeData(Node node, String data)
	{
		node.setData(data);
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
		Plan other = (Plan) obj;
		if (defaultNodes == null)
		{
			if (other.defaultNodes != null)
				return false;
		} else if (!defaultNodes.equals(other.defaultNodes))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (root == null)
		{
			if (other.root != null)
				return false;
		} else if (!root.equals(other.root))
			return false;
		return true;
	}
	
	
}