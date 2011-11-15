package ru.goodsReview.core.db.controller;

import ru.goodsReview.core.model.Category;
import ru.goodsReview.storage.exception.StorageException;

import java.util.List;

/*
 *  Date: 13.11.11
 *  Time: 10:59
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

public interface CategoryController {
    public long addCategory(Category category) throws StorageException;

    public List<Category> getAllCategories();

    public Category getCategoryById(long category_id);
}
