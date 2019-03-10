package software_masters.planner_networking;

import java.util.ArrayList;

/**
 * @author Courtney and Jack
 * @author wesley and lee. 
 */
public class Centre extends Plan
{
	
	public Centre()
	{
		defaultNodes= new ArrayList<String>();
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

}