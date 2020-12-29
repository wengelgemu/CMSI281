package main.textfill;

import java.util.*;

/**
 * A ternary-search-tree implementation of a text-autocompletion
 * trie, a simplified version of some autocomplete software.
 * @author WENGEL_GEMU
 */
public class TernaryTreeTextFiller implements TextFiller {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------
    private TTNode root;
    private int size;
    
    // -----------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------
    public TernaryTreeTextFiller () {
        this.root = null;
        this.size = 0;
    }
    
    
    // -----------------------------------------------------------
    // Methods
    // -----------------------------------------------------------
    
    /** 
     * Returns the number of stored terms inside of the TextFiller 
     * synonymous with asking for the number of wordEnds at TTNodes.
     * @return Returns the number of stored terms
     */
    public int size () {
        return this.size;
    }
    
    /** 
     * Returns true if the TextFiller has no search terms stored, false otherwise.
     * @return true if TextFiller is empty
     */
    public boolean empty () {
        return root == null;
    }
    
    /**
     * Adds the given search term toAdd to the TextFiller by the method specified in the Ternary Tree section above. 
     * If the desired String toAdd already exists inside of the TextFiller, do nothing.
     * @param String toAdd
     */
    public void add (String toAdd) {
        toAdd = normalizeTerm(toAdd);
        root = addsHelper(root, toAdd);
    
    }


	/**
	 * Returns true if the given String exists within the TextFiller, false otherwise.
     * @param String query
     * @return true if the given String is in the TextFiller
     */
    public boolean contains (String query) {
        query = normalizeTerm(query);
        return containsHelper(root, query);
    }


	/**
	 * Returns the first search term contained in the TextFiller with query as a prefix 
     * @param String query
     * @return first search term with query as a prefix, or null
     */
    public String textFill (String query) {
        query = normalizeTerm(query);
        TTNode check = textFillHelper(root, query);
        String result = query;
        // no prefix
        if (check == null) {
            return null;
        }
        
        if (check.wordEnd) {
            return query;
        }
        
        while (check != null) {
            check = check.mid;
            result = result + check.letter;
            if (check.wordEnd) {
                break;
            }
        }
        return result;
    }

	/** 
     * Returns an ArrayList of Strings consisting of the alphabetically sorted search terms.
     * @return ArrayList of alphabetically sorted search terms
     */
    public List<String> getSortedList () {
        ArrayList<String> sortedList = new ArrayList<String>();
        String word = "";
        sortedHelper(root, word, sortedList);
        return sortedList;
    }
    // -----------------------------------------------------------
    // Helper Methods
    // -----------------------------------------------------------
    
    /**
     * Normalizes a term to either add or search for in the tree,
     * since we do not want to allow the addition of either null or
     * empty strings within, including empty spaces at the beginning
     * or end of the string (spaces in the middle are fine, as they
     * allow our tree to also store multi-word phrases).
     * @param s The string to sanitize
     * @return The sanitized version of s
     */
    private String normalizeTerm (String s) {
        // Edge case handling: empty Strings illegal
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        return s.trim().toLowerCase();
    }
    
    /**
     * Given two characters, c1 and c2, determines whether c1 is
     * alphabetically less than, greater than, or equal to c2
     * @param c1 The first character
     * @param c2 The second character
     * @return
     *   - some int less than 0 if c1 is alphabetically less than c2
     *   - 0 if c1 is equal to c2
     *   - some int greater than 0 if c1 is alphabetically greater than c2
     */
    private int compareChars (char c1, char c2) {
        return Character.toLowerCase(c1) - Character.toLowerCase(c2);
    }
    
