package ru.goodsReview.frontend.yalet;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;

import ru.goodsReview.core.model.Review;
import ru.goodsReview.core.model.CitilinkReview;

public class GetReviewYalet implements Yalet {

	public void process(InternalRequest req, InternalResponse res) {
		String request = req.getParameter("query");
		if (request == null || request.isEmpty()) {
			Review review = new CitilinkReview(1,"Error", "Error", "Error", "Error", 1, 1);
			res.add(review);
			return;
		}
		Review review = new CitilinkReview(1,request, request, request, request, 1, 1);
		res.add(review);
		return;
	}
}
