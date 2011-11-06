package ru.goodsReview.storage.controller;
/*
 *  Date: 07.11.11
 *   Time: 00:44
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.model.ThesisUnique;
import ru.goodsReview.storage.mapper.ThesisUniqueMapper;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ThesisUniqueDbController {
    private SimpleJdbcTemplate simpleJdbcTemplate;
    private ThesisUniqueMapper thesisUniqueMapper;

    public ThesisUniqueDbController(SimpleJdbcTemplate simpleJdbcTemplate) {
        this.simpleJdbcTemplate = simpleJdbcTemplate;
        this.thesisUniqueMapper = new ThesisUniqueMapper();
    }

    public long addThesisUnique(ThesisUnique thesisUnique) {
        try {
            simpleJdbcTemplate.getJdbcOperations().update("INSERT INTO thesis_unique (content, frequency, last_scan, positivity, importance) VALUES(?,?,?,?,?)",
                    new Object[]{thesisUnique.getContent(), thesisUnique.getFrequency(), thesisUnique.getLastScan(), thesisUnique.getPositivity(), thesisUnique.getImportance()},
                    new int[]{Types.VARCHAR, Types.INTEGER, Types.DATE, Types.DOUBLE, Types.DOUBLE});
            long lastId = simpleJdbcTemplate.getJdbcOperations().queryForLong("SELECT LAST_INSERT_ID()");
            return lastId;
        } catch (DataAccessException e) {
            // We don't have permissions to update the table.
            // TODO(serebryakov): Log the error.
            e.printStackTrace();
        }
        return -1;
    }

    public List<Long> addThesisUniqueList(List<ThesisUnique> thesisUniqueList) {
        List<Long> ids = new ArrayList<Long>();
        for (ThesisUnique thesisUnique : thesisUniqueList) {
            ids.add(addThesisUnique(thesisUnique));
        }
        return ids;
    }
}
