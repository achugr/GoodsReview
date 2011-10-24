package ru.goodsReview.frontend.yalet;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;
import net.sf.xfresh.core.xml.Xmler;
import ru.goodsReview.frontend.model.ProductForView;

/**
 * @author OlegPan
 */
public class GetReviewOnProductYalet implements Yalet {

	public void process(InternalRequest req, InternalResponse res) {
		String request = req.getParameter("request");
		if (request == null || request.isEmpty()) {
			ProductForView pfv = new ProductForView("dell", "other2");
			res.add(pfv);
			pfv = new ProductForView("apple", "))");
			res.add(pfv);
			return;
		}
		Xmler.Tag ans = Xmler.tag("response", "hello, world!");
		res.add(ans);

		return;
	}
}
