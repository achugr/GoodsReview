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
import org.xml.sax.SAXException;
import ru.common.FileUtil;
import ru.common.Serializer;
import ru.goodsReview.core.exception.DeleteException;
import ru.goodsReview.miner.listener.CitilinkNotebooksScraperRuntimeListener;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.*;

public class GrabberCitilink extends WebHarvestGrabber {
    private static final Logger log = Logger.getLogger(GrabberCitilink.class);
    private static final String site = "http://www.citilink.ru";
    private static final String encoding = "windows-1251";

    private String pagesPath;
    private String descriptionPath;
    private String listPath;
    private String allLinksPath;
    private String newLinksPath;
    private String latterLinksPath;

    public void init() {
        pagesPath = getPath() + "reviews/";
        descriptionPath = getPath() + "descriptions/";
        listPath = getPath() + "list/";
        allLinksPath = listPath + "allLinks.xml";
        newLinksPath = listPath + "newLinks.xml";
        latterLinksPath = listPath + "latterLinks.xml";
    }

    protected void cleanFolders() throws DeleteException {
        FileUtil.cleanFolder(new File(pagesPath));
        FileUtil.cleanFolder(new File(descriptionPath));
    }

    protected void createFolders() throws IOException {
        new File(pagesPath).mkdirs();
        new File(descriptionPath).mkdirs();
        new File(listPath).mkdirs();
    }

    @Override
    public void updateList() {
        try {
            log.info("Update list started");
            ScraperConfiguration config = new ScraperConfiguration(getDownloadConfig());
            Scraper scraper = new Scraper(config, ".");
            scraper.addVariableToContext("path", getPath());
            scraper.addVariableToContext("filePath", latterLinksPath);
            scraper.setDebug(true);
            scraper.execute();
            log.info("Update list successful");
        } catch (Exception e) {
            log.error("Cannot process update list", e);
        }
    }

    @Override
    //TODO: use RandomAcessFile and update lines with old product, but new reviews
    public void downloadPages() {
        try {
            log.info("Adding download pages started");

            File newLinksFile = new File(newLinksPath);
            Map<String, Integer> newLinksMap = Serializer.instance().readMap(newLinksFile);

            File allLinksFile = new File(allLinksPath);
            Map<String, Integer> allLinksMap = new HashMap<String, Integer>();
            if (allLinksFile.exists()) {
                allLinksMap = Serializer.instance().readMap(allLinksFile);
            }

            List<String> linksToDownload = new ArrayList<String>();
            Iterator<String> iterator = newLinksMap.keySet().iterator();
            while (iterator.hasNext()) {
                String productUrl = iterator.next();
                Integer reviewNumber = newLinksMap.get(productUrl);
                String url = site + productUrl + "?opinion";
                linksToDownload.add(url);
            }
            allLinksMap.putAll(newLinksMap);
            Serializer.instance().write(allLinksMap, allLinksFile);
            Downloader.getInstance().addLinks(linksToDownload, pagesPath, encoding);
            log.info("Adding download pages successful");
        } catch (Exception e) {
            log.error("Cannot process download pages", e);
        }
    }

    @Override
    //TODO:: not add, if review number changes, only update
    public void findPages() throws IOException, ParserConfigurationException, SAXException, TransformerException {
        log.info("Find pages started");

        //what links we visited before and count of reviews
        File allLinksFile = new File(allLinksPath);
        Map<String, Integer> allLinksMap = new HashMap<String, Integer>();
        if (allLinksFile.exists()) {
            allLinksMap = Serializer.instance().readMap(allLinksFile);
        }

        File latterLinksFile = new File(latterLinksPath);
        Map<String, Integer> latterLinksMap = Serializer.instance().readMap(latterLinksFile);
        Iterator<String> iterator = latterLinksMap.keySet().iterator();
        Map<String, Integer> newLinksMap = new HashMap<String, Integer>();
        while (iterator.hasNext()) {
            String reviewUrl = iterator.next();
            Integer reviewNumber = latterLinksMap.get(reviewUrl);
            // if new url or added review
            if (!allLinksMap.containsKey(reviewUrl) || allLinksMap.get(reviewUrl) != reviewNumber) {
                newLinksMap.put(reviewUrl, reviewNumber);
            }
        }
        Serializer.instance().write(newLinksMap, new File(newLinksPath));
        log.info("Find pages successful");
    }

    @Override
    public void grabPages() throws FileNotFoundException {
        log.info("Grabbing started");
        ScraperConfiguration config = new ScraperConfiguration(getGrabberConfig());
        Scraper scraper = new Scraper(config, ".");
        scraper.addRuntimeListener(new CitilinkNotebooksScraperRuntimeListener(controllerFactory));
        scraper.addVariableToContext("path", pagesPath);
        scraper.addVariableToContext("numberOfFirstReview", 0);
        scraper.setDebug(true);
        scraper.execute();
        log.info("Grabbing ended successful");
    }
}
