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
    private String opinionWorld;

    public Phrase(String feature, String opinionWorld) {
        this.opinionWorld = opinionWorld;
        this.feature = feature;
    }

    public Phrase(String feature) {
        this.opinionWorld = null;
        this.feature = feature;
    }

    public String getFeature(){
        return feature;
    }

    public String getOpinionWorld(){
        return opinionWorld;
    }

}