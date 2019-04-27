package businessPlannerApp.frontend.serverConnectionView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import businessPlannerApp.frontend.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ServerConnectionViewController extends ViewController {

	@FXML
	TextField ipAddress;
	@FXML
	TextField port;

	@FXML
	public void connect() {
		try {
			this.app.getModel().connectToServer(this.ipAddress.getText(), Integer.parseInt(this.port.getText()));
			this.app.showLoginView();
		} catch (final NumberFormatException e) {
			this.app.sendError("cannot connect to server");
		} catch (final RemoteException e) {
			this.app.sendError("cannot connect to server");
		} catch (final NotBoundException e) {
			this.app.sendError("cannot connect to server");
		}
	}

}
