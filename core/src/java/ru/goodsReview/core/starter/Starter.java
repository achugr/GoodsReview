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
        log.info("App started");
        final FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(new String[]{args[0]});
    }
}
