/*
 *  Date: 11/11/11
 *   Time: 15:01
 *   Author:
 *      Artemij Chugreev
 *      artemij.chugreev@gmail.com
 */

package ru.goodsReview.miner;

import org.apache.log4j.Logger;

import java.io.File;

public abstract class WebHarvestGrabber extends Grabber {
    private static final Logger log = Logger.getLogger(WebHarvestGrabber.class);
    private String downloadConfig;
    private String grabberConfig;
    private String path;

    public abstract void findPages();

    public abstract void grabPages();

    public abstract void downloadPages();

    public void setDownloadConfig(String downloadConfig) {
        this.downloadConfig = downloadConfig;
    }

    public void setGrabberConfig(String grabberConfig) {
        this.grabberConfig = grabberConfig;
    }

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

    public void cleanFolder(File f) {
        if (!f.exists()) {
            log.info("Folder " + path + " not exist");
            return;
        }
        File[] files = f.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                cleanFolder(files[i]);
            } else {
                files[i].delete();
            }
        }
        f.delete();
        log.info("Folder " + path + " deleted successfully");
    }

    @Override
    public void run() {
        try {
            log.info("Run started");
            cleanFolder(new File(path));
            findPages();
            downloadPages();
            grabPages();
            log.info("Run succecsful");
        } catch (Exception e) {
            log.error("Cannot process run");
            log.error(e);
        }
    }
}
