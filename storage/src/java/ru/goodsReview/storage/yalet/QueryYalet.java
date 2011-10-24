package ru.goodsReview.storage.yalet;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import ru.goodsReview.storage.mappers.RecordMapper;
import net.sf.xfresh.db.*;
import ru.goodsReview.storage.mappers.*;

/**
 * Created by IntelliJ IDEA.
 * User: Artemij
 * Date: 17.10.11
 * Time: 17:40
 * To change this template use File | Settings | File Templates.
 */
public class QueryYalet extends net.sf.xfresh.db.AbstractDbYalet {
    private String query;

    public void setQuery(final String query) {
        this.query = query;
    }

    @Override
    public void process(final InternalRequest req, final InternalResponse res) {
        res.add(jdbcTemplate.query(query, new RecordMapper()));
    }
}