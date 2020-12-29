package main.wiki;

import java.util.*;

public class WikiWalker {
    // The value is a hash map containg an integer to record the number of click throughs
    HashMap<String, HashMap<String, Integer>> siteMap;
    
    public WikiWalker() {
        this.siteMap = new HashMap<String, HashMap<String, Integer>>();
    }
    
    /**
     * Adds an article with the given name to the site map and associates the
     * given linked articles found on the page. Duplicate links in that list are
     * ignored, as should an article's links to itself.
     * 
     * @param articleName
     *            The name of the page's article
     * @param articleLinks
     *            List of names for those articles linked on the page
     */
    public void addArticle(String articleName, List<String> articleLinks) {
        this.siteMap.put(articleName, new HashMap<String, Integer>());
        for (String link : articleLinks) {
            // Check for duplicate links
            if (!link.contentEquals(articleName)){
                siteMap.get(articleName).put(link, 0);
            }
        }
    }

    /**
     * Determines whether or not, based on the added articles with their links,
     * there is *some* sequence of links that could be followed to take the user
     * from the source article to the destination.
     * 
     * @param src
     *            The beginning article of the possible path
     * @param dest
     *            The end article along a possible path
     * @return boolean representing whether or not that path exists
     */
    public boolean hasPath(String src, String dest) {
        ArrayList<String> linkPath = new ArrayList<String>();
        return pathHelper(src, dest, linkPath);
    }

    /**
     * Increments the click counts of each link along some trajectory. For
     * instance, a trajectory of ["A", "B", "C"] will increment the click count
     * of the "B" link on the "A" page, and the count of the "C" link on the "B"
     * page. Assume that all given trajectories are valid, meaning that a link
     * exists from page i to i+1 for each index i
     * 
     * @param traj
     *            A sequence of a user's page clicks; must be at least 2 article
     *            names in length
     */
    public void logTrajectory(List<String> traj) {
        // Check each trajectory is at least 2 items in length
        if (traj.size() <= 1) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < traj.size()-1; i++) {
            String current = traj.get(i);
            String next = traj.get(i + 1);
            siteMap.get(current).replace(next, siteMap.get(current).get(next)+1);
        }
    }

    /**
     * Returns the number of clickthroughs recorded from the src article to the
     * destination article. If the destination article is not a link directly
     * reachable from the src, returns -1.
     * 
     * @param src
     *            The article on which the clickthrough occurs.
     * @param dest
     *            The article requested by the clickthrough.
     * @throws IllegalArgumentException
     *             if src isn't in site map
     * @return The number of times the destination has been requested from the
     *         source.
     */
    public int clickthroughs(String src, String dest) {
        // Case: If the source is not located in the site map
        if (!siteMap.containsKey(src)) {
            throw new IllegalArgumentException();
        }
        
        // Case: If the destination article is not a link directly reachable from the source
        if (!siteMap.get(src).containsKey(dest)) {
            return -1;
        }
        return siteMap.get(src).get(dest);
    }

    /**
     * Based on the pattern of clickthrough trajectories recorded by this
     * WikiWalker, returns the most likely trajectory of k clickthroughs
     * starting at (but not including in the output) the given src article.
     * Duplicates and cycles are possible outputs along a most likely trajectory. In
     * the event of a tie in max clickthrough "weight," this method will choose
     * the link earliest in the ascending alphabetic order of those tied.
     * 
     * @param src
     *            The starting article of the trajectory (which will not be
     *            included in the output)
     * @param k
     *            The maximum length of the desired trajectory (though may be
     *            shorter in the case that the trajectory ends with a terminal
     *            article).
     * @return A List containing the ordered article names of the most likely
     *         trajectory starting at src.
     */
    public List<String> mostLikelyTrajectory(String src, int k) {
        List<String> trajectory = new ArrayList<String>();
        String oldKey = "";
        
        // Check there is a given number of clickthorughs
        while (k > 0) {
            if (siteMap.containsKey(src)) {
                // Travel through a set view of siteMap
                for (Map.Entry<String, Integer> map : siteMap.get(src).entrySet()){
                    String currentK = map.getKey();
                    if (oldKey.isEmpty()) {
                        oldKey = currentK;
                    }
                    
                    // Case 1: if next article has greater clickthroughs
                    else if (map.getValue() > siteMap.get(src).get(oldKey)) {
                        oldKey = currentK;
                    }
                    
                    // Case 2: two of the largest article links are equally likely 
                    else if (map.getValue() == siteMap.get(src).get(oldKey)) {
                        // choses the article that precedes alphabetically
                        if (oldKey.compareToIgnoreCase(currentK) > 0){
                            oldKey = currentK;
                        }
                    }
                }
                k--;
                trajectory.add(oldKey);
                src = oldKey;
                oldKey = "";
            } 
            // Case: the requested trajectory begins at a terminal node, returns an empty List
            else {
                k = 0;
            }
        }
        return trajectory;
    }
    /**
     * 
     * @param src
     *          The article on which the clickthrough occurs.
     * @param dest
     *          The article requested by the clickthrough.
     * @param linkPath
     *          The path of article links that have been clicked through
     * @return boolean representing whether or not that path exists
     */
    private Boolean pathHelper(String src, String dest, ArrayList<String> linkPath) {
        // if source and destination are the same article
        if (src.equals(dest)) {
            return true;
        }
        // If either src or dest articles do not exist in the WikiWalker
        if (linkPath.contains(src) || linkPath.contains(dest)) {
            return false;
        }
        linkPath.add(src);
        if (siteMap.containsKey(src)) {
            for (Map.Entry<String, Integer> wikiWalker : siteMap.get(src).entrySet()) {
                if (wikiWalker.getKey().equals(dest)) {
                    return true;
                }
                if (pathHelper(wikiWalker.getKey(), dest, linkPath)) {
                    return true;
                }
            }
        }
        return false;
    }
}
