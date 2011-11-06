package ru.goodsReview.core.model;

import java.util.ArrayList;
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
    public void clear(){
        reviewsList.clear();
    }
    public void setReviewsList(List<Review> reviewsList) {
        this.reviewsList = reviewsList;
    }

    public int getReviewsNum() {
        return this.reviewsList.size();
    }

    public void addReview(Review review) {
        this.reviewsList.add(review);
    }

    public void printReviews() {
        for (int i = 0; i < this.reviewsList.size(); ++i) {
            System.out.println(this.reviewsList.get(i).getId() + " " + this.reviewsList.get(i).getProductId() + " " + this.reviewsList.get(i).getContent() + " " + this.reviewsList.get(i).getAuthor() + " " + this.reviewsList.get(i).getDate() + " " + this.reviewsList.get(i).getDescription() + " " + this.reviewsList.get(i).getSourceId() + " " + this.reviewsList.get(i).getSourceUrl() + " " + this.reviewsList.get(i).getPositivity() + " " + this.reviewsList.get(i).getImportance() + " " + this.reviewsList.get(i).getVotesYes() + " " + this.reviewsList.get(i).getVotesNo());
                                                                        //                                                                                                                  long id, long productId, String content, String author, int date, String description, long sourceId, String sourceUrl, double positivity, double importance, int votesYes, int votesNo
        }
    }

    public List<String> getListOfComments(){
        List<String> listOfComments = new ArrayList<String>();
        for(Review review : this.reviewsList){
            listOfComments.add(review.getContent());
        }
        return listOfComments;
    }

}
