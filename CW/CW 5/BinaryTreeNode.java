/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  BinaryTreeNode.java
 * Purpose    :  Implement a recursive solution to the BinaryTreeNode class
 * @author    :  Wengel, Sureena, Sumaiyah
 * Date       :  2020-11-10
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

package tree.binary;

public class BinaryTreeNode {
    
    // Fields
    // ---------------------------------------------------------------------------------
    
    // [!] Fields made public for exercise purposes only
    public String data;
    public BinaryTreeNode left, right;
    
    
    // Constructor
    // ---------------------------------------------------------------------------------
    
    /**
     * Creates a new BinaryTreeNode that can be linked to
     * others to form a tree of arbitrary depth
     * @param s The data to store at this tree node
     */
    BinaryTreeNode (String s) {
        this.data = s;
        this.left = null;
        this.right = null;
    }
        
    // Methods (To-do)
    // ---------------------------------------------------------------------------------
    /**
     * Returns the number of levels possessed by the tree rooted at this node, 
     * defined as the number of depths that the tree has at least 1 node in. 
     * E.g.,
     *      A       The tree rooted at A has 3 levels because
     *     / \      it has 3 depths with at least 1 node each
     *    B   C     The tree rooted at B has 2 levels because
     *     \        it has 2 depths with at least 1 node each
     *      D
     * @return The number of levels of the tree rooted at this node
     */
    public int levels () {
        return getLevel(this);
    }
    

	/**
     * Doubles the tree rooted at the node on which this method
     * is called, creating a duplicate of each node, storing the
     * duplicate at the left reference of the original, and then
     * moving any previous left-child from the original to the
     * left child of the duplicate.
     */
    public void doubleTree () {
    	duplicate(this);
    }

/**
 * 
 * @param n
 * @return the level of the node
 */
    private int getLevel(BinaryTreeNode n) {
		if (n == null) {
			return 0;
		} else {

//			Recursive Case:
			getLevel(n.left);
			getLevel(n.right);
			int maxDepth = getDepth(n);
			return maxDepth;
		}
	}
    
/**
 * 
 * @param n
 * @return the maximum depth of the tree
 */
    private int getDepth (BinaryTreeNode n) {
    	if (n == null) {
    		return 0;
    	}
    	
//		Recursive Case:
    	int goLeft = getDepth(n.left);
    	int goRight = getDepth(n.right);
    	
    	if (goLeft > goRight) {
    		return (goLeft + 1);
    	} else {
    		return (goRight + 1);
    	}
    }
    
/**
 *  
 * @param binaryTreeNode
 * @return doubles the tree
 */
    private void duplicate(BinaryTreeNode current) {
    	BinaryTreeNode oldTree;
    	if (current == null) {
			return;
		}
		
//		Recursive Case:
    	duplicate(current.left);
    	duplicate(current.right);
    	
//    	reassigning node pointers
    	oldTree = current.left;
    	current.left = new BinaryTreeNode(current.data);
    	current.left.left = oldTree;
	}
    
    // Methods (Given)
    // ---------------------------------------------------------------------------------
    
    public boolean treequal (BinaryTreeNode other) {
        return treequal(this, other);
    }
    
    /**
     * Given two Binary Trees rooted at the provided BinaryTreeNodes
     * n1 and n2, determines whether or not the two trees are
     * equivalent (i.e., have the same nodes with the same values in
     * the same locations in the tree).
     * @param n1 The root of tree 1
     * @param n2 The root of tree 2
     * @return Whether or not n1 and n2 represent the same tree
     */
    private static boolean treequal (BinaryTreeNode n1, BinaryTreeNode n2) {
        // Base case: either null, which will only return true if they both are
        if (n1 == null || n2 == null) {
            return n1 == n2;
        }
        
        // Return true only if the data agrees and both
        // subtrees are equivalent as well (preorder traversal)
        return n1.data.equals(n2.data) &&
               treequal(n1.left, n2.left) &&
               treequal(n1.right, n2.right);
    }
    
}
