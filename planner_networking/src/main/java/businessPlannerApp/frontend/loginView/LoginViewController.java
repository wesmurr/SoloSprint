package businessPlannerApp.frontend.loginView;

import java.rmi.RemoteException;

import businessPlannerApp.frontend.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginViewController extends ViewController {
	@FXML
	PasswordField passwordField;
	@FXML
	TextField usernameField;

	@FXML
	void login(ActionEvent event) {
		try {
			this.app.getModel().login(this.usernameField.getText(), this.passwordField.getText());
			this.app.showPlanSelectionView();
		} catch (final IllegalArgumentException e) {
			this.app.sendError("invalid credentials");
		} catch (final RemoteException e) {
			this.app.sendError("cannot connect to server");
		}
	}

}
