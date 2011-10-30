package ru.goodsReview.frontend.util;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import ru.goodsReview.core.model.Product;
import ru.goodsReview.frontend.model.ProductForView;
import ru.goodsReview.storage.controller.CategoryDbController;
import ru.goodsReview.core.model.Category;

public class ProductForViewPrepare {
	public static ProductForView prepare(final SimpleJdbcTemplate jdbcTemplate, final Product product) throws Exception {
		CategoryDbController cdbc = new CategoryDbController(jdbcTemplate);

		Category category = cdbc.getCategoryById(product.getCategoryId());
		ProductForView pfv = new ProductForView(product, category);
		return pfv;
	}
}
