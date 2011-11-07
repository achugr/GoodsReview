package ru.goodsReview.frontend.model;

import ru.goodsReview.core.model.Thesis;

/*
 *  Date: 31.10.11
 *  Time: 13:13
 *  Author: 
 *     Vanslov Evgeny 
 *     vans239@gmail.com
 */

public class ThesisForView {

	private long id;
	private long review_id;
	private String content;
	private double positivity;
	private double importance;
	private long votes_yes;
	private long votes_no;

	public ThesisForView(Thesis thesis) {
		this.id = thesis.getId();
		this.review_id = thesis.getReviewId();
		this.content = thesis.getContent();
		this.positivity = thesis.getPositivity();
		this.importance = thesis.getImportance();
	}

	public long getId() {
		return id;
	}

	public long getReviewId() {
		return review_id;
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

	public long getVotesYes() {
		return votes_yes;
	}

	public long getVotesNo() {
		return votes_no;
	}
}
