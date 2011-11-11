package ru.goodsReview.searcher;

import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.indexer.Indexer;

import java.io.File;
import java.util.List;

public class TestSearcher {
    static final String DBDirectory = "database";

    public static void main(String[] args) throws Exception {
        final FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(
                "searcher/src/scripts/beans.xml");
        Searcher searcher = (Searcher) context.getBean("searcher");
        searcher.getReadyForSearch();
        List<Product> products = searcher.searchProductByDescription("eee");

        for(Product product : products){
            System.out.println(product.getName());
        }
    }
}
