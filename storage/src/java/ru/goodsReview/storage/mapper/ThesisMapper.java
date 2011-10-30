/**
 * Date: 30.10.2011
 * Time: 16:56:05
 * Author: 
 *   Sergey Serebryakov 
 *   sergey.serebryakoff@gmail.com
 */

package ru.goodsReview.storage.mapper;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import ru.goodsReview.core.model.Thesis;

/**
 * User: Sergey Serebryakov
 * Date: 30.10.2011
 * Time: 16:56:05
 */
public class ThesisMapper implements ParameterizedRowMapper<Thesis> {
    public Thesis mapRow(ResultSet resultSet, int i) {
        try {
            return new Thesis(
                    resultSet.getLong("id"),
                    resultSet.getLong("review_id"),
                    resultSet.getString("content"),
                    resultSet.getDouble("positivity"),
                    resultSet.getDouble("importance"),
                    resultSet.getInt("votes_yes"),
                    resultSet.getInt("votes_no"));
        } catch (SQLException e) {
            // Something is wrong with the base, i.e. one of column labels isn't presented.
            return new Thesis(-1, -1, "NOCONTENT", 0.0, 0.0, 0, 0);
        }
    }
}
