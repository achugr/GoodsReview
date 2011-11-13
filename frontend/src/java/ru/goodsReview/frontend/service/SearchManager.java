package ru.goodsReview.frontend.service;

import ru.goodsReview.core.db.ControllerFactory;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.frontend.model.DetailedProductForView;
import ru.goodsReview.frontend.mapper.ProductMapper;
import ru.goodsReview.searcher.Searcher;

import java.util.ArrayList;
import java.util.List;

/*
 *  Date: 30.10.11
 *   Time: 14:12
 *   Author:
 *      Vanslov Evgeny
 *      vans239@gmail.com
 */

public class SearchManager {
    private ControllerFactory controllerFactory;
    private Searcher searcher;

    public SearchManager() {
    }

    public void setControllerFactory(ControllerFactory controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    public void setSearcher(Searcher searcher) {
        this.searcher = searcher;
    }

    public List<DetailedProductForView> searchByName(String query) throws Exception {
        //todo not working yet
        List<DetailedProductForView> products = new ArrayList<DetailedProductForView>();
        for (Product product : searcher.searchProductByName(query)) {
            DetailedProductForView pfv = ProductMapper.prepareDetailedProductForView(controllerFactory, product);
            products.add(pfv);
        }
        return products;
        /* ProductDbController pdbc = new ProductDbController(jdbcTemplate);
       List<DetailedProductForView> result = new ArrayList<DetailedProductForView>();
       DetailedProductForView pfv = ProductMapper.prepareDetailedProductForView(jdbcTemplate, pdbc.getProductByName(query));
       if (pfv != null) {
           result.add(pfv);
       }
       return result;*/
    }
}
