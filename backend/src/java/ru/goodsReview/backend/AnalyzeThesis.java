/*
* Date: 30.10.11
* Time: 21:57
* Author:
* Artemij Chugreev
* artemij.chugreev@gmail.com
*/
package ru.goodsReview.backend;

import org.apache.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.core.model.Thesis;
import ru.goodsReview.core.model.ThesisUnique;
import ru.goodsReview.storage.controller.ProductDbController;
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
public class AnalyzeThesis extends TimerTask {
    private SimpleJdbcTemplate jdbcTemplate;
    private static final Logger log = org.apache.log4j.Logger.getLogger(AnalyzeThesis.class);


    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private enum TrashWords{
        trash1("на"), trash2("в"), trash3("и"), trash4("а"), trash5("с"), trash6("к"),
        trash7("при"), trash8("без"), trash9("кроме"), trash10("против"), trash11("от"),
        trash12("вместо"), trash13("за"), trash14("по"),
        trash15("про"), trash16("о"), trash17("для"), trash18("из"), trash19("до"),
        trash20("после"), trash21("перед"), trash22("не"), trash23("что"), trash24("но"),
        trash25("очень"), trash26("это"), trash27("я"), trash28("он"), trash29("она"),
        trash30("у"), trash31("его"), trash32("мне"),trash33("меня"),trash34("эти"),
        trash35("то"),trash36("же"), trash37("+"), trash38("-"), trash39("этом"),
        trash40("как"),trash41("так"),trash42("все"), trash44("1"),trash45("2"),
        trash46("3"),trash47("4"),trash48("5"),trash49("6"), trash50("7"),
        trash51("8"), trash52("9"), trash53("0"), trash54("бы"),trash55("там"),trash56("вы"),
        trash57("или"), trash58("1"),trash59("хоть"), trash60("чуть"), trash61("тут"),
        trash62("во"),trash63("еще"), trash64("да"), trash65("уже"), trash66("всё"), trash67("чем"),
        trash68("1."), trash69("2."),trash70("3."),trash71("4."),trash73("5."), trash74("пока"), trash75("нём"),
        trash76("него"), trash77("вот"), trash78("через"), trash79("под");

        private final String trash;

        public String getTrash(){
            return trash;
        }
        TrashWords(String string){
            this.trash = string;
        }
    }

    public AnalyzeThesis() {
    }

    public boolean isInTrashWords(String string){
        TrashWords[] trashWords = TrashWords.values();
        String str = string.toLowerCase();
        str = str.replaceAll("\\s+", "").trim();
        for(int i = 0; i < trashWords.length; ++i){
            if(str.equals(trashWords[i].getTrash())){
                return true;
            }
        }
        return false;
    }
    private Map<Long, Integer> numOfNonTrashWordsInReviews(List<Review> listOfReviews){
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(
                       "storage/src/scripts/beans.xml");
        DataSource dataSource = (DataSource) context.getBean("dataSource");
        ThesisDbController thesisDbController = new ThesisDbController(new SimpleJdbcTemplate(dataSource));
        List<Thesis> listOfThesis = new ArrayList<Thesis>();
        Map<Long, Integer> answer = new HashMap<Long, Integer>();
        List<Review> buffRev = new ArrayList<Review>();
        int freq = 0;
        for(Review rev : listOfReviews){
            buffRev.clear();
            buffRev.add(rev);
            FrequencyAnalyzer freqAnForSingleReview = new FrequencyAnalyzer(buffRev);
            freqAnForSingleReview.makeFrequencyDictionary();
            freq = 0;
            for(Map.Entry<String, Integer> entry : freqAnForSingleReview.getWords().entrySet()){
                if(!isInTrashWords(entry.getKey())){
                    freq += entry.getValue();
                }
            }
            answer.put(rev.getId(), freq);
        }
        return answer;
    }
    private Map<String, Integer> fillingMapOfThesisUnique(List<Review> reviewList) {

        Map<String, Integer> uniqueThesises = new HashMap<String, Integer>();
        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer(reviewList);
        frequencyAnalyzer.makeFrequencyDictionary();
        Integer currfreq;
        for (Map.Entry<String, Integer> entry : frequencyAnalyzer.getWords().entrySet()) {
            // Check if currThesisUnique is among TrashWords
            if(!isInTrashWords(entry.getKey())){
                currfreq = uniqueThesises.get(entry.getKey());
                uniqueThesises.put(entry.getKey(), entry.getValue() + (currfreq == null ? 0 : currfreq));
            }
        }
        return uniqueThesises;
    }

