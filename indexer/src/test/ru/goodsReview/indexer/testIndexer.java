package ru.goodsReview.indexer;

/*
    Date: 25.10.11
    Time: 23:20
    Author:
        Yaroslav Skudarnov
        SkudarnovYI@gmail.com
*/

import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.scheduling.timer.ScheduledTimerTask;
//todo delete this class.

public class TestIndexer {
    static final String DBDirectory = "database";

    public static void main(String[] args) throws Exception {
        final FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(
                "indexer/src/scripts/beans.xml");
        ScheduledTimerTask st = (ScheduledTimerTask) context.getBean("scheduledTask");
/*        SimpleJdbcTemplate jdbcTemplate = (SimpleJdbcTemplate) context.getBean("jdbcTemplate");
        Indexer indexer = new Indexer();
		indexer.setJdbcTemplate(jdbcTemplate);
        indexer.doProductsIndex(DBDirectory);*/
    }
}
