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
   private ArrayList<Thesis> thesis;

    public Review(String review, ArrayList<Thesis> thesis) {
        this.review = review;
        this.thesis = thesis;
    }

    public String getReview(){
        return review;
    }

    public ArrayList<Thesis> getThesis(){
        return thesis;
    }

    /*
    public Object clone() {
        Review newObject = null;
        try {
            newObject = (Review) super.clone();
            newObject.review =   this.review;
            ArrayList<Thesis> t = new ArrayList<Thesis>();
            for (int i = 0; i < this.thesis.size(); i++) {
                t.add(this.thesis.get(i));
            }
            newObject.thesis =   t;

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return newObject;
    }   */

}
