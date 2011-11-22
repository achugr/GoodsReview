package ru.goodsReview.searcher;

import org.springframework.context.support.FileSystemXmlApplicationContext;
import ru.goodsReview.core.model.Product;

import java.util.List;

public class TestSearcher {
    static final String DBDirectory = "database";

    public static void main(String[] args) throws Exception {
        final FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(
                "searcher/src/scripts/beans.xml");
        SimpleSearcher searcher = (SimpleSearcher) context.getBean("searcher");
        searcher.getReadyForSearch();
        List<Product> products = searcher.searchProductByName("eee");

        for(Product product : products){
            System.out.println(product.getName());
        }
    }
}
