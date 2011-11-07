package ru.goodsReview.extractor.listener;

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
import ru.goodsReview.core.model.Review;
import ru.goodsReview.storage.controller.ProductDbController;
import ru.goodsReview.storage.controller.ReviewDbController;

import java.util.Date;
import java.util.Map;


public class CitilinkNotebooksScraperRuntimeListener implements ScraperRuntimeListener {

    private int i = 0;
    private static final Logger log = Logger.getLogger(CitilinkNotebooksScraperRuntimeListener.class);

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

    public void onProcessorExecutionFinished(Scraper scraper, BaseProcessor baseProcessor, Map map) {

          if ("body".equalsIgnoreCase(scraper.getRunningProcessor().getElementDef().getShortElementName()) && (scraper.getRunningLevel() == 6)) {

            String nameProd =  scraper.getContext().get("ProductName").toString();
            String ProdPrice = scraper.getContext().get("Price").toString();
            String ReviewTime = scraper.getContext().get("ReviewTime").toString();
            String StarRate = scraper.getContext().get("StarRate").toString();
            String Description = scraper.getContext().get("Description").toString();
            String GoodFeatures = scraper.getContext().get("GoodFeatures").toString();
            String BadFeatures = scraper.getContext().get("BadFeatures").toString();
            String Comments = scraper.getContext().get("Comments").toString();
            String VoteYes = scraper.getContext().get("VoteYes").toString();
            String VoteNo = scraper.getContext().get("VoteNo").toString();
            Date date = new Date(System.currentTimeMillis());

             //System.out.println(nameProd);
             Review rev =  new Review(1,1, GoodFeatures+"\n"+BadFeatures+"\n"+Comments, "amarch", date, Description, 15 , "httpa",
                                    Integer.parseInt(StarRate), 100500, Integer.parseInt(VoteYes), Integer.parseInt(VoteNo));
            reviewDbController.addReview(rev);
            log.info(" New review addded");
          }
    }

    public void onExecutionError(Scraper scraper, Exception e) {
        if (e != null) {
            log.error("CitilinkNotebooksScraperRuntimeListener error");
        }
    }
}