package ru.goodsReview.storage.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.storage.mapper.ReviewMapper;

import java.sql.Types;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Artemij
 * Date: 19.10.11
 * Time: 1:20
 * To change this template use File | Settings | File Templates.
 */
public class ReviewDbController {
    private SimpleJdbcTemplate simpleJdbcTemplate;
    private ReviewMapper reviewMapper;

    public ReviewDbController(SimpleJdbcTemplate simpleJdbcTemplate) {
        this.simpleJdbcTemplate = simpleJdbcTemplate;
        this.reviewMapper = new ReviewMapper();
    }

    public void addReview(Review review) {
        try {
            simpleJdbcTemplate.getJdbcOperations().update(
                    "INSERT INTO review " +
                    "(product_id, content, author, date, description, source_id, source_url, positivity, importance, votes_yes, votes_no) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?)",
                    new Object[]{
                            review.getProductId(),
                            review.getContent(),
                            review.getAuthor(),
                            review.getDate(),
                            review.getDescription(),
                            review.getSourceId(),
                            review.getSourceUrl(),
                            review.getPositivity(),
                            review.getImportance(),
                            review.getVotesYes(),
                            review.getVotesNo()},
                    new int[]{
                            Types.INTEGER,
                            Types.VARCHAR,
                            Types.VARCHAR,
                            Types.TIMESTAMP,
                            Types.VARCHAR,
                            Types.INTEGER,
                            Types.VARCHAR,
                            Types.DOUBLE,
                            Types.DOUBLE,
                            Types.INTEGER,
                            Types.INTEGER});
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    public Review getReviewById(long review_id) {
        List<Review> reviews = simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM review WHERE id = ?",
                new Object[]{review_id},
                new int[]{Types.INTEGER},
                reviewMapper); 
        return reviews.get(0);
    }

    // TODO(serebryakov): Uncomment this when list of reviews will be implemented properly.
    /*public ListOfReviews getListOfReviews(int productId) {
        ListOfReviews listOfReviews = new ListOfReviews();
        listOfReviews.setReviewsList()= simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM review WHERE product_id = ", new Object[productId], reviewMapper);
    }*/
}