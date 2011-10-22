package ru.goodsReview.frontend.service;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.storage.controller.ProductDbController;

/**
 * Created by IntelliJ IDEA.
 * User: Artemij
 * Date: 18.10.11
 * Time: 23:05
 * To change this template use File | Settings | File Templates.
 */
public class GetProductInfo {

    public Product getProductInfo(SimpleJdbcTemplate simpleJdbcTemplate, String productName){
        ProductDbController productDbController = new ProductDbController(simpleJdbcTemplate);
        Product product = productDbController.getProductByName(productName);
        return product;
    }
}
