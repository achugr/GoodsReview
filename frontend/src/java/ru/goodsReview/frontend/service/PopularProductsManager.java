package ru.goodsReview.frontend.service;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.frontend.model.ProductForView;
import ru.goodsReview.frontend.util.ProductForViewPrepare;
import ru.goodsReview.frontend.yalet.ProductYalet;
import ru.goodsReview.storage.controller.ProductDbController;

import java.util.ArrayList;
import java.util.List;

/*
 *  Date: 30.10.11
 *  Time: 21:46
 *  Author: 
 *     Vanslov Evgeny 
 *     vans239@gmail.com
 */

public class PopularProductsManager {
	private static final Logger log = org.apache.log4j.Logger.getLogger(PopularProductsManager.class);
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
			result.add(ProductForViewPrepare.prepare(jdbcTemplate, product));
		}
		return result;
	}

}
