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
    private int frequency;
    private double positivity;
    private double importance;

    public Thesis(long review_id, String content, int frequency, double positivity, double importance) {
        this.review_id = review_id;
        this.content = content;
        this.positivity = positivity;
        this.importance = importance;
        this.frequency = frequency;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getReviewId() {
        return review_id;
    }

    public void setReviewId(long review_id) {
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

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
