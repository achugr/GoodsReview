/*
    Date: 10/26/11
    Time: 06:20
    Author: Alexander Marchuk
            aamarchuk@gmail.com
*/
package ru.goodsReview.extractor;

/**
 * User: Alexander Marchuk
 * aamarchuk@gmail.com
 * Date: 10/26/11
 * Time: 6:20 AM
 */
import org.apache.log4j.Logger;
import java.util.List;

public class GrabberContainer {

    private static final Logger log = Logger.getLogger(GrabberContainer.class);

    private List<Grabber> grabberList;

    public void setGrabberList(List<Grabber> grabberList) {
        this.grabberList = grabberList;
    }

    public void afterPropertiesSet() {

        log.info("miner started...");
        if (grabberList != null) {
            for (Grabber g : grabberList) {
                g.grab();
            }
        }
        log.info("miner finished.");
    }
}