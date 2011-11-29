package ru.goodsReview.miner;
/*
 *  Date: 11.11.11
 *   Time: 22:20
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.storage.controller.ReviewDbController;

import javax.sql.DataSource;
import java.util.List;

/**
 * in this class i am testing methods for grabbing information and insert it into DB
 */
public class Test {
    private static SimpleJdbcTemplate simpleJdbcTemplate;

    /**
     * here i use beans.xml from analyzer, because it contains all i need, but doesn't start downloading pages from citilink
     */
    public static void setJdbcTemplate(){
        final FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(
                "analyzer/src/scripts/beans.xml");
        DataSource dataSource = (DataSource) context.getBean("dataSource");
        simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    public static void printReviewByProductId(long id){
        ReviewDbController reviewDbController = new ReviewDbController(simpleJdbcTemplate);
        List<Review> reviewList = reviewDbController.getReviewsByProductId(id);
        for(Review review: reviewList){
            System.out.println("review ->> " + review.getContent());
        }
    }
    public static void main(String [] args){
        setJdbcTemplate();
        printReviewByProductId(1366);
    }
}
