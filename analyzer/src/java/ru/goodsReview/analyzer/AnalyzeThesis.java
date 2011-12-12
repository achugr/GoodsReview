/*
* Date: 30.10.11
* Time: 21:57
* Author:
* Artemij Chugreev
* artemij.chugreev@gmail.com
*/
package ru.goodsReview.analyzer;

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
//import java.lang.System.currentTimeMillis();

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
    public void setJdbcTemplate() {
        this.jdbcTemplate = jdbcTemplate;
    }

    public AnalyzeThesis(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Here we got the list of trash words we don't want to
     * have among ThesisUniques
     */
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
        trash76("него"), trash77("вот"), trash78("через"), trash79("под"),trash80("ли"), trash81("почти");

        private final String trash;

        public String getTrash(){
            return trash;
        }
        TrashWords(String string){
            this.trash = string;
        }
    }

    /**
     * This method checks if TrashWords contain "string"
     * @param string - want to know if among TrashWords
     * @return  true if "string" coincides with some trash word; false - if not
     */
    private boolean isInTrashWords(String string){
        TrashWords[] trashWords = TrashWords.values();
        String str = string.toLowerCase();
        str = str.replaceAll("\\s+", "").trim();
        for(int i = 0; i < trashWords.length; ++i){
            if(str.equals(trashWords[i].getTrash()) || (!Character.isLetter(str.charAt(0)))){
                return true;
            }
        }
        return false;
    }

    /**
     * For each review in @param method counts number of non trash words;
     * Need this data to count TF for each thesis;
     * @param listOfReviews - for this list we count
     * @return Map of pairs (id of review, number of non trash words in review)
     */
    private Map<Long, Integer> numOfNonTrashWordsInReviews(List<Review> listOfReviews){
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
    /**
     * For each thesis counts the number of
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
     * counts TF
     * @param frequency - frequency of particular thesisUnique in particular review
     * @param sumFrequency - number of non trash words in review
     * @return  term frequency
     */
    private double countTF(double frequency, double sumFrequency){
        return frequency/sumFrequency;
    }

    /**
     * counts IDF
     * @param numOfReviews - number of all reviews on products
     * @param numOfReviewsContainsTU - number of reviews contain particular thesisUnique
     * @return inverse document frequency
     */
    private double countIDF(double numOfReviews, double numOfReviewsContainsTU){
        return Math.log(numOfReviews/numOfReviewsContainsTU);
    }
    /**
     * Additioning of thesises in DB (non of  them are Trashword)
     * Each thesis hasn't thesisUniqueId field
     * @param listOfReviews - the soucre we get thesises from
     * @return map, where key - a word and value - its frequency
     * @throws StorageException
     */
    private Map<String, Integer> additioningOfThesisesAndGettingOfTUs(List<Review> listOfReviews) throws StorageException {
       ThesisDbController thesisDbController = new ThesisDbController(jdbcTemplate);
       FrequencyAnalyzer freqAnForSingleReview;
       List<Review> buffLOR = new ArrayList<Review>();
       Thesis currThesis;
       Map<String, Integer> thesisUniques = new HashMap<String, Integer>();
       // Here we got some tough stuff
       for (Review rev : listOfReviews) {
           buffLOR.clear();
           buffLOR.add(rev);
           freqAnForSingleReview = new FrequencyAnalyzer(buffLOR);
           freqAnForSingleReview.makeFrequencyDictionary();
           for (Map.Entry<String, Integer> entry : freqAnForSingleReview.getWords().entrySet()) {
               if(!isInTrashWords(entry.getKey())){
                   currThesis = new Thesis(rev.getId(), 0, entry.getKey(),
                           entry.getValue(), 0, 0);
                   if(!thesisUniques.containsKey(entry.getKey())){
                        thesisUniques.put(entry.getKey(), entry.getValue());
                   }else{
                        thesisUniques.put(entry.getKey(), thesisUniques.get(entry.getKey()) + entry.getValue());
                   }
                   thesisDbController.addThesis(currThesis);
               }
           }
       }
        return thesisUniques;
    }
    /**
     * Additioning of thesisUniques in DB
     * @param thesisUniques - map, where key - a word and value - its frequency
     * @return  map, where key - thesisUnique content, value - its id in table
     */
    private Map<String, Long> AdditionOffTUtoDb(Map<String, Integer> thesisUniques){
        Map<String, Long> tableOfId = new HashMap<String, Long>();
        ThesisUniqueDbController thesisUniqueDbController = new ThesisUniqueDbController(jdbcTemplate);
        ThesisUnique currThesisUnique;
        ThesisUnique recievedTU;
        Timestamp time = new Timestamp(System.currentTimeMillis());
        for (Map.Entry<String, Integer> entry : thesisUniques.entrySet()) {
            currThesisUnique = new ThesisUnique(entry.getKey(), entry.getValue(), time, 0, 0);
            try {
                thesisUniqueDbController.addThesisUnique(currThesisUnique);
            } catch (StorageException e) {
                // TODO: handle this situation.
                e.printStackTrace();
                System.out.print(entry.getKey());
            }
            recievedTU = thesisUniqueDbController.getThesisUniqueByContent(entry.getKey());
            tableOfId.put(entry.getKey(), recievedTU.getId());
        }
        return tableOfId;
    }
    /**
     * filling TUid parametrs in each thesis
     * @param tableOfId - map, where key - thesisUnique content, value - its id in table
     */
    private void FillingTUIdParam(Map<String, Long> tableOfId, long productId){
        //считываем из базы все тезисы и дописываем в них поля!
        ThesisDbController thesisDbController = new ThesisDbController(jdbcTemplate);
        List<Thesis> listOfThesis = thesisDbController.getThesesByProductId(productId);
        for(Thesis thesis : listOfThesis){
            thesisDbController.setThesisUniqueId(thesis.getId(), tableOfId.get(thesis.getContent()));
        }
    }
    /**
     * computing tfidf for each thesis in DB
     * @param listOfReviews
     * @return  map, key - thesis id in DB, value - its tfidf
     */
    public Map<Long, Double> mapOfTFIDF(List<Review> listOfReviews){
        Map<Long, Double> mapOfTFIDF = new HashMap<Long, Double>();
        //перебираем все тезисы
        ThesisDbController thesisDbController = new ThesisDbController(jdbcTemplate);
        ReviewDbController reviewDbController = new ReviewDbController(jdbcTemplate);
        List<Thesis> listOfThesis = thesisDbController.getAllTheses();
        double numOfWordsInCurrReview;
        double numOfReviews = (double) reviewDbController.getAllReviews().size();
        double tf, idf;
        Map<String, Integer> NumOfReviewsContainsParticularThesis = getNumOfReviewsContainsParticularThesis(listOfReviews);
        Map<Long, Integer> NonTrashWordsInReviews =  numOfNonTrashWordsInReviews(listOfReviews);
        for(Thesis thesis : listOfThesis){
              numOfWordsInCurrReview = NonTrashWordsInReviews.get(thesis.getReviewId());
              tf = countTF(thesis.getFrequency(), numOfWordsInCurrReview);
              idf = countIDF(numOfReviews, NumOfReviewsContainsParticularThesis.get(thesis.getContent()));
              mapOfTFIDF.put(thesis.getId(), tf * idf);
        }
        return mapOfTFIDF;
    }

    public void updateThesisByProductId(long productId) throws StorageException {
        ReviewDbController reviewDbController = new ReviewDbController(jdbcTemplate);
        List<Review> desiredReviews;
        desiredReviews = reviewDbController.getReviewsByProductId(productId);
        Map<String, Integer> pairsTUandFreq = additioningOfThesisesAndGettingOfTUs(desiredReviews);
        Map<String, Long> pairsTUandId = AdditionOffTUtoDb(pairsTUandFreq);
        FillingTUIdParam(pairsTUandId, productId);
    }
    public void printTFIDF(Map<Long, Double> mapOfTFIDF)
    {
        ProductDbController productDbController = new ProductDbController(jdbcTemplate);
        ReviewDbController reviewDbController = new ReviewDbController(jdbcTemplate);
        ThesisDbController thesisDbController = new ThesisDbController(jdbcTemplate);
        // Достаём все товары
        List<Product> listOfProducts = productDbController.getAllProducts();
        for(Product product : listOfProducts)
        {
            // Теперь для каждого товара пишем его имя..
            System.out.println("<product name =" + product.getName() + '"' + ">");
            //...Достаём все отзывы на него...
            List<Review> reviewList = reviewDbController.getReviewsByProductId(product.getId());
            for(Review review : reviewList)
            {
                //...и теперь для каждого отзыва пишем его содержание...
                System.out.println("<review>");
                System.out.println(review.getContent());
                System.out.println("</review>");
                System.out.println("<info>");
                //...и достаём из базы список нетрэшевых тезисов из него (отзыва)
                List<Thesis> thesisList =  thesisDbController.getThesesByReviewId(review.getId());
                for(Thesis thesis : thesisList)
                {
                    //и теперь для каждого тезиса выводим его содержимое и tfidf
                    System.out.print(thesis.getContent() + ' ');
                    System.out.println(mapOfTFIDF.get(thesis.getId()));
                }
                System.out.println("</info>");
            }
            System.out.println("</product>");
        }
    }

    @Override
    public void run() {
        ProductDbController productDbController = new ProductDbController(jdbcTemplate);
        List<Product> productList;
        productList = productDbController.getAllProducts();
        log.info("Thesis Analyzer begins work");
        for (Product product : productList) {
            log.info("Extracting thesis on " + product.getName());
            try {
                updateThesisByProductId(product.getId());
            } catch (StorageException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        log.info("Thesis Analyzer successful completed");
    }

}
