package ru.goodsReview.frontend.yalet;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;

import ru.goodsReview.core.model.Product;

public class GetProductYalet implements Yalet{
	public void process(InternalRequest req, InternalResponse res) {
		String request = req.getParameter("query");
		if (request == null || request.isEmpty()) {
			Product product = new Product(1, "Error");
			res.add(product);
			return;
		}
		Product product = new Product(1, request);
		res.add(product);
		return;
	}
}
