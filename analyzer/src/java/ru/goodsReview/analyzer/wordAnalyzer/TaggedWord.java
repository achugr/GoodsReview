package ru.goodsReview.analyzer.wordAnalyzer;
/*
 *  Date: 08.02.12
 *   Time: 20:03
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author anton
 */
public class TaggedWord {

    private String word;
    private String pos;

    public TaggedWord(String w, String p) {
        word = w;
        pos = p;
    }

    public String getWord() {
        return word;
    }

    public String getPOS() {
        return pos;
    }
}

