package businessPlannerApp.frontend.ComparisonViews;

import businessPlannerApp.Main;

public class CompareEditsController extends CompareController {
	
	@Override
	public void setApplication(Main app) {
		super.setApplication(app);
		populateAltPlan();
	}

	@Override
	public void populateAltPlan() {
		this.setAltTreeView();
		this.populateFields();
	}

}
