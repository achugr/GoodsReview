package ru.goodsReview.analyzer.algorithmTesting;

/**
 * Date: 05.02.12
 * Time: 21:18
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */
import java.util.ArrayList;

public class Review{
   private String review;
   private ArrayList<Phrase> thesis;

    public Review(String review, ArrayList<Phrase> thesis) {
        this.review = review;
        this.thesis = thesis;
    }

    public String getReview(){
        return review;
    }

    public ArrayList<Phrase> getFeatures(){
        return thesis;
    }

}
