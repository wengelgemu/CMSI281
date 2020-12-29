package main.forneymon.arena;

import java.util.Objects;
import main.forneymon.fmtypes.*;


/**
 * Collections of Forneymon ready to fight in the arena!
 */
public class LinkedForneymonagerie implements ForneymonagerieInterface {

    // Fields
    // -----------------------------------------------------------
    private Node sentinel;
    private int size, modCount;
    
    
    // Constructor
    // -----------------------------------------------------------
    public LinkedForneymonagerie () {
        // [!] Leave this constructor as-is, you may not modify!
        this.size = this.modCount = 0;
        this.sentinel = new Node(null);
        this.sentinel.next = this.sentinel;
        this.sentinel.prev = this.sentinel;
    }
    
    
    // Methods
    // -----------------------------------------------------------
    
    /** 
     * Checks if the LinkedForneymonagerie has any Forneymon inside
     * @return boolean true if the LinkedForneymonagerie has no Forneymon inside
     */
    public boolean empty () {
        return this.size == 0;
    }
    
    /**
     * Checks the number of Forneymon in the LinkedList
     * @return the current size of the LinkedForneymonagerie
     */
    public int size () {
    	return this.size;
    }
    
    /**
     * Attempts to add a Node containing a reference to the given Forneymon to the Forneymonagerie's LinkedList
     * @param 
     * @return boolean true if toAdd was newly added to the LinkedForneymonagerie
     */
    public boolean collect (Forneymon toAdd) {
    	return append(toAdd);
    }
    
    
    /**
     * Removes the Forneymon of the given subtype fmType from the LinkedForneymonagerie, 
     * maintaining the relative order of remaining Forneymon in the LinkedLis
     * @param subtype string fmType from the LinkedForneymonagerie
     * @return boolean true if found and removed
     */
    public boolean releaseType (String fmType) {

    	Node existingType = getForneymonType(fmType);
    	if (existingType == null) {
    		return false;
    	}
    	int index = this.getTypeIndex(fmType);
    	Node current = getNodeAtIndex(index);
    	
    	
//    	case 1: releasing head
    	if (current == this.sentinel.next) {
    		this.sentinel.next = current.next;
    	} 
//    	case 2: releasing tail
    	else if (current == this.sentinel.prev) {
    		current.prev.next = sentinel;
    	} 
//    	case 3: releasing value within list
    	else {
    		current.prev.next = current.next;
    		current.next.prev = current.prev;
    	}
    	
    	this.size--;
    	this.modCount++;
    	return false;
    }
    
    /**
     * Returns the Forneymon at the given index in the LinkedList, if valid
     * @param int index
     * @return forneymon at index
     */
    public Forneymon get (int index) {

    	Node current;
		
    	if(!validityCheck(index)) {
    		throw new IllegalArgumentException();
    	}
    	
    	current = getNodeAtIndex(index);
    	
    	return current.fm;
    }
    
    /**
     * Removes and returns the Forneymon at the given index, if valid, and maintains the relative order 
     * @param int index
     * @returns forneymon at given index
     * */
    public Forneymon remove (int index) {

    	Node current = getNodeAtIndex(index);
    	if (index < 0 || index >= size || current == null) {
    		throw new IllegalArgumentException();
    	}
    	
    	
//    	if released, returns true and decreases size
    	if (releaseType(current.fm.getFMType())) {
        	this.size--;
    	}
    	
    	return current.fm;
    }
    
    /**
     * Returns index of a Forneymon with the given fmType in the LinkedList
     * @param String fmType
     * @returns index of forneymon
     * */
    public int getTypeIndex (String fmType) {
    	int index = 0;
    	for(Node n = sentinel.next; n.fm != null; n = n.next) {
    		if (n.fm.getFMType().equals(fmType)) {
    			return index;
    		}
    		index++;
		}
	
//		if index is not found
    	return -1;
    }
    
    /**
     * Returns true if given fmType is found 
     * @param String fmType
     * @returns boolean true if type is found
     * */
    public boolean containsType (String toCheck) {
    	Node existingType = getForneymonType(toCheck);
    	if (existingType != null) {
    		return true;
    	}
        return false;
    }
    
    /**
     * Swaps contents of the calling Forneymonagerie and the other specified. 
     * @param Forneymonagerie other
     */
    public void trade (LinkedForneymonagerie other) {
    	
    	LinkedForneymonagerie fmClone = new LinkedForneymonagerie();
    	
    	fmClone.sentinel = this.sentinel;
    	fmClone.size = this.size;
    	fmClone.modCount = this.modCount;
    	this.sentinel = other.sentinel;
    	this.size = other.size;
    	this.modCount = other.modCount;
    	other.sentinel = fmClone.sentinel;
    	other.size = fmClone.size;
    	other.modCount = fmClone.modCount;
    }
    
