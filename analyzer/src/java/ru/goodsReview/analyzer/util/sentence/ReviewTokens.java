package ru.goodsReview.analyzer.util.sentence;
/*
 *  Date: 11.02.12
 *   Time: 17:22
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import ru.goodsReview.analyzer.util.dictionary.Dictionary;
import ru.goodsReview.analyzer.wordAnalyzer.MystemAnalyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class ReviewTokens implements Iterable<Token> {
    //    list of tokens
    private ArrayList<Token> tokensList;
    //    position of current token
    private int currentPosition=-1;
    //    "pointer" for traverse next/previous by currentPosition
    private int traversePosition = currentPosition;

    private static Dictionary opinionDictionary = new Dictionary("pure_opinion_words.txt");

    private MystemAnalyzer mystemAnalyzer = new MystemAnalyzer();

    /**
     * create new ReviewTokens from review
     *
     * @param review source String
     */
    public ReviewTokens(String review) throws IOException {
        Token token;
        tokensList = new ArrayList<Token>();
        StringTokenizer stringTokenizer = new StringTokenizer(review, " .,-—:;!()+\'\"\\«»");
        while (stringTokenizer.hasMoreElements()) {
            String currToken  = stringTokenizer.nextToken();
            token = new Token(currToken);

//            token.setMystemPartOfSpeech, and other parameters
//            WordAnalyzer wordAnalyzer = new WordAnalyzer();
            if (opinionDictionary.contains(currToken)) {
                token.setMystemPartOfSpeech(PartOfSpeech.ADJECTIVE);
            } else {
                token.setMystemPartOfSpeech(mystemAnalyzer.partOfSpeech(currToken));
            }
            tokensList.add(token);
        }
        mystemAnalyzer.close();
    }

    public ArrayList<Token>  getTokensList(){
        return tokensList;
    }

    /**
     @return iterator for ReviewTokens object
     */
    public Iterator<Token> iterator(){
        return new Iterator<Token>() {
            /**
             @return if list has next element, returns true, false - otherwise
             */
            public boolean hasNext() {
                try{
                    if(tokensList.get(currentPosition + 1) == null){
                        return false;
                    }
                } catch (IndexOutOfBoundsException e){
                    return false;
                }
                return true;
            }

            /**
             @return next element of list
             */
            public Token next() {
                traversePosition = currentPosition + 1;
                return tokensList.get(++currentPosition);
            }

            /**
             remove current element from list
             */
            public void remove() {
                traversePosition = currentPosition - 1;
                tokensList.remove(currentPosition--);
            }
        };
    }

    /**
     method for traverse next by current element
     @return next element of list, don't increment currentPosition
     */
    public Token getNext(){
        if(traversePosition < currentPosition){
            traversePosition = currentPosition;
        }
        if(traversePosition < tokensList.size() - 1){
            return tokensList.get(++traversePosition);
        }  else{
            return null;
        }
    }

    /**
     method for traverse previous by current element
     @return previous element of list, don't decrement currentPosition
     */
    public Token getPrevious(){
        if(traversePosition > currentPosition){
            traversePosition = currentPosition;
        }
        if(traversePosition > 0){
            return tokensList.get(--traversePosition);
        } else{
            return null;
        }

    }

    public static void main(String [] args) throws IOException {
        ReviewTokens reviewTokens = new ReviewTokens("этот ноут просто клевый! купил и не жалею, клавиатура просто отличная");
        for(Token token : reviewTokens){
            System.out.println(token.getContent() + " -> " + token.getMystemPartOfSpeech());
        }
    }
}
