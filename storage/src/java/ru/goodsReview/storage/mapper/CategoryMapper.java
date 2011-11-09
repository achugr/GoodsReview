package ru.goodsReview.storage.mapper;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import ru.goodsReview.core.model.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Date: 26.10.2011
 * Time: 1:43:36
 * Author:
 *   Sergey Serebryakov
 *   sergey.serebryakoff@gmail.com
 */
public class CategoryMapper implements ParameterizedRowMapper<Category> {
    public Category mapRow(ResultSet resultSet, int i) {
        try {
            return new Category(resultSet.getLong("id"), resultSet.getString("name"),
                                resultSet.getString("description"), resultSet.getLong("parent_category_id"));
        } catch (SQLException e) {
            // Something is wrong with the base, i.e. one of column labels isn't presented.
            return new Category(-1, "NONAME", "", -1);
        }
    }
}
