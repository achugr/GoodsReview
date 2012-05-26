package ru.goodsreview.core.db.controller;

import ru.goodsreview.core.db.exception.StorageException;
import ru.goodsreview.core.model.Category;

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
