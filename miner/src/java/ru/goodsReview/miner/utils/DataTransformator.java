package ru.goodsReview.miner.utils;

/*
    Date: 10/26/11
    Time: 06:23
    Author: Alexander Marchuk
            aamarchuk@gmail.com
*/
public abstract class DataTransformator {

    /**
     * Clear string from html-tags
     * @param review string for clearing
     * @return String without html-tags
     */
    protected static String clearReviewFromTags(String review){
        return review.replaceAll("\\<.*?\\>", "");
    }
}