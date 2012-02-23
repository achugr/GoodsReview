package ru.goodsReview.core.utils;
/*
 *  Date: 23.02.12
 *   Time: 16:27
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import java.util.*;

public class HashMap {

    public static java.util.HashMap<String, Integer> sort(java.util.HashMap<String, Integer> input){
        Map<String, Integer> tempMap = new java.util.HashMap<String, Integer>();
        for (String wsState : input.keySet()){
            tempMap.put(wsState,input.get(wsState));
        }
        List<String> mapKeys = new ArrayList<String>(tempMap.keySet());
        ArrayList<Integer> mapValues = new ArrayList<Integer>(tempMap.values());
        java.util.HashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        TreeSet sortedSet = new TreeSet(mapValues);
        Object[] sortedArray = sortedSet.descendingSet().toArray();
        int size = sortedArray.length;
        for (int i=0; i<size; i++){
            sortedMap.put(mapKeys.get(mapValues.indexOf(sortedArray[i])),(Integer)sortedArray[i]);
        }
        return sortedMap;
    }
}
