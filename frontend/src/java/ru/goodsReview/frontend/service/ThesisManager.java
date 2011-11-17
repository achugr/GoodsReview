package ru.goodsReview.frontend.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import ru.goodsReview.core.model.Thesis;
import ru.goodsReview.frontend.model.ThesisForView;
import ru.goodsReview.core.db.ControllerFactory;

import java.util.ArrayList;
import java.util.List;

/*
 *  Date: 31.10.11
 *  Time: 12:58
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

public class ThesisManager {
    private static final Logger log = org.apache.log4j.Logger.getLogger(ThesisManager.class);
    private ControllerFactory controllerFactory;
    public ThesisManager() {

    }

    @Required
    public void setControllerFactory(ControllerFactory controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    public List<ThesisForView> thesesByProduct(long id) {
        List<ThesisForView> result = new ArrayList<ThesisForView>();

        List<Thesis> theses = controllerFactory.getThesisController().getThesesByProductId(id);
        for (Thesis thesis : theses) {
            log.debug("Added thesis with id " + thesis.getId() + " and content " + thesis.getId());
            result.add(new ThesisForView(thesis));
        }
        return result;
    }


}
