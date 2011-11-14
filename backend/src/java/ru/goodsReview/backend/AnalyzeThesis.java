/*
* Date: 30.10.11
* Time: 21:57
* Author:
* Artemij Chugreev
* artemij.chugreev@gmail.com
*/
package ru.goodsReview.backend;

import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.core.model.Thesis;
import ru.goodsReview.core.model.ThesisUnique;
import ru.goodsReview.storage.controller.ReviewDbController;
import ru.goodsReview.storage.controller.ThesisDbController;
import ru.goodsReview.storage.controller.ThesisUniqueDbController;

import javax.sql.DataSource;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: ruslan
 * Date: 30.10.11
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */
public class AnalyzeThesis {
    private SimpleJdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public AnalyzeThesis() {
    }

    private Map<String, Integer> fillingMapOfThesisUnique( List<Review> reviewList){

        Map<String, Integer>  uniqueThesises = new HashMap<String, Integer>();
        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer(reviewList);
        frequencyAnalyzer.makeFrequencyDictionary();
        Integer currfreq;
        for (Map.Entry<String, Integer> entry : frequencyAnalyzer.getWords().entrySet()) {
            currfreq = uniqueThesises.get(entry.getKey());
            uniqueThesises.put(entry.getKey(), entry.getValue() + (currfreq == null ? 0 : currfreq));
        }
        return uniqueThesises;
    }
    private Map<String, Long> fillMapOfThesisId(Map<String, Integer> thesisUniques){
         FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(
                       "storage/src/scripts/beans.xml");
        DataSource dataSource = (DataSource) context.getBean("dataSource");
        Map<String, Long> tableOfId = new HashMap<String, Long>();   //
        //ThesisUniqueDbController thesisUniqueDbController = new ThesisUniqueDbController(jdbcTemplate);
        ThesisUniqueDbController thesisUniqueDbController = new ThesisUniqueDbController(
                new SimpleJdbcTemplate(dataSource));
        ThesisUnique currThesisUnique;
        ThesisUnique recievedTU;
        Date date = new Date();
        for (Map.Entry<String, Integer> entry : thesisUniques.entrySet()) {
            currThesisUnique = new ThesisUnique(entry.getKey(), entry.getValue(), date, 0, 0);
            thesisUniqueDbController.addThesisUnique(currThesisUnique);
            recievedTU = thesisUniqueDbController.getThesisUniqueByContent(entry.getKey());
            tableOfId.put(entry.getKey(), recievedTU.getId());
        }
        return tableOfId;
    }

    private void addThesisesToDB(List<Review> listOfR, Map<String, Long> idTable){
         FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(
                       "storage/src/scripts/beans.xml");
        DataSource dataSource = (DataSource) context.getBean("dataSource");
        //ThesisDbController thesisDbController = new ThesisDbController(jdbcTemplate);

        ThesisDbController thesisDbController = new ThesisDbController(new SimpleJdbcTemplate(dataSource));
        FrequencyAnalyzer freqAnForSingleReview;
        List<Review> buffLOR = new ArrayList<Review>();
        Thesis currThesis;
        // Here we got some tough stuff
        for (Review rev : listOfR) {
            buffLOR.clear();
            buffLOR.add(rev);
            freqAnForSingleReview = new FrequencyAnalyzer(buffLOR);
            freqAnForSingleReview.makeFrequencyDictionary();
            for (Map.Entry<String, Integer> entry : freqAnForSingleReview.getWords().entrySet()) {
                currThesis = new Thesis(rev.getId(), idTable.get(entry.getKey()), entry.getKey(),
                        entry.getValue(), 0, 0);
                thesisDbController.addThesis(currThesis);
            }
        }

    }
    public void updateThesisByProductId(long productId) {

        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(
                       "storage/src/scripts/beans.xml");
        DataSource dataSource = (DataSource) context.getBean("dataSource");
        //ReviewDbController reviewDbController = new ReviewDbController(jdbcTemplate);
        ReviewDbController reviewDbController = new ReviewDbController(new SimpleJdbcTemplate(dataSource));
        List<Review> desiredReviews;

        //select reviews from database by product id
        desiredReviews = reviewDbController.getReviewsByProductId(productId);

        //filling map of unique thesis
        Map<String, Integer> thesisUniques = fillingMapOfThesisUnique(desiredReviews);

        // Создаём таблицу thesis_unique
        Map<String, Long> tableOfId = fillMapOfThesisId(thesisUniques);

        addThesisesToDB(desiredReviews, tableOfId);
    }
}
