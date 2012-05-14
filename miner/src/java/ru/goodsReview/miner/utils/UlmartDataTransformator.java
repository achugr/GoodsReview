package ru.goodsReview.miner.utils;

import ru.goodsReview.core.model.Product;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.miner.CategoryConfig;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: timur
 * Date: 14.05.12
 * Time: 6:23
 * To change this template use File | Settings | File Templates.
 */
public class UlmartDataTransformator extends DataTransformator{

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
     * Clear review content
     * from whitespace characters in begin of review content,
     * from trash html-tags in content
     *
     * @param review review, in which you will clear content from trash
     * @return Review without trash in content
     */
    public Review clearReviewFromTrash(Review review) {
        String clearReview = clearReviewFromTags(review.getContent());
        clearReview = clearBadSymbols(clearReview);
        clearReview = clearReview.trim();
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
     *
     * @param sourceProductInfo source info about product
     * @return relevant product name
     */
    public static String getProductNameFromSourceProductInfo(String sourceProductInfo, CategoryConfig.RegExp regExp) {
        Pattern p = Pattern.compile(regExp.getExpression());
        Matcher m = p.matcher(sourceProductInfo);
        if (m.find()) {
            List<Integer> groupList = regExp.getGroups();
            StringBuilder name = new StringBuilder();
            for(int i = 0; i < groupList.size(); i++){
                name.append(m.group(groupList.get(i)));
                if(i < groupList.size()-1){
                    name.append(" ");
                }
            }
            return name.toString().trim();
        }
        return sourceProductInfo;
    }

    /**
     * create Product model from source info from Ulmart
     *
     * @param sourceProductInfo String source info
     * @return Product, in which fields "name" and "categoryId" are relevant
     */
    public Product createProductModelFromSource(String sourceProductInfo, CategoryConfig.RegExp regExp) {
        String productName;
        long categoryId;
        categoryId = getGategoryFromSourceProductInfo(sourceProductInfo);
        productName = getProductNameFromSourceProductInfo(sourceProductInfo, regExp);
        Product product = new Product(categoryId, productName, "no description", 1);
        return product;
    }
}
