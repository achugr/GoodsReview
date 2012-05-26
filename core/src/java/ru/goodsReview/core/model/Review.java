package ru.goodsreview.core.model;

/*
    Date: 16.10.11
    Time: 17:01
    Author:
        Artemij Chugreev
        artemij.chugreev@gmail.com
*/
public class Review {
    private long id;
    private long productId;
    private String content;
    private String author;
    private long time;
    private String description;
    private long sourceId;
    private String sourceUrl;
    private double positivity;
    private double importance;
    private int votesYes;
    private int votesNo;


    public Review(long productId, String content) {
        this.productId = productId;
        this.content = content;
    }

    public Review(long id, long productId, String content) {
        this.id = id;
        this.productId = productId;
        this.content = content;
    }

    public Review(long id, long productId, String content, String author, long time, String description, long sourceId,
                  String sourceUrl, double positivity, double importance, int votesYes, int votesNo) {
        this.id = id;
        this.productId = productId;
        this.content = content;
        this.author = author;
        this.time = time;
        this.description = description;
        this.sourceId = sourceId;
        this.sourceUrl = sourceUrl;
        this.positivity = positivity;
        this.importance = importance;
        this.votesYes = votesYes;
        this.votesNo = votesNo;
    }

    public Review(long productId, String content, String author, long time, String description, long sourceId, String sourceUrl, double positivity, double importance, int votesYes, int votesNo) {
        this.productId = productId;
        this.content = content;
        this.author = author;
        this.time = time;
        this.description = description;
        this.sourceId = sourceId;
        this.sourceUrl = sourceUrl;
        this.positivity = positivity;
        this.importance = importance;
        this.votesYes = votesYes;
        this.votesNo = votesNo;
    }

    public int getVotesNo() {
        return votesNo;
    }

    public void setVotesNo(int votesNo) {
        this.votesNo = votesNo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
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

    public int getVotesYes() {
        return votesYes;
    }

    public void setVotesYes(int votesYes) {
        this.votesYes = votesYes;
    }
}
