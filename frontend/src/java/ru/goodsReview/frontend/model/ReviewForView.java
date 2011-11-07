package ru.goodsReview.frontend.model;

import ru.goodsReview.core.model.Review;

import java.util.Date;
import java.util.List;
/*
 *  Date: 31.10.11
 *  Time: 16:58
 *  Author: 
 *     Vanslov Evgeny 
 *     vans239@gmail.com
 */

public class ReviewForView {
	private final long id;
	private final long productId;
	private final String content;
	private final String author;
	private final Date date;
	private final String description;
	private final long sourceId;
	private final String sourceUrl;
	private final double positivity;
	private final double importance;
	private final int votesYes;
	private final int votesNo;
	private final List<ThesisForView> theses;

	public ReviewForView(Review review, List<ThesisForView> theses) {
		this.id = review.getId();
		this.productId = review.getProductId();
		this.content = review.getContent();
		this.author = review.getAuthor();
		this.date = review.getDate();
		this.description = review.getDescription();
		this.sourceId = review.getSourceId();
		this.sourceUrl = review.getSourceUrl();
		this.positivity = review.getPositivity();
		this.importance = review.getImportance();
		this.votesYes = review.getVotesYes();
		this.votesNo = review.getVotesNo();
		this.theses = theses;
	}


	public long getId() {
		return id;
	}

	public long getProductId() {
		return productId;
	}

	public String getContent() {
		return content;
	}

	public String getAuthor() {
		return author;
	}

	/*public String getDate() {
		return date.toString();
	}*/

	public String getDescription() {
		return description;
	}

	public long getSourceId() {
		return sourceId;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public double getPositivity() {
		return positivity;
	}

	public double getImportance() {
		return importance;
	}

	public int getVotesYes() {
		return votesYes;
	}

	public int getVotesNo() {
		return votesNo;
	}

	public List<ThesisForView> getTheses() {
		return theses;
	}

}
