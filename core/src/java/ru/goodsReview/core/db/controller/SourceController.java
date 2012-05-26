package ru.goodsreview.core.db.controller;

/*
 *  Date: 13.11.11
 *  Time: 10:59
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

import ru.goodsreview.core.db.exception.StorageException;
import ru.goodsreview.core.model.Source;

public interface SourceController {
    public long addSource(Source source) throws StorageException;
    public Source getSourceById(long source_id);
}
