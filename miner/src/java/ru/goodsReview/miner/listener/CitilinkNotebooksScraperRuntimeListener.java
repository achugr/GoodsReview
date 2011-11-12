package ru.goodsReview.miner.listener;

/**
 * User: Alexander Marchuk
 * aamarchuk@gmail.com
 * Date: 10/26/11
 * Time: 6:27 AM
 */


import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.ScraperRuntimeListener;
import org.webharvest.runtime.processors.BaseProcessor;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.miner.utils.CitilinkDataTransformator;
import ru.goodsReview.storage.controller.ProductDbController;
import ru.goodsReview.storage.controller.ReviewDbController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class CitilinkNotebooksScraperRuntimeListener implements ScraperRuntimeListener {

    private int i = 0;
    private static double GOOD_FEAUTURE_POSITIVITY = 5.0;
    private static double BAD_FEAUTURE_POSITIVITY = -5.0;
    private static final Logger log = Logger.getLogger(CitilinkNotebooksScraperRuntimeListener.class);
    private static String lastAddedProductName = "";
    private static long lastAddedProductId = 0;

    protected SimpleJdbcTemplate jdbcTemplate;
    protected ReviewDbController reviewDbController;
    protected ProductDbController productDbController;

    public CitilinkNotebooksScraperRuntimeListener(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        reviewDbController = new ReviewDbController(jdbcTemplate);
    }

    public void onExecutionStart(Scraper scraper) {

    }

    public void onExecutionPaused(Scraper scraper) {
    }

    public void onExecutionContinued(Scraper scraper) {
    }

    public void onNewProcessorExecution(Scraper scraper, BaseProcessor baseProcessor) {
    }

    public void onExecutionEnd(Scraper scraper) {


    }
    //TODO i will cleanse this trash at evening, soryy
    //it works, but i need distribute this code on logic-methods
    public void onProcessorExecutionFinished(Scraper scraper, BaseProcessor baseProcessor, Map map) {

        if ("body".equalsIgnoreCase(
                scraper.getRunningProcessor().getElementDef().getShortElementName()) && (scraper.getRunningLevel() == 6)) {

            String nameProd = scraper.getContext().get("ProductName").toString();
            String prodPrice = scraper.getContext().get("Price").toString();
            String reviewTime = scraper.getContext().get("ReviewTime").toString();
            String starRate = scraper.getContext().get("StarRate").toString();
            String description = scraper.getContext().get("Description").toString();
            String goodFeatures = scraper.getContext().get("GoodFeatures").toString();
            String badFeatures = scraper.getContext().get("BadFeatures").toString();
            String comments = scraper.getContext().get("Comments").toString();
            String voteYes = scraper.getContext().get("VoteYes").toString();
            String voteNo = scraper.getContext().get("VoteNo").toString();

            Date date = new Date();
            //System.out.println("product ->>> " + nameProd);
            CitilinkDataTransformator citilinkDataTransformator = new CitilinkDataTransformator();
            Product product = citilinkDataTransformator.createProductModelFromSource(nameProd);
            //add product into DB
            if (!(lastAddedProductName.equals(product.getName()))) {
                ProductDbController productDbController = new ProductDbController(jdbcTemplate);
                System.out.println("product Name = " + product.getName());
                lastAddedProductId = productDbController.addProduct(product);
                Review goodFeauture = new Review(lastAddedProductId, goodFeatures, "anonim", date, "", 1, "citilink.ru", GOOD_FEAUTURE_POSITIVITY, 0.0, 0, 0);
                Review badFeauture = new Review(lastAddedProductId, badFeatures, "anonim", date, "", 1, "citilink.ru", BAD_FEAUTURE_POSITIVITY, 0.0, 0, 0);
                Review comment = new Review(lastAddedProductId, comments, "anonim", date, "", 1, "citilink.ru", 0.0, 0.0, 0, 0);

//            clear reviews content from trash
                goodFeauture = citilinkDataTransformator.clearReviewFromTrash(goodFeauture);
                badFeauture = citilinkDataTransformator.clearReviewFromTrash(badFeauture);
                comment = citilinkDataTransformator.clearReviewFromTrash(comment);
//            add reviews in DB
                List<Review> reviewList = new ArrayList<Review>();
                reviewList.add(goodFeauture);
                reviewList.add(badFeauture);
                reviewList.add(comment);
                reviewDbController.addReviewList(reviewList);

                lastAddedProductName = product.getName();
            } else {
                Review goodFeauture = new Review(lastAddedProductId, goodFeatures, "anonim", date, "", 1, "citilink.ru", GOOD_FEAUTURE_POSITIVITY, 0.0, 0, 0);
                Review badFeauture = new Review(lastAddedProductId, badFeatures, "anonim", date, "", 1, "citilink.ru", BAD_FEAUTURE_POSITIVITY, 0.0, 0, 0);
                Review comment = new Review(lastAddedProductId, comments, "anonim", date, "", 1, "citilink.ru", 0.0, 0.0, 0, 0);

//            clear reviews content from trash
                goodFeauture = citilinkDataTransformator.clearReviewFromTrash(goodFeauture);
                badFeauture = citilinkDataTransformator.clearReviewFromTrash(badFeauture);
                comment = citilinkDataTransformator.clearReviewFromTrash(comment);
//            add reviews in DB
                List<Review> reviewList = new ArrayList<Review>();
                reviewList.add(goodFeauture);
                reviewList.add(badFeauture);
                reviewList.add(comment);
                reviewDbController.addReviewList(reviewList);

            }
        }
    }

    public void onExecutionError(Scraper scraper, Exception e) {
        if (e != null) {
            log.error("CitilinkNotebooksScraperRuntimeListener error");
        }
    }
}
