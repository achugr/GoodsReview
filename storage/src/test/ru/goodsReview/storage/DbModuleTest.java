package ru.goodsReview.storage;

import org.apache.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.storage.controller.ThesisDbController;

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

    /*@Test
    public void testSMB() {
        Assert.assertEquals(4, 2 + 2);
    } */

    public static void main(String[] args) throws SQLException {
        final FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("storage/src/scripts/beans.xml");
        DataSource dataSource = (DataSource) context.getBean("dataSource");

        log.error("PRIVET");
        log.info("PRIVET");
        /*ThesisUniqueDbController thesisUniqueDbController = new ThesisUniqueDbController(new SimpleJdbcTemplate(dataSource));
        ThesisUnique thesisUnique = new ThesisUnique("good phone", 14, new Date(), 5.0, 1.0);
        ThesisUnique thesisUnique2 = new ThesisUnique("bad phone", 2, new Date(), -1.0, 0.1);
        List<ThesisUnique> list = new ArrayList<ThesisUnique>();
        list.add(thesisUnique);
        list.add(thesisUnique2);
        thesisUniqueDbController.addThesisUniqueList(list);            */
        ThesisDbController thesisDbController = new ThesisDbController(new SimpleJdbcTemplate(dataSource));
        thesisDbController.setThesisUniqueId(4, 2);
    }
}
