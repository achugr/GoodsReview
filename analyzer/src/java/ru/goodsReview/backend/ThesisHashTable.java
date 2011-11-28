package ru.goodsReview.backend;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: ruslan
 * Date: 26.10.11
 * Time: 3:38
 * To change this template use File | Settings | File Templates.
 */
public class ThesisHashTable {

    public static void addToHolder(final Map<String, Integer> holderThesisTable,
                                   final Map<String, Integer> thesisTable) {
        if (holderThesisTable != null && !thesisTable.isEmpty()) {
            for (Map.Entry<String, Integer> entry : thesisTable.entrySet()) {
                final String word = entry.getKey();
                Integer val = entry.getValue();
                if (val < 0) {
                    throw new IllegalArgumentException("negative frequency("+val+") in thesis \"" +word+ "\"");
                }
                final Integer holderVal = holderThesisTable.get(word);
                if (holderVal != null) {
                    val += holderVal;
                }
                holderThesisTable.put(word, val);
            }
        }
    }

    public static HashMap<String, Integer> uniteAll(final List<Map<String, Integer>> listOfThesisTables) {
        final HashMap<String, Integer> holder = new HashMap<String, Integer>();
        for (Iterator<Map<String, Integer>> it = listOfThesisTables.iterator(); it.hasNext(); ) {
            addToHolder(holder, it.next());
        }
        return holder;
    }
}
