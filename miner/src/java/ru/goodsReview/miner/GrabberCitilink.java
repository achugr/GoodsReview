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

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void run() {
		try {
            /*
        scraper.getSites
        checkVisitedPages;
        class Downloader via spring.. download pages
                    GrabberCitilink*/
            //findPages();
            //downloadPages();

			log.info("Citilink grabbing started");
			ScraperConfiguration config = new ScraperConfiguration(this.config);
			Scraper scraper = new Scraper(config, ".");
			scraper.addRuntimeListener(new CitilinkNotebooksScraperRuntimeListener(jdbcTemplate));
			scraper.setDebug(true);
			scraper.execute();

			log.info("Citilink grabbing ended succecsful");
		} catch (Exception e) {
			log.error("cannot process Citilink");
			log.error(e);
		}
	}
}