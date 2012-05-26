package ru.goodsreview.frontend.mapper;

/*
 *  Date: 13.11.11
 *  Time: 17:36
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

import ru.goodsreview.core.db.ControllerFactory;
import ru.goodsreview.core.model.Review;
import ru.goodsreview.core.model.Thesis;
import ru.goodsreview.frontend.model.ReviewForView;
import ru.goodsreview.frontend.model.ThesisForView;

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
