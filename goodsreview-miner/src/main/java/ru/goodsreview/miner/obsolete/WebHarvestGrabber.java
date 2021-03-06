/*
 *  Date: 11/11/11
 *   Time: 15:01
 *   Author:
 *      Artemij Chugreev
 *      artemij.chugreev@gmail.com
 */

package ru.goodsreview.miner.obsolete;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

public abstract class WebHarvestGrabber extends Grabber {
    private static final Logger log = Logger.getLogger(WebHarvestGrabber.class);
    private String downloadConfig;
    private String grabberConfig;
    private String path;
    private String pathNameParseConf;

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

    @Required
    protected String getDownloadConfig() {
        return downloadConfig;
    }

    @Required
    protected String getGrabberConfig() {
        return grabberConfig;
    }

    @Required
    protected String getPath() {
        return path;
    }


    public String getPathNameParseConf() {
        return pathNameParseConf;
    }

    public void setPathNameParseConf(String pathNameParseConf) {
        this.pathNameParseConf = pathNameParseConf;
    }
}
