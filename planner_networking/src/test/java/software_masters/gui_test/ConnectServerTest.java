package software_masters.gui_test;

import org.junit.jupiter.api.Test;

import javafx.scene.control.TextField;

/**
 * @author software masters
 */
class ConnectServerTest extends GuiTestBase {

	String CONNECTBUTTON_ID = "#connectButton";
	String IPFIELD_ID = "#ipField";
	String IPLABEL_ID = "#ipLabel";
	String PORTFIELD_ID = "#portField";
	String PORTLABEL_ID = "#portLabel";

	String userNameLabel = "#usernameLabel";

	/**
	 * Ensures all labels and text fields have the intended text values
	 */
	public void defaultValueTest() {
		verifyField(this.IPFIELD_ID, "127.0.0.1");
		verifyField(this.PORTFIELD_ID, "1060");
		verify(this.IPLABEL_ID, "IP Address:");
		verify(this.PORTLABEL_ID, "Port:");

	}

	/**
	 * Verifies that login window is not displayed when user enters an invalid port
	 * and IP, by checking that the connection window's IP address label is still
	 * present.
	 */
	public void invalidIpAndPortTest() {
		clickOn(this.PORTFIELD_ID);
		write("INVALID PORT");
		clickOn(this.IPFIELD_ID);
		write("INVALID IP");
		clickOn(this.CONNECTBUTTON_ID);
		checkPopupMsg("cannot connect to server");
		clickOn("OK");
		TextField textfield = (TextField) find(this.PORTFIELD_ID);
		textfield.setText("1060");
		textfield = (TextField) find(this.IPFIELD_ID);
		textfield.setText("127.0.0.1");

	}

	/**
	 * Verifies that login window is not displayed when user enters invalid IP, by
	 * checking that the connection window's IP address label is still present.
	 */
	public void invalidIPTest() {
		clickOn(this.IPFIELD_ID);
		write("INVALID IP");
		clickOn(this.CONNECTBUTTON_ID);
		checkPopupMsg("cannot connect to server");
		clickOn("OK");
		final TextField textfield = (TextField) find(this.IPFIELD_ID);
		textfield.setText("127.0.0.1");

	}

	/**
	 * Verifies that login window is not displayed when user enters invalid port, by
	 * checking that the connection window's IP address label is still present.
	 */
	public void invalidPortTest() {
		clickOn(this.PORTFIELD_ID);
		write("INVALID PORT");
		clickOn(this.CONNECTBUTTON_ID);
		checkPopupMsg("cannot connect to server");
		clickOn("OK");
		final TextField textfield = (TextField) find(this.PORTFIELD_ID);
		textfield.setText("1060");

	}

	/**
	 * Main test running all subtests, ensures they run sequentially
	 */
	@Test
	public void mainTest() {
		defaultValueTest();
		invalidPortTest();
		invalidIPTest();
		invalidIpAndPortTest();
		validConnectTest();

	}

	/**
	 * Tests that login window displayed when user enters valid IP address and port,
	 * by checking that the login wondow's username label is present
	 *
	 * @throws Exception
	 */
	public void validConnectTest() {
		clickOn(this.CONNECTBUTTON_ID);
		verify(this.userNameLabel, "Username");

	}

}
