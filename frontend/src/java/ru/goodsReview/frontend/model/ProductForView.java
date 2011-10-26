package ru.goodsReview.frontend.model;

import ru.goodsReview.core.model.Product;

public class ProductForView {
	private String name;
	private String description;
	private long id;
	private String category;
	private long categoryId;
	private long popularity;

	public ProductForView(Product product) {
		name = product.getName();
		description = product.getDescription();
		id = product.getId();
		setCategoryId(product.getCategoryId());
		popularity = product.getPopularity();
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

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.category = category;
		category = getCategory(categoryId);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPopularity() {
		return popularity;
	}

	public void setPopularity(long popularity) {
		this.popularity = popularity;
	}

	public static String getCategory(long id){
		//TODO get category name from table
		return id + " ";
	}
}