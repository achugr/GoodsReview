package ru.goodsReview.analyzer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import ru.goodsReview.analyzer.util.sentence.PartOfSpeech;
import ru.goodsReview.analyzer.util.sentence.ReviewTokens;
import ru.goodsReview.analyzer.util.sentence.Token;
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
import java.util.*;

/**
 * first version of thesis extraction algorithm
 * 1) find adjective from dictionary
 * 2) find nearest noun
 * 3) extract this pair
 */
public class ExtractThesisVersion2 extends TimerTask{

    private static final Logger log = org.apache.log4j.Logger.getLogger(AnalyzeThesis.class);
    private static ControllerFactory controllerFactory;
    private static ProductController productController;
    private static ThesisController thesisController;
    private static ReviewController reviewController;


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
     * Extract theses from review.
     *
     * @param review Review with theses had to be extracted.
     * @return List of theses.
     */
    public static List<Thesis> doExtraction(Review review) throws IOException {
        List<Thesis> extractedThesisList = new ArrayList<Thesis>();
        String content = review.getContent();

        ReviewTokens reviewTokens = new ReviewTokens(content);

        Iterator<Token> iterator = reviewTokens.iterator();
        iterator.next();
//      iterator.next();
        while (iterator.hasNext()){
            Token token = iterator.next();
            if (token.getMystemPartOfSpeech().equals(PartOfSpeech.ADJECTIVE)) {
                String adj = token.getContent();
//                String noun = searchNoun(reviewTokens);
                  String [] nouns = searchNouns(reviewTokens);
                for (String noun : nouns){
                    if (noun != null) {
                        extractedThesisList.add(new Thesis(review.getId(), 1, noun + " " + adj, 0, 0.0, 0.0));
                    }
                }
            }

        }
        return extractedThesisList;
    }

    static String searchNoun(ReviewTokens reviewTokens) {
        Token token = reviewTokens.getPrevious();
        while (token != null) {
            if (token.getMystemPartOfSpeech().equals(PartOfSpeech.NOUN)) {
                return token.getContent();
            }
            token = reviewTokens.getPrevious();
        }

        token = reviewTokens.getNext();
        while (token != null) {
            if (token.getMystemPartOfSpeech().equals(PartOfSpeech.NOUN)) {
                return token.getContent();
            }
            token = reviewTokens.getNext();
        }

        return null;
    }

    static String [] searchNouns(ReviewTokens reviewTokens) {
        String [] nouns = new String[2];
        Token token = reviewTokens.getPrevious();
        while (token != null) {
            if (token.getMystemPartOfSpeech().equals(PartOfSpeech.NOUN)) {
                nouns[0] = token.getContent();
                break;
            }
            token = reviewTokens.getPrevious();
        }

        token = reviewTokens.getNext();
        while (token != null) {
            if (token.getMystemPartOfSpeech().equals(PartOfSpeech.NOUN)) {
                nouns[1] = token.getContent();
                break;
            }
            token = reviewTokens.getNext();
        }
        return nouns;
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