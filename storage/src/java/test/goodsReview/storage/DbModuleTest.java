package test.goodsReview.storage;


import java.sql.*;
import java.util.List;

import net.sf.xfresh.db.Cell;
import net.sf.xfresh.db.Record;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
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
            final FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("beans.xml");
            DataSource dataSource = null;
            dataSource = (DataSource) context.getBean("dataSource");
            ProductDbController baseController = new  ProductDbController(new SimpleJdbcTemplate(dataSource));
            Record record;
            record = baseController.getProductByName("iphone");
            List<Cell> row;
            for(int i=0; i<record.getCells().size(); i++ )  {
                row = record.getCells();
                System.out.println(row.get(i).getValue());
            }
        }
}
