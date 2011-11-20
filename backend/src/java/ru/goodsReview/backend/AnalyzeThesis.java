/*
* Date: 30.10.11
* Time: 21:57
* Author:
* Artemij Chugreev
* artemij.chugreev@gmail.com
*/
package ru.goodsReview.backend;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.db.exception.StorageException;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.core.model.Thesis;
import ru.goodsReview.core.model.ThesisUnique;
import ru.goodsReview.storage.controller.ProductDbController;
import ru.goodsReview.storage.controller.ReviewDbController;
import ru.goodsReview.storage.controller.ThesisDbController;
import ru.goodsReview.storage.controller.ThesisUniqueDbController;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: ruslan
 * Date: 30.10.11
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */
public class AnalyzeThesis extends TimerTask {
    private SimpleJdbcTemplate jdbcTemplate;
    private static final Logger log = org.apache.log4j.Logger.getLogger(AnalyzeThesis.class);

    @Required
    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public AnalyzeThesis() {
    }

    private Map<String, Integer> fillingMapOfThesisUnique(List<Review> reviewList) {

        Map<String, Integer> uniqueThesises = new HashMap<String, Integer>();
        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer(reviewList);
        frequencyAnalyzer.makeFrequencyDictionary();
        Integer currfreq;
        for (Map.Entry<String, Integer> entry : frequencyAnalyzer.getWords().entrySet()) {
            currfreq = uniqueThesises.get(entry.getKey());
            uniqueThesises.put(entry.getKey(), entry.getValue() + (currfreq == null ? 0 : currfreq));
        }
        return uniqueThesises;
    }

    private Map<String, Long> fillMapOfThesisId(Map<String, Integer> thesisUniques) {
        Map<String, Long> tableOfId = new HashMap<String, Long>();   //
        ThesisUniqueDbController thesisUniqueDbController = new ThesisUniqueDbController(jdbcTemplate);
        ThesisUnique currThesisUnique;
        ThesisUnique recievedTU;
        Timestamp date = new Timestamp(System.currentTimeMillis());
        for (Map.Entry<String, Integer> entry : thesisUniques.entrySet()) {
            currThesisUnique = new ThesisUnique(entry.getKey(), entry.getValue(), date, 0, 0);
            try {
                thesisUniqueDbController.addThesisUnique(currThesisUnique);
            } catch (StorageException e) {
                // TODO: handle this situation.
                e.printStackTrace();
            }
            recievedTU = thesisUniqueDbController.getThesisUniqueByContent(entry.getKey());
            tableOfId.put(entry.getKey(), recievedTU.getId());
        }
        return tableOfId;
    }

    private void addThesisesToDB(List<Review> listOfR, Map<String, Long> idTable) {
        ThesisDbController thesisDbController = new ThesisDbController(jdbcTemplate);
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
                try {
                    thesisDbController.addThesis(currThesis);
                } catch (StorageException e) {
                    // TODO: handle this situation.
                    e.printStackTrace();
                }
            }
        }

    }

    public void updateThesisByProductId(long productId) {
        ReviewDbController reviewDbController = new ReviewDbController(jdbcTemplate);
        List<Review> desiredReviews;

        //select reviews from database by product id
        desiredReviews = reviewDbController.getReviewsByProductId(productId);

        //filling map of unique thesis
        Map<String, Integer> thesisUniques = fillingMapOfThesisUnique(desiredReviews);

        // Создаём таблицу thesis_unique
        Map<String, Long> tableOfId = fillMapOfThesisId(thesisUniques);

        addThesisesToDB(desiredReviews, tableOfId);
    }

    @Override
    public void run() {
        ProductDbController productDbController = new ProductDbController(jdbcTemplate);
        List<Product> productList;
        productList = productDbController.getAllProducts();
        log.info("Thesis Analyzer begins work");
        for (Product product : productList) {
            log.info("Extracting thesis on " + product.getName());
            updateThesisByProductId(product.getId());
        }
        log.info("Thesis Analyzer successful completed");
    }
}
