/**
 * Date: 30.10.2011
 * Time: 16:52:52
 * Author: 
 *   Sergey Serebryakov 
 *   sergey.serebryakoff@gmail.com
 */

package ru.goodsReview.storage.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.model.Thesis;
import ru.goodsReview.storage.mapper.ThesisMapper;

import java.sql.Types;
import java.util.List;

/**
 * User: Sergey Serebryakov
 * Date: 30.10.2011
 * Time: 16:52:52
 */
public class ThesisDbController {
    private SimpleJdbcTemplate simpleJdbcTemplate;
    private ThesisMapper thesisMapper;

    public ThesisDbController(SimpleJdbcTemplate simpleJdbcTemplate) {
        this.simpleJdbcTemplate = simpleJdbcTemplate;
        this.thesisMapper = new ThesisMapper();
    }

    public void addThesis(Thesis thesis) {
        try {
            simpleJdbcTemplate.getJdbcOperations().update("INSERT INTO thesis (review_id, content, positivity, importance, votes_yes, votes_no) VALUES(?,?,?,?,?,?)",
                    new Object[]{thesis.getReview_id(), thesis.getContent(), thesis.getPositivity(), thesis.getImportance(), thesis.getVotes_yes(), thesis.getVotes_no()},
                    new int[]{Types.INTEGER, Types.VARCHAR, Types.DOUBLE, Types.DOUBLE, Types.INTEGER, Types.INTEGER});
        } catch (DataAccessException e) {
            // We don't have permissions to update the table.
            // TODO(serebryakov): Log the error.
            e.printStackTrace();
        }
    }

    public List<Thesis> getAllTheses() {
        List<Thesis> theses =
                simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM thesis", thesisMapper);
        return theses;
    }

    public Thesis getThesisById(long id) {
        List<Thesis> theses =
                simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM thesis WHERE id = ?",
                        new Object[]{id},
                        new int[]{Types.INTEGER},
                        thesisMapper);
        return theses.get(0);
    }
}
