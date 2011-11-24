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
import ru.goodsReview.core.db.exception.StorageException;
import ru.goodsReview.core.model.ThesisUnique;
import ru.goodsReview.storage.mapper.ThesisUniqueMapper;

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
        if(getThesisUniqueByContent(thesisUnique.getContent())==null){
            try {
                simpleJdbcTemplate.getJdbcOperations().update(
                        "INSERT INTO thesis_unique (content, frequency, last_scan, positivity, importance) VALUES(?,?,?,?,?)",
                        new Object[]{thesisUnique.getContent(), thesisUnique.getFrequency(), thesisUnique.getLastScan(), thesisUnique
                                .getPositivity(), thesisUnique.getImportance()},
                        new int[]{Types.VARCHAR, Types.INTEGER, Types.DATE, Types.DOUBLE, Types.DOUBLE});
                long lastId = simpleJdbcTemplate.getJdbcOperations().queryForLong("SELECT LAST_INSERT_ID()");
                return lastId;
            } catch (DataAccessException e) {
                log.error("Error while inserting thesis_unique (probably not enough permissions): " + thesisUnique, e);
                throw new StorageException();
            }
        } else {
            updateThesisUniqueByContent(thesisUnique.getContent(), thesisUnique.getFrequency());
            //TODO must we fix this?
            return -1;
        }
    }

    public void updateThesisUniqueByContent(String content, int frequency) throws StorageException {

        ThesisUnique thesisUnique = getThesisUniqueByContent(content);
        frequency += thesisUnique.getFrequency();
        log.info("thesis = " + thesisUnique.getContent());
        try {
            simpleJdbcTemplate.getJdbcOperations().update(
                    "UPDATE thesis_unique SET frequency = ? WHERE content = ?",
                    new Object[]{frequency, content});
        } catch (DataAccessException e) {
            log.error("Error while updating thesis_unique (probably not enough permissions) with content : " +
                              content, e);
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
        //TODO is it good practice?
        if(theses.size()>0){
            return theses.get(0);
        } else {
            return null;
        }
    }

    public List<ThesisUnique> getAllThesesUnique() {
        List<ThesisUnique> products = simpleJdbcTemplate.getJdbcOperations()
                                                        .query("SELECT * FROM thesis_unique", thesisUniqueMapper);
        return products;
    }

}
