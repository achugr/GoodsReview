package ru.goodsReview.frontend.model;

import ru.goodsReview.core.model.Product;

/**
 * Created by IntelliJ IDEA.
 * User: Artemij
 * Date: 18.10.11
 * Time: 23:02
 * To change this template use File | Settings | File Templates.
 */

/*  //TODO
    Problems:
        Yalets in storage module
        Table category.. Field parent_category_id can't be empty.. why??
        Table Product.. product with null category...   popularity??
        int (10) int (11)
        all beans should be smaller!!
        Start all classes with starter class from xfresh
        one naming for all directories in diff modules
        where is exceptions in db controller
        download categories

        school239 password:sW75Gxm945dI


 */
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
