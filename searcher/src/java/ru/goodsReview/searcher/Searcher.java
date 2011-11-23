package ru.goodsReview.searcher;
/*
 *  Date: 22.11.11
 *  Time: 08:41
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

import org.apache.lucene.queryParser.ParseException;
import org.jetbrains.annotations.NotNull;
import ru.goodsReview.core.model.Product;

import java.io.IOException;
import java.util.List;

public interface Searcher {
    public @NotNull List<Product> searchProductByName(@NotNull String query) throws ParseException, IOException;
}
