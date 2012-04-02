/*
    Date: 10/26/11
    Time: 06:19
    Author: Alexander Marchuk
            aamarchuk@gmail.com
*/
package ru.goodsReview.miner;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GrabberCitilink extends WebHarvestGrabber{
    private static final Logger log = Logger.getLogger(GrabberCitilink.class);
    private List<CitilinkCategoryGrabber> citilinkCategoryGrabbers = new ArrayList<CitilinkCategoryGrabber>();

    public void addCategories() {
        citilinkCategoryGrabbers.add(
                new CitilinkCategoryGrabber(getPath(),"./miner/src/main/resources/notebooks.xml",
                        getGrabberConfig(),getDownloadConfig(),controllerFactory )
        );

        citilinkCategoryGrabbers.add(
                new CitilinkCategoryGrabber(getPath(),"./miner/src/main/resources/tvs.xml",
                        getGrabberConfig(),getDownloadConfig(),controllerFactory )
        );
    }

    @Override
    public void run() {
        try {
            log.info("Run started");
            addCategories();
            for(CitilinkCategoryGrabber ccg : citilinkCategoryGrabbers){
                Thread thread = new Thread(ccg);
                thread.start();
                thread.join();
            }
            log.info("Run successful");
        } catch (Exception e) {
            log.error("Cannot process run", e);
        }
    }
}
