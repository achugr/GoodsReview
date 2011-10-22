package ru.goodsReview.storage.controller;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.storage.mappers.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Artemij
 * Date: 17.10.11
 * Time: 19:04
 * To change this template use File | Settings | File Templates.
 */
public class ProductDbController {
    private SimpleJdbcTemplate simpleJdbcTemplate;
    private ProductMapper productMapper;

    public ProductDbController(SimpleJdbcTemplate simpleJdbcTemplate){
        this.simpleJdbcTemplate = simpleJdbcTemplate;
        this.productMapper = new ProductMapper();

    }

    public void setProduct(Product product){
        try{
            simpleJdbcTemplate.update("INSERT INTO Product (id, category_id, name, description, popularity) VALUES(?,?,?,?, ?);", product.getId(),
               product.getCategoryId(), product.getName(), product.getDescription(), product.getPopularity());
        } catch (Exception e) {
            //simpleJdbcTemplate.update("INSERT INTO Product (id, name) VALUES (?,?)", product.getid(), product.getname());
            e.printStackTrace();
        }
    }
    public Product getProductById(long product_id){
        List<Product> products = simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM product WHERE id = " + Long.toString(product_id), productMapper);
        return products.get(0);
    }

    public Product getProductByName(String product_name){
        List<Product> products = simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM product WHERE name = ?", new Object[]{product_name}, productMapper);
        return products.get(0);
    }

    public List<Product> getProductListByCategory(long category_id){
        List<Product> products = simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM product WHERE category_id = " + Long.toString(category_id), productMapper);
        return products;
    }
}