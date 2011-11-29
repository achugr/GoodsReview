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

public class Starter {
    private static final Logger log = Logger.getLogger(Starter.class);

    public static void main(final String[] args) {
        if (args.length == 1) {
            log.info("Module started");
            final FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(args[0]);
            while (context.isRunning()) {
                ;
            }
            log.info("Module ended");
        } else {
            log.info("Project started");
            final FileSystemXmlApplicationContext indexer = new FileSystemXmlApplicationContext(
                    "/indexer/src/scripts/beans.xml");
            final FileSystemXmlApplicationContext miner = new FileSystemXmlApplicationContext(
                    "/miner/src/scripts/beans.xml");
            final FileSystemXmlApplicationContext analyzer = new FileSystemXmlApplicationContext(
                    "/analyzer/src/scripts/beans.xml");
            final FileSystemXmlApplicationContext frontend = new FileSystemXmlApplicationContext(
                    "/frontend/src/scripts/beans.xml");
            log.info("Project ended");
        }
    }
}
