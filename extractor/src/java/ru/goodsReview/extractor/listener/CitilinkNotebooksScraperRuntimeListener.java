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
import ru.goodsReview.storage.controller.ProductDbController;
import ru.goodsReview.storage.controller.ReviewDbController;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;


//import ru.brandanalyst.core.model.Article;
//import ru.brandanalyst.miner.util.DataTransformator;


public class CitilinkNotebooksScraperRuntimeListener implements ScraperRuntimeListener {

    private int i = 0;
   // private static final Logger log = Logger.getLogger(CitilinkNotebooksScraperRuntimeListener.class);

    protected SimpleJdbcTemplate jdbcTemplate;
    protected ReviewDbController reviewDbController;
    protected ProductDbController productDbController;

    public CitilinkNotebooksScraperRuntimeListener(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        reviewDbController = new ReviewDbController(jdbcTemplate);
    }

    private Timestamp evalTimestamp(String stringDate) {
        stringDate = stringDate.replace("\n", "");
        stringDate = stringDate.replace(" ", "");

        int minute = Integer.parseInt(stringDate.substring(13, 15));
        int hour = Integer.parseInt(stringDate.substring(10, 12));
        int day = Integer.parseInt(stringDate.substring(0, 2));
        int month = Integer.parseInt(stringDate.substring(3, 5));
        int year = Integer.parseInt(stringDate.substring(6, 10));

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);

        return new Timestamp(calendar.getTime().getTime());
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

        if ("body".equalsIgnoreCase(baseProcessor.getElementDef().getShortElementName()) && (scraper.getRunningLevel() == 5)) {
            String nameProd =  scraper.getContext().get("ProductName").toString();
            String ProdPrice = scraper.getContext().get("Price").toString();
            System.out.println(nameProd + ": " + ProdPrice + "::: "+ scraper.getRunningLevel());
        }

         /* if ("body".equalsIgnoreCase(scraper.getRunningProcessor().getElementDef().getShortElementName()) && (scraper.getRunningLevel() == 8)) {

            String nameProd =  scraper.getContext().get("ProductName").toString();
            String ProdPrice = scraper.getContext().get("Price").toString();
           // System.out.println(nameProd + ": " + ProdPrice);

            String ReviewTime = scraper.getContext().get("ReviewTime").toString();
            String StarRate = scraper.getContext().get("StarRate").toString();
            String Description = scraper.getContext().get("Description").toString();
            String GoodFeatures = scraper.getContext().get("GoodFeatures").toString();
            String BadFeatures = scraper.getContext().get("BadFeatures").toString();
            String Comments = scraper.getContext().get("Comments").toString();
            String VoteYes = scraper.getContext().get("VoteYes").toString();
            String VoteNo = scraper.getContext().get("VoteNo").toString();
            //Review rev =  new Review(1,1, GoodFeatures+BadFeatures+ Comments, "amarch", 10, Description, 15 , "httpa", Integer.parseInt(StarRate), 100500, Integer.parseInt(VoteYes), Integer.parseInt(VoteNo));
            //reviewDbController.addReview(rev);
          //  articleProvider.writeArticleToDataStore(article);
           // log.info(" New review addded");
           // System.out.println(ReviewTime + " " + StarRate + " ");
          }   */


            //Variable newsText = (Variable) scraper.getContext().get("newsFullText");
            //Variable newsDate = (Variable) scraper.getContext().get("newsDate");
            //long brandId = ((Variable) scraper.getContext().get("brandId")).toLong();
            //Timestamp articleTimestamp = evalTimestamp(newsDate.toString());

           // String articleContent = DataTransformator.clearString(newsText.toString());
           // System.out.println(articleContent);
            //String articleTitle = newsTitle.toString();

            //  Article article = new Article(-1, brandId, 10, articleTitle, articleContent, articleLink, articleTimestamp, 0);



    }

    public void onExecutionError(Scraper scraper, Exception e) {
        if (e != null) {
            //log.error("CitilinkNotebooksScraperRuntimeListener error");
        }
    }
}