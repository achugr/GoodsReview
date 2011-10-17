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
        //String path = "D:"+File.separator+"GoodsReview"+File.separator+"storage"+File.separator+"src"+File.separator+"java"+File.separator+"test"+File.separator+"goodsReview"+File.separator+"storage"+File.separator+"beans.xml";
       // ApplicationContext factory  = new ClassPathXmlApplicationContext("beans.xml");
        final FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("beans.xml");
        DataSource dataSource = null;
        dataSource = (DataSource) context.getBean("dataSource");
        ProductDbController baseController = new  ProductDbController(new SimpleJdbcTemplate(dataSource));
        Record record = new Record();
        record = baseController.getProductByName("iphone");
        List<Cell> row;
        for(int i=0; i<record.getCells().size(); i++ )  {
            row = record.getCells();
            System.out.println(row.get(i).getValue());
        }

            //Class.forName("com.mysql.jdbc.Driver");

          /*  Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/GoodsReview",
                    "root", "root");

            if (conn == null) {
                System.out.println("Нет соединения с БД!");
                System.exit(0);
            }

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM product");

            while (rs.next()) {
                System.out.println(rs.getRow() + ". " + rs.getString("name")
                        + "\t" + rs.getString("description"));
            }


            stmt.close();
            */
        }
}
