package ru.goodsreview.frontend.service;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Required;
import ru.goodsreview.core.model.Thesis;
import ru.goodsreview.frontend.model.ThesisForView;
import ru.goodsreview.core.db.ControllerFactory;

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
    public void setControllerFactory(@NotNull ControllerFactory controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    public @NotNull List<ThesisForView> thesesByProduct(long id) {
        List<ThesisForView> result = new ArrayList<ThesisForView>();

        List<Thesis> theses = controllerFactory.getThesisController().getThesesByProductId(id);
        for (Thesis thesis : theses) {
            log.debug("Added thesis with id " + thesis.getId() + " and content " + thesis.getId());
            result.add(new ThesisForView(thesis));
        }
        return result;
    }


}
