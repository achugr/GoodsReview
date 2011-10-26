/**
 * Date: 26.10.2011
 * Time: 1:45:49
 * Author: 
 *   Sergey Serebryakov 
 *   sergey.serebryakoff@gmail.com
 */

package ru.goodsReview.storage.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.model.Category;
import ru.goodsReview.storage.mapper.CategoryMapper;

import java.sql.Types;
import java.util.List;

public class CategoryDbController {
    private SimpleJdbcTemplate simpleJdbcTemplate;
    private CategoryMapper categoryMapper;

    public CategoryDbController(SimpleJdbcTemplate simpleJdbcTemplate) {
        this.simpleJdbcTemplate = simpleJdbcTemplate;
        this.categoryMapper = new CategoryMapper();
    }

    public void addCategory(Category category) {
        try {
            simpleJdbcTemplate.getJdbcOperations().update("INSERT INTO category (name, description, parent_category_id) VALUES(?,?,?)",
                    new Object[]{category.getName(), category.getDescription(), category.getParentCategoryId()},
                    new int[]{Types.VARCHAR, Types.VARCHAR, Types.INTEGER});
        } catch (DataAccessException e) {
            // We don't have permissions to update the table.
            // TODO(serebryakov): Log the error.
        }
    }

    public List<Category> getAllCategories() {
        List<Category> categories =
                simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM category", categoryMapper);
        return categories;
    }

    public Category getCategoryById(long category_id) {
        List<Category> categories =
                simpleJdbcTemplate.getJdbcOperations().query("SELECT * FROM category WHERE id = ?",
                        new Object[]{category_id},
                        new int[]{Types.INTEGER},
                        categoryMapper);
        return categories.get(0);
    }
}