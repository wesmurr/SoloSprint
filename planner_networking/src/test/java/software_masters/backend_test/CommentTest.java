package software_masters.backend_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import businessPlannerApp.backend.Comment;

class CommentTest {

	private Comment comment = null;

	/**
	 * Creates a default comment object.
	 */
	@BeforeEach
	public void setupBeforeClass() { this.comment = new Comment("content", "user"); }

	/**
	 * Test getters and setters for comment content.
	 */
	@Test
	void testGetSetContent() {
		assertEquals("content", this.comment.getContent());
		this.comment.setContent("contentEdit");
		assertEquals("contentEdit", this.comment.getContent());
	}

	/**
	 * Test getter and setters for the username associated with a comment.
	 */
	@Test
	void testGetSetUsername() {
		assertEquals("user", this.comment.getUsername());
		this.comment.setUsername("userEdit");
		assertEquals("userEdit", this.comment.getUsername());
	}

	/**
	 * Tests the getters and setters for comment resolved boolean.
	 */
	@Test
	void testIsSetResolved() {
		assertFalse(this.comment.isResolved());
		this.comment.setResolved(true);
		assertTrue(this.comment.isResolved());
	}

}
