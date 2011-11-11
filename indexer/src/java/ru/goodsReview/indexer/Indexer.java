package ru.goodsReview.indexer;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
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

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private void init(String directoryDB) throws Exception {
        SimpleFSDirectory directory = new SimpleFSDirectory(new File(directoryDB));
        this.writer = new IndexWriter(directory,
                                      new IndexWriterConfig(Version.LUCENE_34, new RussianAnalyzer(Version.LUCENE_34)));
        ProductDbController DBController = new ProductDbController(jdbcTemplate);
    }

    private void finish() throws IOException {
        writer.optimize();
        writer.close();
    }

    public void doProductsIndex(String directoryDB) throws Exception {
        init(directoryDB);
        List<Product> products = new ProductDbController(jdbcTemplate).getAllProducts();
        Document document;
        for (Product product : products) {
            document = new Document();
            document.add(new Field("id", Long.toString(product.getId()), Field.Store.YES, Field.Index.NOT_ANALYZED));
            document.add(new Field("category_id", Long.toString(product.getCategoryId()), Field.Store.YES,
                                   Field.Index.NOT_ANALYZED));
            document.add(new Field("name", product.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
            String desc;
            if ((desc = product.getDescription()) != null) {
                document.add(new Field("description", desc, Field.Store.YES, Field.Index.ANALYZED));
            } else {
                document.add(new Field("description", "", Field.Store.YES, Field.Index.ANALYZED));
            }
            document.add(new Field("popularity", Integer.toString(product.getPopularity()), Field.Store.YES,
                                   Field.Index.ANALYZED));
            writer.addDocument(document);
        }
        finish();
    }

    public void doReviewsIndex(String directoryDB) throws Exception {
        init(directoryDB);
        List<Review> reviews = new ReviewDbController(jdbcTemplate).getAllReviews();
        Document document;
        for (Review review : reviews) {
            document = new Document();
            document.add(new Field("id",Long.toString(review.getId()),Field.Store.YES,Field.Index.NOT_ANALYZED));
            document.add(new Field("productId",Long.toString(review.getProductId()),Field.Store.YES,Field.Index.NOT_ANALYZED));
            String content;
            if ((content = review.getContent()) != null) {
                document.add(new Field("content",content,Field.Store.NO,Field.Index.ANALYZED));
            } else {
                document.add(new Field("content",null,Field.Store.NO,Field.Index.ANALYZED));
            }
            String author;
            if ((author = review.getContent()) != null) {
                document.add(new Field("author",author,Field.Store.NO,Field.Index.ANALYZED));
            } else {
                document.add(new Field("author",null,Field.Store.NO,Field.Index.ANALYZED));
            }
            document.add(new Field("author",review.getAuthor(),Field.Store.NO,Field.Index.ANALYZED));
            document.add(new Field("date",review.getDate().toString(),Field.Store.NO,Field.Index.ANALYZED));
            String desc;
            if ((desc = review.getDescription()) != null) {
                document.add(new Field("description", desc, Field.Store.NO,Field.Index.NO));
            } else {
                document.add(new Field("description", "", Field.Store.NO,Field.Index.NO));
            }
            document.add(new Field("sourceId",Long.toString(review.getSourceId()),Field.Store.NO,Field.Index.NO));
            String sourceURL;
            if ((sourceURL = review.getSourceUrl()) != null) {
                document.add(new Field("sourceUrl", sourceURL, Field.Store.NO, Field.Index.NO));
            } else {
                document.add(new Field("sourceUrl", "", Field.Store.NO, Field.Index.NO));
            }
            document.add(new Field("positivity",Double.toString(review.getPositivity()),Field.Store.NO,Field.Index.NOT_ANALYZED));
            document.add(new Field("importance",Double.toString(review.getImportance()),Field.Store.NO,Field.Index.NOT_ANALYZED));
            document.add(new Field("votesYes",Integer.toString(review.getVotesYes()),Field.Store.NO,Field.Index.NOT_ANALYZED));
            document.add(new Field("votesNo",Integer.toString(review.getVotesNo()),Field.Store.NO,Field.Index.NOT_ANALYZED));
            writer.addDocument(document);
        }
        finish();
    }

    public void doThesesIndex(String directoryDB) throws Exception {
        init(directoryDB);
        List<Thesis> theses = new ThesisDbController(jdbcTemplate).getAllTheses();
        Document document;
        for (Thesis thesis : theses) {
            document = new Document();
            document.add(new Field("id",Long.toString(thesis.getId()),Field.Store.YES,Field.Index.NOT_ANALYZED));
            document.add(new Field("reviewId",Long.toString(thesis.getReviewId()),Field.Store.YES,Field.Index.NOT_ANALYZED));
            document.add(new Field("thesisUniqueId",Long.toString(thesis.getThesisUniqueId()),Field.Store.NO, Field.Index.ANALYZED));
            String content;
            if ((content = thesis.getContent()) != null) {
                document.add(new Field("content",content,Field.Store.NO, Field.Index.ANALYZED));
            } else {
                document.add(new Field("content",null,Field.Store.NO, Field.Index.ANALYZED));
            }
            document.add(new Field("frequency",Integer.toString(thesis.getFrequency()),Field.Store.NO,Field.Index.NOT_ANALYZED));
            document.add(new Field("positivity",Double.toString(thesis.getPositivity()),Field.Store.NO,Field.Index.NOT_ANALYZED));
            document.add(new Field("importance",Double.toString(thesis.getImportance()),Field.Store.NO,Field.Index.NOT_ANALYZED));
            writer.addDocument(document);
        }
        finish();
    }

    public void doThesesUniqueIndex(String directoryDB) throws Exception {
        init(directoryDB);
        List<ThesisUnique> thesesUnique = new ThesisUniqueDbController(jdbcTemplate).getAllThesesUnique();
        Document document;
        for (ThesisUnique thesisUnique : thesesUnique) {
            document = new Document();
            document.add(new Field("id",Long.toString(thesisUnique.getId()),Field.Store.YES,Field.Index.NOT_ANALYZED));
            String content;
            if ((content = thesisUnique.getContent()) != null) {
                document.add(new Field("content",content,Field.Store.NO, Field.Index.ANALYZED));
            } else {
                document.add(new Field("content",null,Field.Store.NO, Field.Index.ANALYZED));
            }
            document.add(new Field("frequency",Integer.toString(thesisUnique.getFrequency()),Field.Store.NO,Field.Index.NOT_ANALYZED));
            document.add(new Field("lastScan",thesisUnique.getLastScan().toString(),Field.Store.NO,Field.Index.NOT_ANALYZED));
            document.add(new Field("positivity",Double.toString(thesisUnique.getPositivity()),Field.Store.NO,Field.Index.NOT_ANALYZED));
            document.add(new Field("importance",Double.toString(thesisUnique.getImportance()),Field.Store.NO,Field.Index.NOT_ANALYZED));
            writer.addDocument(document);
        }
        finish();
    }

    public void doCategoriesIndex(String directoryDB) throws Exception {
        init(directoryDB);
        List<Category> categories = new CategoryDbController(jdbcTemplate).getAllCategories();
        Document document;
        for (Category category : categories) {
            document = new Document();
            document.add(new Field("id",Long.toString(category.getId()),Field.Store.NO,Field.Index.NOT_ANALYZED));
            document.add(new Field("parentCategoryId",Long.toString(category.getParentCategoryId()),Field.Store.NO,Field.Index.NOT_ANALYZED));
            document.add(new Field("name",category.getName(),Field.Store.YES, Field.Index.ANALYZED));
            String desc;
            if ((desc = category.getDescription()) != null) {
                document.add(new Field("description", desc, Field.Store.NO, Field.Index.ANALYZED));
            } else {
                document.add(new Field("description", "", Field.Store.NO, Field.Index.ANALYZED));
            }
        }
    }

    public void run() {
        try {
            doProductsIndex("database");
            log.info("Index created");
        } catch (Exception e) {
            log.error(e);
        }
    }
}
