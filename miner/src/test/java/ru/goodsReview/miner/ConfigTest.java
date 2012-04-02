package ru.goodsReview.miner;

import ru.goodsReview.miner.beans.CategoryConfig;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Artemij Chugreev
 * Date: 25.03.12
 * Time: 16:04
 * email: artemij.chugreev@gmail.com
 * skype: achugr
 */
public class ConfigTest {

//    TODO make marshall wrapper
    public static void main(String[] args) throws JAXBException, IOException {
//        marshal
        {
            JAXBContext jc = JAXBContext.newInstance(CategoryConfig.class);
            Marshaller m = jc.createMarshaller();
            CategoryConfig categoryConfig = new CategoryConfig();
            categoryConfig.setCategory("notebook");
            categoryConfig.setNameRegexp(".*");
            OutputStream outputStream = new FileOutputStream("miner/src/main/resources/notebooks.xml");
            m.marshal(categoryConfig, outputStream);
            outputStream.close();
        }
//        unmarshal
        {
            JAXBContext jc = JAXBContext.newInstance(CategoryConfig.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            CategoryConfig categoryConfig;
            categoryConfig = (CategoryConfig) unmarshaller.unmarshal(new File("miner/src/main/resources/notebooks.xml"));
            System.out.println(categoryConfig.getCategory() + " " + categoryConfig.getNameRegexp());
        }
    }

}
