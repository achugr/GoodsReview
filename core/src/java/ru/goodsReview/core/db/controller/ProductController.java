package ru.goodsReview.core.db.controller;

import ru.goodsReview.core.model.Product;
import ru.goodsReview.storage.exception.StorageException;

import java.util.List;

/*
 *  Date: 13.11.11
 *  Time: 10:59
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

public interface ProductController {
    public long addProduct(Product product) throws StorageException;

    public List<Long> addProductList(List<Product> productList) throws StorageException;

    public List<Product> getAllProducts();

    public List<Product> getPopularProducts(int n);

    public Product getProductById(long product_id);

    public List<Product> getProductsByIds(List<Long> ids);

    public Product getProductByName(String product_name);

    public List<Product> getProductsByName(String product_name);

    public List<Product> getProductsByCategoryId(long category_id);
}
