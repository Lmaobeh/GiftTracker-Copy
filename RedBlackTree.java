// --== CS400 File Header Information ==--
// Name: <Michael Brudos>
// Email: <mbrudos@wisc.edu>
// Team: <NE>
// TA: <Daniel>
// Lecturer: <Gary>
// Notes to Grader: <optional extra notes>
import java.util.LinkedList;

/**
 * RedBlackTree implementation with a Node inner class for representing the nodes within a
 * RedBlackTree. You can use this class' insert method to build a RedBlackTree, and its toString
 * method to display the level order (breadth first) traversal of values in that tree.
 */
public class RedBlackTree<T extends Comparable<T>> implements java.io.Serializable {
  private static final long serialVersionUID = 43;
  /**
   * This class represents a node holding a single value within a binary tree the parent, left, and
   * right child references are always be maintained.
   */
  protected static class Node<T> implements java.io.Serializable {
    private static final long serialVersionUID = 44;
    public T data;
    public Node<T> parent; // null for root node
    public Node<T> leftChild;
    public Node<T> rightChild;

    public Node(T data) {
      this.data = data;
    }

    public boolean isBlack = false;

    /**
     * @return true when this node has a parent and is the left child of that parent, otherwise
     *         return false
     */
    public boolean isLeftChild() {
      return parent != null && parent.leftChild == this;
    }

    /**
     * This method performs a level order traversal of the tree rooted at the current node. The
     * string representations of each data value within this tree are assembled into a comma
     * separated string within brackets (similar to many implementations of java.util.Collection).
     * 
     * @return string containing the values of this tree in level order
     */
    @Override
    public String toString() { // display subtree in order traversal
      String output = "[";
      LinkedList<Node<T>> q = new LinkedList<>();
      q.add(this);
      while (!q.isEmpty()) {
        Node<T> next = q.removeFirst();
        if (next.leftChild != null)
          q.add(next.leftChild);
        if (next.rightChild != null)
          q.add(next.rightChild);
        output += next.data.toString();
        if (!q.isEmpty())
          output += ", ";
      }

      return output + "]";
    }
  }

  protected Node<T> root; // reference to root node of tree, null when empty

  /**
   * Performs a naive insertion into a binary search tree: adding the input data value to a new node
   * in a leaf position within the tree. After this insertion, no attempt is made to restructure or
   * balance the tree. This tree will not hold null references, nor duplicate data values.
   * 
   * @param data to be added into this binary search tree
   * @throws NullPointerException     when the provided data argument is null
   * @throws IllegalArgumentException when the tree already contains data
   */
  public void insert(T data) throws NullPointerException, IllegalArgumentException {
    // null references cannot be stored within this tree
    if (data == null)
      throw new NullPointerException("This RedBlackTree cannot store null references.");

    Node<T> newNode = new Node<>(data);
    if (root == null) {
      root = newNode;
    } // add first node to an empty tree
    else
      insertHelper(newNode, root); // recursively insert into subtree
    root.isBlack = true;
  }

  /**
   * Recursive helper method to find the subtree with a null reference in the position that the
   * newNode should be inserted, and then extend this tree by the newNode in that position.
   * 
   * @param newNode is the new node that is being added to this tree
   * @param subtree is the reference to a node within this tree which the newNode should be inserted
   *                as a descenedent beneath
   * @throws IllegalArgumentException when the newNode and subtree contain equal data references (as
   *                                  defined by Comparable.compareTo())
   */
  private void insertHelper(Node<T> newNode, Node<T> subtree) {
    int compare = newNode.data.compareTo(subtree.data);
    // do not allow duplicate values to be stored within this tree
    if (compare == 0)
      throw new IllegalArgumentException("This RedBlackTree already contains that value.");

    // store newNode within left subtree of subtree
    else if (compare < 0) {
      if (subtree.leftChild == null) { // left subtree empty, add here
        subtree.leftChild = newNode;
        newNode.parent = subtree;
        enforceRBTreePropertiesAfterInsert(newNode); // new addition
        // otherwise continue recursive search for location to insert
      } else
        insertHelper(newNode, subtree.leftChild);
    }

    // store newNode within the right subtree of subtree
    else {
      if (subtree.rightChild == null) { // right subtree empty, add here
        subtree.rightChild = newNode;
        newNode.parent = subtree;
        enforceRBTreePropertiesAfterInsert(newNode); // new addition
        // otherwise continue recursive search for location to insert
      } else
        insertHelper(newNode, subtree.rightChild);
    }
  }

