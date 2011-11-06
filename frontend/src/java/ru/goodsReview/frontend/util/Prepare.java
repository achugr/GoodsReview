package ru.goodsReview.frontend.util;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import ru.goodsReview.core.model.Product;
import ru.goodsReview.core.model.Thesis;
import ru.goodsReview.frontend.model.DetailedProductForView;
import ru.goodsReview.frontend.model.ProductForView;
import ru.goodsReview.frontend.model.ThesisForView;
import ru.goodsReview.storage.controller.CategoryDbController;
import ru.goodsReview.core.model.Category;
import ru.goodsReview.storage.controller.ThesisDbController;

import java.util.ArrayList;
import java.util.List;

/*
 *  Date: 30.10.11
 *   Time: 14:12
 *   Author:
 *      Vanslov Evgeny
 *      vans239@gmail.com
 */

public class Prepare {
	public static ProductForView prepareProductForView(final SimpleJdbcTemplate jdbcTemplate, final Product product) throws Exception {
		CategoryDbController cdbc = new CategoryDbController(jdbcTemplate);
		if (product == null)
			return null;
		Category category = cdbc.getCategoryById(product.getCategoryId());
		ProductForView pfv = new ProductForView(product, category);
		return pfv;
	}
	public static DetailedProductForView prepareDetailedProductForView(final SimpleJdbcTemplate jdbcTemplate, final Product product) throws Exception {
		CategoryDbController cdbc = new CategoryDbController(jdbcTemplate);
		if (product == null)
			return null;
		Category category = cdbc.getCategoryById(product.getCategoryId());

		List<ThesisForView> thesesForView = new ArrayList<ThesisForView>();

		ThesisDbController tdbc = new ThesisDbController(jdbcTemplate);
		List<Thesis> theses = tdbc.getThesesByProductId(product.getId());
		for (Thesis thesis : theses) {
			thesesForView.add(new ThesisForView(thesis));
		}
		return new DetailedProductForView(product, category, thesesForView);
	}
}
