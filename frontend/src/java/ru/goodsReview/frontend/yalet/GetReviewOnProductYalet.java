package ru.goodsReview.frontend.yalet;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;
import net.sf.xfresh.core.xml.Xmler;

/**
 * @author OlegPan
 */
public class GetReviewOnProductYalet implements Yalet {

    public void process(InternalRequest req, InternalResponse res)
    {
        String request = req.getParameter("request");
        System.out.print("hey!");
        if (request.isEmpty()) {
                  Xmler.Tag ans = Xmler.tag("response", "hello, world!");
                  res.add(ans);
                  System.out.print("FUCK YEAH\n");
                  return;
        }
    }
}
