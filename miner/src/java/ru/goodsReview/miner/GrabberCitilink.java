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
import ru.goodsReview.core.exception.DeleteException;
import ru.goodsReview.core.utils.FileUtil;
import ru.goodsReview.miner.listener.CitilinkNotebooksScraperRuntimeListener;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GrabberCitilink extends WebHarvestGrabber {
    private static final Logger log = Logger.getLogger(GrabberCitilink.class);
    private static final String site = "http://www.citilink.ru";

    @Override
    protected void cleanFolders() throws DeleteException {
        FileUtil.cleanFolder(new File(getPath() + "Pages/"));
        FileUtil.cleanFolder(new File(getPath() + "Descriptions/"));
    }

    @Override
    protected void createFolders() throws IOException {
        new File(getPath() + "Pages").mkdirs();
        new File(getPath() + "Descriptions").mkdirs();
        new File(getPath() + "list").mkdirs();
    }

    @Override
    protected void updateList() {
        try {
            log.info("Update list started");
            ScraperConfiguration config = new ScraperConfiguration(getDownloadConfig());
            Scraper scraper = new Scraper(config, ".");
            scraper.addVariableToContext("path", getPath());
            scraper.setDebug(true);
            scraper.execute();
            log.info("Update list successful");
        } catch (Exception e) {
            log.error("Cannot process update list", e);
        }
    }

    @Override
    //TODO: use RandomAcessFile and update lines with old product, but new reviews
    protected void downloadPages() {
        try {
            log.info("Adding download pages started");
            Scanner scanner = new Scanner(new File(getPath() + "list/NewLinks.txt"));
            FileWriter out = new FileWriter(getPath() + "list/AllLinks.txt", true);

            List<String> linksToDownload = new ArrayList<String>();
            while (scanner.hasNext()) {
                String productUrl = scanner.next();
                String reviewNumber = scanner.next();

                String url = site + productUrl + "?opinion";
                //downloadOneLink( url, getPath() + "Pages/" + i + ".html"  );
                linksToDownload.add(url);
                out.write(productUrl + " " + reviewNumber + "\n");
            }
            Downloader.getInstance().addLinks(linksToDownload, getPath() + "Pages", "windows-1251");
            scanner.close();
            out.close();

            log.info("Adding download pages successful");
        } catch (Exception e) {
            log.error("Cannot process download pages", e);
        }
    }

    @Override
    //TODO:: not add, if review number changes, only update
    protected void findPages() throws IOException {
        log.info("Find pages started");

        //what links we visited before and count of reviews
        String allLinks = new String();
        File allLinksFile = new File(getPath() + "list/AllLinks.txt");
        if (allLinksFile.exists()) {
            StringBuilder links = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(allLinksFile));
            String nextLine;
            while ((nextLine = br.readLine()) != null) {
                links.append(nextLine);
            }

            allLinks = links.toString();
            br.close();
        }
        {
            FileWriter out = new FileWriter(getPath() + "list/NewLinks.txt", false);
            Scanner scanner = new Scanner(new FileReader(getPath() + "list/LatterLinks.txt"));
            String reviewNumber;
            String reviewUrl;
            while(scanner.hasNext()){
                reviewUrl = scanner.next();
                reviewNumber = scanner.next();
                int position = allLinks.indexOf(reviewUrl + " " + reviewNumber);
                if (position == -1) {
                    out.write(reviewUrl + " " + reviewNumber + "\n");
                }
            }
            out.close();
            scanner.close();
        }
        log.info("Find pages successful");
    }

    @Override
    protected void grabPages() throws IOException{
        log.info("Grabbing started");
        ScraperConfiguration config = new ScraperConfiguration(getGrabberConfig());
        Scraper scraper = new Scraper(config, ".");
        scraper.addRuntimeListener(new CitilinkNotebooksScraperRuntimeListener(jdbcTemplate));
        scraper.addVariableToContext("path", getPath() + "Pages/");
        scraper.addVariableToContext("numberOfFirstReview", 0);
        scraper.setDebug(true);
        scraper.execute();
        log.info("Grabbing ended successful");
    }
}
