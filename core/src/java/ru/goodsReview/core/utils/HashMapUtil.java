package ru.goodsreview.core.utils;
/*
 *  Date: 23.02.12
 *   Time: 16:27
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import java.util.*;

public class HashMapUtil {

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

    public static Map<String, Integer> sortByValue(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

            public int compare(Map.Entry<String, Integer> m1, Map.Entry<String, Integer> m2) {
                return (m2.getValue()).compareTo(m1.getValue());
            }
        });

        Map<String, Integer> result = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
