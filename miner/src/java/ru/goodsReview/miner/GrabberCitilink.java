/*
    Date: 10/26/11
    Time: 06:19
    Author: Alexander Marchuk
            aamarchuk@gmail.com
*/
package ru.goodsReview.miner;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import ru.goodsReview.miner.listener.CitilinkNotebooksScraperRuntimeListener;

public class GrabberCitilink extends Grabber {
    private static final Logger log = Logger.getLogger(GrabberCitilink.class);

    public void setConfig(String config) {
        this.config = config;
    }

    public void downloadPages(String path) {
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


    public void findPages(String path) {
    }

    public void grabPages(String path) {
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

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    TODO fix the absolute path
    public void run() {
        String path = "/home/amarch/Documents/CSCenter/GoodsReview/WebHarvest/Citilink/CitilinkHTML";
        try {
            log.info("Citilink  run started");
            findPages(path);
            downloadPages(path);
            grabPages(path);
            log.info("Citilink run succecsful");
        } catch (Exception e) {
            log.error("cannot process Citilink run");
            log.error(e);
        }
    }
}
