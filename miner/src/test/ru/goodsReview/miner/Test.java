package ru.goodsReview.miner;

import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.storage.controller.ReviewDbController;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Artemij
 * Date: 15.10.11
 * Time: 2:32
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String[] args) {

        /*HashMap<String, Integer> thesis1= new HashMap<String, Integer >();
        HashMap<String, Integer> thesis2= new HashMap<String, Integer >();
        HashMap<String, Integer> thesis3= new HashMap<String, Integer >();
        thesis1.put("bad",1);
        thesis1.put("good",3);
        ThesisHashTable thesisHashTable = new ThesisHashTable(thesis1);
         thesis2.put("bad",4);
        thesis2.put("aaa",2);
        thesis2.put("good",9);
        //thesisHashTable.add(thesis2);
        //thesisHashTable.print();
        thesis3.put("aaa", 6);
        thesis3.put("bad",14);
        thesis3.put("ololo",5);
        List<Map<String,Integer>>  list1 = new ArrayList<Map<String, Integer>>();
        list1.add(thesis2);
        list1.add(thesis3);
        thesisHashTable.addSeveralThesisTables(list1);
        thesisHashTable.print();*/

        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("storage/src/scripts/beans.xml");
        SimpleJdbcTemplate jdbcTemplate = (SimpleJdbcTemplate) context.getBean("jdbcTemplate");
        ReviewDbController reviewDbController1 = new ReviewDbController(jdbcTemplate);

		Review new_review1 = new Review(1, 1, "good good good phone", null,new Date(), null, 1, null, 0, 0, 0, 0);
		Review new_review2 = new Review(1, 1, "Not good enough", null,new Date(), null, 1, null, 0, 0, 0, 0);

        reviewDbController1.addReview(new_review2);
		reviewDbController1.addReview(new_review1);

        AnalyzeThesis analyzeThesis = new AnalyzeThesis();
        analyzeThesis.updateThesisByProductId(1);

       /* ListOfReviews citilinkReviews;
        citilinkReviews = new ListOfReviews();
        //review initialization
        CitilinkReview citilinkReview = new CitilinkReview(1, "my product", "good", "bad", "i hate this laptop",5, 2);
        CitilinkReview citilinkReview1 = new CitilinkReview(1, "my product", "good", "bad", "i like this phone", 5, 2);
        //reviews list initialization
        citilinkReviews.addCitilinkReview(citilinkReview);
        citilinkReviews.addCitilinkReview(citilinkReview1);

        citilinkReviews.printCitilinkReviews();

        KGrams kGrams = new KGrams(citilinkReviews);
        kGrams.printKGramsTable();
        kGrams.comparecitilinkReviews();

        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer(citilinkReviews);
        frequencyAnalyzer.makeFrequencyDictionary();
        frequencyAnalyzer.printFrequencyDictionary();  */

    }
}
