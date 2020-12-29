package main.forneymon.arena;

import main.forneymon.fmtypes.Forneymon;

/**
 * Contains methods for facing off LinkedForneymonagerie against one another!
 */
public class LinkedForneymonArena {
    
    public static final int BASE_DAMAGE = 5;
    private static int damage1;
    private static int damage2;
    
    /**
     * Conducts a fight between two LinkedForneymonagerie, consisting of the following
     * steps, assisted by Iterators on each LinkedForneymonagerie:
     * <ol>
     *   <li>Forneymon from each LinkedForneymonagerie are paired to fight, in sequence
     *     starting from index 0.</li>
     *  <li>Forneymon that faint (have 0 or less health) are removed from their
     *    respective LinkedForneymonagerie.</li>
     *  <li>Repeat until one or both Forneymonagerie have no remaining Forneymon.</li>     
     * </ol>
     * @param fm1 One of the fighting LinkedForneymonagerie
     * @param fm2 One of the fighting LinkedForneymonagerie
     */
    public static void fight (LinkedForneymonagerie fm1, LinkedForneymonagerie fm2) {
        // [!] Recall: you are NOT allowed to use the LinkedForneymonagerie get method
        // in your implementation -- use your Iterators to conduct the fights instead!
    	
    	LinkedForneymonagerie.Iterator fm1Iterator = fm1.getIterator();
    	LinkedForneymonagerie.Iterator fm2Iterator = fm2.getIterator();
    	
        	while (!fm1.empty() && !fm2.empty()) {
        		Forneymon fm1Current = fm1Iterator.getCurrent();
        		Forneymon fm2Current = fm2Iterator.getCurrent();
        		
        		damage1 = (BASE_DAMAGE + fm2Current.getLevel());
        		fm1Current.takeDamage(damage1, fm2Current.getDamageType());
    		
    			damage2 = (BASE_DAMAGE + fm1Current.getLevel());
    			fm2Current.takeDamage(damage2, fm1Current.getDamageType());

        		
    			
        		if (fm1Current.getHealth() <= 0 || fm1Current == null) {
        			fm1Iterator.removeCurrent();
        		}
        		
        		if (fm2Current.getHealth() <= 0 || fm2Current == null) {
        			fm2Iterator.removeCurrent();
        		}
        		
        		if (fm1.empty() || fm2.empty()) {
        			break;
        		}
        		
        		fm1Iterator.next();
        		fm2Iterator.next();
        	}
        }
    
}
