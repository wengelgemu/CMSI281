/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 File name  :  ForneymonCard.java
 Purpose    :  Create the Forneymon Cards 
 @author    :  Wengel Gemu, Sureena Hukkoo, Sumaiyah Lee 
 Date       :  9/21/2020
*  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

package forneymon.cards;

import java.util.Objects;

public class ForneymonCard {
   
//	fields
	public String name;
	public String type;
	public Forneymon mon;

	/**
     * Creates a new ForneymonCard with a reference to the given
     * Forneymon
     * @param forneymon The Forneymon referenced by the card
     */
    ForneymonCard (Forneymon forneymon) {

//    	If the Forneymon argument is null, throw an IllegalArgumentException.
    	if(forneymon == null) {
    		throw new IllegalArgumentException();
    	}
    	
//    	reference
    	this.mon = forneymon;
    	
    }
    
    
    /**
     * Default constructor to create a new ForneymonCard of type
     * Burnymon with name "MissingNu"
     */
    ForneymonCard () {
    	
    	this.mon = new Burnymon("MissingNu");
    	
    }
    
    /**
     * Getter for the name given to the Forneymon represented by
     * this card
     * @return The card's Forneymon's name, e.g., "Burny"
     */
    public String getName () {
//    	go into Forneymon.java to get name
    	if (this.mon != null) {
    		return this.mon.getName();
    	} else {
    		return null;
    	}
    }
    
    /**
     * Getter for the type of the Forneymon represented by this
     * card
     * @return The card's Forneymon's type, e.g., "Dampymon"
     */
    public String getType () {
//    	return "Burnymon"
    	if (this.mon != null) {
    		return this.mon.getClass().getSimpleName();
    	} else {
    		return null;
    	}
    }
    
    
//    // TODO! Any method overrides here!
    
    @Override 
    public String toString() {
    	return type + ": " + name;
    }
    
    @Override
    public boolean equals(Object other) {
    	if (this.getClass() != other.getClass()) {
    		return false;
    	}
    	return this.getName().equals(((ForneymonCard)other).getName());
    }
    
    @Override
    public int hashCode() {
    	return Objects.hash(this.name);
    }

}
