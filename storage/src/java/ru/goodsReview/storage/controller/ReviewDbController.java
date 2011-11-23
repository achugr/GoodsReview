package ru.goodsReview.storage.controller;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.db.controller.ReviewController;
import ru.goodsReview.core.db.exception.StorageException;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.storage.mapper.ReviewMapper;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/*
 *  Date: 19.10.11
 *   Time: 1:20
 *   Author:
 *      Artemij Chugreev
 *      artemij.chugreev@gmail.com
 */

public class ReviewDbController implements ReviewController {
    private SimpleJdbcTemplate simpleJdbcTemplate;
    private ReviewMapper reviewMapper;
    private static final Logger log = Logger.getLogger(ReviewDbController.class);

    public ReviewDbController(SimpleJdbcTemplate simpleJdbcTemplate) {
        this.simpleJdbcTemplate = simpleJdbcTemplate;
        this.reviewMapper = new ReviewMapper();
    }

    public long addReview(Review review) throws StorageException {
        try {
            simpleJdbcTemplate.getJdbcOperations().update(
                    "INSERT INTO review " + "(product_id, content, author, date, description, source_id, source_url, positivity, importance, votes_yes, votes_no) " + "VALUES(?,?,?,?,?,?,?,?,?,?,?)",
                    new Object[]{review.getProductId(), review.getContent(), review.getAuthor(), review.getTime(), review.getDescription(), review.getSourceId(), review.getSourceUrl(), review.getPositivity(), review.getImportance(), review.getVotesYes(), review.getVotesNo()},
                    new int[]{Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.DOUBLE, Types.DOUBLE, Types.INTEGER, Types.INTEGER});
            long lastId = simpleJdbcTemplate.getJdbcOperations().queryForLong("SELECT LAST_INSERT_ID()");
            return lastId;
        } catch (DataAccessException e) {
            log.error("Error while inserting review (probably not enough permissions): " + review, e);
            throw new StorageException();
        }
    }

    public List<Long> addReviewList(List<Review> reviewList) throws StorageException {
        List<Long> ids = new ArrayList<Long>();
        for (Review review : reviewList) {
            ids.add(addReview(review));
        }
        return ids;
    }

    public Review getReviewById(long review_id) {
        List<Review> reviews = simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM review WHERE id = ?",
                                                                            new Object[]{review_id},
                                                                            new int[]{Types.INTEGER}, reviewMapper);
        if (reviews.size() > 0) {
            return reviews.get(0);
        }
        return null;
    }

    public List<Review> getReviewsByProductId(long product_id) {
        List<Review> reviews = simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM review WHERE product_id = ?",
                                                                            new Object[]{product_id},
                                                                            new int[]{Types.INTEGER}, reviewMapper);
        return reviews;
    }

    public List<Review> getPopularReviewsByProductId(long product_id, int n) {
        List<Review> reviews = simpleJdbcTemplate.getJdbcOperations().query(
                "SELECT * FROM review WHERE product_id = ? ORDER BY importance DESC LIMIT ?",
                new Object[]{product_id, n}, new int[]{Types.INTEGER, Types.INTEGER}, reviewMapper);
        return reviews;

    }

    public List<Review> getAllReviews() {
        List<Review> reviews = simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM review", reviewMapper);
        return reviews;
    }

    public void updateReview(Review review) throws StorageException {
        try {
            simpleJdbcTemplate.getJdbcOperations().update("UPDATE review SET product_id = ?, content = ?, author = ?, date = ?, description = ?, source_id = ?, source_url = ?, positivity = ?, importance = ?, votes_yes = ?, votes_no = ? WHERE id = ?",
                    new Object[]{review.getProductId(), review.getContent(), review.getAuthor(), review.getTime(), review.getDescription(), review.getSourceId(), review.getSourceUrl(), review.getPositivity(), review.getImportance(), review.getVotesYes(), review.getVotesNo(), review.getId()},
                    new int[]{Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.DOUBLE, Types.DOUBLE, Types.INTEGER, Types.INTEGER, Types.INTEGER});
        } catch (DataAccessException e) {
            log.error("Error while updating review (probably not enough permissions): " + review, e);
            throw new StorageException();
        }
    }

    public void updateReviews(List<Review> reviews) throws StorageException {
        for (Review review : reviews) {
            updateReview(review);
        }
    }
}
