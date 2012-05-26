package ru.goodsreview.frontend.service;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Required;
import ru.goodsreview.core.db.ControllerFactory;
import ru.goodsreview.frontend.mapper.ReviewMapper;
import ru.goodsreview.frontend.model.ReviewForView;

import java.util.ArrayList;
import java.util.List;

/*
 *  Date: 06.11.11
 *  Time: 19:57
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

public class ReviewManager {
    private static final Logger log = Logger.getLogger(ReviewManager.class);
    private ControllerFactory controllerFactory;
    private ReviewMapper reviewMapper = new ReviewMapper();
    private int popularCount;


    @Required
    public void setControllerFactory(@NotNull ControllerFactory controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    @Required
    public void setPopularCount(int popularCount) {
        this.popularCount = popularCount;
    }

    public @NotNull List<ReviewForView> reviewById(long id) throws Exception {
        List<ReviewForView> result = new ArrayList<ReviewForView>();
        ReviewForView rfv = reviewMapper.prepareReviewForView(controllerFactory,
                                                               controllerFactory.getReviewController().getReviewById(
                                                                       id));
        if (rfv != null) {
            log.debug("Added product " + rfv.getId());
            result.add(rfv);
        }
        return result;
    }
}
