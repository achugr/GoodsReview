package ru.goodsReview.miner.utils;

import ru.goodsReview.core.model.Product;
import ru.goodsReview.core.model.Review;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *  Date: 11.11.11
 *   Time: 23:54
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

/**
 * extends DataTransformator
 * methods of this class allow to clean data from trash-content
 */
public class CitilinkDataTransformator extends DataTransformator {
    private static final int MIN_WORD_LENGTH = 3;
    /**
     * i am don't sure that enum is a good solving here, but i think that
     * for one shop trash words don't need a dictionary and List<String> might be not
     * very understandable
     *
     * enum for trash words
     */
    public enum TrashWords {
        DEFECTS("Недостатки:"),
        MERIT("Достоинства:"),
        COMMENT("Комментарий");

        private final String trashWord;

        TrashWords(String trashWord) {
            this.trashWord = trashWord;
        }

        public String getTrashWord() {
            return this.trashWord;
        }

    }

    /**
     * enum for product categories
     */
    public enum Category {
        LAPTOP("Ноутбук", 3),
        NETBOOK("Нетбук", 4);

        private String categoryName;
        private long categoryId;

        Category(String categoryName, long categoryId) {
            this.categoryName = categoryName;
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public long getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(long categoryId) {
            this.categoryId = categoryId;
        }
    }

    /**
     * Clear Sting from trash-words
     * @param review      string for clearing
     * @param trashString array of trash-words
     * @return String review content without trash-words
     */
    private static String clearReviewFromTrashString(String review, String[] trashString) {
        for (int i = 0; i < trashString.length; i++) {
            review = review.replaceAll(trashString[i], "");
        }
        return review;
    }

    /**
     * Clear review content
     * from trash words("Недостатки: ", "Достоинства: "),
     * from whitespace characters in begin of review content,
     * from trash html-tags in content
     *
     * @param review review, in which you will clear content from trash
     * @return Review without trash in content
     */
    public Review clearReviewFromTrash(Review review) {
        String clearReview = DataTransformator.clearReviewFromTags(review.getContent());
        //clearReview = clearReview.replaceAll("^(\\s+)", "");
        clearReview = clearReview.trim();
        String[] trashWords = {TrashWords.DEFECTS.getTrashWord(), TrashWords.MERIT.getTrashWord(), TrashWords.COMMENT.getTrashWord()};
        clearReview = clearReviewFromTrashString(clearReview, trashWords);
        review.setContent(clearReview);
        return review;
    }

    /**
     * extracting category id from source info about product
     *
     * @param sourceProductInfo source info about product
     * @return relevant category id of this product
     */
    public static long getGategoryFromSourceProductInfo(String sourceProductInfo) {
        long categoryId = 1;
        for (Category category : Category.values()) {
            if (sourceProductInfo.contains(category.getCategoryName())) {
                categoryId = category.getCategoryId();
                break;
            }
        }
        return categoryId;
    }

    /**
     * extracting product name from source info
     * @param sourceProductInfo source info about product
     * @return relevant product name
     */
    public static String getProductNameFromSourceProductInfo(String sourceProductInfo) {
        Pattern p = Pattern.compile("\"\\s(.+?),");
        Matcher m = p.matcher(sourceProductInfo);
        if (m.find()) {
            //System.out.println("product name  == " + m.group(1));
        }
        return m.group(1);
    }

    /**
     * create Product model from source info from Citilink
     *
     * @param sourceProductInfo String source info
     * @return Product, in which fields "name" and "categoryId" are relevant
     */
    public Product createProductModelFromSource(String sourceProductInfo) {
        String productName;
        long categoryId;
        categoryId = getGategoryFromSourceProductInfo(sourceProductInfo);
        productName = getProductNameFromSourceProductInfo(sourceProductInfo);
        Product product = new Product(categoryId, productName, "no description", 1);
        return product;
    }
}
