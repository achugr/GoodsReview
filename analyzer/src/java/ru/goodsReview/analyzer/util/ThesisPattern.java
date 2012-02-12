package ru.goodsReview.analyzer.util;
/*
 *  Date: 12.02.12
 *   Time: 15:36
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import ru.goodsReview.analyzer.util.sentence.PartOfSpeech;
import ru.goodsReview.analyzer.wordAnalyzer.MystemAnalyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ThesisPattern {
    List<PartOfSpeech> pattern;

    public ThesisPattern(List<PartOfSpeech> pattern){
        this.pattern = pattern;
    }

    public ThesisPattern(PartOfSpeech p1, PartOfSpeech p2){
        pattern = new ArrayList<PartOfSpeech>();
        pattern.add(p1);
        pattern.add(p2);
    }

    public ThesisPattern(PartOfSpeech p1, PartOfSpeech p2, PartOfSpeech p3){
        pattern = new ArrayList<PartOfSpeech>();
        pattern.add(p1);
        pattern.add(p2);
        pattern.add(p3);
    }

    public boolean match(List<String> sourceThesis) throws IOException {
        if(sourceThesis.size() != pattern.size()){
            return false;
        }
        MystemAnalyzer mystemAnalyzer = new MystemAnalyzer();
        int i=0;
        for(String sourceThesisToken : sourceThesis){
            if(!mystemAnalyzer.partOfSpeech(sourceThesisToken).equals(pattern.get(i))){
                return false;
            }
            i++;
        }
        return true;
    }
    
    public static void main(String [] args){
        ThesisPattern thesisPattern = new ThesisPattern(PartOfSpeech.NOUN, PartOfSpeech.ADJECTIVE);
        {
            List<String> source = new ArrayList<String>();
            source.add("ноут");
            source.add("отличный");
            try {
                System.out.println(thesisPattern.match(source));
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        {
            List<String> source = new ArrayList<String>();
            source.add("ноут");
            source.add("сломался");
            try {
                System.out.println(thesisPattern.match(source));
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
