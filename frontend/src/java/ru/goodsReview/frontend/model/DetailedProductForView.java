package ru.goodsReview.frontend.model;

import ru.goodsReview.core.model.Category;
import ru.goodsReview.core.model.Product;

import java.util.List;

/*
 *  Date: 05.11.11
 *  Time: 21:12
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

public class DetailedProductForView extends ProductForView {
    private List<ThesisForView> theses;
    private List<ReviewForView> reviews;

    public DetailedProductForView(Product product, Category category, List<ThesisForView> theses, List<ReviewForView> reviews) throws Exception {
        super(product, category);
        this.theses = theses;
        this.reviews = reviews;
    }

    public List<ThesisForView> getTheses() {
        return theses;
    }

    public List<ReviewForView> getReviews() {
        return reviews;
    }
}
