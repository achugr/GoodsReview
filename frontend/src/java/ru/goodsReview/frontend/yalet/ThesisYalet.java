package ru.goodsReview.frontend.yalet;

import java.util.List;

import org.apache.log4j.Logger;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.xml.Xmler;
import net.sf.xfresh.core.Yalet;

import ru.goodsReview.frontend.model.ThesisForView;
import ru.goodsReview.frontend.service.ThesisManager;

/*
 *  Date: 31.10.11
 *  Time: 12:58
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

public class ThesisYalet implements Yalet {
	private static final Logger log = org.apache.log4j.Logger.getLogger(ThesisYalet.class);
	private ThesisManager thesisManager;

	public void setThesisManager(ThesisManager thesisManager) {
		this.thesisManager = thesisManager;
	}

	public void process(InternalRequest req, InternalResponse res) {
		long id = req.getIntParameter("id");
		log.debug("Request thesis for product id = " + id);
		try {
			List<ThesisForView> theses = thesisManager.thesesByProduct(id);
			if (theses.size() != 0) {
				res.add(theses);
			} else {
				Xmler.Tag ans = Xmler.tag("answer", "Тезисы не найдены. Id: " + id);
				res.add(ans);
			}
		} catch (Exception e) {
			log.error("Something happens wrong with id: " + id);
			Xmler.Tag ans = Xmler.tag("answer", "Все сломалось. Id: " + id);
			res.add(ans);
		}
	}
}
