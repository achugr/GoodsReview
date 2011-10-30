package ru.goodsReview.frontend.yalet;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;
import net.sf.xfresh.core.xml.Xmler;

import org.apache.log4j.Logger;
import ru.goodsReview.frontend.model.ProductForView;
import ru.goodsReview.frontend.service.PopularProductsManager;

import java.util.List;

// todo rewrite this class
/*
 *  Date: 30.10.11
 *   Time: 14:12
 *   Author:
 *      Vanslov Evgeny
 *      vans239@gmail.com
 */

public class PopularProductsYalet implements Yalet {
	private static final Logger log = org.apache.log4j.Logger.getLogger(ProductYalet.class);
	private PopularProductsManager popularProductsManager;

	public void setPopularProductsManager(PopularProductsManager popularProductsManager) {
		this.popularProductsManager = popularProductsManager;
	}
	public void process(InternalRequest req, InternalResponse res) {
		try {
			List<ProductForView> products = popularProductsManager.products();
			if (products.size() != 0) {
				res.add(products);
			} else {
				Xmler.Tag ans = Xmler.tag("answer", "Ничего не найдено.");
				res.add(ans);
			}
		} catch (Exception e) {
			log.error("Something happens wrong");
			Xmler.Tag ans = Xmler.tag("answer", "Все сломалось.");
			res.add(ans);
		}
	}
}
