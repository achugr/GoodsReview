package test.goodsReview.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;
/**
 * @author OlegPan
 */
public class SimpleRequestYalet implements Yalet {

    public void process(InternalRequest req, InternalResponse res)
    {
        String str = req.getParameter("id");
    }
}
