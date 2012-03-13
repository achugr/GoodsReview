package ru.goodsReview.core.db.controller;

/*
 *  Date: 13.11.11
 *  Time: 11:00
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

import ru.goodsReview.core.db.exception.StorageException;
import ru.goodsReview.core.model.Thesis;

import java.util.List;

public interface ThesisController {
    public long addThesis(Thesis thesis) throws StorageException;

    public List<Long> addThesisList(List<Thesis> thesisList) throws StorageException;

    public List<Thesis> getAllTheses();

    public void setThesisUniqueId(long thesisId, long thesisUniqueId);

    public Thesis getThesisById(long id);

    public List<Thesis> getThesesByReviewId(long review_id);

    public List<Thesis> getThesesByProductId(long product_id);

    public void updateImportance(long id, double importance);
}
