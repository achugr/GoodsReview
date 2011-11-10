/**
 * Date: 30.10.2011
 * Time: 23:36:59
 * Author:
 *   Sergey Serebryakov
 *   sergey.serebryakoff@gmail.com
 */

package ru.goodsReview.storage.mapper;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import ru.goodsReview.core.model.Source;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: Sergey Serebryakov
 * Date: 30.10.2011
 * Time: 23:36:59
 */
public class SourceMapper implements ParameterizedRowMapper<Source> {
    public Source mapRow(ResultSet resultSet, int i) {
        try {
            return new Source(resultSet.getLong("id"), resultSet.getString("name"),
                              resultSet.getString("main_page_url"));
        } catch (SQLException e) {
            // Something is wrong with the base, i.e. one of column labels isn't presented.
            return new Source(-1, "NONAME", "NOURL");
        }
    }
}
