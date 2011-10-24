package ru.goodsReview.indexer;

import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.storage.controller.ProductDbController;

import java.io.File;
import java.util.List;

/**
    Date: 23.10.11
    Time: 21:45
    Author:
        Yaroslav Skudarnov
        SkudarnovYI@gmail.com
*/

public class Indexer {

    public Indexer(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void doIndex (String directoryDB) throws Exception{
        SimpleFSDirectory directory = new SimpleFSDirectory(new File(directoryDB));
        this.writer = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_34, new RussianAnalyzer(Version.LUCENE_34)));
        ProductDbController DBController = new ProductDbController(jdbcTemplate);
        List<Product> products = DBController.getAllProducts();
        Document document;
        for (Product product : products) {
            document = new Document();
            document.add(new Field("id",Long.toString(product.getId()),Field.Store.YES,Field.Index.NOT_ANALYZED));
            document.add(new Field("category_id",Long.toString(product.getCategoryId()),Field.Store.YES,Field.Index.NOT_ANALYZED));
            document.add(new Field("name",product.getName(),Field.Store.YES,Field.Index.NOT_ANALYZED));
            document.add(new Field("description",product.getDescription(),Field.Store.YES, Field.Index.ANALYZED));
            document.add(new Field("popularity",Integer.toString(product.getPopularity()),Field.Store.YES, Field.Index.ANALYZED));
            writer.addDocument(document);
        }
        writer.optimize();
        writer.close();
    }

    private IndexWriter writer;
    private SimpleJdbcTemplate jdbcTemplate;
}