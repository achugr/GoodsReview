package ru.goodsreview.miner.obsolete.listener;

import org.apache.log4j.Logger;
import org.webharvest.runtime.Scraper;
import ru.goodsreview.core.db.ControllerFactory;
import ru.goodsreview.core.db.exception.StorageException;
import ru.goodsreview.core.model.Product;
import ru.goodsreview.core.model.Review;
import ru.goodsreview.miner.obsolete.CategoryConfig;
import ru.goodsreview.miner.obsolete.utils.UlmartDataTransformator;

/**
 * Created by IntelliJ IDEA.
 * User: timur
 */

public class UlmartScraperRuntimeListener extends AbstractScraperRuntimeListener{
    private static final Logger log = Logger.getLogger(CitilinkScraperRuntimeListener.class);

    private static final String site = "ulmart.ru";

    public UlmartScraperRuntimeListener(ControllerFactory controllerFactory, CategoryConfig.RegExp regExp){
        super(controllerFactory, regExp);
    }

    public void onExecutionError(Scraper scraper, Exception e) {
        if (e != null) {
            log.error("UlmartScraperRuntimeListener error", e);
        }
    }

    //TODO: ?Compare current product name with product names in DB using EditDistance
    @Override
    protected void addProduct(Scraper scraper) {
        String nameProd = scraper.getContext().get("ProductName").toString();
        UlmartDataTransformator ulmartDataTransformator = new UlmartDataTransformator();
        Product product = ulmartDataTransformator.createProductModelFromSource(nameProd, regExp);

        if (prodInDb.keySet().contains(product.getName().toLowerCase())){
            lastAddedProductId = prodInDb.get(product.getName());

            log.info("Product with Name = " + product.getName() +" already in DB");
        }else{
            try {
                lastAddedProductId = controllerFactory.getProductController().addProduct(product);
                prodInDb.put(product.getName().toLowerCase(),lastAddedProductId);

                log.info("New product Name = " + product.getName());
            } catch (StorageException e) {
                log.error("Error, while add review in db", e);
            }
        }
        lastAddedProductName = product.getName();
    }


    @Override
    protected void addReviews(Scraper scraper) {
        String opinionText = scraper.getContext().get("OpinionText").toString();

        long time = System.currentTimeMillis();

        UlmartDataTransformator ulmartDataTransformator = new UlmartDataTransformator();
        Review review = new Review(lastAddedProductId, opinionText, "anonim", time, "", 1, site, 0.0, 0.0, 0, 0);
        Review cleanReview = ulmartDataTransformator.clearReviewFromTrash(review);

        try {
            controllerFactory.getReviewController().addReview(cleanReview);
        } catch (StorageException e) {
            log.error("Error, while add review in db", e);
        }
        log.info("New review '" + cleanReview.getContent() + "' for " + lastAddedProductName + " with ID " + lastAddedProductId + " added.");
    }
}
