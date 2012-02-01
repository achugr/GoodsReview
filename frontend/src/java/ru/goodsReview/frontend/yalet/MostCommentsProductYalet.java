package ru.goodsReview.frontend.yalet;
/*
 *  Date: 30.01.12
 *   Time: 00:24
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;
import net.sf.xfresh.core.xml.Xmler;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Required;
import ru.goodsReview.frontend.model.DetailedProductForView;
import ru.goodsReview.frontend.service.ProductManager;

import java.util.List;

public class MostCommentsProductYalet implements Yalet{
    private static final Logger log = org.apache.log4j.Logger.getLogger(MostCommentsProductYalet.class);
    private ProductManager productManager;

    @Required
    public void setProductManager(@NotNull ProductManager productManager) {
        this.productManager = productManager;
    }

    public void process(InternalRequest req, InternalResponse res) {
        try {
            List<DetailedProductForView> products = productManager.productsMostNumberOfComments();
            log.debug("Request popular products");
            if (products.size() != 0) {
                res.add(products);
            } else {
                log.debug("Nothing found");
                Xmler.Tag ans = Xmler.tag("answer", "Ничего не найдено.");
                res.add(ans);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Something happens wrong", e);
            Xmler.Tag ans = Xmler.tag("answer", "Все сломалось.");
            res.add(ans);
        }
    }
}
