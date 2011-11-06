package ru.goodsReview.frontend.yalet;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;

import ru.goodsReview.core.model.Product;


/*
 *  Date: 30.10.11
 *   Time: 14:12
 *   Author:
 *      Vanslov Evgeny
 *      vans239@gmail.com
 */

public class SimilarProductYalet implements Yalet {
	public void process(InternalRequest req, InternalResponse res) {
			long id = req.getIntParameter("id");
			Product product = new Product(id, "All is ok" + id);
			//ProductForView pvf = new ProductForView(product);
			//res.add(pvf);
			return;
		}

}
