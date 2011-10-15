package test.goodsReview.miner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by IntelliJ IDEA.
 * User: Artemij
 * Date: 02.10.11
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */

//class for citilinkReviews on a product
//TODO here we must realize interface class, or container, because CitilinkReview is for cpecific store

public class ListOfReviews {
    private List<CitilinkReview> reviewsList = new ArrayList<CitilinkReview>();

    public ListOfReviews(List<CitilinkReview> citilinkReviews) {
        this.reviewsList = citilinkReviews;
    }

    public ListOfReviews(){}

    public List<CitilinkReview> getcitilinkReviews() {
        return this.reviewsList;
    }

    public void setReviewsList(List<CitilinkReview> reviewsList) {
        this.reviewsList = reviewsList;
    }

    public int getCitilinkReviewsNum() {
        return this.reviewsList.size();
    }

    public List<String> getListOfComments(){
        List<String> listOfComments = new ArrayList<String>();
        for(CitilinkReview citilinkReview : this.reviewsList){
            listOfComments.add(citilinkReview.getComment());
        }
        return listOfComments;
    }

    public char[] getCitilinkReviewChars(int i) {
        return this.reviewsList.get(i).getComment().toCharArray();
    }

    public void addCitilinkReview(CitilinkReview citilinkReview){
        this.reviewsList.add(citilinkReview);
    }
    public void printCitilinkReviews() {
        /*for (CitilinkReview citilinkReview : this.reviewsList) {
            System.out.println("comment :" + citilinkReview);
        } */
        Iterator <CitilinkReview> iterator = this.reviewsList.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next().getComment());
        }
    }

}
