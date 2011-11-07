/**
 * Date: 30.10.2011
 * Time: 23:38:57
 * Author: 
 *   Sergey Serebryakov 
 *   sergey.serebryakoff@gmail.com
 */

package ru.goodsReview.storage.controller;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.dao.DataAccessException;
import org.apache.log4j.Logger;
import ru.goodsReview.storage.mapper.SourceMapper;
import ru.goodsReview.core.model.Source;

import java.sql.Types;
import java.util.List;

/**
 * User: Sergey Serebryakov
 * Date: 30.10.2011
 * Time: 23:38:57
 */
public class SourceDbController {
    private SimpleJdbcTemplate simpleJdbcTemplate;
    private SourceMapper sourceMapper;
    private static final Logger log = Logger.getLogger(SourceDbController.class);

    public SourceDbController(SimpleJdbcTemplate simpleJdbcTemplate) {
        this.simpleJdbcTemplate = simpleJdbcTemplate;
        this.sourceMapper = new SourceMapper();
    }

    public long addSource(Source source) {
        try {
            simpleJdbcTemplate.getJdbcOperations().update("INSERT INTO source (name, main_page_url) VALUES(?,?)",
                    new Object[]{source.getName(), source.getMainPageUrl()},
                    new int[]{Types.VARCHAR, Types.VARCHAR});
            long lastId = simpleJdbcTemplate.getJdbcOperations().queryForLong("SELECT LAST_INSERT_ID()");
            return lastId;
        } catch (DataAccessException e) {
            log.error("Error while inserting source (probably not enough permissions): " + source);
        }
        return -1;
    }

    public Source getSourceById(long source_id) {
        List<Source> sources =
                simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM source WHERE id = ?",
                        new Object[]{source_id},
                        new int[]{Types.INTEGER},
                        sourceMapper);
        if (sources.size() > 0) {
            return sources.get(0);
        }
        return null;
    }
}
