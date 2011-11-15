package ru.goodsReview.backend;
/*
 *  Date: 15.11.11
 *   Time: 10:07
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
    //    list of expected values(list of hash maps)
    private static final List<HashMap<String, Integer>> expectedThesisHashTableList = new ArrayList<HashMap<String, Integer>>();
    //    list of input values(list of hash maps)
    private static final List<HashMap<String, Integer>> inputListOfHashTable1 = new ArrayList<HashMap<String, Integer>>();
    private static final List<HashMap<String, Integer>> inputListOfHashTable2 = new ArrayList<HashMap<String, Integer>>();

    /**
     * create test set for function
     * @see ThesisHashTable add
     */
    @Before
    public void setUpAddHashTableData() {
//        fst block of test data
        HashMap<String, Integer> inputHashMap11 = new HashMap<String, Integer>();
        HashMap<String, Integer> inputHashMap12 = new HashMap<String, Integer>();
        HashMap<String, Integer> expectedHashMap1 = new HashMap<String, Integer>();
//        set fst input hash map
        inputHashMap11.put("good", 1);
        inputHashMap11.put("bad", 2);
//       set snd input hashMap
        inputHashMap12.put("good", 2);
        inputHashMap12.put("like", 4);
//        set expected correct result, which you want to see as return parameter
//        of function, that you will test
        expectedHashMap1.put("good", 3);
        expectedHashMap1.put("bad", 2);
        expectedHashMap1.put("like", 4);

//        snd block of test data
        HashMap<String, Integer> inputHashMap21 = new HashMap<String, Integer>();
        HashMap<String, Integer> inputHashMap22 = new HashMap<String, Integer>();
        HashMap<String, Integer> expectedHashMap2 = new HashMap<String, Integer>();

        inputHashMap21.put("beautiful", 3);
        inputHashMap21.put("nice", 6);

        inputHashMap22.put("slow", 2);
        inputHashMap22.put("idiotic", 1);
        inputHashMap22.put("beautiful", 1);
        inputHashMap22.put("nice", 2);

        expectedHashMap2.put("beautiful", 4);
        expectedHashMap2.put("nice", 8);
        expectedHashMap2.put("slow", 2);
        expectedHashMap2.put("idiotic", 1);

        expectedThesisHashTableList.add(expectedHashMap1);
        inputListOfHashTable1.add(inputHashMap11);
        inputListOfHashTable2.add(inputHashMap12);
        expectedThesisHashTableList.add(expectedHashMap2);
        inputListOfHashTable1.add(inputHashMap21);
        inputListOfHashTable2.add(inputHashMap22);
    }

    /**
     * clean test set
     */
    @After
    public void tearDownHashTableData() {
        expectedThesisHashTableList.clear();
        inputListOfHashTable2.clear();
        inputListOfHashTable1.clear();
    }

    /**
     * execute test for method
     * @see ThesisHashTable add
     */
    @Test
    public void testAddHashTable() {
        int j = 0;
//        move on test set (list of hash tables)
        for (int i = 0; i < expectedThesisHashTableList.size(); i++) {
            final ThesisHashTable thesisHashTable = new ThesisHashTable(inputListOfHashTable1.get(i));
            final HashMap<String, Integer> hashTable = inputListOfHashTable2.get(i);

//            method that we test
            thesisHashTable.add(hashTable);

//            here i compare two hash maps, i'm sure that this is hard-code, but this is
//            the first idea that occured to
//            i'll be grateful if you offer me how i can make better
            HashMap<String, Integer> expectedThesisHashTable = expectedThesisHashTableList.get(i);
            Object[] expectedKeysObj = expectedThesisHashTable.keySet().toArray();
            String[] expectedKeys = new String[expectedKeysObj.length];
            for (Object obj : expectedKeysObj) {
                expectedKeys[j++] = obj.toString();
            }
            j = 0;
            for (Map.Entry<String, Integer> map : thesisHashTable.getThesisTable().entrySet()) {
//                compare keys of expected and actual hash maps
                assertEquals(map.getKey(), expectedKeys[j]);
//                compare values of expected and actual hash maps
                assertEquals(map.getValue().intValue(), expectedThesisHashTable.get(expectedKeys[j]).intValue());
                j++;
            }
            j = 0;
        }
    }

}
