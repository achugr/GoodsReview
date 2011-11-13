package ru.goodsReview.core.db;

/*
 *  Date: 13.11.11
 *  Time: 10:58
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

import ru.goodsReview.core.db.controller.*;

public class MainController {
    private CategoryController categoryController;
    private ProductController productController;
    private ReviewController reviewController;
    private SourceController sourceController;
    private ThesisController thesisController;
    private ThesisUniqueController thesisUniqueController;

    public void setCategoryController(CategoryController categoryController) {
        this.categoryController = categoryController;
    }

    public void setProductController(ProductController productController) {
        this.productController = productController;
    }

    public void setReviewController(ReviewController reviewController) {
        this.reviewController = reviewController;
    }

    public void setSourceController(SourceController sourceController) {
        this.sourceController = sourceController;
    }

    public void setThesisController(ThesisController thesisController) {
        this.thesisController = thesisController;
    }

    public void setThesisUniqueController(ThesisUniqueController thesisUniqueController) {
        this.thesisUniqueController = thesisUniqueController;
    }

    public CategoryController getCategoryController() {
        return categoryController;
    }

    public ProductController getProductController() {
        return productController;
    }

    public ReviewController getReviewController() {
        return reviewController;
    }

    public SourceController getSourceController() {
        return sourceController;
    }

    public ThesisController getThesisController() {
        return thesisController;
    }

    public ThesisUniqueController getThesisUniqueController() {
        return thesisUniqueController;
    }
}
