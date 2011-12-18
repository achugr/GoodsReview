package ru.goodsReview.analyzer;

/*
    Date: 28.11.11
    Time: 21:25
    Author: 
        Yaroslav Skudarnov 
        SkudarnovYI@gmail.com
*/

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.analyzer.wordAnalyzer.AdjectiveAnalyzer;
import ru.goodsReview.core.db.exception.StorageException;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.core.model.Thesis;
import ru.goodsReview.core.utils.EditDistance;
import ru.goodsReview.storage.controller.ProductDbController;
import ru.goodsReview.storage.controller.ReviewDbController;
import ru.goodsReview.storage.controller.ThesisDbController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimerTask;

public class ExtractThesis extends TimerTask{

    private static List<String> dictionaryWords = new ArrayList<String>();
    private static SimpleJdbcTemplate jdbcTemplate;
    private static final Logger log = org.apache.log4j.Logger.getLogger(AnalyzeThesis.class);
    private static AdjectiveAnalyzer aa;

    @Required
    public void setJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate){
        this.jdbcTemplate = simpleJdbcTemplate;
    }

    /**
     * Check if words are similar, using Levenshtein distance. Should work great, I suppose.
     *
     * @param word1 — firstWord
     * @param word2 — secondWord
     * @return true if words are similar enough, false — otherwise
     */
    private static boolean areSimilar(String word1, String word2) {

        double ed = EditDistance.editDist(word1, word2);

        int minLength = Math.min(word1.length(), word2.length());
        int maxLength = Math.max(word1.length(), word2.length());

        if ((ed / minLength <= 0.25) && ((ed / maxLength <= 0.35))) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Filling dictionary with words, huh.
     */
    public static void fillDictionary() {
        dictionaryWords.add("клавиатура");
        dictionaryWords.add("клава");
        dictionaryWords.add("клавиши");
        dictionaryWords.add("монитор");
        dictionaryWords.add("моник");
        dictionaryWords.add("дисплей");
        dictionaryWords.add("экран");
        dictionaryWords.add("картинка");
        dictionaryWords.add("разрешение");
        dictionaryWords.add("процессор");
        dictionaryWords.add("проц");
        dictionaryWords.add("камень");
        dictionaryWords.add("ядро");
        dictionaryWords.add("RAM");
        dictionaryWords.add("оператива");
        dictionaryWords.add("память");
        dictionaryWords.add("оперативная память");
        dictionaryWords.add("жёсткий диск");
        dictionaryWords.add("хард");
        dictionaryWords.add("винт");
        dictionaryWords.add("объём");
        dictionaryWords.add("винчестер");
        dictionaryWords.add("HDD");
        dictionaryWords.add("батарея");
        dictionaryWords.add("аккумулятор");
        dictionaryWords.add("аккумуль");
        dictionaryWords.add("батарейка");
        dictionaryWords.add("время работы");
        dictionaryWords.add("тачпад");
        dictionaryWords.add("тач");
        dictionaryWords.add("трекпоинт");
        dictionaryWords.add("микрофон");
        dictionaryWords.add("скорость");
        dictionaryWords.add("производительность");
        dictionaryWords.add("быстродействие");
        dictionaryWords.add("динамик");
        dictionaryWords.add("акустика");
        dictionaryWords.add("колонки");
        dictionaryWords.add("звук");
        dictionaryWords.add("корпус");
        dictionaryWords.add("пластик");
        dictionaryWords.add("поверхность");
        dictionaryWords.add("видеокарта");
        dictionaryWords.add("видяха");
        dictionaryWords.add("видеокарточка");
        dictionaryWords.add("цветопередача");
        dictionaryWords.add("ноут");
        dictionaryWords.add("бук");
        dictionaryWords.add("ноутбук");
        dictionaryWords.add("нетбук");
        dictionaryWords.add("лаптоп");
        dictionaryWords.add("нетбук");
        dictionaryWords.add("машинка");
        dictionaryWords.add("модель");
        dictionaryWords.add("девайс");
        dictionaryWords.add("компьютер");
        dictionaryWords.add("отделка");
        dictionaryWords.add("биос");
        dictionaryWords.add("вес");
        dictionaryWords.add("масса");
        dictionaryWords.add("камера");
        dictionaryWords.add("вебка");
        dictionaryWords.add("вебкамера");
        dictionaryWords.add("операционка");
        dictionaryWords.add("ось");
        dictionaryWords.add("операционная система");
        dictionaryWords.add("ОС");
        dictionaryWords.add("система");
        dictionaryWords.add("OS");
        dictionaryWords.add("цвет");
        dictionaryWords.add("железо");
        dictionaryWords.add("конфигурация");
        dictionaryWords.add("кулер");
        dictionaryWords.add("вентилятор");
        dictionaryWords.add("дизайн");
        dictionaryWords.add("стиль");
        dictionaryWords.add("вид");
        dictionaryWords.add("матрица");
        dictionaryWords.add("матричка");
        dictionaryWords.add("поддержка");
        dictionaryWords.add("сервис");
    }

    /**
     * Checking if word is in dictionary
     *
     * @param word
     * @return true if word is here. false — otherwise
     */
    public static boolean isInDictionary(String word) {
        for (String variant : dictionaryWords) {
            if (areSimilar(word, variant)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Extract theses from review. Now uses just a simple rule "adj + noun" or "noun + adj". Probably, will get tougher.
     *
     * @param review Review with theses had to be extracted.
     * @return List of theses.
     */
    public static List<Thesis> doExtraction(Review review) throws IOException {
        List<Thesis> extractedThesisList = new ArrayList<Thesis>();
        fillDictionary();
        String content = review.getContent();

        StringTokenizer st = new StringTokenizer(content, " .,-—:;()+\'\"\\«»");
        String currToken;
        String nextToken;
        while(st.hasMoreElements()){
//            get current token
            currToken = st.nextToken();
//            if it's an adjective
            if(aa.isAdjective(currToken)){
//                go on review
                while (st.hasMoreElements()){
                    nextToken = st.nextToken();
//                    if next token is a noun from dictionary
                    if(isInDictionary(nextToken)){
//                        create Thesis, which content = nextToken(noun from dictionary) + currentToken(adjective)
                        extractedThesisList.add(new Thesis(review.getId(),1, nextToken +" "+ currToken, 0, 0.0, 0.0));
                        break;
                    }
                }
            } else {
//                if current token is noun from dictionary
                    if(isInDictionary(currToken)){
//                        go on review
                        while (st.hasMoreElements()){
                            nextToken = st.nextToken();
//                            if nextToken is an adjective
                            if(aa.isAdjective(nextToken)){
//                              create Thesis, which content = currToken(adjective) + nextToken(noun from dictionary)
                                extractedThesisList.add(new Thesis(review.getId(),1, currToken +" "+ nextToken, 0, 0.0, 0.0));
                                break;
                            }
                        }
                    }
            }
        }

        //aa.close();
        return extractedThesisList;
    }


    public static void extractThesisOnProduct(long productId) throws IOException {
        ReviewDbController reviewDbController = new ReviewDbController(jdbcTemplate);
        ThesisDbController thesisDbController = new ThesisDbController(jdbcTemplate);

        List<Review> reviews = reviewDbController.getReviewsByProductId(productId);
        log.info("extracting thesis on " + productId);
        for(Review review : reviews){
            try {
                thesisDbController.addThesisList(doExtraction(review));
            } catch (StorageException e) {
                log.error("something wrong with this thesis (probably it's already exist in db)", e);
            }
        }
    }

    public static void showThesisOnProduct(long productId){
        ThesisDbController thesisDbController = new ThesisDbController(jdbcTemplate);
        List<Thesis> thesisList = thesisDbController.getThesesByProductId(productId);
        for(Thesis thesis : thesisList){
            System.out.println("    <thesis> " +thesis.getContent().replaceAll("\\s+", " ")+ " </thesis>");
//            log.info("thesis --> "+ thesis.getContent());
        }
    }

    public static void showThesisOnAllProducts(){
        ProductDbController productDbController = new ProductDbController(jdbcTemplate);
        List<Product> list = productDbController.getAllProducts();
        for(Product product : list){
            System.out.println("<product name=\""+product.getName()+"\">");
            showThesisOnProduct(product.getId());
        }
    }

    public static void extractThesisOnAllProducts() throws IOException {
        ProductDbController productDbController = new ProductDbController(jdbcTemplate);
        List<Product> list = productDbController.getAllProducts();
        for(Product product : list){
            log.info("progress..");
            extractThesisOnProduct(product.getId());
        }
    }
    
    @Override
    public void run() {
        try {
            aa = new AdjectiveAnalyzer();
            extractThesisOnAllProducts();
            aa.close();
        } catch (IOException e) {
        }
        showThesisOnAllProducts();
        log.info("extraction is complete");
    }

//    Yaroslav version
  /**
     * Extract theses from review. Now uses just a simple rule "adj + noun" or "noun + adj". Probably, will get tougher.
     * @param rev Review with theses had to be extracted.
     * @return List of theses.
     */
    /*
    public List<Thesis> doExtraction(Review rev) {
        List<Thesis> result = new ArrayList<Thesis>();

        String content = rev.getContent();

        StringTokenizer st = new StringTokenizer(content," .,-—:;()\'\"\\«»");
        boolean prevWasAdj = false;
        String currToken;
        String prevToken = "123456789";

        while (st.hasMoreTokens()) {
            if (AdjectiveAnalyzer.isAdjective(currToken = st.nextToken())) {
                if (isInDictionary(prevToken)) {
                    Thesis temp = new Thesis(rev.getId(),0,prevToken + " " + currToken,0,0,0);
                    result.add(temp);
                }
                prevWasAdj = true;
            } else {
                if (prevWasAdj) {
                    if (isInDictionary(currToken)) {
                        Thesis temp = new Thesis(rev.getId(),0,currToken + " " + prevToken,0,0,0);
                    }
                }
                prevWasAdj = false;
                currToken = st.nextToken().trim();
            }
        }

        return result;

    }
    */
}