package ru.goodsReview.miner;

import org.springframework.context.support.FileSystemXmlApplicationContext;
import ru.goodsReview.miner.utils.ReviewFileWriter;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * Date: 21.11.2011
 * Time: 0:08
 * Author:
 * Sergey Serebryakov
 * sergey.serebryakoff@gmail.com
 */
public class ReviewFileWriterTest {
    public static void main(String[] args) {
        // TODO: Does this code belong here? Would be cool e.g. to get an instance of the controller.
        // Please also notice that this code uses beans.xml from storage module, which is probably inappropriate.
        final FileSystemXmlApplicationContext context =
                new FileSystemXmlApplicationContext("storage/src/scripts/beans.xml");
        DataSource dataSource = (DataSource) context.getBean("dataSource");

        try {
            ReviewFileWriter.writeReviewsToFile(dataSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
