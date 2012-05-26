package ru.goodsreview.analyzer.util;
/*
 *  Date: 16.11.11
 *   Time: 11:14
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class FrequencyDictionary {

    /**
     * add word to frequency dictionary
     *
     * @param dictionary
     * @param word
     */
    public static void addWord(final Map<String, Integer> dictionary, final String word) {
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
     *
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
