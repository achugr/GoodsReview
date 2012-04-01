package ru.goodsReview.miner;

import org.apache.log4j.Logger;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import org.xml.sax.SAXException;
import ru.common.FileUtil;
import ru.common.Serializer;
import ru.goodsReview.core.db.ControllerFactory;
import ru.goodsReview.core.exception.DeleteException;
import ru.goodsReview.miner.beans.CategoryConfig;
import ru.goodsReview.miner.listener.CitilinkNotebooksScraperRuntimeListener;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: timur
 * Date: 01.04.12
 * Time: 21:28
 * To change this template use File | Settings | File Templates.
 */
public class CitilinkCategoryGrabber extends CategoryGrabber{
    private static final Logger log = Logger.getLogger(CitilinkCategoryGrabber.class);
    private static final String site = "http://www.citilink.ru";
    private static final String encoding = "windows-1251";

    private String path;
    private CategoryConfig categoryConfig;
    private String downloadConfig;
    private String grabberConfig;
    protected ControllerFactory controllerFactory;
    
    private String pagesPath;
    private String descriptionPath;
    private String listPath;
    
    private String allLinksPath;
    private String newLinksPath;
    private String latterLinksPath;

    public CitilinkCategoryGrabber(String path, String pathToXml, String downloadConfig, String grabberConfig, ControllerFactory controllerFactory ){
        this.path = path;
        categoryConfig = getCategoryConfig(pathToXml);
        this.downloadConfig = downloadConfig;
        this.grabberConfig = grabberConfig;
        this.controllerFactory = controllerFactory;
    }
    
    @Override
    protected void init() {
        pagesPath = path + categoryConfig.getCategory() + "reviews/";
        descriptionPath = path + categoryConfig.getCategory() + "descriptions/";
        listPath = path + categoryConfig.getCategory() + "list/";
        allLinksPath = listPath + "allLinks.xml";
        newLinksPath = listPath + "newLinks.xml";
        latterLinksPath = listPath + "latterLinks.xml";
    }

    private CategoryConfig getCategoryConfig(String pathToConfigXml) {
        CategoryConfig categoryConfig = null;
        try{
            JAXBContext jc = JAXBContext.newInstance(CategoryConfig.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            categoryConfig = (CategoryConfig) unmarshaller.unmarshal(new File(pathToConfigXml));
        }catch(JAXBException e){
            log.error("Incorrect path to configuration xml file");
        }
        return categoryConfig;
    }

    public void cleanFolders() throws DeleteException {
        FileUtil.cleanFolder(new File(pagesPath));
        FileUtil.cleanFolder(new File(descriptionPath));
    }

    public void createFolders() throws IOException {
        new File(pagesPath).mkdirs();
        new File(descriptionPath).mkdirs();
        new File(listPath).mkdirs();
    }

    public void updateList() {
        try {
            log.info("Update list started");
            ScraperConfiguration config = new ScraperConfiguration(downloadConfig);
            Scraper scraper = new Scraper(config, "");

            scraper.addVariableToContext("category", categoryConfig.getCategory());
            scraper.addVariableToContext("filePath", latterLinksPath);
            scraper.setDebug(true);
            scraper.execute();
            log.info("Update list successful");
        } catch (Exception e) {
            log.error("Cannot process update list", e);
        }
    }

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

    public void grabPages() throws FileNotFoundException {
        log.info("Grabbing started");
        ScraperConfiguration config = new ScraperConfiguration(grabberConfig);
        Scraper scraper = new Scraper(config, "");
        scraper.addRuntimeListener(new CitilinkNotebooksScraperRuntimeListener(controllerFactory, categoryConfig.getNameRegexp()));
        scraper.addVariableToContext("path", pagesPath);
        scraper.addVariableToContext("numberOfFirstReview", 0);

        scraper.setDebug(true);
        scraper.execute();
        log.info("Grabbing ended successful");
    }
}
