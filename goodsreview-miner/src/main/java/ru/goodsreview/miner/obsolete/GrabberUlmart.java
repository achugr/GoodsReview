package ru.goodsreview.miner.obsolete;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: timur
 */

public class GrabberUlmart extends WebHarvestGrabber{
    private static final Logger log = Logger.getLogger(GrabberUlmart.class);
    private List<UlmartCategoryGrabber> ulmartCategoryGrabbers = new ArrayList<UlmartCategoryGrabber>();

    public void addCategories() {
        ulmartCategoryGrabbers.add(
                new UlmartCategoryGrabber(getPath(), getPathNameParseConf()+"notebooks.xml",
                        getDownloadConfig(), getGrabberConfig(), controllerFactory )
        );
    }

    @Override
    public void run() {
        try {
            log.info("Run started");
            addCategories();
            for(UlmartCategoryGrabber ucg : ulmartCategoryGrabbers){
                ucg.run();
            }
            log.info("Run successful");
        } catch (Exception e) {
            log.error("Cannot process run", e);
        }
    }
}
