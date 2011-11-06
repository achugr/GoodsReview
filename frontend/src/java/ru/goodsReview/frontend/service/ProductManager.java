package ru.goodsReview.frontend.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import ru.goodsReview.core.model.Product;
import ru.goodsReview.frontend.model.DetailedProductForView;
import ru.goodsReview.frontend.model.ProductForView;
import ru.goodsReview.frontend.util.Prepare;
import ru.goodsReview.storage.controller.ProductDbController;

/*
 *  Date: 30.10.11
 *   Time: 14:12
 *   Author:
 *      Vanslov Evgeny
 *      vans239@gmail.com
 */

public class ProductManager {
	private static final Logger log = Logger.getLogger(ProductManager.class);
	private SimpleJdbcTemplate jdbcTemplate;
	private int popularCount = 5;
	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setPopularCount(int popularCount) {
		this.popularCount = popularCount;
	}

	public List<DetailedProductForView> productById(long id) throws Exception {
		ProductDbController pdbc = new ProductDbController(jdbcTemplate);
		List<DetailedProductForView> result = new ArrayList<DetailedProductForView>();
		DetailedProductForView pfv = Prepare.prepareDetailedProductForView(jdbcTemplate, pdbc.getProductById(id));
		if (pfv != null) {
			log.debug("Added product " + pfv.getName());
			result.add(pfv);
		}
		return result;
	}

	public List<DetailedProductForView> popularProducts() throws Exception {
		ProductDbController pdbc = new ProductDbController(jdbcTemplate);
		List<DetailedProductForView> result = new ArrayList<DetailedProductForView>();
		for (Product product : pdbc.getPopularProducts(popularCount)) {
			log.debug("Product added:" + product.getName() + " Id:" + product.getId());
			result.add(Prepare.prepareDetailedProductForView(jdbcTemplate, product));
		}
		return result;
	}
}
