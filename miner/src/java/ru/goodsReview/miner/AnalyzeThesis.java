/*
* Date: 30.10.11
* Time: 21:57
* Author: 
* Artemij Chugreev 
* artemij.chugreev@gmail.com
*/
package ru.goodsReview.miner;

import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.model.ListOfReviews;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.core.model.Thesis;
import ru.goodsReview.storage.controller.ReviewDbController;
import ru.goodsReview.storage.controller.ThesisDbController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: ruslan
 * Date: 30.10.11
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */
public class AnalyzeThesis {
    public AnalyzeThesis() {
    }

    public void updateThesisByProductId(long productId){

        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("storage/src/scripts/beans.xml");
        javax.sql.DataSource dataSource = (javax.sql.DataSource) context.getBean("dataSource");

        ReviewDbController reviewDbController = new ReviewDbController(new SimpleJdbcTemplate(dataSource));
        List<Review> desiredReviews = new ArrayList<Review>();

        desiredReviews =  reviewDbController.getReviewsByProductId(productId);

        List<Review> derivedFromDbReviews = new ArrayList<Review>();
        derivedFromDbReviews = reviewDbController.getReviewsByProductId(productId);
        ListOfReviews new_listOfReviews = new ListOfReviews(derivedFromDbReviews);
        FrequencyAnalyzer newFrequencyAnalyzer = new FrequencyAnalyzer(new_listOfReviews);

        ThesisDbController thesisDbController = new ThesisDbController(new SimpleJdbcTemplate(dataSource));
        FrequencyAnalyzer  freqAnForSingleReview;
        ListOfReviews buffLOR = new ListOfReviews();
        Thesis currThesis;
        for(Review rev : desiredReviews){
            buffLOR.clear();
            buffLOR.addReview(rev);
            freqAnForSingleReview = new FrequencyAnalyzer(buffLOR);
            freqAnForSingleReview.makeFrequencyDictionary();
            for(Map.Entry<String, Integer> entry : freqAnForSingleReview.getWords().entrySet()){
                currThesis = new Thesis(entry.getValue(), entry.getKey());
                thesisDbController.addThesis(currThesis);
            }
        }

        ListOfReviews listOfReviews;
        listOfReviews = new ListOfReviews(desiredReviews);
        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer(listOfReviews);
        frequencyAnalyzer.makeFrequencyDictionary();

        for(Map.Entry<String, Integer> entry : frequencyAnalyzer.getWords().entrySet()){
            currThesis = new Thesis(entry.getValue(), entry.getKey());
            thesisDbController.addThesis(currThesis);
        }
    }
}