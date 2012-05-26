package ru.goodsreview.frontend.model;

import org.jetbrains.annotations.NotNull;
import ru.goodsreview.core.model.Thesis;

/*
 *  Date: 31.10.11
 *  Time: 13:13
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

public class ThesisForView {

    private final long id;
    private final long reviewId;
    private final long thesisUniqueId;
    private final String content;
    private final int frequency;
    private final double positivity;
    private final double importance;

    public ThesisForView(@NotNull Thesis thesis) {
        this.id = thesis.getId();
        this.reviewId = thesis.getReviewId();
        this.content = thesis.getContent();
        this.positivity = thesis.getPositivity();
        this.importance = thesis.getImportance();
        this.thesisUniqueId = thesis.getThesisUniqueId();
        this.frequency = thesis.getFrequency();
    }

    public long getId() {
        return id;
    }

    public long getReviewId() {
        return reviewId;
    }

    public String getContent() {
        return content;
    }

    public double getPositivity() {
        return positivity;
    }

    public double getImportance() {
        return importance;
    }

    public long getThesisUniqueId() {
        return thesisUniqueId;
    }

    public long getFrequency() {
        return frequency;
    }
}
