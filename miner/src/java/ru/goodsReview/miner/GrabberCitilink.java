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

    @Override
    public void downloadPages() {
        try {
            //todo you shouldn't write citilink in log. It will be written by logger based on row 15
            log.info("Download pages started");
            ScraperConfiguration config = new ScraperConfiguration(getDownloadConfig());
            Scraper scraper = new Scraper(config, ".");
            scraper.addVariableToContext("path", getPath());
            scraper.setDebug(true);
            scraper.execute();

            log.info("Download pages succecsful");
        } catch (Exception e) {
            log.error("Cannot process download pages");
            log.error(e);
        }
    }

    @Override
    public void findPages() {
    }
    //todo where should be path??

    @Override
    public void grabPages() {
        try {
            log.info("Grabbing started");
            ScraperConfiguration config = new ScraperConfiguration(getGrabberConfig());
            Scraper scraper = new Scraper(config, ".");
            scraper.addRuntimeListener(new CitilinkNotebooksScraperRuntimeListener(jdbcTemplate));
            scraper.addVariableToContext("path", getPath());
            scraper.setDebug(true);
            scraper.execute();

            log.info("Grabbing ended succecsful");
        } catch (Exception e) {
            log.error("Cannot process grabber");
            log.error(e);
        }
    }


}
