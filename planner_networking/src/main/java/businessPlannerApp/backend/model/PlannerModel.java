package businessPlannerApp.backend.model;

import businessPlannerApp.backend.Client;
import businessPlannerApp.backend.Server;

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
