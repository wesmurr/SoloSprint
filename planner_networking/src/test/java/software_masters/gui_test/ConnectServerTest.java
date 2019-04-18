package software_masters.gui_test;

import static org.junit.jupiter.api.Assertions.*;

import java.rmi.RemoteException;
import java.util.concurrent.TimeoutException;
import org.junit.Before;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import application.Main;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import software_masters.planner_networking.ServerImplementation;

import static org.testfx.api.FxAssert.verifyThat;

import javafx.scene.Node;
import javafx.scene.control.Label;

class ConnectServerTest extends ApplicationTest
{

	@Override
	public void start(Stage stage) throws Exception
	{

		stage.show();
	}

	private void afterEachTest()
	{
		try
		{
			FxToolkit.hideStage();
		}
		catch (TimeoutException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		release(new KeyCode[] {});
		release(new MouseButton[] {});
	}

	String IPLABEL_ID = "#ipLabel";
	String PORTLABEL_ID = "#portLabel";
	String IPFIELD_ID = "#ipField";
	String PORTFIELD_ID = "#portField";
	String CONNECTBUTTON_ID = "#connectButton";

	/**
	 * Main test running all subtests, ensures they run sequentially
	 */
	@Test
	public void mainTest()
	{
		setUp();
		try
		{
			ServerImplementation.main(null);
		}
		catch (RemoteException e)
		{
			e.printStackTrace();
		}
		defaultValueTest();
		invalidPortTest();
		invalidIPTest();
		invalidIpAndPortTest();
		validConnectTest();
		afterEachTest();

	}

	/**
	 * Helper method for grabbing nodes
	 * 
	 * @param query
	 * @return
	 */
	public <T extends Node> T find(final String query)
	{

		return (T) lookup(query).queryAll().iterator().next();
	}

	/**
	 * Esnures all labels and text fields have the intended text values
	 */
	public void defaultValueTest()
	{
		verifyThat(IPFIELD_ID, (TextField field) ->
		{
			return field.getText().equals("127.0.0.1");
		});
		verifyThat(PORTFIELD_ID, (TextField field) ->
		{
			return field.getText().equals("1060");
		});
		verifyThat(IPLABEL_ID, (Label label) ->
		{
			return label.getText().equals("IP Address:");
		});
		verifyThat(PORTLABEL_ID, (Label label) ->
		{
			return label.getText().equals("Port:");
		});

	}

	String userNameLabel = "#usernameLabel";

	/**
	 * Tests that login window displayed when user enters valid IP address and port,
	 * by checking that the login wondow's username label is present
	 * 
	 * @throws Exception
	 */
	public void validConnectTest()
	{
		clickOn(CONNECTBUTTON_ID);
		verifyThat(userNameLabel, (Label label) ->
		{
			return label.getText().equals("Username");
		});

	}

	/**
	 * Verifies that login window is not displayed when user enters invalid IP, by
	 * checking that the connection window's IP address label is still present.
	 */
	public void invalidIPTest()
	{
		clickOn(IPFIELD_ID);
		write("INVALID IP");
		clickOn(CONNECTBUTTON_ID);
		verifyThat(IPLABEL_ID, (Label label) ->
		{
			return label.getText().equals("IP Address:");
		});
		clickOn("OK");
		TextField textfield = (TextField) find(IPFIELD_ID);
		textfield.setText("127.0.0.1");

	}

	/**
	 * Verifies that login window is not displayed when user enters invalid port, by
	 * checking that the connection window's IP address label is still present.
	 */
	public void invalidPortTest()
	{
		clickOn(PORTFIELD_ID);
		write("INVALID PORT");
		clickOn(CONNECTBUTTON_ID);
		verifyThat(IPLABEL_ID, (Label label) ->
		{
			return label.getText().equals("IP Address:");
		});
		clickOn("OK");
		TextField textfield = (TextField) find(PORTFIELD_ID);
		textfield.setText("1060");

	}

	/**
	 * Verifies that login window is not displayed when user enters an invalid port
	 * and IP, by checking that the connection window's IP address label is still
	 * present.
	 */
	public void invalidIpAndPortTest()
	{
		clickOn(PORTFIELD_ID);
		write("INVALID PORT");
		clickOn(IPFIELD_ID);
		write("INVALID IP");
		clickOn(CONNECTBUTTON_ID);
		verifyThat(IPLABEL_ID, (Label label) ->
		{
			return label.getText().equals("IP Address:");
		});
		clickOn("OK");
		TextField textfield = (TextField) find(PORTFIELD_ID);
		textfield.setText("1060");
		textfield = (TextField) find(IPFIELD_ID);
		textfield.setText("127.0.0.1");

	}

	private void setUp()
	{

		try
		{
			ApplicationTest.launch(Main.class);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
