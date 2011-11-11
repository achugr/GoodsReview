package ru.goodsReview.frontend.model;

import ru.goodsReview.core.model.Category;
import ru.goodsReview.core.model.Product;

/*
 *  Date: 30.10.11
 *   Time: 14:12
 *   Author:
 *      Vanslov Evgeny
 *      vans239@gmail.com
 */

public class ProductForView {
    private final String name;
    private final String description;
    private final long id;
    private final String category;
    private final long categoryId;
    private final long popularity;

    public ProductForView(Product product, Category category) throws Exception {
        name = product.getName();
        description = product.getDescription();
        id = product.getId();
        if (product.getCategoryId() != category.getId()) {
            //throw new Exception("Category mismatch");
        }
        categoryId = category.getId();
        this.category = category.getName();
        popularity = product.getPopularity();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public String getCategory() {
        return category;
    }

    public long getId() {
        return id;
    }

    public long getPopularity() {
        return popularity;
    }

}
