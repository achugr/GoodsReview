package ru.goodsReview.miner.listener;

/**
 * User: Alexander Marchuk
 * aamarchuk@gmail.com
 * Date: 10/26/11
 * Time: 6:27 AM
 */


import org.apache.log4j.Logger;
import org.webharvest.runtime.Scraper;
import ru.goodsReview.core.db.ControllerFactory;
import ru.goodsReview.core.db.exception.StorageException;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.miner.CategoryConfig;
import ru.goodsReview.miner.utils.CitilinkDataTransformator;

import java.util.ArrayList;
import java.util.List;


public class CitilinkScraperRuntimeListener extends AbstractScraperRuntimeListener {
    private static final Logger log = Logger.getLogger(CitilinkScraperRuntimeListener.class);

    private static final String site = "citilink.ru";

    private static double GOOD_FEAUTURE_POSITIVITY = 5.0;
    private static double BAD_FEAUTURE_POSITIVITY = -5.0;

    public CitilinkScraperRuntimeListener(ControllerFactory controllerFactory, CategoryConfig.RegExp regExp){
        super(controllerFactory, regExp);
    }

    public void onExecutionError(Scraper scraper, Exception e) {
        if (e != null) {
            log.error("CitilinkScraperRuntimeListener error", e);
        }
    }

    protected void addProduct(Scraper scraper) {
        String nameProd = scraper.getContext().get("ProductName").toString();
        CitilinkDataTransformator citilinkDataTransformator = new CitilinkDataTransformator();
        Product product = citilinkDataTransformator.createProductModelFromSource(nameProd, regExp);
        if (prodInDb.keySet().contains(product.getName())){
            lastAddedProductId = prodInDb.get(product.getName());

            log.info("Product with Name = " + product.getName() +" already in DB");
        }else{
            try {
                lastAddedProductId = controllerFactory.getProductController().addProduct(product);
                prodInDb.put(product.getName(),lastAddedProductId);

                log.info("New product Name = " + product.getName());
            } catch (StorageException e) {
                log.error("Error, while add review in db", e);
            }
        }
        lastAddedProductName = product.getName();
    }

    protected void addReviews(Scraper scraper) {
        String nameProd = scraper.getContext().get("ProductName").toString();
        String prodPrice = scraper.getContext().get("Price").toString();
        String reviewTime = scraper.getContext().get("ReviewTime").toString();
        String starRate = scraper.getContext().get("StarRate").toString();
        String opinionText = scraper.getContext().get("OpinionText").toString();
        String voteYes = scraper.getContext().get("VoteYes").toString();
        String voteNo = scraper.getContext().get("VoteNo").toString();

        long time = System.currentTimeMillis();

        CitilinkDataTransformator citilinkDataTransformator = new CitilinkDataTransformator();
        //takes indexes of text parts and parse opinion text
        String goodOpinion = citilinkDataTransformator.getGoodPartOfOpinion(opinionText);
        String badOpinion = citilinkDataTransformator.getBadPartOfOpinion(opinionText);
        String commentOpinion = citilinkDataTransformator.getCommentPartOfOpinion(opinionText);
        List<Review> reviewList = new ArrayList<Review>();
        if(goodOpinion != null){
            Review goodFeauture = new Review(lastAddedProductId, goodOpinion,"anonim", time, "", 1, site,
                GOOD_FEAUTURE_POSITIVITY, 0.0, 0, 0);
            goodFeauture = citilinkDataTransformator.clearReviewFromTrash(goodFeauture);
            reviewList.add(goodFeauture);
        }
        if(badOpinion != null){
            Review badFeauture = new Review(lastAddedProductId, badOpinion, "anonim", time, "", 1, site,
                BAD_FEAUTURE_POSITIVITY, 0.0, 0, 0);
            badFeauture = citilinkDataTransformator.clearReviewFromTrash(badFeauture);
            reviewList.add(badFeauture);
        }
        if(commentOpinion != null){
            Review comment = new Review(lastAddedProductId, commentOpinion, "anonim", time, "", 1, site,
                0.0, 0.0, 0, 0);
            comment = citilinkDataTransformator.clearReviewFromTrash(comment);
            reviewList.add(comment);
        }
//            clear reviews content from trash
//            add reviews in DB
        try {
            controllerFactory.getReviewController().addReviewList(reviewList);
        } catch (StorageException e) {
            log.error("Error, while add review in db", e);
        }
        log.info("New review for " + lastAddedProductName + " with ID " + lastAddedProductId + " added.");
    }
}
