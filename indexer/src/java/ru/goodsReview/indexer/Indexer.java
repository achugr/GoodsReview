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
            document.add(new Field("id", Long.toString(product.getId()), Field.Store.YES, Field.Index.ANALYZED));
            document.add(new Field("category_id", Long.toString(product.getCategoryId()), Field.Store.YES,
                                   Field.Index.ANALYZED));
            document.add(new Field("name", product.getName(), Field.Store.YES, Field.Index.ANALYZED));
            String desc = product.getDescription();
            if (desc != null) {
                document.add(new Field("description", desc, Field.Store.YES, Field.Index.ANALYZED));
            }
            document.add(new Field("popularity", Integer.toString(product.getPopularity()), Field.Store.YES,
                                   Field.Index.ANALYZED));
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
            document.add(new Field("id", Long.toString(review.getId()), Field.Store.YES, Field.Index.ANALYZED));
            document.add(new Field("productId", Long.toString(review.getProductId()), Field.Store.YES,
                                   Field.Index.ANALYZED));
            String content = review.getContent();
            if (content != null) {
                document.add(new Field("content", content, Field.Store.YES, Field.Index.ANALYZED));
            }
            String author = review.getContent();
            if (author != null) {
                document.add(new Field("author", author, Field.Store.YES, Field.Index.ANALYZED));
            }
            document.add(new Field("author", review.getAuthor(), Field.Store.YES, Field.Index.ANALYZED));
            document.add(new Field("date", review.getDate().toString(), Field.Store.YES, Field.Index.ANALYZED));
            String desc = review.getDescription();
            if (desc != null) {
                document.add(new Field("description", desc, Field.Store.YES, Field.Index.NO));
            }
            document.add(new Field("sourceId", Long.toString(review.getSourceId()), Field.Store.YES,
                                   Field.Index.ANALYZED));
            String sourceURL = review.getSourceUrl();
            if (sourceURL != null) {
                document.add(new Field("sourceUrl", sourceURL, Field.Store.YES, Field.Index.NO));
            }
            document.add(new Field("positivity", Double.toString(review.getPositivity()), Field.Store.YES,
                                   Field.Index.ANALYZED));
            document.add(new Field("importance", Double.toString(review.getImportance()), Field.Store.YES,
                                   Field.Index.ANALYZED));
            document.add(new Field("votesYes", Integer.toString(review.getVotesYes()), Field.Store.YES,
                                   Field.Index.ANALYZED));
            document.add(new Field("votesNo", Integer.toString(review.getVotesNo()), Field.Store.YES,
                                   Field.Index.ANALYZED));
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
            document.add(new Field("id", Long.toString(thesis.getId()), Field.Store.YES, Field.Index.ANALYZED));
            document.add(new Field("reviewId", Long.toString(thesis.getReviewId()), Field.Store.YES,
                                   Field.Index.ANALYZED));
            document.add(new Field("thesisUniqueId", Long.toString(thesis.getThesisUniqueId()), Field.Store.YES,
                                   Field.Index.ANALYZED));
            String content = thesis.getContent();
            if (content != null) {
                document.add(new Field("content", content, Field.Store.YES, Field.Index.ANALYZED));
            }
            document.add(new Field("frequency", Integer.toString(thesis.getFrequency()), Field.Store.YES,
                                   Field.Index.ANALYZED));
            document.add(new Field("positivity", Double.toString(thesis.getPositivity()), Field.Store.YES,
                                   Field.Index.ANALYZED));
            document.add(new Field("importance", Double.toString(thesis.getImportance()), Field.Store.YES,
                                   Field.Index.ANALYZED));
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
                    new Field("id", Long.toString(thesisUnique.getId()), Field.Store.YES, Field.Index.ANALYZED));
            String content = thesisUnique.getContent();
            if (content != null) {
                document.add(new Field("content", content, Field.Store.YES, Field.Index.ANALYZED));
            }
            document.add(new Field("frequency", Integer.toString(thesisUnique.getFrequency()), Field.Store.YES,
                                   Field.Index.ANALYZED));
            document.add(new Field("lastScan", thesisUnique.getLastScan().toString(), Field.Store.YES,
                                   Field.Index.ANALYZED));
            document.add(new Field("positivity", Double.toString(thesisUnique.getPositivity()), Field.Store.YES,
                                   Field.Index.ANALYZED));
            document.add(new Field("importance", Double.toString(thesisUnique.getImportance()), Field.Store.YES,
                                   Field.Index.ANALYZED));
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
            document.add(new Field("id", Long.toString(category.getId()), Field.Store.YES, Field.Index.ANALYZED));
            document.add(new Field("parentCategoryId", Long.toString(category.getParentCategoryId()), Field.Store.YES,
                                   Field.Index.ANALYZED));
            document.add(new Field("name", category.getName(), Field.Store.YES, Field.Index.ANALYZED));
            String desc = category.getDescription();
            if (desc != null) {
                document.add(new Field("description", desc, Field.Store.YES, Field.Index.ANALYZED));
            }
        }
        finish();
        log.debug("Category Index creating ended");
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