    // [!] Add your own helper methods here!
    /**
     * private helper method to add node to the tree according to tree structure
     * @param node
     * @param term
     * @return node with new term added in appropriate place
     */
    private TTNode addsHelper(TTNode node, String toAdd) {
        // Base case: empty tree, create node
        if (node == null) {
            TTNode newNode = new TTNode(toAdd.charAt(0), toAdd.length() == 1);
            TTNode traversal = newNode;
            for (int i = 1; i < toAdd.length(); i++) {
                TTNode current = new TTNode(toAdd.charAt(i), i == (toAdd.length() - 1));
                traversal.mid = current;
                traversal = current;
            }
            size++;
            return newNode;
        }
        
        // Recursive case: not empty
        int compare = compareChars(node.letter, toAdd.charAt(0)); 
        // Case 1: c1 is the same as c2
        if (compare == 0) {
            if (toAdd.length() == 1) {
                node.wordEnd = true;
                return node;
                }
            node.mid = addsHelper(node.mid, toAdd.substring(1));
            return node; 
            }
        // Case 2: c1 alphabetically follows c2
        else if (compare > 0) {
            node.left = addsHelper(node.left, toAdd);
            return node;
        }
        // Case 3: c1 alphabetically precedes c2
        else {
            node.right = addsHelper(node.right, toAdd);
            return node;
            }
        }
    

    /**
     * private helper method to check if given String exists within the TextFiller
     * @param node
     * @param query
     * @return true if exists
     */
    private boolean containsHelper(TTNode node, String query) {
        // Base case: end of the tree
        if (node == null || query.length() == 0) {
            return false;
        }
        int compare = compareChars(node.letter, query.charAt(0));
        if (compare == 0) {
            if (node.wordEnd && query.length() == 1) {
                return true;
            }
            return containsHelper(node.mid, query.substring(1));
        }
        
        if (compare > 0) {
            return containsHelper(node.left, query);
        }
        
        if (compare < 0) {
            return containsHelper(node.right, query);
            }
        
        return false;
        }
    
    /**
     * private helper method to trace nodes in a prefix
     * @param node
     * @param query
     * @return last node of prefix
     */
    private TTNode textFillHelper(TTNode node, String query) {
        // Base case: end of the tree
        if (node == null || query.length() == 0) {
            return node;
        }
        
        // Recursive case: not empty
        int compare = compareChars(node.letter, query.charAt(0)); 
        
        // Case 1: c1 is the same as c2
        if (compare == 0) { 		
            if (query.length() == 1) {
                return node;
                }
            return textFillHelper(node.mid, query.substring(1));
        } 
        // Case 2: c1 alphabetically follows c2
        else if (compare > 0) {
            
            return textFillHelper(node.left, query);
        } 
        // Case 3: c1 alphabetically precedes c2
        else {
            return textFillHelper(node.right, query);
       }
    }
    
    /**
     * private helper method to alphabetically sort TextFiller search terms into an ArrayList
     * @param node
     * @param word
     * @param sortedSearchTerms
     */
    private void sortedHelper(TTNode node, String word, ArrayList<String> sortedSearchTerms) {
        // Base case:
        if (node == null) {
            return;
        }
        
        // Case Left:
        sortedHelper(node.left, word, sortedSearchTerms);
        
        // Case Middle:
        String insert = word + node.letter;
        if (node.wordEnd) {
            sortedSearchTerms.add(insert);
        }
        sortedHelper(node.mid, insert, sortedSearchTerms);

        // Case Right:
        sortedHelper(node.right, word, sortedSearchTerms);
    }
    
    // -----------------------------------------------------------
    // Extra Credit Methods
    // -----------------------------------------------------------
    
    public void add (String toAdd, int priority) {
        throw new UnsupportedOperationException();
    }
    
    public String textFillPremium (String query) {
        throw new UnsupportedOperationException();
    }
    
    
    // -----------------------------------------------------------
    // TTNode Internal Storage
    // -----------------------------------------------------------
    
    /**
     * Internal storage of autocompleter search terms
     * as represented using a Ternary Tree with TTNodes
     */
    private class TTNode {
        
        boolean wordEnd;
        char letter;
        TTNode left, mid, right;
        
        /**
         * Constructs a new TTNode containing the given character
         * and whether or not it represents a word-end, which can
         * then be added to the existing tree.
         * @param c Letter to store at this node
         * @param w Whether or not this is a word-end
         */
        TTNode (char c, boolean w) {
            letter  = c;
            wordEnd = w;
        }
        
    }
    
}