  /**
   * This method performs a level order traversal of the tree. The string representations of each
   * data value within this tree are assembled into a comma separated string within brackets
   * (similar to many implementations of java.util.Collection, like java.util.ArrayList, LinkedList,
   * etc).
   * 
   * @return string containing the values of this tree in level order
   */
  @Override
  public String toString() {
    return root.toString();
  }

  /**
   * Performs the rotation operation on the provided nodes within this BST. When the provided child
   * is a leftChild of the provided parent, this method will perform a right rotation (sometimes
   * called a left-right rotation). When the provided child is a rightChild of the provided parent,
   * this method will perform a left rotation (sometimes called a right-left rotation). When the
   * provided nodes are not related in one of these ways, this method will throw an
   * IllegalArgumentException.
   * 
   * @param child  is the node being rotated from child to parent position (between these two node
   *               arguments)
   * @param parent is the node being rotated from parent to child position (between these two node
   *               arguments)
   * @throws IllegalArgumentException when the provided child and parent node references are not
   *                                  initially (pre-rotation) related that way
   */
  private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
    // TODO: Implement this method.
    if (child.parent == null) {
      throw new IllegalArgumentException("Child is not related to parent");
    }
    // Left child
    else if (child.isLeftChild()) {
      // change parent
      Node<T> parentOfParent;
      if (root == parent) {
        root = child;
        parentOfParent = null;
      } else {
        parentOfParent = parent.parent;
        if (parentOfParent != null) {
          if (parent.isLeftChild()) {
            parentOfParent.leftChild = child;
          } else {
            parentOfParent.rightChild = child;
          }
        }
      }
      parent.parent = child;
      parent.leftChild = child.rightChild;
      child.rightChild = parent;
      child.parent = parentOfParent;
    }
    // Child is a right child
    else {
      Node<T> parentOfParent;
      if (root == parent) {
        root = child;
        parentOfParent = null;
      } else {
        parentOfParent = parent.parent;
        if (parentOfParent != null) {
          if (parent.isLeftChild()) {
            parentOfParent.leftChild = child;
          } else {
            parentOfParent.rightChild = child;
          }
        }
      }
      parent.parent = child;
      parent.rightChild = child.leftChild;
      child.leftChild = parent;
      child.parent = parentOfParent;
    }

  }

  /**
   * This method is used to ensure that insertions into the RBT conform to the three rules that
   * govern RBTs To do this, the method gathers relevant information from the surrounding subtree in
   * order to determine whether the insertion falls under a Case 1, Case 2, or Case 3, Insertion.
   * Once the method determines the Case, appropriate steps are taken to correct any violations.
   * 
   * @param n - node recently inserted into the tree
   */
  private void enforceRBTreePropertiesAfterInsert(Node<T> n) {
    boolean insertedNodeIsLeftChild;
    // Check if the provided node is the root of the tree, in which no action is needed
    if (n.parent != null) {
      // Provided node is not the root. Cool. Now create useful variables for fixing issues later
      insertedNodeIsLeftChild = n.isLeftChild();
      Node<T> parent = n.parent;
      // Checks if the parent of given node is a first generation child of root, if the parent is
      // black, no necessary changes are required when adding a red node
      if (n.parent.parent != null && !parent.isBlack) {
        // Node is a first generation child of root and parent is red (Violation of RBT)
        // Gather references and useful Case selectors
        Node<T> grandParent = n.parent.parent;
        Node<T> parentSibling;
        boolean parentNodeIsLeftChild = parent.isLeftChild(); // was grandParent
        boolean parentSiblingBlack;
        // Determine relationship of the parent's sibling for use in later comparison
        if (parentNodeIsLeftChild == true && grandParent.rightChild != null) {
          parentSibling = grandParent.rightChild;
          parentSiblingBlack = parentSibling.isBlack;
        } else if (parentNodeIsLeftChild == true && grandParent.rightChild == null) {
          parentSibling = null;
          parentSiblingBlack = true;
        }
        // Right child and grandparent has a left child
        else if (parentNodeIsLeftChild == false && grandParent.leftChild != null) {
          parentSibling = grandParent.leftChild;
          parentSiblingBlack = parentSibling.isBlack;
        }
        // parentNodeIsLeftChild == false && grandParent.leftChild == null
        else {
          parentSibling = null;
          parentSiblingBlack = true;
        }
        // Check for Case 1 - If child sibling is red
        if (parentSiblingBlack == false) {
          // Private helper method to change row color to black
          makeLevelBlack(n);
          // Change color of grandParent
          grandParent.isBlack = false;
          // recur up tree - only if the parent of grandParent is also red (Violation)
          if (grandParent.parent != null && !grandParent.parent.isBlack) {
            enforceRBTreePropertiesAfterInsert(grandParent);
          }
        }
        // Case 3 - parentSibling is black and is same side
        else if (parentSiblingBlack && insertedNodeIsLeftChild != parent.isLeftChild()) { // changed
          // != ->
          // ==
          // fixed
          // --------------
          // parentSibling==
          // to
          // parent!=
          
          // Make a Case 2
          rotate(n, parent);
          // Case 2 - modified - Black parentSibling w/ opposite side
          rotate(n, grandParent);
          grandParent.isBlack = false;
          n.isBlack = true;
        }
        // Case 2 - Black parentSibling w/ opposite side
        else {
          rotate(parent, grandParent);
          grandParent.isBlack = false;
          parent.isBlack = true;
        }
      }
    }

  }

  /**
   * This is a private helper method for the enforceRBTreePropertiesAfterInsert method above. This
   * method changes the row above the provided node to black. This method determines the depth of
   * the row to change
   * 
   * @param n - child of a parent in the row to change to black
   */
  private void makeLevelBlack(Node<T> n) {
    Node<T> current = n;
    int count = 0;
    // Count to the root
    while (current.parent != null) {
      count++;
      current = current.parent;
    }
    // Adjust for off by 1
    count--;
    // Recurs from top to row elements to change them to black
    makeLevelBlack(current, count);
  }

  private void makeLevelBlack(Node<T> n, int levelsToGo) {
    // Check if the correct row has been achieved
    if (levelsToGo == 0) {
      n.isBlack = true;
    } else {
      // Recur further if the children of current node exist
      levelsToGo--;
      // Left Child
      if (n.leftChild != null) {
        makeLevelBlack(n.leftChild, levelsToGo);
      }
      // Right Child
      if (n.rightChild != null) {
        makeLevelBlack(n.rightChild, levelsToGo);
      }
    }

  }
  // public static void main(String[] args) {
  // RedBlackTree<Integer> tree = new RedBlackTree<>();
  //// tree.insert(23);
  //// System.out.println(tree);
  //// tree.insert(7);
  //// System.out.println(tree);
  //// tree.insert(41);
  //// System.out.println(tree);
  //// tree.insert(37);
  //// System.out.println(tree);
  //
  //// tree.insert(45);
  //// tree.insert(26);
  //// tree.insert(72);
  //// tree.root.rightChild.isBlack = true;
  //// tree.insert(18);
  //// System.out.println(tree);
  // }
}
