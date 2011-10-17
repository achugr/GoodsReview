package ru.goodsReview.storage;

/**
 * Created by IntelliJ IDEA.
 * User: Artemij
 * Date: 17.10.11
 * Time: 16:20
 * To change this template use File | Settings | File Templates.
 */
public class Cell {
    private String name;
    private Object value;

    public Cell(final String name, final Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
