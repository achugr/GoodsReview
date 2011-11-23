/*
    Date: 25.10.11
    Time: 22:56
    Author:
        Artemij Chugreev
        artemij.chugreev@gmail.com
*/

package ru.goodsReview.core.starter;

import org.apache.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class Starter {
    private static final Logger log = Logger.getLogger(Starter.class);
    private final List<String> paths = new ArrayList<String>();

    public Starter(List<String> paths){
        this.paths.addAll(paths);
    }

    public void run(){
        for(String path :paths){
            final FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(path);
        }
    }

    public static void main(final String[] args) throws InterruptedException{
        List<String> paths = new ArrayList<String>();
        if (args.length > 0) {
            for(String s : args)
                paths.add(s);

        } else {
            paths.add("/indexer/src/scripts/beans.xml");
            paths.add("/miner/src/scripts/beans.xml");
            paths.add("/backend/src/scripts/beans.xml");
            paths.add("/frontend/src/scripts/beans.xml");
        }
        log.info("Project started");
        new Starter(paths).run();
        log.info("Project ended");
    }
}
