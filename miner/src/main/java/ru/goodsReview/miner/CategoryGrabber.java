package ru.goodsReview.miner;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import ru.goodsReview.core.exception.DeleteException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: timur
 * Date: 01.04.12
 * Time: 23:45
 * To change this template use File | Settings | File Templates.
 */
public abstract class CategoryGrabber implements Runnable {
    private static final Logger log = Logger.getLogger(CategoryGrabber.class);

    protected abstract void init();

    protected abstract void findPages() throws IOException, ParserConfigurationException, SAXException, TransformerException;

    protected abstract void grabPages() throws IOException;

    protected abstract void downloadPages();

    protected abstract void updateList();

    protected abstract void cleanFolders() throws DeleteException;

    protected abstract void createFolders() throws IOException;

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
    
    public void run(){
        try{
            log.info("Run started");
            init();
            downloader();
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
