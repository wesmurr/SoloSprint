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

class ConnectServerTest extends ApplicationTest {

	@Before
	public void setUpClass() throws Exception {
		
		System.out.println("Got here!");
		ApplicationTest.launch(Main.class);
	}
	
	
	@Override
	public void start(Stage stage) throws Exception {
		
		stage.show();
	}
	
	@After
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
	public void defaultValueTest()
	{	
		System.out.println("Test, here!");
		verifyThat(IPFIELD_ID, (TextField field) -> {return field.getText().contains("IP Address:");});
	}

}
