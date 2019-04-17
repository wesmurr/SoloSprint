package software_masters.gui_test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeoutException;
import org.junit.Before;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import application.Main;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import static org.testfx.api.FxAssert.verifyThat;
import javafx.scene.control.Label;

class ConnectServerTest extends ApplicationTest {

//	@Before
//	public void setUpClass() throws Exception {
//		
//		System.out.println("Got here!");
//		
//	}
	
	
	@Override
	public void start(Stage stage) throws Exception {
		
		stage.show();
	}
	
	public void afterEachTest() throws TimeoutException {
		FxToolkit.hideStage();
		release(new KeyCode[] {});
		release(new MouseButton[] {});
	}
	
	String IPLABEL_ID = "#ipLabel";
	String PORTLABEL_ID ="#portLabel";
	String IPFIELD_ID = "#ipField";
	String PORTFIELD_ID ="#portField";
	String CONNECTBUTTON_ID = "#connectButton";
	
	@Test
	public void defaultValueTest() throws Exception
	{	
		setUp();
		System.out.println("Test, here!");
		verifyThat(IPFIELD_ID, (TextField field) -> {return field.getText().equals("127.0.0.1");});
		verifyThat(PORTFIELD_ID, (TextField field) -> {return field.getText().equals("1060");});
		verifyThat(IPLABEL_ID, (Label label)-> {return label.getText().equals("IP Address:");});
		verifyThat(PORTLABEL_ID, (Label label)-> {return label.getText().equals("Port:");});
		afterEachTest();
		
	}
	
	String userNameLabel = "#usernameLabel";
	@Test
	public void connectTest() throws Exception {
		setUp();
		clickOn(CONNECTBUTTON_ID);
		verifyThat(userNameLabel, (Label label)-> {return label.getText().equals("IP Address:");});
		
		
		
	}
	
	private void setUp() throws Exception {
		
		ApplicationTest.launch(Main.class);
	}
	


}
