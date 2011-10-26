package ru.goodsReview.core.model;

/**
 * User: Artemij
 * Date: 18.10.11
 * Time: 23:27
 */
public class Product {
    private long id;
    private long categoryId;
    private String name;
    private String description;
    private int popularity;

    public Product(long id, long categoryId, String name, String description, int popularity) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.popularity = popularity;
    }

    public Product(long categoryId, String name, String description, int popularity) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.popularity = popularity;
    }

    public Product(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
}