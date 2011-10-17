package ru.goodsReview.storage.controller;

import net.sf.xfresh.db.Record;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.storage.mappers.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Artemij
 * Date: 17.10.11
 * Time: 19:04
 * To change this template use File | Settings | File Templates.
 */
public class ProductDbController {
    private SimpleJdbcTemplate simpleJdbcTemplate;
    private RecordMapper recordMapper;

    public ProductDbController(SimpleJdbcTemplate simpleJdbcTemplate){
        this.simpleJdbcTemplate = simpleJdbcTemplate;
        this.recordMapper = new RecordMapper();

    }

    public Record getProductById(long product_id){
        List<Record> record = simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM product WHERE id = " + Long.toString(product_id), recordMapper);
        return record.get(0);
    }

    public Record getProductByName(String product_name){
        List<Record>  record = simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM product WHERE name = ?", new Object[]{product_name}, recordMapper);
        return record.get(0);
    }

    public List<Record> getProductListByCategory(long category_id){
        List<Record> record = simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM product WHERE category_id = " + Long.toString(category_id), recordMapper);
        return record;
    }
}