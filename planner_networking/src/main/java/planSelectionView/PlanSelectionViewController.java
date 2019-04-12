package planSelectionView;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import software_masters.model.PlannerModel;

/**
 * @author lee.kendall
 *
 *MVC Controller for the plan selection view
 *
 */
public class PlanSelectionViewController
{

    @FXML
    private ListView<?> planTemplateList;

    @FXML
    private ListView<?> departmentPlanList;
    
    private Main app;

    /**
     * Resets client's cookie and planFile, displays the login window
     * 
     * @param event Action
     */
    @FXML
    public void Logout(ActionEvent event) 
    {
    	app.getModel().setCookie(null);
    	app.getModel().setCurrNode(null);
    	app.getModel().setCurrPlanFile(null);
    	app.showLoginView();
    }
    
    /**
     * Allows controller to access showPlanEditView, showPlanReadOnlyView,
     *  and showLoginView methods in the main application
     * @param app main application
     */
    public void setApplication(Main app)
    {
    	this.app = app;
    }
    


}