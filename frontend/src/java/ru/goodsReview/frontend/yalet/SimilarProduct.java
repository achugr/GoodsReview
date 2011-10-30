package ru.goodsReview.frontend.yalet;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.frontend.model.ProductForView;

/*
 *  Date: 30.10.11
 *   Time: 14:12
 *   Author:
 *      Vanslov Evgeny
 *      vans239@gmail.com
 */

public class SimilarProduct implements Yalet {
	public void process(InternalRequest req, InternalResponse res) {
			long id = req.getIntParameter("id");
			Product product = new Product(id, "All is ok" + id);
			//ProductForView pvf = new ProductForView(product);
			//res.add(pvf);
			return;
		}

}
