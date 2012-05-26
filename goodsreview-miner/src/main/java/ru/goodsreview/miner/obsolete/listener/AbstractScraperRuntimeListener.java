package ru.goodsreview.miner.obsolete.listener;

import org.apache.log4j.Logger;
import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.ScraperRuntimeListener;
import org.webharvest.runtime.processors.BaseProcessor;
import ru.goodsreview.core.db.ControllerFactory;
import ru.goodsreview.core.model.Product;
import ru.goodsreview.miner.obsolete.CategoryConfig;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: timur
 */

public abstract class AbstractScraperRuntimeListener implements ScraperRuntimeListener{
    private static final Logger log = Logger.getLogger(AbstractScraperRuntimeListener.class);
    
    protected static String lastAddedProductName = "";
    protected static long lastAddedProductId = 0;
     
    protected ControllerFactory controllerFactory;
    protected CategoryConfig.RegExp regExp;

    protected Map<String,Long> prodInDb = new HashMap<String,Long>();
    
    public AbstractScraperRuntimeListener(ControllerFactory controllerFactory, CategoryConfig.RegExp regExp){
        this.controllerFactory = controllerFactory;
        this.regExp = regExp;
    }

    public void onExecutionStart(Scraper scraper) {
        scraper.pauseExecution();
        List<Product> productList = controllerFactory.getProductController().getAllProducts();
        for(Product product : productList){
            prodInDb.put(product.getName().toLowerCase(), product.getId());
        }
        scraper.continueExecution();
    }

    public void onNewProcessorExecution(Scraper scraper, BaseProcessor baseProcessor) {
        if ((scraper.getRunningLevel() == 5) &&
                baseProcessor.getElementDef().getShortElementName().equalsIgnoreCase("loop") ) {
            scraper.pauseExecution();
            //add product into DB
            addProduct(scraper);
            scraper.continueExecution();
        }
    }

    public void onProcessorExecutionFinished(Scraper scraper, BaseProcessor baseProcessor, Map map) {
        /*log.info("Running level: " + scraper.getRunningLevel());
        log.info("Processor element: " + baseProcessor.getElementDef().getShortElementName()
                + " with id:  " + baseProcessor.getElementDef().getId() + "\n");
        */
        if ((scraper.getRunningLevel() == 6) &&
                baseProcessor.getElementDef().getShortElementName().equalsIgnoreCase("body")) {
            scraper.pauseExecution();
            addReviews(scraper);
            scraper.continueExecution();
        }
    }

    public void onExecutionPaused(Scraper scraper) {}

    public void onExecutionContinued(Scraper scraper) {}

    public void onExecutionEnd(Scraper scraper) {}

    protected abstract void addProduct(Scraper scraper);

    protected abstract void addReviews(Scraper scraper);
}
