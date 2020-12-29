/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  WebNavigator.java
 * Purpose    :  Design a web browser navigation suite that can be used to visit sites, return users to 
 * previously visited sites, and move forward to previously visited sites that were returned from
 * @author    :  Wengel, Sureena, Sumaiyah
 * Date       :  2020-10-10
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


package webnav;
import java.util.LinkedList;

/**
 * Web Navigator used to track which URLs a user is currently
 * or was previously browsing, as well as tools for updating the
 * currently viewed based on their session history.
 */
public class WebNavigator {

    private String current;
    private int currentIndex;
    private LinkedList<String> goBack;
    private LinkedList<String> goForw;
    
    // [!] TODO: Choose your relevant JCF fields to manage
    // the browser's history!
    
    WebNavigator () {
    	this.current = null;
    	this.currentIndex = 0;
    	this.goBack = new LinkedList<String>();
    	this.goForw = new LinkedList<String>();
    }
    
    /**
     *  Visits the current site, clears the forward history,
     *  and records the visited site in the back history
     *  @param site The new site being visited
     */
    public void visit (String site) {
      /* adding current site to beginning of list, starting out site as null*/
    	goBack.addFirst(current);
        current = site;
        currentIndex = 0;
        this.goForw = new LinkedList<String>();
     }
    
    /**
     *  Changes the current site to the one that was last
     *  visited in the order on which visit was called on it
     */
    public void back () {
    	/* cannot use back commands on a null value, if size is 1 current returns current */
  
    	if (goBack.size() > 1) {
    		goForw.addFirst(current);
    		if (currentIndex > 0) {
        		currentIndex++;
    		}
    		/* return and remove current index */
    		current = goBack.get(currentIndex);
    		current = goBack.remove(currentIndex);
    		
    	}
    }
    
    /**
     * Changes the current site to the one that was last
     * returned to via back()
     */
    public void forw () {
    	if (goForw.size() > 0) {
    		goBack.addLast(current);
    		if (currentIndex > 0) {
        		currentIndex--;   			
    		}

    		current = goForw.get(currentIndex);
    		current = goForw.remove(currentIndex);
    		
    	}
    	
    }
    
    /**
     * Returns the String representing the site that the navigator
     * is currently at
     * @return The current site's URL
     */
    public String getCurrent () {
        return this.current;
    }
    
}
