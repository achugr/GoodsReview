package ru.goodsReview.searcher;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import ru.goodsReview.core.model.Product;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Searcher {
    private static final Logger log = Logger.getLogger(Searcher.class);
    private static final int MAX_DOC = 1000;

    private String directoryDB;
    private IndexSearcher indexSearcherProduct;

    public void setDirectoryDB(String directoryDB) {
        this.directoryDB = directoryDB;
    }

    public void getReadyForSearch() throws IOException {
        indexSearcherProduct = new IndexSearcher(new SimpleFSDirectory(new File(directoryDB)));
        //  log.error("Must create index before use UI");
    }

    public List<Product> searchProductByName(String query) throws ParseException, IOException {

        Analyzer analyzer = new RussianAnalyzer(Version.LUCENE_34);
        QueryParser parser = new QueryParser(Version.LUCENE_34, "name", analyzer);
        Query search = parser.parse(query);

        ScoreDoc[] hits = indexSearcherProduct.search(search, null, MAX_DOC).scoreDocs;
        List<Product> lst = new ArrayList<Product>();
        for (int i = 0; i < hits.length; i++) {
            Document doc = indexSearcherProduct.doc(hits[i].doc);
            lst.add(productMap(doc));
        }
        return lst;
    }

    private Product productMap(Document doc) {
        return new Product(Long.parseLong(doc.get("id")), doc.get("name"));
    }
}
