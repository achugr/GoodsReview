package ru.goodsReview.indexer;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.model.*;
import ru.goodsReview.storage.controller.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

/**
 Date: 23.10.11
 Time: 21:45
 Author:
 Yaroslav Skudarnov
 SkudarnovYI@gmail.com
 */
public class Indexer extends TimerTask {
    private static final Logger log = org.apache.log4j.Logger.getLogger(Indexer.class);

    private IndexWriter writer;
    private SimpleJdbcTemplate jdbcTemplate;

    /**
     * Sets up jdbcTemplate for This Indexer object.
     * @param jdbcTemplate Object of SimpleJdbcTemplate class, which has to be set by this method.
     * @return Nothing.
     */
    @Required
    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Creates an IndexWriter object
     * @param directoryDB Place where writer should write an index.
     * @return
     */
    private void init(String directoryDB) throws Exception {
        SimpleFSDirectory directory = new SimpleFSDirectory(new File(directoryDB));
        writer = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_34, new RussianAnalyzer(Version.LUCENE_34)));
        writer.deleteAll();
    }

    /**
     * Should be executed after indexing itself. Optimizes index and closes it.
     * @throws IOException
     */
    private void finish() throws IOException {
        writer.optimize();
        writer.close();
    }

    /**
     * Indexes products.
     * @param directoryDB Place where index should be.
     * @throws Exception
     */
    public void doProductsIndex(String directoryDB) throws Exception {
        log.debug("Products Index creating started");

        init(directoryDB);
        List<Product> products = new ProductDbController(jdbcTemplate).getAllProducts();
        Document document;
        for (Product product : products) {
            document = new Document();
            document.add(addField("id", Long.toString(product.getId())));
            document.add(addField("category_id", Long.toString(product.getCategoryId())));
            document.add(addField("name", product.getName()));
            String desc = product.getDescription();
            if (desc != null) {
                document.add(addField("description", desc));
            }
            document.add(addField("popularity", Integer.toString(product.getPopularity())));
            writer.addDocument(document);
        }
        finish();
        log.debug("Products Index creating ended");
    }

    /**
     * Indexes reviews.
     * @param directoryDB Place where index should be.
     * @throws Exception
     */
    public void doReviewsIndex(String directoryDB) throws Exception {
        log.debug("Reviews Index creating started");

        init(directoryDB);
        List<Review> reviews = new ReviewDbController(jdbcTemplate).getAllReviews();
        Document document;
        for (Review review : reviews) {
            document = new Document();
            document.add(addField("id", Long.toString(review.getId())));
            document.add(addField("productId", Long.toString(review.getProductId())));
            String content = review.getContent();
            if (content != null) {
                document.add(addField("content", content));
            }
            String author = review.getContent();
            if (author != null) {
                document.add(addField("author", author));
            }
            document.add(addField("author", review.getAuthor()));
            document.add(addField("date", String.valueOf(review.getTime())));
            String desc = review.getDescription();
            if (desc != null) {
                document.add(addField("description", desc));
            }
            document.add(addField("sourceId", Long.toString(review.getSourceId())));
            String sourceURL = review.getSourceUrl();
            if (sourceURL != null) {
                document.add(addField("sourceUrl", sourceURL));
            }
            document.add(addField("positivity", Double.toString(review.getPositivity())));
            document.add(addField("importance", Double.toString(review.getImportance())));
            document.add(addField("votesYes", Integer.toString(review.getVotesYes())));
            document.add(addField("votesNo", Integer.toString(review.getVotesNo())));
            writer.addDocument(document);
        }
        finish();
        log.debug("Reviews Index creating ended");
    }

    /**
     * Indexes theses.
     * @param directoryDB Place where index should be.
     * @throws Exception
     */
    public void doThesesIndex(String directoryDB) throws Exception {
        log.debug("Thesis Index creating started");

        init(directoryDB);
        List<Thesis> theses = new ThesisDbController(jdbcTemplate).getAllTheses();
        Document document;
        for (Thesis thesis : theses) {
            document = new Document();
            document.add(addField("id", Long.toString(thesis.getId())));
            document.add(addField("reviewId", Long.toString(thesis.getReviewId())));
            document.add(addField("thesisUniqueId", Long.toString(thesis.getThesisUniqueId())));
            String content = thesis.getContent();
            if (content != null) {
                document.add(addField("content", content));
            }
            document.add(addField("frequency", Integer.toString(thesis.getFrequency())));
            document.add(addField("positivity", Double.toString(thesis.getPositivity())));
            document.add(addField("importance", Double.toString(thesis.getImportance())));
            writer.addDocument(document);
        }
        finish();
        log.debug("Thesis Index creating ended");
    }

    /**
     * Indexes unique theses.
     * @param directoryDB Place where index should be.
     * @throws Exception
     */
    public void doThesesUniqueIndex(String directoryDB) throws Exception {
        log.debug("ThesisUnique Index creating started");
        init(directoryDB);
        List<ThesisUnique> thesesUnique = new ThesisUniqueDbController(jdbcTemplate).getAllThesesUnique();
        Document document;
        for (ThesisUnique thesisUnique : thesesUnique) {
            document = new Document();
            document.add(
                    addField("id", Long.toString(thesisUnique.getId())));
            String content = thesisUnique.getContent();
            if (content != null) {
                document.add(addField("content", content));
            }
            document.add(addField("frequency", Integer.toString(thesisUnique.getFrequency())));
            document.add(addField("lastScan", String.valueOf(thesisUnique.getLastScan())));
            document.add(addField("positivity", Double.toString(thesisUnique.getPositivity())));
            document.add(addField("importance", Double.toString(thesisUnique.getImportance())));
            writer.addDocument(document);
        }
        finish();
        log.debug("Thesis Unique Index creating ended");
    }

    /**
     * Indexes categories.
     * @param directoryDB Place where index should be.
     * @throws Exception
     */
    public void doCategoriesIndex(String directoryDB) throws Exception {
        log.debug("Category Index creating started");
        init(directoryDB);
        List<Category> categories = new CategoryDbController(jdbcTemplate).getAllCategories();
        Document document;
        for (Category category : categories) {
            document = new Document();
            document.add(addField("id", Long.toString(category.getId())));
            document.add(addField("parentCategoryId", Long.toString(category.getParentCategoryId())));
            document.add(addField("name", category.getName()));
            String desc = category.getDescription();
            if (desc != null) {
                document.add(addField("description", desc));
            }
        }
        finish();
        log.debug("Category Index creating ended");
    }

    private Field addField(String name, String value){
        return new Field(name, value, Field.Store.YES, Field.Index.ANALYZED);
    }

    /**
     * Launches needful indexing methods.
     */
    @Override
    public void run() {
        try {
            log.info("Index creating started");
            doProductsIndex("data/index/products");
            doCategoriesIndex("data/index/categories");
            doReviewsIndex("data/index/reviews");
            doThesesIndex("data/index/thesis");
            doThesesUniqueIndex("data/index/theseUnique");
            log.info("Index creating ended started");
        } catch (Exception e) {
            log.error(e);
        }
    }
}
