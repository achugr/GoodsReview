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

import ru.goodsReview.miner.exception.DeleteException;

import java.io.File;
import java.io.IOException;

public abstract class WebHarvestGrabber extends Grabber {
    private static final Logger log = Logger.getLogger(WebHarvestGrabber.class);
    private String downloadConfig;
    private String grabberConfig;
    private String path;

    public abstract void findPages() throws IOException;

    public abstract void grabPages();

    public abstract void downloadPages();

    public abstract void updateList();

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

    public void cleanFolder(File f) throws DeleteException {
        if (!f.exists()) {
            log.info("Cleaning Folder " + path + " not exist");
            return;
        }
        File[] files = f.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                cleanFolder(files[i]);
            } else {
                if (!files[i].delete())
                    throw new DeleteException("Unavailable delete file");
            }
        }
        if (!f.delete())
            throw new DeleteException("Unavailable delete file");
        log.info(" Cleaning Folder " + path + " deleted successfully");
    }

    @Override
    public void run() {
        try {
            log.info("Run started");
//           cleanFolder(new File(path + "Citilink/Pages/"));
//            cleanFolder(new File(path + "Citilink/Descriptions/"));
//            updateList();
//            findPages();
//            downloadPages();
            grabPages();
//            cleanFolder(new File(path + "Citilink/Pages/"));
//            cleanFolder(new File(path + "Citilink/Descriptions/"));
            log.info("Run succecsful");
        } catch (Exception e) {
            log.error("Cannot process run");
            log.error(e);
        }
    }
}
