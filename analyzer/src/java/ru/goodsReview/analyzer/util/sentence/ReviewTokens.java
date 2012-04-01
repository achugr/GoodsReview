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
import java.util.StringTokenizer;

public class ReviewTokens {
    //    list of tokens
    private ArrayList<Token> tokensList;
    //    position of current token
    private int currentPosition=-1;
    //    "pointer" for traverse next/previous by currentPosition
    private int traversePosition = currentPosition;

    private static Dictionary opinionDictionary = new Dictionary("pure_opinion_words.txt");

//    private static MystemAnalyzer mystemAnalyzer =  new MystemAnalyzer();



    /**
     * create new ReviewTokens from review
     *
     * @param review source String
     */
    public ReviewTokens(String review, MystemAnalyzer mystemAnalyzer) throws IOException, InterruptedException {
        Token token;
        tokensList = new ArrayList<Token>();
        StringTokenizer stringTokenizer = new StringTokenizer(review, " .,-—:;!()+\'\"\\«»");
        while (stringTokenizer.hasMoreElements()) {
            String currToken  = stringTokenizer.nextToken();
            currToken = currToken.trim();
            currToken = currToken.toLowerCase();
//            TODO it's strange, but here we can get empty string
            if(currToken.equals("")){
                System.out.println("fail");
                continue;
            }
           // System.out.println(currToken);
            token = new Token(currToken);
            if (opinionDictionary.contains(currToken)) {
                token.setMystemPartOfSpeech(PartOfSpeech.ADJECTIVE);
            } else {
                PartOfSpeech  partOfSpeech = mystemAnalyzer.partOfSpeech(currToken);
                if(!partOfSpeech.equals(PartOfSpeech.ADJECTIVE)){
                   token.setMystemPartOfSpeech(partOfSpeech);
                }else{
                    token.setMystemPartOfSpeech(PartOfSpeech.UNKNOWN);
                }

            }
            tokensList.add(token);
        }
//        mystemAnalyzer.close();
    }

    public ArrayList<Token>  getTokensList(){
        return tokensList;
    }

    public Dictionary  getDic(){
        return opinionDictionary;
    }

}
