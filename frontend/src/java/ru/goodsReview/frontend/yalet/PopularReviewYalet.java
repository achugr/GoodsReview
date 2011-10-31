package ru.goodsReview.frontend.yalet;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.xml.Xmler;
import net.sf.xfresh.core.Yalet;

import ru.goodsReview.frontend.model.ReviewForView;
import ru.goodsReview.frontend.service.PopularReviewManager;

/*
 *  Date: 31.10.11
 *  Time: 16:50
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

public class PopularReviewYalet implements Yalet {
	private static final Logger log = org.apache.log4j.Logger.getLogger(PopularReviewYalet.class);
	private PopularReviewManager popularReviewManager;

	public void setPopularReviewManager(PopularReviewManager popularReviewManager) {
		this.popularReviewManager = popularReviewManager;
	}

	public void process(InternalRequest req, InternalResponse res) {
		long id = req.getIntParameter("id");
		log.debug("Request popular review for product id = " + id);
		try {
			List<ReviewForView> reviews = popularReviewManager.reviewsByProduct(id);
			if (reviews.size() != 0) {
				res.add(reviews);
				//log.debug("sdas");
			} else {
				Xmler.Tag ans = Xmler.tag("answer", "Отзывы не найдены. Id: " + id);
				res.add(ans);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Something happens wrong with id: " + id);
			Xmler.Tag ans = Xmler.tag("answer", "Все сломалось. Id: " + id);
			res.add(ans);
		}
	}
}
