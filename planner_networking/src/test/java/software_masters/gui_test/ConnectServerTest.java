package software_masters.gui_test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import application.Main;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

class ConnectServerTest extends ApplicationTest {

	@Before
	public void setUpClass() throws Exception {
		
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
	
	@Test
	void test() { fail("Not yet implemented"); }

}
