/**
 * Date: 14.11.2011
 * Time: 5:56:44
 * Author: 
 *   Sergey Serebryakov 
 *   sergey.serebryakoff@gmail.com
 */

package ru.goodsReview.storage.controller;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.dao.DataAccessException;
import org.apache.log4j.Logger;
import ru.goodsReview.storage.mapper.QueryUniqueMapper;
import ru.goodsReview.storage.exception.StorageException;
import ru.goodsReview.core.model.QueryUnique;

import java.sql.Types;
import java.util.List;

/**
 * User: Sergey Serebryakov
 * Date: 14.11.2011
 * Time: 5:56:44
 */
public class QueryUniqueDbController {
    private SimpleJdbcTemplate simpleJdbcTemplate;
    private QueryUniqueMapper queryUniqueMapper;
    private static final Logger log = Logger.getLogger(ThesisDbController.class);

    public QueryUniqueDbController(SimpleJdbcTemplate simpleJdbcTemplate) {
        this.simpleJdbcTemplate = simpleJdbcTemplate;
        this.queryUniqueMapper = new QueryUniqueMapper();
    }

    public long addQueryUnique(QueryUnique query) throws StorageException {
        try {
            simpleJdbcTemplate.getJdbcOperations().update(
                    "INSERT INTO query_unique (id,  text, last_scan, frequency) VALUES(?,?,?,?)",
                    new Object[]{query.getId(), query.getText(), query.getLastScan(), query.getFrequency()},
                    new int[]{Types.INTEGER, Types.VARCHAR, Types.TIMESTAMP, Types.INTEGER});
            long lastId = simpleJdbcTemplate.getJdbcOperations().queryForLong("SELECT LAST_INSERT_ID()");
            return lastId;
        } catch (DataAccessException e) {
            log.error("Error while inserting query_unique (probably not enough permissions): " + query);
            throw new StorageException();
        }
    }

    public QueryUnique getQueryUniqueById(long id) {
        List<QueryUnique> queries = simpleJdbcTemplate.getJdbcOperations().query(
                "SELECT * FROM query_unique WHERE id = ?",
                new Object[]{id}, new int[]{Types.INTEGER},
                queryUniqueMapper);
        if (queries.size() > 0) {
            return queries.get(0);
        }
        return null;
    }
}
