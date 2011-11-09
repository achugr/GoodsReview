package ru.goodsReview.core.model;
/*
 *  Date: 06.11.11
 *   Time: 23:50
 *   Author:
 *      Artemij Chugreev
 *      artemij.chugreev@gmail.com
 */

import java.util.Date;

public class ThesisUnique {
    private long id;
    private String content;
    private int frequency;
    private Date lastScan;
    private double positivity;
    private double importance;

    public ThesisUnique(String content, int frequency, Date lastScan, double positivity, double importance) {
        this.content = content;
        this.frequency = frequency;
        this.lastScan = lastScan;
        this.positivity = positivity;
        this.importance = importance;
    }

    public ThesisUnique(long id, String content, int frequency, Date lastScan, double positivity, double importance) {
        this.id = id;
        this.content = content;
        this.frequency = frequency;
        this.lastScan = lastScan;
        this.positivity = positivity;
        this.importance = importance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public Date getLastScan() {
        return lastScan;
    }

    public void setLastScan(Date lastScan) {
        this.lastScan = lastScan;
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
}
