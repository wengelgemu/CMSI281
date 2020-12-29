/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 File name  :  FlippingForneymonCard.java
 Purpose    :  Flip the Forneymon Cards 
 @author    :  Wengel Gemu, Sureena Hukkoo, Sumaiyah Lee 
 Date       :  9/21/2020
*  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

package forneymon.cards;

public class FlippingForneymonCard extends ForneymonCard {

	public boolean f;
	public String name;
	
    /**
     * Creates a new FlippingForneymonCard with a reference to the given
     * Forneymon and whether or not it begins face-down
     * @param forneymon The Forneymon referenced by the card
     * @param f Whether or not the card begins face-down (false = face-down)
     */
    FlippingForneymonCard (Forneymon forneymon, boolean f) {
    	super();
    	
//    	If the Forneymon argument is null, throw an IllegalArgumentException.
    	if(forneymon == null) {
    		throw new IllegalArgumentException();
    	}
    	
        this.f = f;
        this.mon = forneymon;
    }
    
    /**
     * Default constructs a new FlippingForneymonCard that represents a
     * face-down Burnymon with name "MissingNu"
     */
    FlippingForneymonCard () {
    	super();
    	
//        true because face down, starts out down
        this.f = true;
    }
    
    /**
     * Turns the card face-up if face-down and vice versa.
     * @return The current state of whether or not the card is face-down
     * after the flip
     */
    public boolean flip () {
        if (f == true) {
        	f = false;
        	return f;
        } else {
        	f = true;
        	return f;
        }
    }
    
    /**
     * Used to determine (by matching cardgame mechanics) whether or not
     * the given other FlippingForneymonCard is a match with the current one.
     * @param other The compared FlippingForneymonCard to this one
     * @return int code corresponding to:
     * <ol>
     *   <li>2 if either this or the other FlippingForneymonCard are face-down.</li>
     *   <li>1 if both are face-up and considered equal</li>
     *   <li>0 if both are face-up and considered unequal</li>
     * </ol>
     */
    int match (FlippingForneymonCard other) {
    	
    	boolean equalName = getName().equals(other.getName());
    	boolean equalType = getType().equals(other.getType());
    	
        if (f || other.f) {
        	return 2;
        } else if ( (!f && !other.f) && (equalName && equalType)) {
        	return 1;
        } else {
        	return 0;
        }
    }
    
    // TODO! Any method overrides here!
    @Override
    public String toString () {
    	if(this.f == true){
    		return "?: ?";
    	}
      return getType() + ": " + getName();
    }
    
}