    /**
     * Moves the Forneymon of the given fmType from its current position in the LinkedForneymonagerie 
     * to the one specified by the index, shifting any existing Forneymon around the requested index 
     * @param String fmType, int index
     */
    public void rearrange (String fmType, int index) {
    	/* Maintains index range [0, size-1] */
    	if(!validityCheck(index)) {
    		throw new IllegalArgumentException();
    	}
    	
    	int currIndex = this.getTypeIndex(fmType);
    	
//    	reserving values surrounding node
    	Node currentNode = getNodeAtIndex(currIndex);
    	Node oldNode = getNodeAtIndex(index);
    	Node beforeOldNode = oldNode.prev;

//    	reassigning values surrounding node
    	currentNode.next = oldNode;
    	currentNode.prev = beforeOldNode;
    	oldNode.prev = currentNode;
    	beforeOldNode.next = currentNode;
    }
    
    /**
     * Returns a new Iterator on the calling LinkedForneymonagerie that begins on the first Node in the sequence.
     * @return new Iterator on the calling LinkedForneymonagerie
     */
    public LinkedForneymonagerie.Iterator getIterator () {
        if (empty()) {
        	throw new IllegalArgumentException();
        }
        return new Iterator(this);
    }
    
    /**
     * Returns a deep copy of LinkedForneymonagerie, a new LinkedForneymonagerie object but with new instances and it's own collection
     * @return copy of LinkedForneymonagerie
     */
    @Override
    public LinkedForneymonagerie clone () {
    	LinkedForneymonagerie copy = new LinkedForneymonagerie();
    	if(empty()) {
    		return copy;
    	}
    	
//    	adds Forneymon to cloned LinkedForneymonagerie
    	Node current = this.sentinel.next;
    	while (current.fm != null) {
    		copy.collect(current.fm.clone());
    		current = current.next;
    	}
    	return copy;
    }
    
    /**
     * Returns whether or not the given Object other is an equivalent LinkedForneymonagerie to this one
     * It contains equal LinkedForneymonagerie in the same order in the collection as this one
     * @return boolean true if given object and LinkedForneymonagerie are equal
     */
    @Override
    public boolean equals (Object other) {
    	LinkedForneymonagerie otherFm = (LinkedForneymonagerie) other;
    	if (this.size() != otherFm.size()) {
    		return false;
    	}
    	
    	int index = 0;
    	for(Node current = sentinel.next; current.fm != null; current = current.next) {
    		if(!current.fm.equals(otherFm.get(index))) {
    			return false;
    		}
    		index++;
    	}
    	return true;
    }
    
    @Override
    public int hashCode () {
        return Objects.hash(this.sentinel, this.size, this.modCount);
    }
    
    /**
     * @return a String representation 
     */
    @Override
    public String toString () {
        String[] result = new String[size];
        int i = 0;
        for (Node curr = this.sentinel.next; curr != this.sentinel; curr = curr.next, i++) {
            result[i] = curr.fm.toString();
        }
        return "[ " + String.join(", ", result) + " ]";
    }
    
    
    // Private helper methods
    // -----------------------------------------------------------
    
    
    /**
     * Handles insertion of a new forneymon to linked list
     * @param toAdd
     * @return if a Forneymon was added or not
     */
    private boolean append(Forneymon toAdd) {

//    	gets toAdds type
    	String toAddType = toAdd.getFMType();
    	
//    	returns type if found in list, otherwise null
    	Node foundType = getForneymonType(toAddType); 
    	
    	// Case 1 : Adding a Forneymon to the last open index.
    	// list is empty, or type is not there
    	if (foundType == null) {
    		Node newNode = new Node(toAdd);
    		if (size == 0) {
    			sentinel.next = newNode;
    			sentinel.prev = newNode;
    			newNode.prev = sentinel;
    			newNode.next = sentinel;
    		} else {
//    			saving old tail values
    			Node oldTail = sentinel.prev;
    			oldTail.next = newNode;
    			sentinel.prev = newNode;
    			newNode.prev = oldTail;
    			newNode.next = sentinel;
    		}
    		this.size++;
    		this.modCount++;
			return true;
    	}
    	
    	else if (foundType != null) {
    		
    		// Case 2 : Adding a Forneymon that is already stored within will do nothing
    		if (foundType.fm == (toAdd)) {
    			return false;
    		}
        	// Case 3 : Adding a Forneymon of the same subtype as a Forneymon will absorb levels
    		else if (foundType.fm.getFMType().equals(toAdd.getFMType())){
    			foundType.fm.addLevels(toAdd.getLevel());
    			this.modCount++;
    			return false;
    		}
    	}
    	return false;
    }
    
