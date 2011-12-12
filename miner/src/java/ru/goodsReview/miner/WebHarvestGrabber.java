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
import ru.goodsReview.core.exception.DeleteException;
import ru.goodsReview.core.utils.FileUtil;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public abstract class WebHarvestGrabber extends Grabber {
    private static final Logger log = Logger.getLogger(WebHarvestGrabber.class);
    private String downloadConfig;
    private String grabberConfig;
    private String path;

    protected abstract void findPages() throws IOException;

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
    public void downloader() throws DeleteException, IOException {
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
            downloader();
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
