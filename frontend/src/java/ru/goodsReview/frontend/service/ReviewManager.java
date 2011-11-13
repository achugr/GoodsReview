package ru.goodsReview.frontend.service;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.db.ControllerFactory;
import ru.goodsReview.frontend.model.ReviewForView;
import ru.goodsReview.frontend.util.Prepare;
import ru.goodsReview.storage.controller.ReviewDbController;

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
    private int popularCount;

    public void setControllerFactory(ControllerFactory controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    public void setPopularCount(int popularCount) {
        this.popularCount = popularCount;
    }

    public List<ReviewForView> reviewById(long id) throws Exception {
        List<ReviewForView> result = new ArrayList<ReviewForView>();
        ReviewForView rfv = Prepare.prepareReviewForView(controllerFactory,
                                                         controllerFactory.getReviewController().getReviewById(id));
        if (rfv != null) {
            log.debug("Added product " + rfv.getId());
            result.add(rfv);
        }
        return result;
    }
}
