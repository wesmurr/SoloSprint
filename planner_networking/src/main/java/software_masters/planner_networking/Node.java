
/**
 * 
 */
package software_masters.planner_networking;
import java.util.ArrayList;
/**
 * @author Courtney and Jack
 *
 */

public class Node
{
	Node parent;
	String name;
	String data;
	ArrayList<Node> children = new ArrayList<Node>();
	
	
	//constructor 
	/*public Node(String name, String data, Node parent)
	{
		this.name = name;
		this.data = data;
		this.parent = parent;
		//this.children = children;
	}*/
	
	//constructor is data is not known	
	/**
	 * Takes a Node parent, String name, String data, and list of children
	 * Sets values in node
	 * @param parent parent of node
	 * @param name name of node
	 * @param data data for node
	 * @param child list of children
	 */
	public Node(Node parent, String name, String data, ArrayList<Node> child)
	{
		this.name = name;
		this.parent = parent;
		this.data = data;
		
	}
	
	//empty constructor for XML
	public Node()
	{
		this(null, "blank",  "empty", null);
	}


	//Getter and setters
	/**
	 * returns a String name of node
	 * @return String name of node
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets name of node 
	 * @param name name to set as name of node
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Returns node's data
	 * @return String data of node
	 */
	public String getData()
	{
		return data;
	}

	/**
	 * Takes a String data and sets node's data
	 * @param data data to set as data of node
	 */
	public void setData(String data)
	{
		this.data = data;
	}

	/**
	 * returns the parent node
	 * @return Node parent of node
	 */
	public Node getParent()
	{
		return parent;
	}
  
	/**
	 * Takes a Node parent and sets the nodes parent
	 * @param parent parent to set as parent of node
	 */
	public void setParent(Node parent)
	{
		this.parent = parent;
	}
	
	/**
	 * Returns a list of children nodes
	 * @return ArrayList list of children
	 */
	public ArrayList<Node> getChildren()
	{
		return children;
	}
	
	//
	
	//add a Node child to another node
	/**
	 * Takes a node child and adds child to child list
	 * @param child child to be added to this node
	 */
	public void addChild(Node child)
	{
		this.children.add(child);
	}
	
	
	//remove child node from a node's children list
	/**
	 * @param child child to be removed from this node
	 */
	public void removeChild(Node child)
	{
		this.children.remove(child);
	}
	

}
