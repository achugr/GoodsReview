package test.goodsReview.miner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Artemij
 * Date: 02.10.11
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */

//class for comments on a product
//TODO here we must realize interface class, or container, because CitilinkReview is for cpecific store

public class ListOfReviews {
    private List<CitilinkReview> comments = new ArrayList<CitilinkReview>();

    public ListOfReviews(List<CitilinkReview> comments) {
        this.comments = comments;
    }

    public ListOfReviews(){}

    public List<CitilinkReview> getComments() {
        return this.comments;
    }

    public void setComments(List<CitilinkReview> comments) {
        this.comments = comments;
    }

    public int getCommentsNum() {
        return this.comments.size();
    }

   /*public char[] getCommentChars(int i) {
        return this.comments.get(i).toCharArray();
    }  */
    public void addComment(CitilinkReview comment){
        this.comments.add(comment);
    }
    public void printComments() {
        for (CitilinkReview comment : this.comments) {
            System.out.println(comment);
        }
    }

}
