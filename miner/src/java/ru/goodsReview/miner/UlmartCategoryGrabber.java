package ru.goodsReview.miner;

import org.apache.log4j.Logger;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import org.xml.sax.SAXException;
import ru.common.FileUtil;
import ru.common.Serializer;
import ru.goodsReview.core.db.ControllerFactory;
import ru.goodsReview.core.exception.DeleteException;
import ru.goodsReview.miner.listener.UlmartScraperRuntimeListener;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: timur
 */

public class UlmartCategoryGrabber extends CategoryGrabber {
    private static final Logger log = Logger.getLogger(UlmartCategoryGrabber.class);
    private static final String site = "http://www.ulmart.ru";
    private static final String encoding = "UTF-8";

    private String path;
    private String workingDir;
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

    public UlmartCategoryGrabber(String path, String pathToXml, String downloadConfig, String grabberConfig, ControllerFactory controllerFactory ){
        this.path = path;
        categoryConfig = getCategoryConfig(pathToXml);
        this.downloadConfig = downloadConfig;
        this.grabberConfig = grabberConfig;
        this.controllerFactory = controllerFactory;
    }

    @Override
    protected void init() {
        workingDir = path + categoryConfig.getCategory();
        pagesPath =  workingDir + "reviews/";
        descriptionPath = workingDir + "descriptions/";
        listPath = workingDir + "list/";
        allLinksPath = listPath + "allLinks.xml";
        newLinksPath = listPath + "newLinks.xml";
        latterLinksPath = listPath + "latterLinks.xml";
    }

    private CategoryConfig getCategoryConfig(String pathToConfigXml) {
        CategoryConfig categoryConfig = null;
        JAXBContext jc = null;
        try{
            jc = JAXBContext.newInstance(CategoryConfig.class);
        }catch(JAXBException e){
            log.error("Error in JAXBContent");
        }
        Unmarshaller unmarshaller = null;
        try {
            unmarshaller = jc.createUnmarshaller();
        } catch (JAXBException e) {
            log.error("Error while creating the Unmarshaller");
        }
        try{
            StreamSource xmlConfigFile = new StreamSource(pathToConfigXml);
            categoryConfig = (CategoryConfig)((JAXBElement) unmarshaller.unmarshal(xmlConfigFile, CategoryConfig.class)).getValue();
        }catch (JAXBException e) {
            log.error("Error in unmarshal method");
        }

        return categoryConfig;
    }

    @Override
    protected void cleanFolders() throws DeleteException {
        FileUtil.cleanFolder(new File(pagesPath));
        FileUtil.cleanFolder(new File(descriptionPath));
    }

    @Override
    protected void createFolders() throws IOException {
        new File(pagesPath).mkdirs();
        new File(descriptionPath).mkdirs();
        new File(listPath).mkdirs();
    }

    @Override
    protected void updateList() {
        try {
            log.info("Update list started");
            //File linksFile = new File(latterLinksPath);
            //linksFile.createNewFile();

            ScraperConfiguration config = new ScraperConfiguration(downloadConfig);
            Scraper scraper = new Scraper(config, "./");

            scraper.addVariableToContext("category", categoryConfig.getCategory());
            scraper.addVariableToContext("file", latterLinksPath);
            scraper.setDebug(true);
            scraper.execute();
            log.info("Update list successful");
        } catch (FileNotFoundException e) {
            log.error("Cannot process update list: downloadConfig.xml doesn't exit", e);
        } catch (IOException e) {
            log.error("Cannot process update list: can't create latterLinks.xml", e);
        }
    }

    @Override
    protected void findPages() throws IOException, ParserConfigurationException, SAXException, TransformerException {
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
    protected void downloadPages() {
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
                String url = site + productUrl + "?tab=estimate&p=1";
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
    protected void grabPages() throws IOException {
        log.info("Grabbing started");
        ScraperConfiguration config = new ScraperConfiguration(grabberConfig);
        Scraper scraper = new Scraper(config, "./");
        scraper.addRuntimeListener(new UlmartScraperRuntimeListener(controllerFactory, categoryConfig.getRegExp()));
        scraper.addVariableToContext("path", pagesPath);
        scraper.addVariableToContext("numberOfFirstReview", 0);

        scraper.setDebug(true);
        scraper.execute();
        log.info("Grabbing ended successful");
    }
}
