package businessPlannerApp.backend;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Courtney and Jack
 * @author lee and wesley
 */

public class PlanSection implements Serializable {
	private static final long serialVersionUID = 5908372020728915437L;
	private ArrayList<PlanSection> children = new ArrayList<>();
	private LinkedList<Comment> comments = new LinkedList<>();
	private String data;
	private String name;
	private PlanSection parent;

	// empty constructor for XML
	public PlanSection() throws RemoteException { this(null, "blank", "empty", null); }

	/**
	 * @param parent parent plan section
	 * @param name   name of section
	 * @param data   data for section
	 * @param child  list of section children
	 */
	public PlanSection(PlanSection parent, String name, String data, ArrayList<PlanSection> child)
			throws RemoteException {
		if (name == null) name = "";
		if (data == null) data = "";
		this.name = name;
		this.parent = parent;
		this.data = data;
	}

	/**
	 * Takes a PlanSection child and adds it to children list
	 *
	 * @param child child to be added to this node
	 */
	public void addChild(PlanSection child) { this.children.add(child); }

	/**
	 * Takes a comment object and pushes it on stack.
	 *
	 * @param comment comment to be added to this plan section
	 */
	public void addComment(Comment comment) { this.comments.push(comment); }

	/**
	 * Returns a list of children PlanSections
	 *
	 * @return ArrayList list of children
	 */
	public ArrayList<PlanSection> getChildren() { return this.children; }

	/**
	 * Returns the comments associated with this section
	 *
	 * @return comments
	 */
	public LinkedList<Comment> getComments() { return this.comments; }

	/**
	 * Returns the content for this section
	 *
	 * @return String data
	 */
	public String getData() { return this.data; }

	/**
	 * Returns a String name of this section
	 *
	 * @return String name
	 */
	public String getName() { return this.name; }

	/**
	 * Returns the parent for this section
	 *
	 * @return PlanSection parent
	 */
	public PlanSection getParent() { return this.parent; }

	/**
	 * Returns the unresolved comments associated with this section
	 *
	 * @return comments
	 */
	public List<Comment> getUnresolvedComments() {
		return this.comments.stream().filter(comment -> !comment.isResolved()).collect(Collectors.toList());
	}

	/**
	 * Removes a PlanSection from list of children
	 *
	 * @param child child to be removed from this section
	 */
	public void removeChild(PlanSection child) { this.children.remove(child); }

	/**
	 * Removes a Comment from this section
	 *
	 * @param comment comment to be removed from this section
	 */
	public void removeComment(Comment comment) { this.comments.remove(comment); }

	/**
	 * @param children the children to set
	 */
	public void setChildren(ArrayList<PlanSection> children) { this.children = children; }

	/**
	 * @param comments the comments to set
	 */
	public void setComments(LinkedList<Comment> comments) { this.comments = comments; }

	/**
	 * Takes a string to set the section's data
	 *
	 * @param data data to set as data of node
	 */
	public void setData(String data) { this.data = data; }

	/**
	 * Sets name of the section
	 *
	 * @param name name to set as name of node
	 */
	public void setName(String name) { this.name = name; }

	/**
	 * Takes a section and sets the current section's parent to that section
	 *
	 * @param parent parent to set as parent of section
	 */
	public void setParent(PlanSection parent) { this.parent = parent; }

	/**
	 * Sets resolved boolean for a comment to indicate if it is relevant any longer.
	 * If resolved the comment will not be displayed in view.
	 *
	 * @param comment comment to hide from view
	 */
	public void setResolved(Comment comment) { comment.setResolved(true); }

	/*
	 * (non-Javadoc) For testing only.
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean testEquals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (this.getClass() != obj.getClass()) return false;
		final PlanSection other = (PlanSection) obj;
		if (this.children == null) if (other.children != null) return false;
		for (int i = 0; i < this.children.size(); i++)
			if (!this.children.get(i).testEquals(other.children.get(i))) return false;
		if (this.data == null) {
			if (other.data != null) return false;
		} else if (!this.data.equals(other.data)) return false;
		if (this.name == null) {
			if (other.name != null) return false;
		} else if (!this.name.equals(other.name)) return false;
		if (this.parent == null) if (other.parent != null) return false;
		return true;
	}

	@Override
	public String toString() { return this.name; }

}
