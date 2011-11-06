package ru.goodsReview.frontend.service;
/*
 *  Date: 31.10.11
 *  Time: 16:51
 *  Author: 
 *     Vanslov Evgeny 
 *     vans239@gmail.com
 */


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import ru.goodsReview.core.model.Review;
import ru.goodsReview.frontend.model.ReviewForView;
import ru.goodsReview.storage.controller.ReviewDbController;


public class PopularReviewManager {
	private static final Logger log = Logger.getLogger(PopularReviewManager.class);
	private int count;
	private SimpleJdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<ReviewForView> reviewsByProduct(long id) throws Exception {
		ReviewDbController pdbc = new ReviewDbController(jdbcTemplate);
		List<ReviewForView> result = new ArrayList<ReviewForView>();
		/*for (Review review : pdbc.getPopularReviewsByProductId(id, count)) {
			log.debug("Review added Id:" + review.getId());
			result.add(new ReviewForView(review));
		}*/
		return result;
	}
}
