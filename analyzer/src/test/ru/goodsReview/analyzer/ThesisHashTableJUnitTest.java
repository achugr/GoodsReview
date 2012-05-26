package ru.goodsreview.analyzer;
/*
 *  Date: 15.11.11
 *   Time: 10:07
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * for JUnit test of ThesisHashTable class
 *
 * @see ThesisHashTable
 */
public class ThesisHashTableJUnitTest extends Assert {

    /**
     * test for method uniteAll from class
     *
     * @see ThesisHashTable
     */
    @org.junit.Test
    public void testUniteAll() {
        final List<Map<String, Integer>> testHashTableSet = new ArrayList<Map<String, Integer>>();

        {
            Map<String, Integer> testHashTable = new HashMap<String, Integer>();
            testHashTable.put("good", 2);
            testHashTable.put("bad", 3);
            testHashTable.put("like", 1);
            testHashTableSet.add(testHashTable);
        }

        {
            Map<String, Integer> testHashTable = new HashMap<String, Integer>();
            testHashTable.put("good", 5);
            testHashTable.put("like", 6);
            testHashTableSet.add(testHashTable);
        }

        {
            Map<String, Integer> testHashTable = new HashMap<String, Integer>();
            testHashTable.put("beautiful", 1);
            testHashTable.put("idiotic", 10);
            testHashTableSet.add(testHashTable);
        }
        final Map<String, Integer> result = ThesisHashTable.uniteAll(testHashTableSet);

        assertEquals(7, (int) result.get("good"));
        assertEquals(3, (int) result.get("bad"));
        assertEquals(7, (int) result.get("like"));
        assertEquals(1, (int) result.get("beautiful"));
        assertEquals(10, (int) result.get("idiotic"));
    }

}
