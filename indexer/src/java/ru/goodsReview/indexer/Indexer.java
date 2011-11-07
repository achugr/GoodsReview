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
import ru.goodsReview.core.model.Product;
import ru.goodsReview.storage.controller.ProductDbController;

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
		this.writer = new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_34, new RussianAnalyzer(Version.LUCENE_34)));
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
			document.add(new Field("category_id", Long.toString(product.getCategoryId()), Field.Store.YES, Field.Index.NOT_ANALYZED));
			document.add(new Field("name", product.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
			document.add(new Field("description", product.getDescription(), Field.Store.YES, Field.Index.ANALYZED));
			document.add(new Field("popularity", Integer.toString(product.getPopularity()), Field.Store.YES, Field.Index.ANALYZED));
			writer.addDocument(document);
		}
		finish();
	}


	public void doReviewsIndex(String directoryDB) throws Exception {
/*        init(directoryDB);
        List<Review> reviews = new ReviewDbController().getAllReviews();
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
        finish();*/
		//Something will be written here. I promise you! But only when I'll can use this f-n ReviewDbController ;(
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