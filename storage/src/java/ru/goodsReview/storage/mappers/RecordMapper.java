package ru.goodsReview.storage.mappers;

import net.sf.xfresh.db.Record;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Artemij
 * Date: 17.10.11
 * Time: 17:55
 * To change this template use File | Settings | File Templates.
 */
public class RecordMapper implements ParameterizedRowMapper<Record> {

    public Record mapRow(final ResultSet resultSet, final int rowNumber) throws SQLException {
        final Record record = new Record();
        final ResultSetMetaData metaData = resultSet.getMetaData();
        final int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            record.addCell(
                    metaData.getColumnName(i),
                    resultSet.getObject(i)
            );
        }
        return record;
    }
}
