/*
    Date: 10/26/11
    Time: 06:19
    Author: Alexander Marchuk
            aamarchuk@gmail.com
*/
package ru.goodsReview.miner;

import org.apache.log4j.Logger;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import ru.goodsReview.miner.listener.CitilinkNotebooksScraperRuntimeListener;

public class GrabberCitilink extends WebHarvestGrabber {
    private static final Logger log = Logger.getLogger(GrabberCitilink.class);
    private final String config = "miner/webHarvest/configs/Citilink/CitilinkReviewsConfig.xml";
    private final String path = "data/miner/CitilinkPages";

    @Override
    public void downloadPages() {
        try {
            log.info("Citilink download pages started");
            ScraperConfiguration config = new ScraperConfiguration("miner/webHarvest/configs/downloadCitilinkSite.xml");
            Scraper scraper = new Scraper(config, ".");
            scraper.setDebug(true);
            scraper.execute();

            log.info("Citilink download pages succecsful");
        } catch (Exception e) {
            log.error("cannot process Citilink download pages");
            log.error(e);
        }
    }

    @Override
    public void findPages() {
    }


    @Override
    public void grabPages() {
        try {
            log.info("Citilink grabbing started");
            ScraperConfiguration config = new ScraperConfiguration(this.config);
            Scraper scraper = new Scraper(config, ".");
            scraper.addRuntimeListener(new CitilinkNotebooksScraperRuntimeListener(jdbcTemplate));
            scraper.setDebug(true);
            scraper.execute();

            log.info("Citilink grabbing ended succecsful");
        } catch (Exception e) {
            log.error("cannot process Citilink grabber");
            log.error(e);
        }
    }

    @Override
    public void run() {
        try {
            log.info("Citilink  run started");
            findPages();
            downloadPages();
            grabPages();
            log.info("Citilink run succecsful");
        } catch (Exception e) {
            log.error("cannot process Citilink run");
            log.error(e);
        }
    }
}
