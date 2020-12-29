package senttools;

/**
 * Simple library which might (outside of this assignment) contain
 * various functions related to some sentence tools.
 */
public class SentTools {
	
    /**
     * Returns the number of unique, unrepeated words that are found
     * in the given sentence sent
     * @param sent The sentence in which to count unique words
     * @return The number of unique, unrepeated words in sent
     */
    public static int uniqueWords (String sent) {

    	int counter = 0;
    	String[] words = sent.split(" ");
    	
    	if(words.length == 1){
			counter = 1;
			return counter;
		}
    	for( int i = 0; i < words.length; i++){

    		boolean unique = true;
    		for(int j = 0; j < words.length; j++ ){
    			if(i != j){
    				if(words[i].equals(words[j])){
        				unique = false;
        			}
    			}
    		}
    		if(unique == true){
				counter++;
			}
    		
    	}
    	return counter;
      
    }
