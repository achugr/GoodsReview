package ru.goodsReview.frontend.service;

import java.util.ArrayList;
import java.util.List;

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

public class SearchManager {
	private SimpleJdbcTemplate jdbcTemplate;

	public SearchManager() {

	}

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	//private Searcher searcher;

	//public void setSearcher(Searcher searcher) {
	//	this.searcher = searcher;
	//}

	public List<ProductForView> searchByName(String query) throws Exception {

		/*		List<ProductForView> brandList = new ArrayList<ProductForView>();
					for (Brand b : searcher.searchBrandByDescription(query)) {
						brandList.add(new ProductForView(b.getId(), b.getName(), b.getDescription(), b.getWebsite()));
					}
					return brandList;*/
		ProductDbController pdbc = new ProductDbController(jdbcTemplate);
		List<ProductForView> result = new ArrayList<ProductForView>();
		ProductForView pfv = Prepare.prepareProductForView(jdbcTemplate, pdbc.getProductByName(query));
		if(pfv != null)
			result.add(pfv);
		return result;
	}
}
