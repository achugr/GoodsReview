package ru.goodsReview.analyzer;
/*
 *  Date: 18.02.12
 *   Time: 16:04
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import ru.goodsReview.analyzer.util.ThesisPattern;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class ExtractThesis extends TimerTask{
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
     * Extract theses from review.
     *
     * @param review Review with theses had to be extracted.
     * @return List of theses.
     */
    public static List<Thesis> doExtraction(Review review) throws IOException {
        List<Thesis> extractedThesisList = new ArrayList<Thesis>();
        String content = review.getContent();

        List<ThesisPattern> thesisPatternList = new ArrayList<ThesisPattern>();
        thesisPatternList.add(new ThesisPattern(PartOfSpeech.NOUN, PartOfSpeech.ADJECTIVE));
        thesisPatternList.add(new ThesisPattern(PartOfSpeech.NOUN, PartOfSpeech.ADVERB));

        ReviewTokens reviewTokens = new ReviewTokens(content);
        ArrayList<Token> tokensList = reviewTokens.getTokensList();
        
        for(ThesisPattern thesisPattern : thesisPatternList){
            ThesisPattern pattern = thesisPattern;

            String token1 = null;
            PartOfSpeech part1 = pattern.getPattern().get(0);
            PartOfSpeech part2 = pattern.getPattern().get(1);
            for (int i = 0; i < tokensList.size(); i++) {
                Token currToken = tokensList.get(i);

                if (currToken.getMystemPartOfSpeech().equals(part1)) {
                    token1 = currToken.getContent();
                }
                if (token1 != null && currToken.getMystemPartOfSpeech().equals(part2)) {
                    String token2 = currToken.getContent();
                    extractedThesisList.add(new Thesis(review.getId(), 1, token1 + " " + token2, 0, 0.0, 0.0));
                    token1 = null;
                }
            }
        }

        return extractedThesisList;
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

    @Override
    public void run() {
        try {
            extractThesisOnAllProducts();
        } catch (IOException e) {
        }
//        showAllReviews();
        log.info("extraction is complete");
    }


}
