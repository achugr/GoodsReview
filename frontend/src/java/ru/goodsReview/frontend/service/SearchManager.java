package ru.goodsReview.frontend.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Required;
import ru.goodsReview.core.db.ControllerFactory;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.frontend.model.DetailedProductForView;
import ru.goodsReview.frontend.mapper.ProductMapper;
import ru.goodsReview.searcher.Searcher;
import ru.goodsReview.searcher.SimpleSearcher;

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
    private ProductMapper productMapper = new ProductMapper();
    public SearchManager() {
    }

    @Required
    public void setControllerFactory(@NotNull ControllerFactory controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    @Required
    public void setSearcher(Searcher searcher) {
        this.searcher = searcher;
    }

    public @NotNull List<DetailedProductForView> searchByName(String query) throws Exception {
        List<DetailedProductForView> products = new ArrayList<DetailedProductForView>();
        for (Product product : searcher.searchProductByName(query)) {
            DetailedProductForView pfv = productMapper.prepareDetailedProductForView(controllerFactory, product);
            products.add(pfv);
        }
        return products;
    }
}
