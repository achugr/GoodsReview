package ru.goodsReview.miner.utils;
import ru.goodsReview.core.model.Review;

/*
 *  Date: 11.11.11
 *   Time: 23:54
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

/**
 * Class, extends DataTransformator
 * methods of this class allow to clean data from trash-content
 */
public class CitilinkDataTransformator extends DataTransformator{
    private static final String DEFECTS = "Недостатки:";
    private static final String MERIT = "Достоинства:";
    private static final String COMMENT = "Комментарий:";

   /**
    * Clear Sting from trash-words
    * @param review string for clearing
    * @param trashString array of trash-words
    * @return String review content without trash-words
    */
    private static String clearReviewFromTrashString(String review, String [] trashString){
        for(int i=0; i<trashString.length; i++){
            review = review.replaceAll(trashString[i], "");
        }
        return review;
    }
    /**
     * Clear review content
     * from trash words("Недостатки: ", "Достоинства: "),
     * from whitespace characters in begin of review content,
     * from trash html-tags in content
     * @param review review, in which you will clear content from trash
     * @return Review without trash in content
     */
    public Review clearReviewFromTrash(Review review){
        String clearReview = DataTransformator.clearReviewFromTags(review.getContent());
        clearReview = clearReview.replaceAll("^(\\s+)", "");
        String [] trashWords = {DEFECTS, MERIT};
        clearReview = clearReviewFromTrashString(clearReview, trashWords);
        review.setContent(clearReview);
        return review;
    }
}
