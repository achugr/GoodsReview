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
import ru.goodsReview.core.db.exception.StorageException;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.miner.utils.CitilinkDataTransformator;
import ru.goodsReview.storage.controller.ProductDbController;
import ru.goodsReview.storage.controller.ReviewDbController;

import java.sql.Timestamp;
import java.util.ArrayList;
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
            String opinionText = scraper.getContext().get("OpinionText").toString();
            String voteYes = scraper.getContext().get("VoteYes").toString();
            String voteNo = scraper.getContext().get("VoteNo").toString();

            Timestamp time = new Timestamp(System.currentTimeMillis());

            CitilinkDataTransformator citilinkDataTransformator = new CitilinkDataTransformator();
            Product product = citilinkDataTransformator.createProductModelFromSource(nameProd);
            //add product into DB
            if (!lastAddedProductName.equals(product.getName())) {
                ProductDbController productDbController = new ProductDbController(jdbcTemplate);
                System.out.println("product Name = " + product.getName());
                try {
                    lastAddedProductId = productDbController.addProduct(product);
                } catch (StorageException e) {
                    e.printStackTrace();
                }
                lastAddedProductName = product.getName();
            }

            //takes indexes of text parts and parse opinion text
            String goodOpinion = "";
            String badOpinion = "";
            String commentOpinion = "";

            if (opinionText.indexOf("Комментарий:") > 0) {
                commentOpinion = opinionText.substring(opinionText.indexOf("Комментарий:"));
            }

            if (opinionText.indexOf("Недостатки:") >= 0) {
                if (opinionText.indexOf("Комментарий:") > 0) {
                    badOpinion = opinionText.substring(opinionText.indexOf("Недостатки:"), opinionText.indexOf("Комментарий:"));
                } else {
                    badOpinion = opinionText.substring(opinionText.indexOf("Недостатки:"), opinionText.length());
                }
            }

            if (opinionText.indexOf("Достоинства:") >= 0) {
                if (opinionText.indexOf("Недостатки:") > 0) {
                    goodOpinion = opinionText.substring(opinionText.indexOf("Достоинства:"), opinionText.indexOf("Недостатки:"));
                } else if (opinionText.indexOf("Комментарий:") >= 0) {
                    goodOpinion = opinionText.substring(opinionText.indexOf("Достоинства:"), opinionText.indexOf("Комментарий:"));
                }
            }

            Review goodFeauture = new Review(lastAddedProductId, goodOpinion, "anonim", time, "", 1, "citilink.ru", GOOD_FEAUTURE_POSITIVITY, 0.0, 0, 0);
            Review badFeauture = new Review(lastAddedProductId, badOpinion, "anonim", time, "", 1, "citilink.ru", BAD_FEAUTURE_POSITIVITY, 0.0, 0, 0);
            Review comment = new Review(lastAddedProductId, commentOpinion, "anonim", time, "", 1, "citilink.ru", 0.0, 0.0, 0, 0);

//            clear reviews content from trash
            goodFeauture = citilinkDataTransformator.clearReviewFromTrash(goodFeauture);
            badFeauture = citilinkDataTransformator.clearReviewFromTrash(badFeauture);
            comment = citilinkDataTransformator.clearReviewFromTrash(comment);
//            add reviews in DB
            List<Review> reviewList = new ArrayList<Review>();
            reviewList.add(goodFeauture);
            reviewList.add(badFeauture);
            reviewList.add(comment);
            try {
                reviewDbController.addReviewList(reviewList);
            } catch (StorageException e) {
                e.printStackTrace();
            }
            log.info("New review for " + lastAddedProductName + " with ID " + lastAddedProductId + " added.");
        }
    }

    public void onExecutionError
            (Scraper scraper, Exception e) {
        if (e != null) {
            log.error("CitilinkNotebooksScraperRuntimeListener error");
        }
    }
}
