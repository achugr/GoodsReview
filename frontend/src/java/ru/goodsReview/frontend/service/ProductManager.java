package ru.goodsReview.frontend.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

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

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<ProductForView> productById(long id) throws Exception {
		ProductDbController pdbc = new ProductDbController(jdbcTemplate);
		List<ProductForView> result = new ArrayList<ProductForView>();
		ProductForView pfv = Prepare.prepareProductForView(jdbcTemplate, pdbc.getProductById(id));
		if (pfv != null) {
			log.debug("Added product " + pfv.getName());
			result.add(pfv);
		}
		return result;
	}

}
