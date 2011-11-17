package ru.goodsReview.storage.mapper;
/*
 *  Date: 07.11.11
 *   Time: 00:29
 *   Author:
 *      Artemij Chugreev
 *      artemij.chugreev@gmail.com
 */

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import ru.goodsReview.core.model.ThesisUnique;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ThesisUniqueMapper implements ParameterizedRowMapper<ThesisUnique> {
    public ThesisUnique mapRow(ResultSet resultSet, int i) {
        try {
            return new ThesisUnique(resultSet.getLong("id"), resultSet.getString("content"),
                                    resultSet.getInt("frequency"), resultSet.getLong("last_scan"),
                                    resultSet.getDouble("positivity"), resultSet.getDouble("importance"));
        } catch (SQLException e) {
            // Something is wrong with the base, i.e. one of column labels isn't presented.
            return new ThesisUnique(-1, "NOCONTENT", 0, System.currentTimeMillis(), 0.0, 0.0);
        }
    }
}
