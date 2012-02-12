package ru.goodsReview.analyzer.util.sentence;
/*
 *  Date: 11.02.12
 *   Time: 17:22
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class ReviewTokens implements Iterable<Token> {
    //    list of tokens
    private List<Token> tokensList;
    //    position of current token
    private int currentPosition=-1;
    //    "pointer" for traverse next/previous by currentPosition
    private int traversePosition = currentPosition;
    /**
     * create new ReviewTokens from review
     *
     * @param review source String
     */
    public ReviewTokens(String review) {
        Token token;
        tokensList = new ArrayList<Token>();
        StringTokenizer stringTokenizer = new StringTokenizer(review, " .,-—:;()+\'\"\\«»");
        while (stringTokenizer.hasMoreElements()) {
            token = new Token(stringTokenizer.nextToken());
//            token.setMystemPartOfSpeech, and other parameters
            tokensList.add(token);
        }
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
        return tokensList.get(++traversePosition);
    }

    /**
     method for traverse previous by current element
     @return previous element of list, don't decrement currentPosition
     */
    public Token getPrevious(){
        if(traversePosition > currentPosition){
            traversePosition = currentPosition;
        }
        return tokensList.get(--traversePosition);
    }

    public static void main(String [] args){
        ReviewTokens reviewTokens = new ReviewTokens("When I find myself in times of trouble");
//        System.out.println(reviewTokens.getNext().getToken());
//        System.out.println(reviewTokens.getPrevious().getToken());
//        System.out.println(reviewTokens.getNext().getToken());
        Iterator<Token> iterator = reviewTokens.iterator();
        iterator.next();
        iterator.next();
        while (iterator.hasNext()){
            Token token = iterator.next();
            for (int i=0; i< 2; i++){
                System.out.println(reviewTokens.getPrevious().getToken());
            }
//            System.out.println(reviewTokens.getPrevious().getToken() +" "+ token.getToken() +" "+reviewTokens.getNext
//                    ().getToken() );

        }

    }
}
