package software_masters.backend_test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CentreTest.class, IowaStateTest.class, LocalTestSuite.class, NodeTest.class, RemoteClientTest.class,
		ServerTest.class, VMOSATest.class })
public class RemoteTestSuite {

}
