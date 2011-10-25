package test.goodsReview.storage;


import java.sql.*;

import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.storage.controller.ProductDbController;
//import ru.goodsReview.storage.mappers.*;

import javax.sql.DataSource;

/**
 * Created by IntelliJ IDEA.
 * User: Artemij
 * Date: 17.10.11
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
public class DbModuleTest {

    public static void main(String[] args) throws SQLException {
            final FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("storage/src/scripts/beans.xml");
            DataSource dataSource = null;
            dataSource = (DataSource) context.getBean("dataSource");
            ProductDbController baseController = new  ProductDbController(new SimpleJdbcTemplate(dataSource));
            Product product;
            product = baseController.getProductByName("iphone");
            System.out.print("name" +  product.getName());
            Product product2 = new Product(2, 2, "motorola", "bad phone", 10);
            baseController.setProduct(product2);
        }
}
