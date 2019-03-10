package software_masters.planner_networking;

import java.util.ArrayList;

/**
 * @author Courtney and Jack
 * @author wesley and lee. 
 *
 */
public abstract class Plan
{
	public String name;
	public ArrayList<String> defaultNodes = new ArrayList<String>(); 
	public Node root;
	
	protected void addDefaultNodes()
	{
		root = new Node(null, defaultNodes.get(0), null, null);
		Node newParent = new Node(root, defaultNodes.get(1), null, null);
		root.addChild(newParent);
		addNode(newParent);		
	}
	//abstract methods addNode, removeNode, getRoot, getList 
	//   to be implemented in concrete classes
	
	abstract public boolean addNode(Node parent);
	
	abstract public boolean removeNode(Node Node);
	
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
	
	/**
	 * returns the root node
	 * @return Node root node
	 * 
	 */
	public Node getRoot()
	{
		return root;
	}
	
	/**
	 * returns a list of default node strings
	 * @return ArrayList list of default node strings
	 */
	public ArrayList<String> getList()
	{
		return defaultNodes;
	}
	
	/**
	 * returns a String name of plan
	 * @return String strings of plan name
	 * 
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Takes a String name and sets name of plan
	 * @param name name to set as plan name
	 * 
	 */
	public void setName(String name)
	{
		this.name = name;
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