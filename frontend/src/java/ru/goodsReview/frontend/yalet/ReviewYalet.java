package ru.goodsReview.frontend.yalet;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;

import ru.goodsReview.core.model.Review;

// todo rewrite this class
public class ReviewYalet implements Yalet {
	public void process(InternalRequest req, InternalResponse res) {
		String request = req.getParameter("query");
		if (request == null || request.isEmpty()) {
			Review review = new Review(1, 1, "Error", "Error", 1, "Error", 1, "Error", 1, 1, 1, 1);
			res.add(review);
			return;
		}
		Review review = new Review(1, 1, request, request, 1, request, 1, request, 1, 1, 1, 1);
		res.add(review);
		return;
	}
}

