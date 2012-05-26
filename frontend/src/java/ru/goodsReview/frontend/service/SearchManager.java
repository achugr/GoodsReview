package ru.goodsreview.frontend.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Required;
import ru.goodsreview.core.db.ControllerFactory;
import ru.goodsreview.core.model.Product;
import ru.goodsreview.frontend.model.DetailedProductForView;
import ru.goodsreview.frontend.mapper.ProductMapper;
import ru.goodsreview.searcher.Searcher;

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
