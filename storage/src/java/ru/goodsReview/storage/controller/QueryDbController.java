/**
 * Date: 14.11.2011
 * Time: 5:55:16
 * Author: 
 *   Sergey Serebryakov 
 *   sergey.serebryakoff@gmail.com
 */

package ru.goodsReview.storage.controller;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.dao.DataAccessException;
import org.apache.log4j.Logger;
import ru.goodsReview.storage.mapper.QueryMapper;
import ru.goodsReview.storage.exception.StorageException;
import ru.goodsReview.core.model.Query;

import java.sql.Types;
import java.util.List;

/**
 * User: Sergey Serebryakov
 * Date: 14.11.2011
 * Time: 5:55:16
 */
public class QueryDbController {
    private SimpleJdbcTemplate simpleJdbcTemplate;
    private QueryMapper queryMapper;
    private static final Logger log = Logger.getLogger(ThesisDbController.class);

    public QueryDbController(SimpleJdbcTemplate simpleJdbcTemplate) {
        this.simpleJdbcTemplate = simpleJdbcTemplate;
        this.queryMapper = new QueryMapper();
    }

    public long addQueryUnique(Query query) throws StorageException {
        try {
            simpleJdbcTemplate.getJdbcOperations().update(
                    "INSERT INTO query (id,  query_unique_id, text, date) VALUES(?,?,?,?)",
                    new Object[]{query.getId(), query.getQueryUniqueId(), query.getText(), query.getDate()},
                    new int[]{Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.TIMESTAMP});
            long lastId = simpleJdbcTemplate.getJdbcOperations().queryForLong("SELECT LAST_INSERT_ID()");
            return lastId;
        } catch (DataAccessException e) {
            log.error("Error while inserting query (probably not enough permissions): " + query);
            throw new StorageException();
        }
    }

    public Query getQueryById(long id) {
        List<Query> queries = simpleJdbcTemplate.getJdbcOperations().query(
                "SELECT * FROM query WHERE id = ?",
                new Object[]{id}, new int[]{Types.INTEGER},
                queryMapper);
        if (queries.size() > 0) {
            return queries.get(0);
        }
        return null;
    }

    public List<Query> getQueriesByQueryUniqueId(long id) {
        List<Query> queries = simpleJdbcTemplate.getJdbcOperations().query(
                "SELECT * FROM query WHERE query_unique_id = ?",
                new Object[]{id}, new int[]{Types.INTEGER},
                queryMapper);
        return queries;
    }
}
