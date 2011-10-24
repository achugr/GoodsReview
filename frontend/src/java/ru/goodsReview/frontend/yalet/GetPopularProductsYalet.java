package ru.goodsReview.frontend.yalet;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;

import ru.goodsReview.core.model.Product;

// todo rewrite this class

public class GetPopularProductsYalet implements Yalet {
	public void process(InternalRequest req, InternalResponse res) {
		res.add(new Product(1, "Lenovo notebooks"));
		res.add(new Product(2, "BMW X5"));
		res.add(new Product(3, "Contex"));
		return;
	}
}
