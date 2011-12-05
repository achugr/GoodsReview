package ru.goodsReview.analyzer;
/*
 *  Date: 04.12.11
 *   Time: 13:27
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.core.model.Thesis;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class ExtractThesisTest {

    private static SimpleJdbcTemplate jdbcTemplate;

    private static final Logger log = Logger.getLogger(Test.class);

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @org.junit.Test
    public void thesisExtractionTest(){
        {
            long  time = System.currentTimeMillis();
            Review review = new Review(2, "ноутбук очень мощный, ну просто отличная клавиатура", "anonim", time, "", 1, null, 1.0, 1.0, 1,1);
            ExtractThesis extractThesis = new ExtractThesis();
            List<Thesis> list =  extractThesis.doExtraction(review);
            assertEquals("ноутбук мощный", list.get(0).getContent());
            assertEquals("клавиатура отличная", list.get(1).getContent());
        }
        {
            long  time = System.currentTimeMillis();
            Review review = new Review(2, "+отличный дизайн+четкая картинка+громкий и отличный звук", "anonim", time, "", 1, null, 1.0, 1.0, 1,1);
            ExtractThesis extractThesis = new ExtractThesis();
            List<Thesis> list =  extractThesis.doExtraction(review);
            assertEquals("дизайн отличный", list.get(0).getContent());
            assertEquals("картинка четкая", list.get(1).getContent());
            assertEquals("звук громкий", list.get(2).getContent());
        }
    }
}
