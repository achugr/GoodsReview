package ru.goodsReview.core.model;

/*
    Date: 26.10.11
    Time: 00:02
    Author: 
        Yaroslav Skudarnov 
        SkudarnovYI@gmail.com
*/

public class Thesis {

    private long id;
    private long review_id;
    private String content;
    private double positivity;
    private double importance;
    private long votes_yes;
    private long votes_no;

    public Thesis(long id, long review_id, String content, double positivity, double importance, long votes_yes, long votes_no) {
        this.id = id;
        this.review_id = review_id;
        this.content = content;
        this.positivity = positivity;
        this.importance = importance;
        this.votes_yes = votes_yes;
        this.votes_no = votes_no;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getReview_id() {
        return review_id;
    }

    public void setReview_id(long review_id) {
        this.review_id = review_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getPositivity() {
        return positivity;
    }

    public void setPositivity(double positivity) {
        this.positivity = positivity;
    }

    public double getImportance() {
        return importance;
    }

    public void setImportance(double importance) {
        this.importance = importance;
    }

    public long getVotes_yes() {
        return votes_yes;
    }

    public void setVotes_yes(long votes_yes) {
        this.votes_yes = votes_yes;
    }

    public long getVotes_no() {
        return votes_no;
    }

    public void setVotes_no(long votes_no) {
        this.votes_no = votes_no;
    }

}
