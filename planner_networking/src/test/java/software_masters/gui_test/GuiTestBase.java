package software_masters.gui_test;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import application.MockMain;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public abstract class GuiTestBase extends ApplicationTest
{
	/**
	 * This the gui interface starting at the login window rather than connect to server.
	 * The client is given the actual server object.
	 */
	@BeforeAll
	public static void setUpBeforeClass()
	{
		try
		{
			ApplicationTest.launch(MockMain.class);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage stage) throws Exception
	{
		stage.show();
	}
	
	/**
	 * This closes the window and clears any action event after a test is executed.
	 * @throws TimeoutException
	 */
	@AfterAll
	public static void afterAllTest() throws TimeoutException
	{
		FxToolkit.hideStage();
		FxToolkit.cleanupStages();
	}
	
	/**
	 * This resets keyboard and mouse events after each sub test.
	 */
	@AfterEach
	public void afterEachTest()
	{
		release(new KeyCode[] {});
		release(new MouseButton[] {});
	}
	
	/**
	 * Helper method for checking popups for the right error message
	 * @param msg
	 */
	public void checkPopupMsg(String msg) {
		verifyThat(msg, LabeledMatchers.hasText(msg));
	}
	
	/**
	 * Helper method for grabbing nodes.
	 * 
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Node> T find(final String query)
	{
		return (T) lookup(query).queryAll().iterator().next();
	}

}
