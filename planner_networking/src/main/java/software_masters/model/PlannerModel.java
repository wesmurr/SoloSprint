package software_masters.model;

import software_masters.planner_networking.Client;
import software_masters.planner_networking.Server;

public class PlannerModel extends Client
{

	/**
	 * 
	 */
	public PlannerModel()
	{
		super();
	}

	/**
	 * @param server
	 */
	public PlannerModel(Server server)
	{
		super(server);
	}

}
