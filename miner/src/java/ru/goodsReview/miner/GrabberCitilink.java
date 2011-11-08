/*
    Date: 10/26/11
    Time: 06:19
    Author: Alexander Marchuk
            aamarchuk@gmail.com
*/
package ru.goodsReview.miner;

import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import ru.goodsReview.miner.listener.CitilinkNotebooksScraperRuntimeListener;
import org.apache.log4j.Logger;

import javax.sql.DataSource;

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
            final FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("storage/src/scripts/beans.xml");
                         DataSource dataSource = (DataSource) context.getBean("dataSource");

			log.info("Citilink grabbing started");
			ScraperConfiguration config = new ScraperConfiguration(this.config);
			Scraper scraper = new Scraper(config, ".");
			scraper.addRuntimeListener(new CitilinkNotebooksScraperRuntimeListener(new SimpleJdbcTemplate(dataSource)));
			scraper.setDebug(true);
			scraper.execute();

			log.info("Citilink grabbing ended succecsful");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("cannot process Citilink");
			log.error(e);
		}
	}

    public static void main(String[] args){

        SimpleJdbcTemplate jdbcTemplate = null;
        GrabberCitilink citi =   new GrabberCitilink();
        citi.setConfig("extractor/webHarvest/configs/CitilinkReviewsConfig.xml");
        citi.setJdbcTemplate(jdbcTemplate);
        citi.run();

    }
}