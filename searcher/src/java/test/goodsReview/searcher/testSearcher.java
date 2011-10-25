package goodsReview.searcher;

import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.indexer.Indexer;

import javax.sql.DataSource;
import java.io.File;
import java.util.ArrayList;

public class testSearcher {
    static final String DBDirectory = "database";
    public static void main(String[] args) throws Exception {
        final FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("storage/src/scripts/beans.xml");
        DataSource dataSource = null;
        dataSource = (DataSource) context.getBean("dataSource");
        Indexer indexer = new Indexer(new SimpleJdbcTemplate(dataSource));
        indexer.doIndex(DBDirectory);
        IndexSearcher indexSearcher = new IndexSearcher(new SimpleFSDirectory(new File(DBDirectory)));
        QueryParser queryParser = new QueryParser(Version.LUCENE_34, "",new RussianAnalyzer(Version.LUCENE_34));
        Query query = queryParser.parse("name:iPhone 4");
        ArrayList<Review> reviews = new ArrayList<Review>();
        //Not written yet. Please, careful!
    }
}