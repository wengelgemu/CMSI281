package main.forneymon.arena;

import java.util.Objects;

/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  Forneymonagerie.java
 * Purpose    :  Collecting Forneymon to fight
 * @author    :  Wengel Gemu
 * Date       :  2020-10-8
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

import main.forneymon.fmtypes.*;

/**
 * Collections of Forneymon ready to fight in the arena!
 */
public class Forneymonagerie implements ForneymonagerieInterface {

    // Constants
    // ----------------------------------------------------------
    // [!] DO NOT change this START_SIZE for your collection, and
    // your collection *must* be initialized to this size
    private static final int START_SIZE = 4;

    // Fields
    // ----------------------------------------------------------
    private Forneymon[] collection;
    private int size;
    
    
    // Constructor
    // ----------------------------------------------------------
    public Forneymonagerie () {

    	collection = new Forneymon[START_SIZE];
    	this.size = 0;
    	
    }
    
    
    // Methods
    // ----------------------------------------------------------
    
    
    /** 
     * Checks if there are any Forneymon in the Forneymonagerie
     * @return boolean true if the Forneymonagerie has no Forneymon inside
     */
    public boolean empty () {
        return (this.size == 0);
    }
    
    
    /**
     * Checks the number of Forneymon in the collection
     * @return value of current size in the Forneymonagerie
     */
    public int size () {
    	return (this.size);
    }
    
    
    /**
     * Attempts to add a reference to the given Forneymon to the Forneymonagerie's collection
     * @param
     * @return boolean true if toAdd was newly added to the Forneymonagerie
     */
    public boolean collect (Forneymon toAdd) {
    	
    	/* Forneymon type is already there */
    	for (int i = 0; i < this.size; i++) {
    		if(this.collection[i] == toAdd) {
    			/* increase the level of stored Forneymon by level of toAdd */
    			return false;
    		}
    		if(this.collection[i].getFMType().equals(toAdd.getFMType())){
    	    	this.collection[i].addLevels(toAdd.getLevel());
    	    	return false;
    		}
    	}
    	/* Forneymon type is NOT already in collection */
    	checkAndGrow();
    	this.collection[size] = toAdd;
    	this.size++;
    	return true;
    	
    }
    
    /**
     * Removes the Forneymon of the given subtype fmType from the Forneymonagerie, 
     * maintaining the relative order of remaining Forneymon in the collection
     * @param subtype string fmType from the Forneymonagerie
     * @return boolean true if found and removed
     */
    public boolean releaseType (String fmType) {
        for (int i = 0; i < this.size; i++) {
        	if(this.collection[i].getFMType().equals(fmType)) {
        		shiftLeft(i);
        		this.size--;
        		return true;
        	}
        }
        /* not in Forneymonagerie */
        return false;
    }
    
    /**
     * Returns the Forneymon at the given index in the collection, if valid
     * @param int index
     * @return forneymon at index
     */
    public Forneymon get (int index) {
    	if (index > -1) {
    		return collection[index];
    		
    	} else {
    		throw new IllegalArgumentException();
    	}
    }
    
    /**
     * Removes and returns the Forneymon at the given index, if valid, and maintains the relative order 
     * @param int index
     * @returns forneymon at given index
     * */
    public Forneymon remove (int index) {
    	if (index > -1) {
    		Forneymon fmAtIndex = collection[index];
    		shiftLeft(index);
    		this.size--;
    		return fmAtIndex;
    	} else {
    		throw new IllegalArgumentException();
    	}
    }
    
    
    /**
     * Returns index of the Forneymon with the given fmType in collection 
     * @param String fmType
     * @returns index of forneymon
     * */
    public int getTypeIndex (String fmType) {
    	for (int i = 0; i < this.size; i++) {
    		if(collection[i].getFMType().equals(fmType)) {
    			return i;
    		}
    	}
    	
    	/* Returns -1 if type in not found */
    	return -1;
    	
    }
    
    
    /**
     * Returns true if given fmType is found 
     * @param String fmType
     * @returns boolean true if type is found
     * */
    
//    public boolean containsType (String toCheck) {
    public boolean containsType (String fmType) {
    	for (int i = 0; i < this.size; i++) {
    		if(collection[i].getFMType().equals(fmType)) {
    			return true;
    		}
    	}
    	/* Returns false if type in not found */
    	return false;
    	
    }
    
    
    /**
     * Swaps contents of the calling Forneymonagerie and the other specified. 
     * @param Forneymonagerie other
     */
    public void trade (Forneymonagerie other) {
    	
    	Forneymonagerie fmClone = new Forneymonagerie();
    	
    	fmClone.collection = this.collection;
    	fmClone.size = this.size;
    	this.collection = other.collection;
    	this.size = other.size;
    	other.collection = fmClone.collection;
    	other.size = fmClone.size;
    	
    	
    }
   
