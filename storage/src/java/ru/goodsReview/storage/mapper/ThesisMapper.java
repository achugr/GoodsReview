/**
 * Date: 30.10.2011
 * Time: 16:56:05
 * Author:
 *   Sergey Serebryakov
 *   sergey.serebryakoff@gmail.com
 */

package ru.goodsReview.storage.mapper;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import ru.goodsReview.core.model.Thesis;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: Sergey Serebryakov
 * Date: 30.10.2011
 * Time: 16:56:05
 */
public class ThesisMapper implements ParameterizedRowMapper<Thesis> {
    public Thesis mapRow(ResultSet resultSet, int i) {
        try {
            return new Thesis(resultSet.getLong("id"), resultSet.getLong("review_id"),
                              resultSet.getLong("thesis_unique_id"), resultSet.getString("content"),
                              resultSet.getInt("frequency"), resultSet.getDouble("positivity"),
                              resultSet.getDouble("importance"),resultSet.getDouble("tfidf"));
        } catch (SQLException e) {
            // Something is wrong with the base, i.e. one of column labels isn't presented.
            return new Thesis(-1, -1, -1, "NOCONTENT", 0, 0.0, 0.0, 0.0);
        }
    }
}
