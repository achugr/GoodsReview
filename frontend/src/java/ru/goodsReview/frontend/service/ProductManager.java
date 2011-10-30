package ru.goodsReview.frontend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import ru.goodsReview.frontend.model.ProductForView;
import ru.goodsReview.frontend.util.ProductForViewPrepare;
import ru.goodsReview.storage.controller.ProductDbController;

/*
 *  Date: 30.10.11
 *   Time: 14:12
 *   Author:
 *      Vanslov Evgeny
 *      vans239@gmail.com
 */

public class ProductManager {
	private SimpleJdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<ProductForView> productById(long id) throws Exception {
		ProductDbController pdbc = new ProductDbController(jdbcTemplate);
		List<ProductForView> result = new ArrayList<ProductForView>();

		result.add(ProductForViewPrepare.prepare(jdbcTemplate, pdbc.getProductById(id)));
		return result;
	}

}
