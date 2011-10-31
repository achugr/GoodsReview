package ru.goodsReview.frontend.yalet;

import java.util.List;

import org.apache.log4j.Logger;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;
import net.sf.xfresh.core.xml.Xmler;

import ru.goodsReview.frontend.model.ProductForView;
import ru.goodsReview.frontend.service.ProductManager;

/*
 *  Date: 30.10.11
 *   Time: 14:12
 *   Author:
 *      Vanslov Evgeny
 *      vans239@gmail.com
 */

public class ProductYalet implements Yalet {
	private static final Logger log = org.apache.log4j.Logger.getLogger(ProductYalet.class);
	private ProductManager productManager;

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public void process(InternalRequest req, InternalResponse res) {
		long id = req.getIntParameter("id");
		try {
			log.debug("Request product. id = " + id);
			List<ProductForView> products = productManager.productById(id);

			if (products.size() != 0) {
				res.add(products);
			} else {
				log.debug("Nothing found for id = " + id);
				Xmler.Tag ans = Xmler.tag("answer", "Ничего не найдено. Id: " + id);
				res.add(ans);
			}
		} catch (Exception e) {
			log.error("Something happens wrong with id: " + id);
			Xmler.Tag ans = Xmler.tag("answer", "Все сломалось. Id: " + id);
			res.add(ans);
		}
	}

}
