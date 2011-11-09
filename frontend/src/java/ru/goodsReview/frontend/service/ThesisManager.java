package ru.goodsReview.frontend.service;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.model.Thesis;
import ru.goodsReview.frontend.model.ThesisForView;
import ru.goodsReview.storage.controller.ThesisDbController;

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
    private SimpleJdbcTemplate jdbcTemplate;

    public ThesisManager() {

    }

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ThesisForView> thesesByProduct(long id) throws Exception {
        List<ThesisForView> result = new ArrayList<ThesisForView>();

        ThesisDbController tdbc = new ThesisDbController(jdbcTemplate);
        List<Thesis> theses = tdbc.getThesesByProductId(id);
        for (Thesis thesis : theses) {
            log.debug("Added thesis with id " + thesis.getId() + " and content " + thesis.getId());
            result.add(new ThesisForView(thesis));
        }
        return result;
    }


}
