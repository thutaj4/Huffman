import java.util.LinkedList;
import java.util.Stack;

public class HuffmanTree {
	// DO NOT include the frequency or priority in the tree
	private class Node {
		private Node left;
		private char data;
		private Node right;
		private Node parent;

		private Node(Node L, char d, Node R, Node P) {
			left = L;
			data = d;
			right = R;
			parent = P;
		}
	}

	private Node root;
	private Node current; // this value is changed by the move methods
	int numchars;
	char noleaf;

	public HuffmanTree() {
		root = null;
		current = null;
		numchars = 0;
	}

	public HuffmanTree(char d) {
		// makes a single node tree
		root = new Node(null, d, null, null);
		current = root;
		numchars = 1;
	}

	public HuffmanTree(String t, char nonLeaf) {
		// nonLeaf is the char value of the data in the non-leaf nodes
		// in classs we used (char) 128 for the non-leaf value

		Stack<Node> s = new Stack<>();
		char[] characters = t.toCharArray();
		for (int i = 0; i < characters.length; i++) {
			if (characters[i] == nonLeaf && s.size() != 0) {
				Node parent = new Node(null, nonLeaf, null, null);
				parent.right = s.pop();
				parent.left = s.pop();
				parent.right.parent = parent;
				parent.left.parent = parent;
				s.push(parent);
			} else {
				numchars++;
				s.push(new Node(null, characters[i], null, null));
			}
		}

		root = s.pop();
		current = root;
		noleaf = nonLeaf;

	}

	public HuffmanTree(HuffmanTree b1, HuffmanTree b2, char d) {
		// makes a new tree where b1 is the left subtree and b2 is the right subtree
		// d is the data in the root
		root = new Node(b1.root, d, b2.root, null);
		root.right.parent = root;
		root.left.parent = root;
		current = root;
		numchars = leafcount(root);
	}

	private int leafcount(Node r) {
		if (r == null) {
			return 0;
		}
		if (r.left == null && r.right == null) {
			return 1;
		} else {
			return leafcount(r.left) + leafcount(r.right);
		}
	}

	// use the move methods to traverse the tree
	// the move methods change the value of current
	// use these in the decoding process
	public void moveToRoot() {
		// change current to reference the root of the tree
		current = root;
	}

	public void moveToLeft() {
		// PRE: the current node is not a leaf
		// change current to reference the left child of the current node
		current = current.left;
	}

	public void moveToRight() {
		// PRE: the current node is not a leaf
		// change current to reference the right child of the current node
		current = current.right;
	}

	public void moveToParent() {
		// PRE: the current node is not the root
		// change current to reference the parent of the current node
		current = current.parent;
	}

	public boolean atRoot() {
		// returns true if the current node is the root otherwise returns false
		if (current == root)
			return true;
		return false;
	}

	public boolean atLeaf() {
		// returns true if current references a leaf other wise returns false
		if (current.left == null && current.right == null) {
			return true;
		}
		return false;
	}

	public char current() {
		// returns the data value in the node referenced by current
		return current.data;

	}

	public String[] pathsToLeaves() {
		/*
		 * returns an array of strings with all paths from the root to the leaves each
		 * value in the array contains a leaf value followed by a seqeunce of 0s and 1s.
		 * The 0s and 1s represent the path from the root to the node containing the
		 * leaf value.
		 */
		String[] paths = new String[numchars];
		LinkedList<String> a = new LinkedList<>();
		String s = "";
		a = getLeaves(root, a, s);
		for (int i = 0; i < paths.length; i++) {
			paths[i] = a.remove();
		}

		return paths;

	}

	private LinkedList<String> getLeaves(Node r, LinkedList<String> a, String s) {

		if (r == null) {
			return null;
		}

		if (r.left == null && r.right == null) {
			a.add(Character.toString(r.data) + s);
		}

		getLeaves(r.left, a, s + "0");
		getLeaves(r.right, a, s + "1");

		return a;

	}

	public String toString() {
		// returns a string representation of the tree using the postorder format
		// discussed in class
		return createString(root);
	}

	private String createString(Node r) {
		String s = "";
		if (r != null) {
			s += createString(r.left);
			s += createString(r.right);
			s += Character.toString(r.data);
		}
		return s;
	}
}
