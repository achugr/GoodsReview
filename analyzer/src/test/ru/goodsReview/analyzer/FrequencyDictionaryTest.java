package ru.goodsReview.analyzer;
/*
 *  Date: 16.11.11
 *   Time: 12:26
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import org.junit.Assert;
import ru.goodsReview.analyzer.util.FrequencyDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * tests for class FrequencyDictionary
 */
public class FrequencyDictionaryTest extends Assert {

    @org.junit.Test
    public void testCreateFrequencyDictionary(){
        List<HashMap<String, Integer>> resultSet = new ArrayList<HashMap<String, Integer>>();
        {
            String str = "good bad good good";
            resultSet.add(FrequencyDictionary.createFrequencyDictionary(str));
        }

        assertEquals(3, resultSet.get(0).get("good"));
        assertEquals(1, resultSet.get(0).get("bad"));

    }
}
