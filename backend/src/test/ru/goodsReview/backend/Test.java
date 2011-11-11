package ru.goodsReview.backend;

import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.storage.controller.ProductDbController;
import ru.goodsReview.storage.controller.ReviewDbController;

import javax.sql.DataSource;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Artemij
 * Date: 15.10.11
 * Time: 2:32
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    private static SimpleJdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

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
         final FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(
                "backend/src/scripts/beans.xml");
        DataSource dataSource = (DataSource) context.getBean("dataSource");

        ProductDbController productDbController = new ProductDbController(new SimpleJdbcTemplate(dataSource));
        ReviewDbController reviewDbController = new ReviewDbController(new SimpleJdbcTemplate(dataSource));

        Product pro1 = new Product(1, "Motorola", "bad phone", 0);
        Product pro2 = new Product(1, "Motorola Razer V3", "very bad phone",0);
        productDbController.addProduct(pro1);
        productDbController.addProduct(pro2);

        //TODO please, don't name variables with "_", we must keep in common code style (Artemij)
        //TODO first argument in this constructor - id of Review, in db it's autoincrement, please, use constructor without id
        Review new_review1 = new Review(1, 1, "good good good phone", null, new Date(), null, 1, null, 0, 0, 0, 0);
        Review new_review2 = new Review(2, 1, "Not good enough", null, new Date(), null, 1, null, 0, 0, 0, 0);
        Review new_review3 = new Review(3, 1, "good bad good phone", null, new Date(), null, 1, null, 0, 0, 0, 0);
        Review new_review4 = new Review(4, 1, " Still not good enough", null, new Date(), null, 1, null, 0, 0, 0, 0);


        reviewDbController.addReview(new_review1);
        reviewDbController.addReview(new_review2);
        reviewDbController.addReview(new_review3);
        reviewDbController.addReview(new_review4);

        AnalyzeThesis analyzeThesis = new AnalyzeThesis();
        analyzeThesis.updateThesisByProductId(1);
        //analyzeThesis.updateThesisByProductId(2);

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
