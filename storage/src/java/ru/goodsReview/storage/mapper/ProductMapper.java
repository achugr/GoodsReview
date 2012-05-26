package ru.goodsreview.storage.mapper;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import ru.goodsreview.core.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements ParameterizedRowMapper<Product> {
    public Product mapRow(ResultSet resultSet, int i) {
        try {
            return new Product(resultSet.getLong("id"), resultSet.getLong("category_id"), resultSet.getString("name"),
                               resultSet.getString("description"), resultSet.getInt("popularity"));
        } catch (SQLException e) {
            // Something is wrong with the base, i.e. one of column labels isn't presented.
            return new Product(-1, -1, "NONAME", "NODESCRIPTION", -1);
        }
    }
}
