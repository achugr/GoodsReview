package ru.goodsReview.frontend.model;

/**
 * Created by IntelliJ IDEA.
 * User: Artemij
 * Date: 18.10.11
 * Time: 23:02
 * To change this template use File | Settings | File Templates.
 */
public class ProductForView {
    private String name;
    private String description;

    public ProductForView(String name, String description) {
        this.name = name;
        this.description = description;
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
}
