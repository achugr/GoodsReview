package test.goodsReview.miner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Artemij
 * Date: 02.10.11
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */
//
public class Comments {

    private List<String> comments = new ArrayList<String>();

    public Comments(List<String> comments) {
        this.comments = comments;
    }

    public List<String> getComments() {
        return this.comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public int getCommentsNum() {
        return this.comments.size();
    }

    public char[] getCommentChars(int i) {
        return this.comments.get(i).toCharArray();
    }

    public void printComments() {
        for (String comment : this.comments) {
            System.out.println(comment);
        }
    }

}
