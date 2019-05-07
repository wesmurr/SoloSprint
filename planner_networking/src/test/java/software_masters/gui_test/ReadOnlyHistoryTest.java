package software_masters.gui_test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

class ReadOnlyHistoryTest extends ReadOnlyTest {
	private final String toggleEdits = "#editsToggle";
	
	/**
	 * Verifies the program toggles between views well
	 */
	@Test
	public void toggleViewsTest() {
		getToView("2020 Read Only");
		clickOn(this.toggleEdits);
		find("HideEdits");
		assertThrows(NoSuchElementException.class, () -> find("ShowEdits"));
		clickOn(this.toggleEdits);
		find("ShowEdits");
		assertThrows(NoSuchElementException.class, () -> find("HideEdits"));
		defaultItemsTest();
	}
}
