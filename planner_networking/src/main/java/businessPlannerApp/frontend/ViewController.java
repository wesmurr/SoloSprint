package businessPlannerApp.frontend;

import businessPlannerApp.Main;
import businessPlannerApp.backend.model.PlannerModel;

public class ViewController {

	protected Main app;
	protected PlannerModel model;

	/**
	 * All controllers need the application and model
	 *
	 * @param app
	 */
	public void setApplication(Main app) {
		this.app = app;
		this.model = this.app.getModel();
	}
}