    private Map<String, Long> fillMapOfThesisId(Map<String, Integer> thesisUniques) {
        Map<String, Long> tableOfId = new HashMap<String, Long>();   //
        ThesisUniqueDbController thesisUniqueDbController = new ThesisUniqueDbController(jdbcTemplate);
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

    /**
     * For each thesis this method will count the number of
     * reviews it belongs to
     * @param listOfReviews - list of reviews we get thesises from
     * @return - Map of String, Integer where key - content field of thesis and
     *           value - desired number of reviews
     */
    private Map<String, Integer> getNumOfReviewsContainsParticularThesis(List<Review> listOfReviews){

        Map<String, Integer> answ = new HashMap<String, Integer>();
        List<Review> buffLOR = new ArrayList<Review>();
        FrequencyAnalyzer freqAnForSingleReview;
        for (Review rev : listOfReviews) {
            buffLOR.clear();
            buffLOR.add(rev);
            freqAnForSingleReview = new FrequencyAnalyzer(buffLOR);
            freqAnForSingleReview.makeFrequencyDictionary();
            for (Map.Entry<String, Integer> entry : freqAnForSingleReview.getWords().entrySet()) {
                 if(!answ.containsKey(entry.getKey())){
                     answ.put(entry.getKey(), 1);
                 }
                 else{
                     answ.put(entry.getKey(), answ.get(entry.getKey()) + 1);
                 }
            }
        }
        return answ;
    }
    /**
     *
     * @param listOfReviews
     * @param idTable
     */
    private void addThesisesToDB(List<Review> listOfReviews, Map<String, Long> idTable){
         FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(
                       "storage/src/scripts/beans.xml");
        DataSource dataSource = (DataSource) context.getBean("dataSource");

        ThesisDbController thesisDbController = new ThesisDbController(new SimpleJdbcTemplate(dataSource));
        ReviewDbController reviewDbController = new ReviewDbController(new SimpleJdbcTemplate(dataSource));
        ThesisUniqueDbController thesisUniqueDbController = new ThesisUniqueDbController(new SimpleJdbcTemplate(dataSource));
        FrequencyAnalyzer freqAnForSingleReview;
        List<Review> buffLOR = new ArrayList<Review>();
        Thesis currThesis;
        //double sumFreq = (double) thesisUniqueDbController.getSumFrequency();
        double numOfWordsInCurrReview;
        double numOfReviews = (double) reviewDbController.getNumOfReviews();
        Map<String, Integer> NumOfReviewsContainsParticularThesis = getNumOfReviewsContainsParticularThesis(listOfReviews);
        Map<Long, Integer> NonTrashWordsInReviews =  numOfNonTrashWordsInReviews(listOfReviews);
        double tf, idf;
        // Here we got some tough stuff
        for (Review rev : listOfReviews) {
            buffLOR.clear();
            buffLOR.add(rev);
            numOfWordsInCurrReview = NonTrashWordsInReviews.get(rev.getId());
            buffLOR.add(rev);
            freqAnForSingleReview = new FrequencyAnalyzer(buffLOR);
            freqAnForSingleReview.makeFrequencyDictionary();
            for (Map.Entry<String, Integer> entry : freqAnForSingleReview.getWords().entrySet()) {
                if(!isInTrashWords(entry.getKey())){
                    currThesis = new Thesis(rev.getId(), idTable.get(entry.getKey()), entry.getKey(),
                            entry.getValue(), 0, 0, 0);
                    tf = countTF(entry.getValue(),numOfWordsInCurrReview);
                    idf = countIDF(numOfReviews, NumOfReviewsContainsParticularThesis.get(entry.getKey()));
                    currThesis.setTfidf(tf * idf);
                    thesisDbController.addThesis(currThesis);
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

    /*private double countTF(ThesisUnique thesisUnique, double SumFrequency){
        double longFreq = (double) thesisUnique.getFrequency();
        double TF = longFreq / SumFrequency;
        return TF;
    }*/
    private double countTF(double frequency, double sumFrequency){
        return frequency/sumFrequency;
    }
    /*private double countIDF(ThesisUnique thesisUnique, double numOfReviews, double numOfReviewsContainsTU){
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(
                       "storage/src/scripts/beans.xml");
        DataSource dataSource = (DataSource) context.getBean("dataSource");
        ReviewDbController reviewDbController = new ReviewDbController(new SimpleJdbcTemplate(dataSource));
        ThesisDbController thesisDbController = new ThesisDbController(new SimpleJdbcTemplate(dataSource));
        double numOfReviews = (double) reviewDbController.getNumOfReviews();
        double numOfReviewsContainsTU = (double) thesisDbController.getNumOfReviewContainsThesisUnique(thesisUnique.getContent());
        double IDF = Math.log((double) (numOfReviews) / (numOfReviewsContainsTU));
        return IDF;
    }*/
    private double countIDF(double numOfReviews, double numOfReviewsContainsTU){
        return Math.log(numOfReviews/numOfReviewsContainsTU);
    }
//    public double countTFIDF(ThesisUnique thesisUnique){
//        return countTF(thesisUnique) * countIDF(thesisUnique);
//    }
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
