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

import java.io.*;

public class GrabberCitilink extends WebHarvestGrabber {
    private static final Logger log = Logger.getLogger(GrabberCitilink.class);

    @Override
    public void updateList() {
        try {
            //todo you shouldn't write citilink in log. It will be written by logger based on row 15
            log.info("Update list started");
            ScraperConfiguration config = new ScraperConfiguration(getDownloadConfig());
            Scraper scraper = new Scraper(config, ".");
            scraper.addVariableToContext("path", getPath());
            scraper.setDebug(true);
            scraper.execute();

            log.info("Update list succecsful");
        } catch (Exception e) {
            log.error("Cannot process update list", e);
        }
    }

    @Override
    //TODO: use RandomAcessFile and update lines with old product, but new reviews
    public void downloadPages() {
        try {
            log.info("Download pages started");


            ScraperConfiguration config = new ScraperConfiguration("miner/webHarvest/configs/Citilink/downloadOnePage.xml");
            Scraper scraper = new Scraper(config, ".");
            scraper.setDebug(true);

            FileInputStream ffstream = new FileInputStream("data/miner/Citilink/list/NewLinks.txt");
            DataInputStream all = new DataInputStream(ffstream);
            BufferedReader brr = new BufferedReader(new InputStreamReader(all));
            String product = brr.readLine();

            FileWriter out = new FileWriter("data/miner/Citilink/list/AllLinks.txt", true);

            long i = 1;
            while ((product = brr.readLine()) != null) {
                String productURl = product.substring(0, product.indexOf(":::"));
                scraper.addVariableToContext("path", getPath() + "Citilink/");
                scraper.addVariableToContext("pageUrl", productURl);
                scraper.addVariableToContext("name", i);
                scraper.execute();
                out.write("\n" + product);
                i++;

                //System.out.println(productURl + " "+ s +" "+getPath());
            }
            all.close();
            out.close();

            log.info("Download pages succecsful");
        } catch (Exception e) {
            log.error("Cannot process download pages", e);
        }
    }

    @Override
    //TODO:: not add, if review number changes, only update
    public void findPages() {
            this.updateList();
            log.info("Find pages started");
        try {
            FileInputStream ffstream = new FileInputStream("data/miner/Citilink/list/AllLinks.txt");
            DataInputStream all = new DataInputStream(ffstream);
            BufferedReader brr = new BufferedReader(new InputStreamReader(all));
            String allLinks = "";
            String nextLine;
            while ((nextLine = brr.readLine()) != null) {
                allLinks += nextLine;
            }
            all.close();
            FileWriter out = new FileWriter("data/miner/Citilink/list/NewLinks.txt", false);

            FileInputStream fstream = new FileInputStream("data/miner/Citilink/list/LatterLinks.txt");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String reviewNumber;
            String reviewURL;
            while ((reviewURL = br.readLine()) != null && (reviewNumber = br.readLine()) != null) {
                reviewURL = reviewURL.replace("\" \"", "");
                int position = allLinks.indexOf(reviewURL + ":::" + reviewNumber);
                if (position == -1) {
                    out.write(reviewURL + ":::" + reviewNumber+"\n");
                    //System.out.println("AAAAdddded");
                } else {//System.out.println("Not!AAAAdddded");
                }
            }
            in.close();
            out.close();
            log.info("Find pages succesful");
        } catch (Exception e) {
            log.error("Cannot process find pages", e);
        }
    }

    @Override
    public void grabPages() {
        try {
            log.info("Grabbing started");
            ScraperConfiguration config = new ScraperConfiguration(getGrabberConfig());
            Scraper scraper = new Scraper(config, ".");
            scraper.addRuntimeListener(new CitilinkNotebooksScraperRuntimeListener(jdbcTemplate));
            scraper.addVariableToContext("path", getPath() + "Citilink/Pages/");
            scraper.addVariableToContext("numberOfFirstReview", 0);
            scraper.setDebug(true);
            scraper.execute();
            log.info("Grabbing ended succecsful");
        } catch (Exception e) {
        log.error("Cannot process grabber", e);
        }
    }


}
