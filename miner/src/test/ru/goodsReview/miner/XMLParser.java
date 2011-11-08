package ru.goodsReview.miner;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: YaroslavSkudarnov
 * Date: 15.10.11
 * Time: 18:13
 * To change this template use File | Settings | File Templates.
 */

class Product {
    String productName;
    Vector<String> productReviews;

    Product(String productName) {
        this.productName = productName;
        this.productReviews = new Vector<String>();
    }
}

class Goods {
    public HashMap<String, Vector<String>> getGoods() {
        return goods;
    }

    HashMap<String,Vector<String>> goods;

    Goods() {
        goods = new HashMap<String,Vector<String>>();
    }
}

public class XMLParser {
    public static void main(String argv[]) {
        try {
            File file = new File("citilinkReviews.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            NodeList nodeLst = doc.getElementsByTagName("product");

            Goods goods = new Goods();

            for (int i = 0; i < nodeLst.getLength(); i++) {
                Element elem = (Element) nodeLst.item(i);
                Product product = new Product(elem.getFirstChild().getTextContent());

                NodeList descsList = elem.getElementsByTagName("desc");
                if ((descsList != null) && (descsList.getLength() > 0)) {
                    product.productReviews = new Vector<String>();
                    for (int j = 0; j < descsList.getLength(); j++) {
                        product.productReviews.add(descsList.item(j).getTextContent());
                    }
                }

                NodeList prosList = elem.getElementsByTagName("good");
                if ((prosList != null) && (prosList.getLength() > 0)) {
                    for (int j = 0; j < prosList.getLength(); j++) {
                        if (prosList.item(j).getTextContent().length() > 12) {
                            product.productReviews.add(prosList.item(j).getTextContent().substring(13));
                        } else {
                            product.productReviews.add(prosList.item(j).getTextContent());
                        }
                    }
                }

                NodeList consList = elem.getElementsByTagName("bad");
                if ((consList != null) && (consList.getLength() > 0)) {
                    for (int j = 0; j < consList.getLength(); j++) {
                        if (consList.item(j).getTextContent().length() > 13) {
                            product.productReviews.add(consList.item(j).getTextContent().substring(12));
                        } else {
                            product.productReviews.add(consList.item(j).getTextContent());
                        }
                    }
                }

                goods.goods.put(product.productName,product.productReviews);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}