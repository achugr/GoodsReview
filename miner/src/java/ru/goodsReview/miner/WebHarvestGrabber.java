/*
 *  Date: 11/11/11
 *   Time: 15:01
 *   Author:
 *      Artemij Chugreev
 *      artemij.chugreev@gmail.com
 */

package ru.goodsReview.miner;

public abstract class WebHarvestGrabber extends Grabber {
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
}
