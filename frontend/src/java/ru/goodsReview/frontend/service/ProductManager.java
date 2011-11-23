package ru.goodsReview.frontend.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.jetbrains.annotations.*;

import ru.goodsReview.core.db.ControllerFactory;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.frontend.mapper.ProductMapper;
import ru.goodsReview.frontend.model.DetailedProductForView;
import java.util.ArrayList;
import java.util.List;
/*
 *  Date: 30.10.11
 *   Time: 14:12
 *   Author:
 *      Vanslov Evgeny
 *      vans239@gmail.com
 */

public class ProductManager {
    private static final Logger log = Logger.getLogger(ProductManager.class);
    private int popularCount;
    private ControllerFactory controllerFactory;
    private ProductMapper productMapper = new ProductMapper();


    @Required
    public void setControllerFactory(@NotNull ControllerFactory controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    @Required
    public void setPopularCount(int popularCount) {
        this.popularCount = popularCount;
    }

    public List<DetailedProductForView> productById(long id) throws Exception {
        List<DetailedProductForView> result = new ArrayList<DetailedProductForView>();
        DetailedProductForView pfv = productMapper.prepareDetailedProductForView(controllerFactory,
                                                                                 controllerFactory.getProductController().getProductById(
                                                                                         id));
        if (pfv != null) {
            log.debug("Added product " + pfv.getName());
            result.add(pfv);
        }
        return result;
    }

    public @NotNull List<DetailedProductForView> popularProducts() throws Exception {
        List<DetailedProductForView> result = new ArrayList<DetailedProductForView>();
        for (Product product : controllerFactory.getProductController().getPopularProducts(popularCount)) {
            log.debug("Product added:" + product.getName() + " Id:" + product.getId());
            result.add(productMapper.prepareDetailedProductForView(controllerFactory, product));
        }
        return result;
    }
}
