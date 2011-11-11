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
import ru.goodsReview.miner.utils.CitilinkDataTransformator;
import ru.goodsReview.storage.controller.ReviewDbController;

import javax.sql.DataSource;
import java.util.List;

// test class for miner module
public class Test {

    public static void main(String [] args){
        //here i use beans.xml from backend, because it contains all i need, but doesn't start downloading pages from citilink
        final FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(
                "backend/src/scripts/beans.xml");
        DataSource dataSource = (DataSource) context.getBean("dataSource");
        SimpleJdbcTemplate simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);

        ReviewDbController reviewDbController = new ReviewDbController(simpleJdbcTemplate);
        List<Review> reviewList;
        reviewList = reviewDbController.getReviewsByProductId(1);
        CitilinkDataTransformator dataTransformator = new CitilinkDataTransformator();
        //cleaning data from trash-content
        for(Review review : reviewList){
            //review = dataTransformator.clearReviewFromTrash(review);
            System.out.println("REVIEW ->> " + review.getContent());
        }
    }
}
