/**
 * 
 */
package software_masters.planner_networking;

import java.util.ArrayList;

/**
 * @author Courtney and Jack
 *
 */
public class IowaState extends Plan
{

	// constructor
	// clears list of default nodes
	// sets default strings in defaultNodes
	// adds node for each string in list
	public IowaState()
	{
		defaultNodes.clear();
		setDefaultStrings();
		addDefaultNodes();
	}
	
	//set strings for default stages IowaState plan
	private void setDefaultStrings()
	{
		defaultNodes.add("Vision");
		defaultNodes.add("Mission");
		defaultNodes.add("Core Value");
		defaultNodes.add("Strategy");
		defaultNodes.add("Goal");
		defaultNodes.add("Objective");
		defaultNodes.add("Action Plan");
		defaultNodes.add("Assessment");
	}
	

	// make nodes for all of the strings in defaultNodes
	// Create pointer for tree called root
	private void addDefaultNodes()
	{
		root = new Node(null, defaultNodes.get(0), null, null);
		Node newNode = new Node(root, defaultNodes.get(1), null, null);
		root.addChild(newNode);
		addNode(newNode);
				
			
	}
	
	
	
	
	
	// addNode method from abstract Plan class
	// cannot add to Vision or Mission since there can only be one
	// makes node and sets to parent, uses for loop to iterate through the list of names
	//     to add the nodes that follow
	
	/**
	 * Takes a Node parent and returns a boolean
	 * true if added
	 * @param parent parent of node to be added
	 * @return boolean true if added
	 */
	public boolean addNode(Node parent)
	{	
		if (parent.getName() == "Vision" || parent == null)
		{
			throw new IllegalArgumentException("Cannot add to this parent");
		}
		else
		{
		
			for (int i = (defaultNodes.indexOf(parent.getName()))+1; i < defaultNodes.size(); i++)
			{
			
				Node newNode = new Node(parent, defaultNodes.get(i), null, null);
			
				parent.addChild(newNode);
				parent = newNode;
			
			}
			return true;
		}
	}
	
	// remove a node if it is allowed to be removed
	// cannot be removed if it is the only child of its parent
	//     or if it is the root node
	/**
	 * Takes a Node nodeRemove and returns a boolean
	 * true if removed
	 * @param nodeRemove node to be removed
	 * @return boolean true if removed
	 * 
	 */
	public boolean removeNode(Node nodeRemove)
	{
		if (nodeRemove.getName() == root.getName()
				|| nodeRemove.getParent().children.size()==1 || nodeRemove==null)
		{
		
			throw new IllegalArgumentException("Cannot remove this node");
		
	    }
		else
		{
			nodeRemove.parent.removeChild(nodeRemove);
			nodeRemove.setParent(null);
			return true;

		}
	}
	
	//Getter and setters
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
	 * returns a list of strings for the default nodes
	 * @return ArrayList list of default node strings
	 * 
	 */
	public ArrayList<String> getList()
	{
		return defaultNodes;
	}
	
	/**
	 * returns a string of the plan name
	 * @return String plan name
	 * 
	 */
	public String getName()
	{
		return name;
	}

	
	/**
	 * Takes a String name and sets the name of the plan
	 * @param name name to set plan name as
	 * 
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}
