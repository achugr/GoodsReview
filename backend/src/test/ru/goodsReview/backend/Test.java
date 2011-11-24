package ru.goodsReview.backend;

import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.db.exception.StorageException;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.storage.controller.ProductDbController;
import ru.goodsReview.storage.controller.ReviewDbController;
import ru.goodsReview.storage.controller.ThesisDbController;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/*
 *  Date: 15.10.11
 *   Time: 0:32
 *   Author:
 *      Artemij Chugreev
 *      artemij.chugreev@gmail.com
 */

public class Test {
    private static SimpleJdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void main(String[] args) throws StorageException {

        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(
                       "storage/src/scripts/beans.xml");
        DataSource dataSource = (DataSource) context.getBean("dataSource");
        SimpleJdbcTemplate simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);


        ReviewDbController reviewDbController = new ReviewDbController(simpleJdbcTemplate);
        ProductDbController productDbController = new ProductDbController(simpleJdbcTemplate);
        ThesisDbController th = new ThesisDbController(simpleJdbcTemplate);

        Review rev1 = new Review(1, "beer vodka tequila");
        Review rev2 = new Review(1, "vodka cogniac beer vodka");

        //reviewDbController.addReview(rev1);
        //reviewDbController.addReview(rev2);
        List<Review> list = reviewDbController.getReviewsByProductId(1);

        AnalyzeThesis analyzeThesis = new AnalyzeThesis(simpleJdbcTemplate);
        //Map<String, Integer> map =  analyzeThesis.simpleAdditionOfThesis(list);
        //Map<String, Long> map2 = analyzeThesis.simpleFillingTU(map);
        //analyzeThesis.simpleFillingTUIdField(map2);
        Map<Long, Double> map = analyzeThesis.mapOfTFIDF(list);
        for(Map.Entry<Long, Double> entry : map.entrySet()){
            System.out.println(entry.getValue());
        }

        /*
        Product pro1 = new Product(1, "Motorola", "bad phone", 0);
        Product pro2 = new Product(1, "Motorola Razer V3", "very bad phone",0);
        productDbController.addProduct(pro1);
        productDbController.addProduct(pro2);

        Review review1 = new Review(1, "good good good phone", null, new Date(), null, 1, null, 0, 0, 0, 0);
        Review review2 = new Review(1, "Not good enough", null, new Date(), null, 1, null, 0, 0, 0, 0);
        Review review3 = new Review(1, "good bad good phone", null, new Date(), null, 1, null, 0, 0, 0, 0);
        Review review4 = new Review(1, " Still not good enough", null, new Date(), null, 1, null, 0, 0, 0, 0);

        reviewDbController.addReview(review1);
        reviewDbController.addReview(review2);
        reviewDbController.addReview(review3);
        reviewDbController.addReview(review4);

        AnalyzeThesis analyzeThesis = new AnalyzeThesis();

        analyzeThesis.updateThesisByProductId(1);

        ThesisDbController thesisDbController = new ThesisDbController(simpleJdbcTemplate);
        List<Thesis> thesisList = thesisDbController.getThesesByProductId(1);
        for(Thesis thesis: thesisList){
            System.out.println(thesis.getContent());
        }
        */
    }
}
