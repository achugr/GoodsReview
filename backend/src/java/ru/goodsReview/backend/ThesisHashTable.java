package ru.goodsReview.backend;

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
    private Map<String, Integer> thesisTable;

    public void print(){
        for(Map.Entry<String, Integer> ent : thesisTable.entrySet()){
            System.out.println(ent.getKey() + " " + ent.getValue());
        }
    }
    public ThesisHashTable(Map<String, Integer> thesisTable) {
        this.thesisTable = thesisTable;
    }
    public void add(Map<String, Integer> thesisTable){
        Integer currFreq = 0;
        for(Map.Entry<String, Integer> entry : thesisTable.entrySet()){
            currFreq = this.thesisTable.get(entry.getKey());
            this.thesisTable.put(entry.getKey(), entry.getValue() + (currFreq == null ? 0 : currFreq));
        }
    }
    public void addSeveralThesisTables(List<Map<String,Integer>> listOfThesisTables){
        for(Iterator<Map<String, Integer>> it = listOfThesisTables.iterator();it.hasNext();){
            this.add(it.next());
        }

    }
    public void getThesisTableFromDatabase(long productId){
        //this.thesisTable = Selecting Table from DB
        //
    }
}
