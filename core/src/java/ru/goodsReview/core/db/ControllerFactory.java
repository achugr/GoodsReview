package ru.goodsreview.core.db;

/*
 *  Date: 13.11.11
 *  Time: 10:58
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

import org.springframework.beans.factory.annotation.Required;
import ru.goodsreview.core.db.controller.*;

public class ControllerFactory {
    private CategoryController categoryController;
    private ProductController productController;
    private ReviewController reviewController;
    private SourceController sourceController;
    private ThesisController thesisController;
    private ThesisUniqueController thesisUniqueController;

    @Required
    public void setCategoryController(CategoryController categoryController) {
        this.categoryController = categoryController;
    }

    @Required
    public void setProductController(ProductController productController) {
        this.productController = productController;
    }

    @Required
    public void setReviewController(ReviewController reviewController) {
        this.reviewController = reviewController;
    }

    @Required
    public void setSourceController(SourceController sourceController) {
        this.sourceController = sourceController;
    }

    @Required
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
