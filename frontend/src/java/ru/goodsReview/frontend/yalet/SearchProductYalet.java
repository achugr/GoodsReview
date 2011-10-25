package ru.goodsReview.frontend.yalet;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;

import ru.goodsReview.core.model.Product;
import ru.goodsReview.frontend.model.ProductForView;

public class SearchProductYalet implements Yalet{
	public void process(InternalRequest req, InternalResponse res) {
		String request = req.getParameter("query");
		if (request == null || request.isEmpty()) {
			Product product = new Product(1, "Error");
			ProductForView pvf = new ProductForView(product);
			res.add(pvf);
			return;
		}
		Product product = new Product(1, request);
		ProductForView pvf = new ProductForView(product);
		res.add(pvf);
		return;
	}
}
