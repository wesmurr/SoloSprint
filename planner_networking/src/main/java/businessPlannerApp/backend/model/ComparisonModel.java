package businessPlannerApp.backend.model;

import java.rmi.RemoteException;

import businessPlannerApp.backend.PlanFile;
import businessPlannerApp.backend.PlanSection;
import businessPlannerApp.backend.Server;

public class ComparisonModel extends PlannerModel {

	private static final long serialVersionUID = -7195523651250461327L;
	private PlanSection altCurrNode;
	private PlanFile altCurrPlanFile;
	
	/**
	 * Create a comparison model object from planner model object.
	 * @param model
	 * @throws RemoteException
	 */
	public ComparisonModel(PlannerModel model) throws RemoteException { 
		super.setController(model.getController());
		super.setCookie(model.getCookie());
		super.setCurrNode(model.getCurrNode());
		super.setCurrPlanFile(model.getCurrPlanFile());
		super.setServer(model.getServer());
	}
	
	/**
	 * Create a new comparison model based on the server object.
	 * @param server
	 * @throws RemoteException
	 */
	public ComparisonModel(Server server) throws RemoteException {
		super.setServer(server);
	}
	
	/**
	 * Initialize comparison model.
	 * @throws RemoteException
	 */
	public ComparisonModel() throws RemoteException {
		
	}

	/**
	 * This method updates the comparison model so that comparison display can handle it.
	 * @param plan
	 */
	public void updatePlan(PlanFile plan) {
		this.altCurrPlanFile=plan;
		this.altCurrNode=plan.getPlan().getRoot();
	}
	
	/**
	 * Returns planFile object from the user's department given a year. Throws
	 * exception if that planFile doesn't exist.
	 *
	 * @param year
	 * @return
	 * @throws IllegalArgumentException
	 */
	public void getAltPlan(String year) throws IllegalArgumentException, RemoteException {
		this.altCurrPlanFile = this.getServer().getPlan(year, this.getCookie());
		this.altCurrNode = this.altCurrPlanFile.getPlan().getRoot();
	}
	
	/**
	 * @return the altCurrNode
	 */
	public PlanSection getAltCurrNode() { return altCurrNode; }

	/**
	 * @return the altCurrPlanFile
	 */
	public PlanFile getAltCurrPlanFile() { return altCurrPlanFile; }

	/**
	 * @param altCurrNode the altCurrNode to set
	 */
	public void setAltCurrNode(PlanSection altCurrNode) { this.altCurrNode = altCurrNode; }

	/**
	 * @param altCurrPlanFile the altCurrPlanFile to set
	 */
	public void setAltCurrPlanFile(PlanFile altCurrPlanFile) { this.altCurrPlanFile = altCurrPlanFile; }

}
