package ru.goodsReview.frontend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import ru.goodsReview.core.model.Product;
import ru.goodsReview.frontend.model.ProductForView;
import ru.goodsReview.storage.controller.ProductDbController;

public class SearchManager {
	private SimpleJdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	//private Searcher searcher;

	//public void setSearcher(Searcher searcher) {
	//	this.searcher = searcher;
	//}

	public List<ProductForView> getSearchResultByBrand(String query) throws Exception {

	/*		List<ProductForView> brandList = new ArrayList<ProductForView>();
			for (Brand b : searcher.searchBrandByDescription(query)) {
				brandList.add(new ProductForView(b.getId(), b.getName(), b.getDescription(), b.getWebsite()));
			}
			return brandList;*/
		ProductDbController pdbc = new ProductDbController(jdbcTemplate);
		List<ProductForView> result = new ArrayList<ProductForView>();
		result.add(new ProductForView(pdbc.getProductByName(query)));
		return result;
	}

}
