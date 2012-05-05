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
import ru.goodsReview.analyzer.algorithmTesting.Phrase;
import ru.goodsReview.analyzer.util.ThesisPattern;
import ru.goodsReview.analyzer.util.sentence.PartOfSpeech;
import ru.goodsReview.analyzer.util.sentence.ReviewTokens;
import ru.goodsReview.analyzer.util.sentence.Token;
import ru.goodsReview.analyzer.wordAnalyzer.MystemAnalyzer;
import ru.goodsReview.core.db.ControllerFactory;
import ru.goodsReview.core.db.controller.ProductController;
import ru.goodsReview.core.db.controller.ReviewController;
import ru.goodsReview.core.db.controller.ThesisController;
import ru.goodsReview.core.db.exception.StorageException;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.core.model.Thesis;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimerTask;

public class ExtractThesis extends TimerTask{
    private static final Logger log = org.apache.log4j.Logger.getLogger(AnalyzeThesis.class);
    private static ControllerFactory controllerFactory;
    private static ProductController productController;
    private static ThesisController thesisController;
    private static ReviewController reviewController;

    static String[] dict = {"более", "достаточно", "очень", "не", "слишком", "довольно"};

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
     * @param review Review with theses had to be extracted.
     * @return List of theses.
     */
    public static List<Thesis> doExtraction(Review review, MystemAnalyzer mystemAnalyzer) throws IOException, InterruptedException {
        List<Thesis> extractedThesisList = new ArrayList<Thesis>();
        String content = review.getContent();

        ArrayList<Phrase> listThesis = doExtraction(content, mystemAnalyzer);

        for (Phrase phrase:listThesis){
            String token1 =  phrase.getFeature();
            String token2 =  phrase.getOpinion();
            extractedThesisList.add(new Thesis(review.getId(), 1, token1 + " " + token2, 0, 0.0, 0.0));
        }

        return extractedThesisList;
    }

    public static ArrayList<Phrase> doExtraction(String content, MystemAnalyzer mystemAnalyzer) throws IOException, InterruptedException {
        ArrayList<Phrase> extractedThesisList = new ArrayList<Phrase>();

        List<ThesisPattern> thesisPatternList = new ArrayList<ThesisPattern>();
        thesisPatternList.add(new ThesisPattern(PartOfSpeech.NOUN, PartOfSpeech.ADJECTIVE));
        thesisPatternList.add(new ThesisPattern(PartOfSpeech.ADJECTIVE, PartOfSpeech.NOUN));

        StringTokenizer stringTokenizer = new StringTokenizer(content, ".,-—:;!()+\'\"\\«»");

        while (stringTokenizer.hasMoreElements()) {
            String str  = stringTokenizer.nextToken();

            ReviewTokens reviewTokens = new ReviewTokens(str, mystemAnalyzer);
            ArrayList<Token> tokensList = reviewTokens.getTokensList();

            for(ThesisPattern thesisPattern : thesisPatternList){
                ThesisPattern pattern = thesisPattern;

                if(pattern.getPattern().get(0).equals(PartOfSpeech.NOUN)){
                    nounAtFirstPositionExtraction(extractedThesisList,tokensList,pattern,mystemAnalyzer);
                }  else{
                    if(pattern.getPattern().get(1).equals(PartOfSpeech.NOUN)){
                        nounAtSecondPositionExtraction(extractedThesisList,tokensList,pattern,mystemAnalyzer);
                    }  else{
                        System.out.println("incorrect pattern");
                    }
                }

            }
        }


        return extractedThesisList;
    }


    static void nounAtFirstPositionExtraction(ArrayList<Phrase> extractedThesisList, ArrayList<Token> tokensList, ThesisPattern pattern, MystemAnalyzer mystemAnalyzer) throws UnsupportedEncodingException {
        String token1 = null;
        PartOfSpeech noun = pattern.getPattern().get(0);
        PartOfSpeech part2 = pattern.getPattern().get(1);
        int n1 = 0;
        int n2 = 0;
        for (int i = 0; i < tokensList.size(); i++) {
            Token currToken = tokensList.get(i);

            if (currToken.getMystemPartOfSpeech().equals(noun)) {
                token1 = currToken.getContent();
                 n1 = i;
            } else {
                if (token1 != null && currToken.getMystemPartOfSpeech().equals(part2)) {
                     n2 = i;
                    
                    boolean patternCondition = (Math.abs(n1-n2)==2)&&(dictContains(tokensList.get(n1+1).getContent()));
                    if(Math.abs(n1-n2)==1||patternCondition){
                        String token2 = currToken.getContent();

                        if(checkWordsCorrespondence(token1, token2, mystemAnalyzer)) {

                           if(patternCondition){
                            //   System.out.println(token1+" "+tokensList.get(n1+1).getContent()+" "+token2);
                            //   token2= tokensList.get(n1+1).getContent()+" "+token2;
                          }
                            extractedThesisList.add(new Phrase(token1,token2));
                        }
                    }

                    token1 = null;
                }
            }
        }
    }

