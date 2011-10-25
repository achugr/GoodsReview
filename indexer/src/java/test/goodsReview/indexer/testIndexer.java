package test.goodsReview.indexer;

/*
    Date: 25.10.11
    Time: 23:20
    Author: 
        Yaroslav Skudarnov 
        SkudarnovYI@gmail.com
*/

import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.indexer.Indexer;

import javax.sql.DataSource;

public class testIndexer {
    static final String DBDirectory = "database";
    public static void main(String[] args) throws Exception {
        final FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("storage/src/scripts/beans.xml");
        DataSource dataSource = null;
        dataSource = (DataSource) context.getBean("dataSource");
        Indexer indexer = new Indexer(new SimpleJdbcTemplate(dataSource));
        indexer.doIndex(DBDirectory);
    }
}