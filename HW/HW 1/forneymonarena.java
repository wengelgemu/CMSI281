package main.forneymon.arena;

import main.forneymon.fmtypes.Forneymon;

/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* File name  :  ForneymonArena.java
* Purpose    :  Forneymon fight sequence
* @author    :  Wengel Gemu
* Date       :  2020-10-8
*  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

/**
 * Contains methods for facing off Forneymonagerie against one another!
 */
public class ForneymonArena {
    
    public static final int BASE_DAMAGE = 5;
    private static int damage1;
    private static int damage2;
    
    /**
     * Conducts a fight between two Forneymonageries, consisting of the following
     * steps:
     * <ol>
     *   <li>Forneymon from each Forneymonagerie are paired to fight, in sequence
     *     starting from index 0.</li>
     *  <li>Forneymon that faint (have 0 or less health) are removed from their
     *    respective Forneymonagerie.</li>
     *  <li>Repeat until one or both Forneymonagerie have no remaining Forneymon.</li>     
     * </ol>
     * @param fm1 One of the fighting Forneymonagerie
     * @param fm2 One of the fighting Forneymonagerie
     */
    public static void fight (Forneymonagerie fm1, Forneymonagerie fm2) {
    	
    	int fm2Index = 0;
    	/* Repeat until one or both Forneymonagerie have no remaining Forneymon */
    	while ((!fm1.empty()) && (!fm2.empty())) {
	    	for (int i = 0; i < fm1.size(); i++) {
	    		
	    		/* damage = BASE_DAMAGE + ATTACKER_LEVEL + DAMAGE_MODIFIER */
	    		damage1 = (BASE_DAMAGE + fm2.get(fm2Index).getLevel());
	    		fm1.get(i).takeDamage(damage1, fm2.get(fm2Index).getDamageType());
	    		
	    		damage2 = (BASE_DAMAGE + fm1.get(i).getLevel());
	    		fm2.get(fm2Index).takeDamage(damage2, fm1.get(i).getDamageType());
	    		
	    		
	    		/* Forneymon that faint (have 0 or less health) are removed from their respective Forneymonagerie. */
	    		if ((fm1.get(i).getHealth() <= 0)||(fm1.get(i) == null)) {
	    			fm1.remove(i);
	    			i--;
	    		}
	    		
	    		if ((fm2.get(fm2Index).getHealth() <= 0)||(fm2.get(fm2Index) == null)) {
	    			fm2.remove(fm2Index);
	    			fm2Index--;
	    		}
	    		
	    		fm2Index++;
	    		if (fm2Index >= fm2.size()) {
	    			fm2Index = 0;
	    		}
	    		
	    		/* Repeat until one or both Forneymonagerie have no remaining Forneymon */
	    		if (fm1.empty() || fm2.empty()){
	    			break;
	    		}
	    	}
    	}
    }
    
}
