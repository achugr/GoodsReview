/*
 *  Date: 11/11/11
 *   Time: 15:01
 *   Author:
 *      Artemij Chugreev
 *      artemij.chugreev@gmail.com
 */

package ru.goodsReview.miner;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.xml.sax.SAXException;
import ru.goodsReview.core.exception.DeleteException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;

public abstract class WebHarvestGrabber extends Grabber {
    private static final Logger log = Logger.getLogger(WebHarvestGrabber.class);
    private String downloadConfig;
    private String grabberConfig;
    private String path;

    protected abstract void init();

    protected abstract void findPages() throws IOException, ParserConfigurationException, SAXException, TransformerException;

    protected abstract void grabPages() throws IOException;

    protected abstract void downloadPages();

    protected abstract void updateList();

    protected abstract void cleanFolders() throws DeleteException;

    protected abstract void createFolders() throws IOException;

    @Required
    public void setDownloadConfig(String downloadConfig) {
        this.downloadConfig = downloadConfig;
    }

    @Required
    public void setGrabberConfig(String grabberConfig) {
        this.grabberConfig = grabberConfig;
    }

    @Required
    public void setPath(String path) {
        this.path = path;
    }

    protected String getDownloadConfig() {
        return downloadConfig;
    }

    protected String getGrabberConfig() {
        return grabberConfig;
    }

    protected String getPath() {
        return path;
    }


    /**
     * Download new pages. This method need ethernet connection. DDos sites.
     */
    public void downloader() throws DeleteException, IOException, TransformerException, SAXException, ParserConfigurationException {
        cleanFolders();
        createFolders();
        updateList();
        findPages();
        downloadPages();
    }

    /**
     * Grabs pages to db.
     */
    public void grabber() throws IOException {
        grabPages();
    }

    @Override
    public void run() {
        try {
            log.info("Run started");
            init();

//            downloader();
            //this method should be one for all grabbers
            Thread thread = new Thread(Downloader.getInstance());
            thread.start();
            thread.join();
            grabber();

            log.info("Run successful");
        } catch (Exception e) {
            log.error("Cannot process run", e);
        }
    }
}
