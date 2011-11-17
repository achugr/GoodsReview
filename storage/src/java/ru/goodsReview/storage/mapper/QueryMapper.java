/**
 * Date: 14.11.2011
 * Time: 5:47:09
 * Author:
 *   Sergey Serebryakov
 *   sergey.serebryakoff@gmail.com
 */

package ru.goodsReview.storage.mapper;

import ru.goodsReview.core.model.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

/**
 * User: Sergey Serebryakov
 * Date: 14.11.2011
 * Time: 5:47:09
 */
public class QueryMapper implements ParameterizedRowMapper<Query> {
    public Query mapRow(ResultSet resultSet, int i) {
        try {
            return new Query(resultSet.getLong("id"),
                    resultSet.getLong("query_unique_id"),
                    resultSet.getString("text"),
                    resultSet.getLong("date"));
        } catch (SQLException e) {
            // Something is wrong with the base, i.e. one of column labels isn't presented.
            return new Query(-1, -1, "NOTEXT", System.currentTimeMillis());
        }
    }
}
