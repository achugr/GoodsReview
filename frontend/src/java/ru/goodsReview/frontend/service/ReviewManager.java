package ru.goodsReview.frontend.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import ru.goodsReview.core.model.Product;
import ru.goodsReview.frontend.model.DetailedProductForView;
import ru.goodsReview.frontend.model.ReviewForView;
import ru.goodsReview.frontend.util.Prepare;
import ru.goodsReview.storage.controller.ProductDbController;
import ru.goodsReview.storage.controller.ReviewDbController;

/*
 *  Date: 06.11.11
 *  Time: 19:57
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

public class ReviewManager {
	private static final Logger log = Logger.getLogger(ReviewManager.class);
	private SimpleJdbcTemplate jdbcTemplate;
	private int popularCount = 5;

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setPopularCount(int popularCount) {
		this.popularCount = popularCount;
	}

	public List<ReviewForView> reviewById(long id) throws Exception {
		ReviewDbController rdbc = new ReviewDbController(jdbcTemplate);
		List<ReviewForView> result = new ArrayList<ReviewForView>();
		ReviewForView rfv = Prepare.prepareReviewForView(jdbcTemplate, rdbc.getReviewById(id));
		if (rfv != null) {
			log.debug("Added product " + rfv.getId());
			result.add(rfv);
		}
		return result;
	}

	/*public List<DetailedProductForView> popularProducts() throws Exception {
		ProductDbController pdbc = new ProductDbController(jdbcTemplate);
		List<DetailedProductForView> result = new ArrayList<DetailedProductForView>();
		for (Product product : pdbc.getPopularProducts(popularCount)) {
			log.debug("Product added:" + product.getName() + " Id:" + product.getId());
			result.add(Prepare.prepareDetailedProductForView(jdbcTemplate, product));
		}
		return result;
	}*/
}
