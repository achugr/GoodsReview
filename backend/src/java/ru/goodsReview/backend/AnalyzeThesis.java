/*
* Date: 30.10.11
* Time: 21:57
* Author: 
* Artemij Chugreev 
* artemij.chugreev@gmail.com
*/
package ru.goodsReview.backend;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.model.ListOfReviews;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.core.model.Thesis;
import ru.goodsReview.core.model.ThesisUnique;
import ru.goodsReview.storage.controller.ReviewDbController;
import ru.goodsReview.storage.controller.ThesisDbController;
import ru.goodsReview.storage.controller.ThesisUniqueDbController;

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

    //public int

	public void updateThesisByProductId(long productId) {

        //select reviews from database by product id
		ReviewDbController reviewDbController = new ReviewDbController(jdbcTemplate);
		List<Review> desiredReviews;
        desiredReviews = reviewDbController.getReviewsByProductId(productId);

		List<Review> derivedFromDbReviews;
		derivedFromDbReviews = reviewDbController.getReviewsByProductId(productId);
		ListOfReviews new_listOfReviews = new ListOfReviews(derivedFromDbReviews);
		FrequencyAnalyzer newFrequencyAnalyzer = new FrequencyAnalyzer(new_listOfReviews);


        ThesisUniqueDbController thesisUniqueDbController = new ThesisUniqueDbController(jdbcTemplate);
        ListOfReviews listOfReviews;
        listOfReviews = new ListOfReviews(desiredReviews);
        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer(listOfReviews);
        frequencyAnalyzer.makeFrequencyDictionary();
        Date date = new Date();
        Map<String, Integer> thesisUniques = new HashMap<String, Integer>();
        Map<String, Long> tableOfId = new HashMap<String, Long>();   //
        Integer currfreq;
        for(Map.Entry<String, Integer> entry : frequencyAnalyzer.getWords().entrySet()){
            //currThesisUnique = new ThesisUnique(entry.getKey(), entry.getValue(), date, 0, 0);
            currfreq = thesisUniques.get(entry.getKey());
            thesisUniques.put(entry.getKey(), entry.getValue() + (currfreq == null ? 0 : currfreq));
            //tableOfId.put(entry.getKey(), currThesisUnique.getId());
        }

        // Создаём таблицу thesis_unique
        // True hardcore
        ThesisUnique currThesisUnique;
        ThesisUnique recievedTU;
        for(Map.Entry<String, Integer> entry : thesisUniques.entrySet()){
            currThesisUnique = new ThesisUnique(entry.getKey(), entry.getValue(), date, 0, 0);
            thesisUniqueDbController.addThesisUnique(currThesisUnique);
            recievedTU = thesisUniqueDbController.getThesisUniqueByContent(entry.getKey());
            tableOfId.put(entry.getKey(), recievedTU.getId());
        }


        ThesisDbController thesisDbController = new ThesisDbController(jdbcTemplate);
        FrequencyAnalyzer  freqAnForSingleReview;
        ListOfReviews buffLOR = new ListOfReviews();
        Thesis currThesis;
        // Here we got some tough stuff
        for(Review rev : desiredReviews){
            buffLOR.clear();
            buffLOR.addReview(rev);
            freqAnForSingleReview = new FrequencyAnalyzer(buffLOR);
            freqAnForSingleReview.makeFrequencyDictionary();
            for(Map.Entry<String, Integer> entry : freqAnForSingleReview.getWords().entrySet()){
                currThesis = new Thesis(rev.getId(), tableOfId.get(entry.getKey()), entry.getKey(), entry.getValue(),0,0);
                thesisDbController.addThesis(currThesis);
            }
        }
    }
}