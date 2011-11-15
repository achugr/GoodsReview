package ru.goodsReview.storage.controller;
/*
 *  Date: 07.11.11
 *   Time: 00:44
 *   Author:
 *      Artemij Chugreev
 *      artemij.chugreev@gmail.com
 */

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.db.controller.ThesisUniqueController;
import ru.goodsReview.core.model.ThesisUnique;
import ru.goodsReview.storage.mapper.ThesisUniqueMapper;
import ru.goodsReview.storage.exception.StorageException;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ThesisUniqueDbController implements ThesisUniqueController {
    private SimpleJdbcTemplate simpleJdbcTemplate;
    private ThesisUniqueMapper thesisUniqueMapper;
    private static final Logger log = Logger.getLogger(ThesisUniqueDbController.class);

    public ThesisUniqueDbController(SimpleJdbcTemplate simpleJdbcTemplate) {
        this.simpleJdbcTemplate = simpleJdbcTemplate;
        this.thesisUniqueMapper = new ThesisUniqueMapper();
    }

    public long addThesisUnique(ThesisUnique thesisUnique) throws StorageException {
        try {
            simpleJdbcTemplate.getJdbcOperations().update(
                    "INSERT INTO thesis_unique (content, frequency, last_scan, positivity, importance) VALUES(?,?,?,?,?)",
                    new Object[]{thesisUnique.getContent(), thesisUnique.getFrequency(), thesisUnique.getLastScan(), thesisUnique.getPositivity(), thesisUnique.getImportance()},
                    new int[]{Types.VARCHAR, Types.INTEGER, Types.DATE, Types.DOUBLE, Types.DOUBLE});
            long lastId = simpleJdbcTemplate.getJdbcOperations().queryForLong("SELECT LAST_INSERT_ID()");
            return lastId;
        } catch (DataAccessException e) {
            log.error("Error while inserting thesis_unique (probably not enough permissions): " + thesisUnique);
            throw new StorageException();
        }
    }

    public List<Long> addThesisUniqueList(List<ThesisUnique> thesisUniqueList) throws StorageException {
        List<Long> ids = new ArrayList<Long>();
        for (ThesisUnique thesisUnique : thesisUniqueList) {
            ids.add(addThesisUnique(thesisUnique));
        }
        return ids;
    }

    public ThesisUnique getThesisUniqueByContent(String content) {
        List<ThesisUnique> theses = simpleJdbcTemplate.getJdbcOperations().query(
                "SELECT * FROM thesis_unique WHERE content = ?", new Object[]{content}, new int[]{Types.VARCHAR},
                thesisUniqueMapper);
        return theses.get(0);
    }

    public List<ThesisUnique> getAllThesesUnique() {
        List<ThesisUnique> products = simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM thesis_unique", thesisUniqueMapper);
        return products;
    }

}
