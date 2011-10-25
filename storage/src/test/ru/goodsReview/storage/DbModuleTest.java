/*
    Date: 26.10.11
    Time: 00:09
    Author: 
        Artemij Chugreev 
        artemij.chugreev@gmail.com
*/

package ru.goodsReview.storage;

import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.storage.controller.ProductDbController;

import javax.activation.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbModuleTest {
    public static void main(String[] args) throws SQLException {
            final FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("storage/src/scripts/beans.xml");
            DataSource dataSource = null;
            dataSource = (DataSource) context.getBean("dataSource");
            ProductDbController baseController = new  ProductDbController(new SimpleJdbcTemplate(dataSource));
            List<Product> productsList = new ArrayList<Product>();
            productsList.add(new Product(1, "iphone_4", "mobile_phone_by_apple", 10));
            productsList.add(new Product(1, "htc_desire", "mobile phone by HTC", 6));
            productsList.add(new Product(1, "nokia_3310", "cool mobile phone by Nokia", 100));
            baseController.setProductList(productsList);
        }
}
