package ru.goodsReview.core.model;

/**
 * Created by IntelliJ IDEA.
 * User: Artemij
 * Date: 16.10.11
 * Time: 17:01
 * To change this template use File | Settings | File Templates.
 */
public abstract class Review {
    private String comment;

    protected Review(String comment) {
        this.comment = comment;
    }

    protected Review(){}

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }
}

