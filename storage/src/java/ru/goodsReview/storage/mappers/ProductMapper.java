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
public class ProductMapper implements ParameterizedRowMapper<Product>{
    public Product mapRow(ResultSet resultSet, int i)throws SQLException, NumberFormatException {
         try{
            return new Product(Long.parseLong(resultSet.getString("id")), Long.parseLong(resultSet.getString("category_id")), resultSet.getString("name"),resultSet.getString("description"),Integer.parseInt(resultSet.getString("popularity")));
        } catch (Exception e) {
            return new Product(Long.parseLong(resultSet.getString("id")), -1, resultSet.getString("name"),"",-1);
        }
    }

}
