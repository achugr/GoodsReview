package ru.goodsReview.storage.mappers;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import ru.goodsReview.core.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Artemij
 * Date: 18.10.11
 * Time: 23:31
 * To change this template use File | Settings | File Templates.
 */
public class ProductMapper implements ParameterizedRowMapper<Product> {
    public Product mapRow(ResultSet resultSet, int i) {
        try {
            return new Product(
                    resultSet.getLong("id"),
                    resultSet.getLong("category_id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getInt("popularity"));
        } catch (SQLException e) {
            // Something is wrong with the base, i.e. one of column labels isn't presented.
            return new Product(-1, -1, "NONAME", "", 0);
        }
    }
}
