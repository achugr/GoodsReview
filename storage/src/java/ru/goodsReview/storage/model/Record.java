package ru.goodsReview.storage.model;

import ru.goodsReview.storage.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Artemij
 * Date: 17.10.11
 * Time: 16:24
 * To change this template use File | Settings | File Templates.
 */
public class Record {
    private final List<Cell> cells = new ArrayList<Cell>();

    public void addCell(final Cell cell) {
        cells.add(cell);
    }

    public void addCell(final String name, final Object value) {
        addCell(new Cell(name, value));
    }

    public List<Cell> getCells() {
        return cells;
    }
}
