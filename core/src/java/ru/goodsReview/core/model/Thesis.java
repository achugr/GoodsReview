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
    private long reviewId;
    private long thesisUniqueId;
    private String content;
    private int frequency;
    private double positivity;
    private double importance;
    private double tfidf;

    public Thesis(long reviewId, long thesisUniqueId, String content, int frequency, double positivity,
                  double importance, double tfidf) {
        this.reviewId = reviewId;
        this.thesisUniqueId = thesisUniqueId;
        this.content = content;
        this.positivity = positivity;
        this.importance = importance;
        this.frequency = frequency;
        this.tfidf = tfidf;
    }

    public Thesis(long thesisUniqueId, String content, int frequency, double positivity, double importance, double tfidf) {
        this.thesisUniqueId = thesisUniqueId;
        this.content = content;
        this.frequency = frequency;
        this.positivity = positivity;
        this.importance = importance;
        this.tfidf = tfidf;
    }

    public Thesis(long id, long reviewId, long thesisUniqueId, String content, int frequency, double positivity,
                  double importance, double tfidf) {
        this.id = id;
        this.reviewId = reviewId;
        this.thesisUniqueId = thesisUniqueId;
        this.content = content;
        this.frequency = frequency;
        this.positivity = positivity;
        this.importance = importance;
        this.tfidf = tfidf;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getThesisUniqueId() {
        return thesisUniqueId;
    }

    public void setThesisUniqueId(long thesisUniqueId) {
        this.thesisUniqueId = thesisUniqueId;
    }

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long review_id) {
        this.reviewId = review_id;
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
    public double getTfidf(){
        return tfidf;
    }
    public void setTfidf(double tfidf){
        this.tfidf = tfidf;
    }
}
