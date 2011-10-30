package ru.goodsReview.storage.mapper;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import ru.goodsReview.core.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements ParameterizedRowMapper<Product>{
    public Product mapRow(ResultSet resultSet, int i)throws SQLException, NumberFormatException {
         try{
            return new Product(Long.parseLong(resultSet.getString("id")), Long.parseLong(resultSet.getString("category_id")), resultSet.getString("name"),resultSet.getString("description"),Integer.parseInt(resultSet.getString("popularity")));
        } catch (Exception e) {
            return new Product(Long.parseLong(resultSet.getString("id")), -1, resultSet.getString("name"),"",-1);
        }
    }

}
