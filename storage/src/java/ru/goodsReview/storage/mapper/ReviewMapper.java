package ru.goodsReview.storage.mapper;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import ru.goodsReview.core.model.Review;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ReviewMapper implements ParameterizedRowMapper<Review> {
    public Review mapRow(ResultSet resultSet, int i) {
        try {
            return new Review(resultSet.getLong("id"), resultSet.getLong("product_id"), resultSet.getString("content"), resultSet.getString("author"), resultSet.getTimestamp("date"), resultSet.getString("description"), resultSet.getLong("source_id"), resultSet.getString("source_url"), resultSet.getDouble("positivity"), resultSet.getDouble("importance"), resultSet.getInt("votes_yes"), resultSet.getInt("votes_no"));
        } catch (SQLException e) {
            // Something is wrong with the base, i.e. one of column labels isn't presented.
            return new Review(-1, -1, "NOCONTENT", "NOAUTHOR", new Date(), "", -1, "", 0.0, 0.0, 0, 0);
        }
    }
}
