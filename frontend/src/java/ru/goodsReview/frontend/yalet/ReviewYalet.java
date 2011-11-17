package ru.goodsReview.frontend.yalet;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;
import net.sf.xfresh.core.xml.Xmler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import ru.goodsReview.frontend.model.ReviewForView;
import ru.goodsReview.frontend.service.ReviewManager;

import java.util.List;

// todo rewrite this class
/*
 *  Date: 30.10.11
 *   Time: 14:12
 *   Author:
 *      Vanslov Evgeny
 *      vans239@gmail.com
 */

public class ReviewYalet implements Yalet {
    private static final Logger log = Logger.getLogger(ProductYalet.class);
    private ReviewManager reviewManager;

    @Required
    public void setReviewManager(ReviewManager reviewManager) {
        this.reviewManager = reviewManager;
    }

    public void process(InternalRequest req, InternalResponse res) {
        int id = req.getIntParameter("id");

        try {
            log.debug("Request product. id = " + id);
            List<ReviewForView> products = reviewManager.reviewById(id);

            if (products.size() != 0) {
                res.add(products);
            } else {
                log.debug("Nothing found for id = " + id);
                Xmler.Tag ans = Xmler.tag("answer", "Ничего не найдено. Id: " + id);
                res.add(ans);
            }
        } catch (Exception e) {
            log.error("Something happens wrong with id: " + id);
            Xmler.Tag ans = Xmler.tag("answer", "Все сломалось. Id: " + id);
            res.add(ans);
        }
    }
}
