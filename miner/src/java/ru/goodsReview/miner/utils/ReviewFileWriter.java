/**
 * Date: 20.11.2011
 * Time: 23:28:27
 * Author: 
 *   Sergey Serebryakov 
 *   sergey.serebryakoff@gmail.com
 */

package ru.goodsReview.miner.utils;

import ru.goodsReview.core.db.controller.ProductController;
import ru.goodsReview.core.db.controller.ReviewController;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.storage.controller.ProductDbController;
import ru.goodsReview.storage.controller.ReviewDbController;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.util.List;
import java.io.*;

/**
 * User: Sergey Serebryakov
 * Date: 20.11.2011
 * Time: 23:28:27
 */
// TODO: Think out a good name for this class.
public class ReviewFileWriter {
    private static final String FILENAME = "data/miner/comments.txt";
    private static final String BEFORE_PRODUCT_DELIMITER = "******************************";
    private static final String AFTER_PRODUCT_DELIMITER = "******************************";
    private static final String BEFORE_REVIEW_DELIMITER = "";
    private static final String AFTER_REVIEW_DELIMITER = "------------------------------";


    private static final Logger log = Logger.getLogger(ReviewFileWriter.class);

    public static void writeReviewsToFile(DataSource dataSource) throws IOException {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(FILENAME)));

            ProductController productController = new ProductDbController(new SimpleJdbcTemplate(dataSource));
            ReviewController reviewController = new ReviewDbController(new SimpleJdbcTemplate(dataSource));
            List<Product> products = productController.getAllProducts();

            for (Product product : products) {
                out.println(BEFORE_PRODUCT_DELIMITER);
                out.println(product.getName());
                out.println(AFTER_PRODUCT_DELIMITER);

                List<Review> reviews = reviewController.getReviewsByProductId(product.getId());
                for (Review review : reviews) {
                    out.println(BEFORE_REVIEW_DELIMITER);
                    out.println(review.getContent());
                    out.println(AFTER_REVIEW_DELIMITER);
                }
            }

            out.close();
        } catch (IOException e) {
            log.error("I/O error when trying to write to file: " + FILENAME);
            throw e;
        }
    }
}
