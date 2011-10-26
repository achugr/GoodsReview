package ru.goodsReview.core.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Artemij
 * Date: 02.10.11
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */

//class for citilinkReviews on a product

public class ListOfReviews {
    private List<Review> reviewsList = new ArrayList<Review>();

    public ListOfReviews(List<Review> reviews) {
        this.reviewsList = reviews;
    }

    public ListOfReviews(){}

    public List<Review> getReviews() {
        return this.reviewsList;
    }

    public void setReviewsList(List<Review> reviewsList) {
        this.reviewsList = reviewsList;
    }

    public int getReviewsNum() {
        return this.reviewsList.size();
    }

    public List<String> getListOfComments(){
        List<String> listOfComments = new ArrayList<String>();
        for(Review review : this.reviewsList){
            listOfComments.add(review.getContent());
        }
        return listOfComments;
    }

    public char[] getCitilinkReviewChars(int i) {
        return this.reviewsList.get(i).getContent().toCharArray();
    }

    public void addCitilinkReview(CitilinkReview citilinkReview){
        this.reviewsList.add(citilinkReview);
    }
    public void printCitilinkReviews() {
        Iterator <Review> iterator = this.reviewsList.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next().getContent());
        }
    }

}
