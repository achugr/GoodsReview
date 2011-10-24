package ru.goodsReview.frontend.yalet;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;

import ru.goodsReview.core.model.Product;

public class GetPopularProductsYalet implements Yalet {
	public void process(InternalRequest req, InternalResponse res) {
		res.add(new Product(1, "Lenovo notebooks"));
		res.add(new Product(1, "BMW X5"));
		res.add(new Product(1, "Contex"));
		return;
	}
}
