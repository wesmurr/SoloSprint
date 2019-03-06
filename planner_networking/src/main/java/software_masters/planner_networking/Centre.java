/**
 * 
 */
package software_masters.planner_networking;

import java.util.ArrayList;

/**
 * @author Courtney and Jack
 *
 */
public class Centre extends Plan
{
	// name of plan, set by client
	public String name;
	// list of default nodes
	public ArrayList<String> defaultNodes = new ArrayList<String>(); 
	//pointer to top of IowaState plan tree
	public Node root;
	
	
	// constructor
	// clears list of default nodes
	// sets default strings in defaultNodes
	// adds node for each string in list
	
	public Centre()
	{
		defaultNodes.clear();
		setDefaultStrings();
		addDefaultNodes();
	}
	//set strings for default stages Centre plan

	private void setDefaultStrings()
	{
		defaultNodes.add("Mission");
		defaultNodes.add("Goal");
		defaultNodes.add("Learning Objective");
		defaultNodes.add("Assessment Process");
		defaultNodes.add("Results");
	}
	
	// make nodes for all of the strings in defaultNodes
	// Create pointer for tree called root
	private void addDefaultNodes()
	{
		root = new Node(null, defaultNodes.get(0), null, null);
		Node newParent = new Node(root, defaultNodes.get(1), null, null);
		root.addChild(newParent);
		addNode(newParent);		
	}
	
	
	
	// addNode method from abstract Plan class
	// cannot add to Mission since there can only be one
	// makes node and sets to parent, uses for loop to iterate through the list of names
//  to add the nodes that follow
	/**
	 * Take a Node parent and adds the required children and returns a boolean
	 * true if added
	 * @param parent parent of node that needs to be added
	 * @return boolean true if added
	 */
	public boolean addNode(Node parent)
	{	
		if (parent == null)
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
	 * true is removed
	 * @param nodeRemove node to be removed
	 * @return boolean true if removed
	 */
	public boolean removeNode(Node nodeRemove)
	{
		if ((nodeRemove.getName() == root.getName()) 
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
	 * returns a list of default node strings
	 * @return ArrayList list of default node strings
	 * 
	 */
	public ArrayList<String> getList()
	{
		return defaultNodes;
	}
	
	/**
	 * returns a string name of the plan
	 * @return String name of plan
	 * 
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * takes a string name and sets plan name
	 * @param name name to set as plan name
	 * 
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}