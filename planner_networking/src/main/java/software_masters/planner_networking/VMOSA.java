package software_masters.planner_networking;

import java.util.ArrayList;

/**
 * @author Courtney and Jack
 * @author wesley and lee. 
 *
 */
public class VMOSA extends Plan
{

	public VMOSA()
	{
		defaultNodes= new ArrayList<String>(); 
		setDefaultStrings();
		addDefaultNodes();
	}
	
	private void setDefaultStrings()
	{
		defaultNodes.add("Vision");
		defaultNodes.add("Mission");
		defaultNodes.add("Objective");
		defaultNodes.add("Strategy");
		defaultNodes.add("Action Plan");
		defaultNodes.add("Assessment");
	}

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
}