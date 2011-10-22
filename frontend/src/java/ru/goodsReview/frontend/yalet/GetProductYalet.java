package ru.goodsReview.frontend.yalet;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.xml.Xmler;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.frontend.service.GetProductInfo;
import ru.goodsReview.storage.yalet.AbstractDbYalet;

/**
 * Created by IntelliJ IDEA.
 * User: Artemij
 * Date: 19.10.11
 * Time: 3:35
 * To change this template use File | Settings | File Templates.
 */
public class GetProductYalet extends AbstractDbYalet {
    private GetProductInfo getProductInfo;

    public void process(InternalRequest req, InternalResponse res){
        String request = req.getParameter("request");
        if (request.isEmpty()) {
                  Xmler.Tag ans = Xmler.tag("response", "request id empty");
                  res.add(ans);
                  return;
        }
        Product product = getProductInfo.getProductInfo(simpleJdbcTemplate, request);
        res.add(product);
    }
}
