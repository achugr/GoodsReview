package ru.goodsReview.searcher.mapper;

/*
 *  Date: 13.11.11
 *  Time: 17:05
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

import org.apache.lucene.document.Document;
import org.jetbrains.annotations.NotNull;
import ru.goodsReview.core.model.Product;

public class ProductMapper {
    /**
     * Makes Product by Document.
     * @param doc Document which represents a needed product.
     * @return A product itself.
     */
    public Product mapDoc(@NotNull Document doc){
        Product product = new Product(Long.parseLong(doc.get("id")), doc.get("name"));
        product.setCategoryId(Long.parseLong(doc.get("category_id")));
        product.setDescription(doc.get("description"));
        product.setDescription(doc.get("popularity"));
        return product;
    }
}