    /**
     * Moves the Forneymon of the given fmType from its current position in the Forneymonagerie to the one specified by the index,
     * shifting any existing Forneymon around the requested index 
     * @param String fmType, int index
     */
    public void rearrange (String fmType, int index) {   	
    	
    	/* Maintains index range [0, size-1] */
    	if ((index >= size) || (index < 0)){
    		throw new IllegalArgumentException();
    		
    	}
    	int foundIndex = this.getTypeIndex(fmType);
    	Forneymon temp = collection[foundIndex];
    	shiftLeft(foundIndex);
    	shiftRight(foundIndex);
    	collection[index] = temp;
    	
    }
    
    @Override
    /**
     * Returns a deep copy of Forneymonagerie, a new Forneymonagerie object but with new instances and it's own collection
     * @return copy of Forneymonagerie
     */
    public Forneymonagerie clone () {
    	Forneymonagerie copy = new Forneymonagerie();
    	copy.size = this.size;
    	for (int i = 0; i < size; i++) {
    		copy.collection[i] = this.collection[i].clone();
    	}
    	return copy;
    }
    
    /**
     * Returns whether or not the given Object other is an equivalent Forneymonagerie to this one
     * which we define as meaning that it contains equal Forneymon in the same order in the collection as this one
     * @return boolean true if given object and Forneymon are equal
     */
    @Override
    public boolean equals (Object other) {
    	Forneymonagerie otherFm = (Forneymonagerie) other;
    	if (this.size() != otherFm.size()) {
    		return false;
    	}
    	for (int i = 0; i < size; i++) {
    		if (collection[i].getLevel() != otherFm.get(i).getLevel()){
    			return false;
    		}
			if (collection[i].getHealth() != otherFm.get(i).getHealth()) {
				return false;
			}
			if (!(collection[i].getFMType().equals(otherFm.get(i).getFMType()))) {
				return false;
			}
    	}
    	return true;
    	
    }
    
    @Override
    public int hashCode () {
        // This one is a freebie, no changes necessary here
        return Objects.hash(this.collection, this.size);
    }
    
    @Override
    public String toString () {
        // This one's also freebie -- you don't have to add or
        // change anything here!
        String[] result = new String[size];
        for (int i = 0; i < size; i++) {
            result[i] = collection[i].toString();
        }
        return "[ " + String.join(", ", result) + " ]";
    }
    
    
    
//     Private helper methods
//     ----------------------------------------------------------
    
    
    /**
     * Expands the size of the list whenever it is at capacity
     */
    private void checkAndGrow() {
    	// Case: big enough to fit another item, so no need to grow
    	if (size > collection.length) {
    		return;
    	}
    	
    	// Case: we're at capacity and need to grow
    	// doubling the size of the old array
    	Forneymon[] newCollection = new Forneymon[(collection.length)*2];
    	
    	// copying items from the old array to new
    	for (int i = 0; i < collection.length; i++) {
    		newCollection[i] = collection[i];
    	}
    	
    	// updating collection reference
    	collection = newCollection;
    }
    
    
    /**
     * Shifts all elements to the right of the given index one left
     * @param int index
     */
    private void shiftLeft(int index) {
    	for (int i = index; i < size - 1; i++) {
    		collection[i] = collection[i+1];
    	}
    }
    
    /**
     * Shifts all elements to the right of the given index one left
     * @param int index
     */
    private void shiftRight(int index) {
    	for (int i = index+1; i > 0; i--) {
    		collection[i] = collection[i-1];
    	}
    }
    
}
