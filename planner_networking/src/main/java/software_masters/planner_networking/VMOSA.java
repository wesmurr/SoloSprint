/**
 * 
 */
package software_masters.planner_networking;

import java.util.ArrayList;

/**
 * @author Courtney and Jack
 *
 */
public class VMOSA extends Plan
{
	
	// constructor
	// clears default nodes so information is not duplicated
	// sets strings in default nodes
	
	public VMOSA()
	{
		defaultNodes.clear();
		setDefaultStrings();
		addDefaultNodes();
	}
	
	//set strings for default stages VMOSA plan
	private void setDefaultStrings()
	{
		defaultNodes.add("Vision");
		defaultNodes.add("Mission");
		defaultNodes.add("Objective");
		defaultNodes.add("Strategy");
		defaultNodes.add("Action Plan");
		defaultNodes.add("Assessment");
	}
	

	// make nodes for first two strings in defaultNodes
	// Set the pointer, root, to the first node in the tree
	// add children to newNode
	private void addDefaultNodes()
	{
		root = new Node(null, defaultNodes.get(0), null, null);
		Node newParent = new Node(null, defaultNodes.get(1), null, null);
		root.addChild(newParent);
		addNode(newParent);		
			
	}

	
	// addNode method from abstract Plan class
	// if trying to add Vision or Mission and they are already there
	// makes node and sets to parent, uses for loop to iterate through the list of names
	/**
	 * Takes a Node parent and returns a boolean
	 * True if added
	 * @param parent parent node of node that needs to be added
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
	 * true if added
	 * @param nodeRemove node to be removed
	 * @return boolean true is removed
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
}