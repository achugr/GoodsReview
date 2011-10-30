package ru.goodsReview.storage;

import org.apache.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.model.Category;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.storage.controller.CategoryDbController;
import ru.goodsReview.storage.controller.ProductDbController;

import javax.sql.DataSource;
import java.sql.SQLException;

/*
    Date: 26.10.11
    Time: 00:52
    Author:
        Artemij Chugreev
        artemij.chugreev@gmail.com
*/
public class DbModuleTest {
	private static final Logger log = Logger.getLogger(DbModuleTest.class);
    public static void main(String[] args) throws SQLException {
        final FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("storage/src/scripts/beans.xml");
        DataSource dataSource = (DataSource) context.getBean("dataSource");

        log.error("PRIVET");
        log.info("PRIVET");
        CategoryDbController categoryController = new CategoryDbController(new SimpleJdbcTemplate(dataSource));
        Category category0 = new Category(1, "Mobile phones", "PHONES", 1);
        categoryController.addCategory(category0);

        ProductDbController productController = new ProductDbController(new SimpleJdbcTemplate(dataSource));
        Product product0 = new Product(2, 1, "motorola", "bad phone", 10);
        productController.addProduct(product0);

        Product product1;
        product1 = productController.getProductByName("motorola");
        log.debug("desc: " + product1.getDescription());
    }

     /* public static void main(String[] args) throws SQLException {
            final FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("storage/src/scripts/beans.xml");
            DataSource dataSource = null;
            dataSource = (DataSource) context.getBean("dataSource");
            ProductDbController baseController = new  ProductDbController(new SimpleJdbcTemplate(dataSource));
            List<Product> productsList = new ArrayList<Product>();
            productsList.add(new Product(1, "iphone 4", "mobile_phone_by_apple", 10));
            productsList.add(new Product(1, "htc desire", "mobile phone by HTC", 6));
            productsList.add(new Product(1, "nokia 3310", "cool mobile phone by Nokia", 100));
            productsList.add(new Product(1, "motorola", "bad phone", 100));

            baseController.addProductList(productsList);
      }   */
}
