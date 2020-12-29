package senttools;
import java.util.*;

/**
 * Simple library which might (outside of this assignment) contain
 * various functions related to some sentence tools.
 */
public class SentTools {

    /**
     * Returns the number of unique, unrepeated words that are found
     * in the given sentence sent
     * NOTE: This solution is not very good!!! It can be simplified
     * by using ArrayLists, but even those aren't the best choice here!
     * @param sent The sentence in which to count unique words
     * @return The number of unique, unrepeated words in sent
     */
    public static int uniqueWords (String sent) {
        String[] words = sent.split(" ");
        int count = 0;
        HashMap<String, Integer> uniqueWords = new HashMap<>();
        
        if (words.length == 1) {
            if (words[0] == "") {
                return 0;
            }
        }
        
        for (int i = 0; i < words.length; i++) {
            if (!uniqueWords.containsKey(words[i]) ) {
                System.out.println(words[i]);
                uniqueWords.put(words[i], 1);
            } else {
                int repeatedIndex = uniqueWords.get(words[i]);
                uniqueWords.replace(words[i],repeatedIndex + 1);
            }
        }
        
        Set <String> keys = uniqueWords.keySet();
        System.out.print(keys);
        for (String str : keys) {
            if (uniqueWords.get(str) == 1) {
                count++;
            }
        }
        return count;
    }
}
