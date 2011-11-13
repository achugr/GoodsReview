package ru.goodsReview.frontend.mapper;

import ru.goodsReview.core.db.ControllerFactory;
import ru.goodsReview.core.model.Category;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.core.model.Thesis;
import ru.goodsReview.frontend.model.DetailedProductForView;
import ru.goodsReview.frontend.model.ReviewForView;
import ru.goodsReview.frontend.model.ThesisForView;

import java.util.ArrayList;
import java.util.List;

/*
 *  Date: 30.10.11
 *   Time: 14:12
 *   Author:
 *      Vanslov Evgeny
 *      vans239@gmail.com
 */

public class ProductMapper {
    private ReviewMapper reviewMapper = new ReviewMapper();

    public DetailedProductForView prepareDetailedProductForView(final ControllerFactory controllerFactory,
                                                                       final Product product) throws Exception {
        if (product == null) {
            throw new Exception();
        }
        Category category = controllerFactory.getCategoryController().getCategoryById(product.getCategoryId());

        List<ThesisForView> thesesForView = new ArrayList<ThesisForView>();
        List<Thesis> theses = controllerFactory.getThesisController().getThesesByProductId(product.getId());
        for (Thesis thesis : theses) {
            thesesForView.add(new ThesisForView(thesis));
        }

        List<ReviewForView> reviewsForView = new ArrayList<ReviewForView>();
        List<Review> reviews = controllerFactory.getReviewController().getReviewsByProductId(product.getId());
        for (Review review : reviews) {
            reviewsForView.add(reviewMapper.prepareReviewForView(controllerFactory, review));
        }
        return new DetailedProductForView(product, category, thesesForView, reviewsForView);
    }


}
