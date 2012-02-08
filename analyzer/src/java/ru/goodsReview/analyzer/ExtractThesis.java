package ru.goodsReview.analyzer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import ru.goodsReview.analyzer.util.dictionary.Dictionary;
import ru.goodsReview.analyzer.wordAnalyzer.TaggedWord;
import ru.goodsReview.analyzer.wordAnalyzer.WordAnalyzer;
import ru.goodsReview.core.db.ControllerFactory;
import ru.goodsReview.core.db.controller.ProductController;
import ru.goodsReview.core.db.controller.ReviewController;
import ru.goodsReview.core.db.controller.ThesisController;
import ru.goodsReview.core.db.exception.StorageException;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.core.model.Thesis;
import ru.goodsReview.core.utils.EditDistance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimerTask;

/**
 * first version of thesis extraction algorithm
 * 1) find noun from dictionary
 * 2) find nearest adjective
 * 3) extract this pair
 */
public class ExtractThesis extends TimerTask{

    private static List<String> dictionaryWords = new ArrayList<String>();
    private static final Logger log = org.apache.log4j.Logger.getLogger(AnalyzeThesis.class);
    private static ControllerFactory controllerFactory;
    private static ProductController productController;
    private static ThesisController thesisController;
    private static ReviewController reviewController;
    private static Dictionary nounDictionary = new Dictionary("pure_opinion_words.txt");

    @Required
    public void setControllerFactory(ControllerFactory controllerFactory1){
        this.controllerFactory = controllerFactory1;
        setControllers();
    }

    public static void setControllers(){
        productController = controllerFactory.getProductController();
        reviewController = controllerFactory.getReviewController();
        thesisController = controllerFactory.getThesisController();
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
     * Extract theses from review. Now uses just a simple rule "adj + noun" or "noun + adj". Probably, will get tougher.
     *
     * @param review Review with theses had to be extracted.
     * @return List of theses.
     */
    public static List<Thesis> doExtraction(Review review) throws IOException {
        List<Thesis> extractedThesisList = new ArrayList<Thesis>();
        String content = review.getContent();
        String[] sentences = content.split(".");

        WordAnalyzer wordAnalyzer = new WordAnalyzer();

        for (int i = 0; i < sentences.length; i++) {
            String sentence = sentences[i];
            StringTokenizer st = new StringTokenizer(sentence, " .,-—:;()+\'\"\\«»");
            String currToken;

            ArrayList<TaggedWord> sentList = new ArrayList<TaggedWord>();

            while (st.hasMoreElements()) {
                currToken = st.nextToken();
                if (nounDictionary.containsWhether(currToken)) {
                    sentList.add(new TaggedWord(currToken, "adj"));
                } else {
                    if (wordAnalyzer.isNoun(currToken)) {
                        sentList.add(new TaggedWord(currToken, "noun"));
                    } else {
                        sentList.add(new TaggedWord(currToken, ""));
                    }
                }
            }


            for (int j = 0; j < sentList.size(); j++) {
                if (sentList.get(j).getPOS().equals("adj")) {
                    String adj = sentList.get(j).getWord();
                    String noun = searchNoun(sentList, j);

                    if (noun!=null) {
                        extractedThesisList.add(new Thesis(review.getId(), 1, noun + " " + adj, 0, 0.0, 0.0));
                    }
                }
            }

        }

        return extractedThesisList;
    }

    static String searchNoun(ArrayList<TaggedWord> list, int j) {
        for (int k = j - 1; k > 0; k--) {
            if (list.get(k).getPOS().equals("noun")) {
                return list.get(k).getWord();
            }
        }

        for (int k = j + 1; k < list.size(); k++) {
            if (list.get(k).getPOS().equals("noun")) {
                return list.get(k).getWord();
            }
        }

        return null;
    }

    /**
     * Extract thesis on all products from database
     * @throws IOException
     */
    public static void extractThesisOnAllProducts() throws IOException {
        List<Product> list = productController.getAllProducts();
        for(Product product : list){
            log.info("progress..");
            extractThesisOnProduct(product.getId());
        }
    }

    /**
     * print thesis on all products
     */
    public void showThesisOnAllProducts(){
        List<Product> list = productController.getAllProducts();
        for(Product product : list){
            System.out.println("<product id=\"" +product.getId() + "\"name=\""+product.getName()+"\">");
            List<Review> reviews = reviewController.getReviewsByProductId(product.getId());
            for(Review review : reviews){
                System.out.println("    <review content=\"" + review.getId() + "\">");
                List<Thesis> thesises = thesisController.getThesesByReviewId(review.getId());
                for(Thesis thesis : thesises){
                    System.out.println("        <thesis> " + thesis.getContent().replaceAll("\\s+", " ") + " </thesis>");
                }
            }
        }
    }

    /**
     * Extract thesis from all reviews on this product. (Run method doExtraction for all reviews on product).
     * @param productId
     * @throws IOException
     */
    public static void extractThesisOnProduct(long productId) throws IOException {
        List<Review> reviews = reviewController.getReviewsByProductId(productId);
        log.info("extracting thesis on " + productId);
        for(Review review : reviews){
            try {
                System.out.println("<review id = \"" + review.getId() + "\">");
                List<Thesis> thesises = doExtraction(review);
                for(Thesis thesis : thesises){
                    log.info("thesis ========================= " + thesis.getContent());
                }
                thesisController.addThesisList(thesises);
//                thesisController.addThesisList(doExtraction(review));
            } catch (StorageException e) {
                log.error("something wrong with this thesis (probably it's already exist in db)", e);
            }
        }
    }

    public void showAllReviews(){
        List<Review> reviews = reviewController.getAllReviews();
        for(Review review : reviews){
            System.out.println("<review id = " + review.getId() + " >");
            System.out.println("    "+review.getContent().replaceAll("\\s+", " "));
            System.out.println("</review>");
        }
    }

    @Override
    public void run() {
         try {
           extractThesisOnAllProducts();
       } catch (IOException e) {
       }
        showThesisOnAllProducts();
//        showAllReviews();
        log.info("extraction is complete");
    }

}