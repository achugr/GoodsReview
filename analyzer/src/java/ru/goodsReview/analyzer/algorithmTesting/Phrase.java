package ru.goodsReview.analyzer.algorithmTesting;



/**
 * Date: 31.03.12
 * Time: 23:16
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */

public class Phrase {
    private String feature;
    private String opinion;


    public Phrase(String feature, String opinion) {
        this.opinion = opinion;
        this.feature = feature;
    }

    public Phrase(String feature) {
        this.opinion = null;
        this.feature = feature;
    }

    public String getFeature(){
        return feature;
    }

    public String getOpinion(){
        return opinion;
    }

}