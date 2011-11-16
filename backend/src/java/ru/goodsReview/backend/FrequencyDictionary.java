package ru.goodsReview.backend;
/*
 *  Date: 16.11.11
 *   Time: 11:14
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import java.util.HashMap;
import java.util.StringTokenizer;

public class FrequencyDictionary {

    /**
     * add word to frequency dictionary
     * @param dictionary
     * @param word
     */
    public static void addWord(final HashMap<String, Integer> dictionary, final String word) {
        if (dictionary.containsKey(word)) {
            Integer val = dictionary.get(word);
            val++;
            dictionary.put(word, val);
        } else {
            dictionary.put(word, 1);
        }
    }

    /**
     * crate frequency dictionary from string argument
     * @param str
     * @return
     */
    public static HashMap<String, Integer> createFrequencyDictionary(final String str) {
        HashMap<String, Integer> frequencyDictionary = new HashMap<String, Integer>();
        StringTokenizer stringTokenizer = new StringTokenizer(str, " ,");
        while (stringTokenizer.hasMoreTokens()) {
            addWord(frequencyDictionary, stringTokenizer.nextToken());
        }
        return frequencyDictionary;
    }

}
