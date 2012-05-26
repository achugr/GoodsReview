/**
 * Date: 14.11.2011
 * Time: 5:51:19
 * Author:
 *   Sergey Serebryakov
 *   sergey.serebryakoff@gmail.com
 */

package ru.goodsreview.storage.mapper;

import ru.goodsreview.core.model.QueryUnique;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

/**
 * User: Sergey Serebryakov
 * Date: 14.11.2011
 * Time: 5:51:19
 */
public class QueryUniqueMapper implements ParameterizedRowMapper<QueryUnique> {
        public QueryUnique mapRow(ResultSet resultSet, int i) {
        try {
            return new QueryUnique(resultSet.getLong("id"),
                    resultSet.getString("text"),
                    resultSet.getLong("last_scan"),
                    resultSet.getInt("frequency"));
        } catch (SQLException e) {
            // Something is wrong with the base, i.e. one of column labels isn't presented.
            return new QueryUnique(-1, "NOTEXT", System.currentTimeMillis(), 0);
        }
    }
}
