package ru.goodsReview.storage.controller;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.dao.DataAccessException;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.storage.mappers.*;

import java.sql.Types;
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

    public ProductDbController(SimpleJdbcTemplate simpleJdbcTemplate) {
        this.simpleJdbcTemplate = simpleJdbcTemplate;
        this.productMapper = new ProductMapper();
    }

    public void addProduct(Product product) {
        try {
            simpleJdbcTemplate.getJdbcOperations().update("INSERT INTO product (category_id, name, description, popularity) VALUES(?,?,?,?)",
                    new Object[]{product.getCategoryId(), product.getName(), product.getDescription(), product.getPopularity()},
                    new int[]{Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.INTEGER});
        } catch (DataAccessException e) {
            // We don't have permissions to update the table.
            // TODO(serebryakov): Log the error.
            System.err.println("ERROR: addProduct failed");
        }
    }

    public void addProductList(List<Product> productList) {
        for (Product product : productList) {
            addProduct(product);
        }
    }

    public List<Product> getAllProducts() {
        List<Product> products =
                simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM product", productMapper);
        return products;
    }

    public Product getProductById(long product_id) {
        List<Product> products =
                simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM product WHERE id = ?",
                        new Object[]{product_id},
                        new int[]{java.sql.Types.INTEGER},
                        productMapper);
        return products.get(0);
    }

    public Product getProductByName(String product_name) {
        List<Product> products =
                simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM product WHERE name = ?",
                        new Object[]{product_name},
                        new int[]{Types.VARCHAR},
                        productMapper);
        return products.get(0);
    }

    public List<Product> getProductsByCategory(long category_id) {
        List<Product> products =
                simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM product WHERE category_id = ?",
                        new Object[]{category_id},
                        new int[]{Types.INTEGER},
                        productMapper);
        return products;
    }
}