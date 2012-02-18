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

/**
 * class to represent thesis patterns
 */
public class ThesisPattern {
//    list of part of speech - this is pattern
    List<PartOfSpeech> pattern;
    MystemAnalyzer mystemAnalyzer;

    /**
     * create new pattern
     * @param pattern
     */
    public ThesisPattern(List<PartOfSpeech> pattern) throws IOException {
        this.pattern = pattern;
        mystemAnalyzer = new MystemAnalyzer();
    }

    /**
     * create new pattern
     * @param p1 first token of pattern
     * @param p2 second token of pattern
     */
    public ThesisPattern(PartOfSpeech p1, PartOfSpeech p2) throws IOException {
        pattern = new ArrayList<PartOfSpeech>();
        pattern.add(p1);
        pattern.add(p2);
        mystemAnalyzer = new MystemAnalyzer();
    }

    /**
     * create new pattern
     * @param p1 first token of pattern
     * @param p2 second token of pattern
     * @param p3 third token of pattern
     */
    public ThesisPattern(PartOfSpeech p1, PartOfSpeech p2, PartOfSpeech p3) throws IOException {
        pattern = new ArrayList<PartOfSpeech>();
        pattern.add(p1);
        pattern.add(p2);
        pattern.add(p3);
        mystemAnalyzer = new MystemAnalyzer();
    }

    public PartOfSpeech get(int i){
        return pattern.get(i);
    }
    /**
     * matches with this pattern
     * @param sourceThesis possibly this is thesis
     * @return true if sourceThesis matches with pattern, false - otherwise
     * @throws IOException
     */
    public boolean match(List<String> sourceThesis) throws IOException {
        if(sourceThesis.size() != pattern.size()){
            return false;
        }
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
        ThesisPattern thesisPattern = null;
        try {
            thesisPattern = new ThesisPattern(PartOfSpeech.NOUN, PartOfSpeech.ADJECTIVE);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
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
