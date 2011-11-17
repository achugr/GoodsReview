/**
 * Date: 26.10.2011
 * Time: 1:45:49
 * Author:
 *   Sergey Serebryakov
 *   sergey.serebryakoff@gmail.com
 */

package ru.goodsReview.storage.controller;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.db.exception.StorageException;
import ru.goodsReview.core.model.Category;
import ru.goodsReview.storage.mapper.CategoryMapper;
import ru.goodsReview.core.db.controller.CategoryController;

import java.sql.Types;
import java.util.List;

public class CategoryDbController implements CategoryController {
    private SimpleJdbcTemplate simpleJdbcTemplate;
    private CategoryMapper categoryMapper;
    private static final Logger log = Logger.getLogger(CategoryDbController.class);

    public CategoryDbController(SimpleJdbcTemplate simpleJdbcTemplate) {
        this.simpleJdbcTemplate = simpleJdbcTemplate;
        this.categoryMapper = new CategoryMapper();
    }

    public long addCategory(Category category) throws StorageException {
        try {
            simpleJdbcTemplate.getJdbcOperations().update(
                    "INSERT INTO category (name, description, parent_category_id) VALUES(?,?,?)",
                    new Object[]{category.getName(), category.getDescription(), category.getParentCategoryId()},
                    new int[]{Types.VARCHAR, Types.VARCHAR, Types.INTEGER});
            long lastId = simpleJdbcTemplate.getJdbcOperations().queryForLong("SELECT LAST_INSERT_ID()");
            return lastId;
        } catch (DataAccessException e) {
            log.error("Error while inserting category (probably not enough permissions): " + category);
            throw new StorageException();
        }
    }

    public List<Category> getAllCategories() {
        List<Category> categories = simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM category",
                categoryMapper);
        return categories;
    }

    public Category getCategoryById(long category_id) {
        List<Category> categories = simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM category WHERE id = ?",
                new Object[]{category_id},
                new int[]{Types.INTEGER},
                categoryMapper);
        if (categories.size() > 0) {
            return categories.get(0);
        }
        return null;
    }
}
