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

	
	
	
}