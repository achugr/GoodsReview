package ru.goodsreview.miner.obsolete.utils;

import org.jetbrains.annotations.NotNull;

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
    protected static String clearReviewFromTags(@NotNull String review){
        return review.replaceAll("\\<.*?\\>", "");
    }

    protected static String clearBadSymbols(@NotNull String s){

        //it is not simple space symbols
        s = s.replaceAll("['\\u00A0''\\u2007''\\u202F']", " ");

        s = s.replaceAll("['\\u0097']", "-");

        //replace breakline symbols
        s = s.replaceAll("\n+"," ");

        return s;
    }
}
