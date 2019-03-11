package software_masters.planner_networking;

import java.rmi.RemoteException;

/**
 * @author Courtney and Jack
 * @author wesley and lee. 
 *
 */
public class IowaState extends Plan
{

	private static final long serialVersionUID = 3096239674948462908L;

	/**
	 * @throws RemoteException
	 */
	public IowaState() throws RemoteException
	{
		super();
	}

	//set strings for default stages IowaState plan
	/* (non-Javadoc)
	 * @see software_masters.planner_networking.Plan#setDefaultStrings()
	 */
	protected void setDefaultStrings()
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
	
	/**
	 * Takes a Node parent and returns a boolean
	 * true if added
	 * @param parent parent of node to be added
	 * @return boolean true if added
	 */
	public boolean addNode(Node parent) throws RemoteException,IllegalArgumentException
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
	 * true if removed
	 * @param nodeRemove node to be removed
	 * @return boolean true if removed
	 * 
	 */
	public boolean removeNode(Node nodeRemove) throws IllegalArgumentException
	{
		if (nodeRemove.getName() == this.root.getName()
				|| nodeRemove.getParent().getChildren().size()==1 || nodeRemove==null)
		{
		
			throw new IllegalArgumentException("Cannot remove this node");
		
	    }
		else
		{
			nodeRemove.getParent().removeChild(nodeRemove);
			nodeRemove.setParent(null);
			return true;

		}
	}
}