    static void nounAtSecondPositionExtraction(ArrayList<Phrase> extractedThesisList, ArrayList<Token> tokensList, ThesisPattern pattern, MystemAnalyzer mystemAnalyzer) throws UnsupportedEncodingException {
        String token1 = null;
        PartOfSpeech part2 = pattern.getPattern().get(0);
        PartOfSpeech noun = pattern.getPattern().get(1);
        int n1 = 0;
        int n2 = 0;
        for (int i = tokensList.size()-1; i >= 0 ; i--) {
            Token currToken = tokensList.get(i);

            if (currToken.getMystemPartOfSpeech().equals(noun)) {
                token1 = currToken.getContent();
                n1 = i;
            } else {
                if (token1 != null && currToken.getMystemPartOfSpeech().equals(part2)) {
                    n2 = i;
                    boolean patternCondition = false;
                    if(n2!=0){
                        patternCondition = (Math.abs(n1-n2)==1)&&(dictContains(tokensList.get(n2-1).getContent()));
                    }

                    if(Math.abs(n1-n2)==1||patternCondition){

                        String token2 = currToken.getContent();

                        if(checkWordsCorrespondence(token1, token2, mystemAnalyzer)) {
                            if(patternCondition){
                              //  System.out.println(tokensList.get(n2-1).getContent()+" "+token2+" "+token1);
                              //  token2 = tokensList.get(n2-1).getContent()+" "+token2;
                            }
                            extractedThesisList.add(new Phrase(token1,token2));
                        }
                    }
                    token1 = null;
                }
            }
        }
    }

    static boolean checkWordsCorrespondence(String token1, String token2, MystemAnalyzer mystemAnalyzer) throws UnsupportedEncodingException {
        String[] a1 = mystemAnalyzer.wordCharacteristic1(token1);
        String[] a2 = mystemAnalyzer.wordCharacteristic1(token2);
        String p1 = a1[0];
        String p2 = a2[0];
        String num1 = a1[1];
        String num2 = a2[1];
        String case1 = a1[2];
        String case2 = a2[2];
        boolean con1 = check(p1, p2);
        boolean con2 = check(num1, num2);
        boolean con3 = check(case1, case2);

        boolean sep = con1 && con2 && con3;

        return sep;
    }

    static boolean check(String s1, String s2) {
        if (!s1.equals("unk") && !s2.equals("unk")) {
            return s1.equals(s2);
        }
        return false;
    }

    static boolean dictContains(String s) {
        for (String str:dict){
            if(s.equals(str)){
                return true;
            }
        }
        return false;
    }

    /**
     * Extract thesis on all products from database
     * @throws IOException
     */
    public static void extractThesisOnAllProducts() throws IOException, InterruptedException {
        List<Product> list = productController.getAllProducts();
        MystemAnalyzer mystemAnalyzer = new MystemAnalyzer();

        for(Product product : list){
            log.info("progress..");
            extractThesisOnProduct(product.getId(), mystemAnalyzer);
        }
        mystemAnalyzer.close();
    }
    /**
     * Extract thesis from all reviews on this product. (Run method doExtraction for all reviews on product).
     * @param productId
     * @throws IOException
     */
    public static void extractThesisOnProduct(long productId, MystemAnalyzer mystemAnalyzer) throws IOException, InterruptedException {
        List<Review> reviews = reviewController.getReviewsByProductId(productId);
        log.info("extracting thesis on " + productId);
        for(Review review : reviews){
            try {
                System.out.println("<review id = \"" + review.getId() + "\">");
                List<Thesis> thesises = doExtraction(review, mystemAnalyzer);
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
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
//        showAllReviews();
        log.info("extraction is complete");
    }


}
