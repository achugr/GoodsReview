package ru.goodsReview.frontend.yalet;

import java.util.List;

import org.apache.log4j.Logger;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;
import net.sf.xfresh.core.xml.Xmler;

import ru.goodsReview.frontend.model.DetailedProductForView;
import ru.goodsReview.frontend.model.ProductForView;
import ru.goodsReview.frontend.service.SearchManager;

/*
 *  Date: 30.10.11
 *   Time: 14:12
 *   Author:
 *      Vanslov Evgeny
 *      vans239@gmail.com
 */

public class SearchProductYalet implements Yalet {
	private static final Logger log = org.apache.log4j.Logger.getLogger(SearchProductYalet.class);
	private SearchManager searchManager;

	public void setSearchManager(SearchManager searchManager) {
		this.searchManager = searchManager;
	}

	public void process(InternalRequest req, InternalResponse res) {
		String query = req.getParameter("query");
		log.debug("Request search query:" + query);
		if (query.isEmpty()) {
			log.debug("Empty query");
			Xmler.Tag ans = Xmler.tag("answer", "Пустой запрос. Query: " + query);
			res.add(ans);
			return;
		}

		try {
			List<DetailedProductForView> products = searchManager.searchByName(query);
			if (products.size() != 0) {
				res.add(products);
			} else {
				log.debug("Nothing found for query " + query);
				Xmler.Tag ans = Xmler.tag("answer", "Ничего не найдено. Query: " + query);
				res.add(ans);
			}
		} catch (Exception e) {
			log.error("Something happens wrong with query: " + query);
			Xmler.Tag ans = Xmler.tag("answer", "Все сломалось. Query: " + query);
			res.add(ans);
		}
	}
}

