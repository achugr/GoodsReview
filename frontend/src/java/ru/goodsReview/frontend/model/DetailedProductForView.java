package ru.goodsReview.frontend.model;

import org.jetbrains.annotations.NotNull;
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

    public DetailedProductForView(@NotNull Product product, @NotNull Category category,
                                  @NotNull List<ThesisForView> theses,
                                  @NotNull List<ReviewForView> reviews) throws Exception {
        super(product, category);
        this.theses = theses;
        this.reviews = reviews;
    }

    public @NotNull List<ThesisForView> getTheses() {
        return theses;
    }

    public @NotNull List<ReviewForView> getReviews() {
        return reviews;
    }
}
