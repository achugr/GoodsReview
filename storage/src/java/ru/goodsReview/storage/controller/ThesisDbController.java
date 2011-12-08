/**
 * Date: 30.10.2011
 * Time: 16:52:52
 * Author:
 *   Sergey Serebryakov
 *   sergey.serebryakoff@gmail.com
 */

package ru.goodsReview.storage.controller;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.db.controller.ThesisController;
import ru.goodsReview.core.db.exception.StorageException;
import ru.goodsReview.core.model.Thesis;
import ru.goodsReview.storage.mapper.ThesisMapper;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Sergey Serebryakov
 * Date: 30.10.2011
 * Time: 16:52:52
 */
public class ThesisDbController implements ThesisController {
    private SimpleJdbcTemplate simpleJdbcTemplate;
    private ThesisMapper thesisMapper;
    private static final Logger log = Logger.getLogger(ThesisDbController.class);

    public ThesisDbController(SimpleJdbcTemplate simpleJdbcTemplate) {
        this.simpleJdbcTemplate = simpleJdbcTemplate;
        this.thesisMapper = new ThesisMapper();
    }

    public long addThesis(Thesis thesis) throws StorageException {
        try {
            simpleJdbcTemplate.getJdbcOperations().update(
                    "INSERT INTO thesis (review_id,  thesis_unique_id, content, frequency, positivity, importance) VALUES(?,?,?,?,?,?)",
                   /* new PreparedStatementSetter() {
                        public void setValues(final PreparedStatement ps) throws SQLException {
                            ps.setInt();
                            ps.setObject(1, );

                        }
                    })   */

//                    TODO Types.OTHER
                    new Object[]{thesis.getReviewId(), thesis.getThesisUniqueId(), thesis.getContent(), thesis.getFrequency(), thesis.getPositivity(), thesis.getImportance()},
                    new int[]{Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.DOUBLE, Types.DOUBLE});
            long lastId = simpleJdbcTemplate.getJdbcOperations().queryForLong("SELECT LAST_INSERT_ID()");
            return lastId;
        } catch (DataAccessException e) {
            log.error("Error while inserting thesis (probably not enough permissions): " + thesis, e);
            throw new StorageException();
        }
    }

    public List<Long> addThesisList(List<Thesis> thesisList) throws StorageException {
        List<Long> ids = new ArrayList<Long>();
        for (Thesis thesis : thesisList) {
            ids.add(addThesis(thesis));
        }
        return ids;
    }

    public List<Thesis> getAllTheses() {
        List<Thesis> theses = simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM thesis", thesisMapper);
        return theses;
    }

    public void setThesisUniqueId(long thesisId, long thesisUniqueId) {
        simpleJdbcTemplate.getJdbcOperations().update("UPDATE thesis SET thesis_unique_id = ? where id = ?",
                                                      new Object[]{thesisUniqueId, thesisId});
    }

    public Thesis getThesisById(long id) {
        List<Thesis> theses = simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM thesis WHERE id = ?",
                                                                           new Object[]{id}, new int[]{Types.INTEGER},
                                                                           thesisMapper);
        if (theses.size() > 0) {
            return theses.get(0);
        }
        return null;
    }

    public List<Thesis> getThesesByReviewId(long review_id) {
        List<Thesis> theses = simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM thesis WHERE review_id = ?",
                                                                           new Object[]{review_id},
                                                                           new int[]{Types.INTEGER}, thesisMapper);
        return theses;
    }

    public List<Thesis> getThesesByProductId(long product_id) {
        List<Thesis> theses = simpleJdbcTemplate.getJdbcOperations().query(
                "SELECT * FROM (thesis JOIN review ON thesis.review_id = review.id) WHERE product_id = ?",
                new Object[]{product_id}, new int[]{Types.INTEGER}, thesisMapper);
        return theses;
    }
}
