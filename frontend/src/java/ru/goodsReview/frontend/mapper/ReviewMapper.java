package ru.goodsReview.frontend.mapper;

/*
 *  Date: 13.11.11
 *  Time: 17:36
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

import ru.goodsReview.core.db.ControllerFactory;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.core.model.Thesis;
import ru.goodsReview.frontend.model.ReviewForView;
import ru.goodsReview.frontend.model.ThesisForView;

import java.util.ArrayList;
import java.util.List;

public class ReviewMapper {
    public ReviewForView prepareReviewForView(final ControllerFactory controllerFactory,
                                                     final Review review) throws Exception {
        if (review == null) {
            throw new Exception();
        }

        List<ThesisForView> thesesForView = new ArrayList<ThesisForView>();

        List<Thesis> theses = controllerFactory.getThesisController().getThesesByReviewId(review.getId());
        for (Thesis thesis : theses) {
            thesesForView.add(new ThesisForView(thesis));
        }

        return new ReviewForView(review, thesesForView);
    }
}