    /**
     * searches for node in linked list
     * @param toAddType
     * @return the found Node
     */
    private Node getForneymonType(String toAddType) {
    	
//    	if nothing is in the list
    	if (this.empty()) {
    		return null;
    	}
    	
    	for (Node curr = this.sentinel.next; curr.fm != null; curr = curr.next) {
    		if(toAddType.equals(curr.fm.getFMType())) {
    			return curr;
    		}
    	}
    	return null;
    }
    
    /**
     * searches for Node at specified index
     * @param index
     * @return the found Node
     */
    private Node getNodeAtIndex(int index) {
    	Node current = this.sentinel.next;
    	while (index > 0) {
    		current = current.next;
    		index--;
    	}
    	return current;
    }
    
    /**
     * Checks for validity of index
     * @param index
     * @return if valid or not
     */
    private boolean validityCheck(int index) {
    	if ((index >= size) || (index < 0)){
    		return false;
    	}
    	return true;
    }

    
    
    // Inner Classes
    // -----------------------------------------------------------
    
    public class Iterator {
        private LinkedForneymonagerie host;
        private Node current;
        private int itModCount;
        
        /**
         * Constructor defines LinkedForneymonagerie host
         * Sets its itModCounter to that of its owner's modCount
         * @param host
         */
        Iterator (LinkedForneymonagerie host) {
        	this.host = host;
        	current = host.sentinel.next;
        	this.itModCount = host.modCount;
        }
        
        /**
         * Checks if this Iterator's itModCount agrees with that of its owner's modCount and LinkedForneymonagerie is not empty
         * @return true if itModCount and modCount agree, LinkedForneymonagerie has at least one element
         */
        public boolean isValid () {
            if (host.empty()) {
            	System.out.println("empty");
            	return false;
            }
            return itModCount == host.modCount;
        }
        
        /**
         * Checks if the Iterator is valid and its current.next is the host's Sentinel node, false otherwise.
         * @return true if the Iterator is valid and its current.next is the host's Sentinel node
         */
        public boolean atEnd () {
        	if (!isValid()) {
        		return false;
        	}
        	return (current.next == host.sentinel);
        }
        
        /**
         * Checks if the Iterator is valid and its current.prev is the host's Sentinel node, false otherwise.
         * @return true if the Iterator is valid and its current.prev is the host's Sentinel node
         */
        public boolean atStart () {
        	if (!isValid()) {
        		return false;
        	}
        	return (current.prev == host.sentinel);
        }
        
        /**
         * Checks Forneymon stored in the Node that the Iterator is currently pointing at (i.e., the fm field of the Node it is referring to).
         * @return a reference to the current Forneymon
         */
        public Forneymon getCurrent () {
        	if (!isValid() || current.fm == null) {
        		throw new IllegalStateException();
        	}
        	return current.fm;
        }

        /**
         * Advances the Iterator's current reference to point to the next Node in the sequence.
         * If this next Node is the Sentinel, continue next a second time
         */
        public void next () {
        	if (!isValid()) {
        		throw new IllegalStateException();
        	}
        	
        	if (atEnd()) {
        		current = current.next.next;
        	} else {
        		current = current.next;
        	}    	
        }
        
        /**
         * Advances the Iterator's current reference to point to the previous Node in the sequence.
         * If this previous Node is the Sentinel, continue previously a second time
         */
        public void prev () {
        	if (!isValid()) {
        		throw new IllegalStateException();
        	}
//        	if previous Node is the Sentinel
        	if (atStart()) {
        		current = current.prev.prev;
        	} else {
        		current = current.prev;
        	}
        }
        
        /**
         * Removes the Node that this Iterator references from the LinkedForneymonagerie
         * Moves the iterator to the Node preceding the one deleted while maintaining relative order
         * @return a reference to the Forneymon of the removed Node.
         */
        public Forneymon removeCurrent () {
        	if (!isValid()) {
        		throw new IllegalStateException();
        	}
        	Forneymon removed = host.remove(host.getTypeIndex(getCurrent().getFMType()));
        	this.itModCount++;
        	current.prev.next = current.next;
        	current.next.prev = current.prev;
        	if (!empty()) {
            	this.prev();
        	} else {
        		current = host.sentinel;
        	}
        	return removed;
        }
        
    }
    
    private class Node {
        Node next, prev;
        Forneymon fm;
        
        Node (Forneymon fm) {
            this.fm = fm;
        }
    }
    
}
