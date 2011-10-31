package ru.goodsReview.frontend.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import ru.goodsReview.core.model.Product;
import ru.goodsReview.frontend.model.ProductForView;
import ru.goodsReview.frontend.util.Prepare;
import ru.goodsReview.storage.controller.ProductDbController;

/*
 *  Date: 30.10.11
 *  Time: 21:46
 *  Author: 
 *     Vanslov Evgeny 
 *     vans239@gmail.com
 */

public class PopularProductsManager {
	private static final Logger log = Logger.getLogger(PopularProductsManager.class);
	private int count;
	private SimpleJdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<ProductForView> products() throws Exception {
		ProductDbController pdbc = new ProductDbController(jdbcTemplate);
		List<ProductForView> result = new ArrayList<ProductForView>();
		for (Product product : pdbc.getPopularProducts(count)) {
			log.debug("Product added:" + product.getName() + " Id:" + product.getId());
			result.add(Prepare.prepareProductForView(jdbcTemplate, product));
		}
		return result;
	}
}
