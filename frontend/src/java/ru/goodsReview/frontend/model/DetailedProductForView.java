package ru.goodsReview.frontend.model;

import java.util.ArrayList;
import java.util.List;

import ru.goodsReview.core.model.Category;
import ru.goodsReview.core.model.Product;

/*
 *  Date: 05.11.11
 *  Time: 21:12
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

public class DetailedProductForView extends ProductForView {
	private List<ThesisForView> theses;
	private List<ReviewForView> reviews;

	public DetailedProductForView(Product product, Category category, List<ThesisForView> theses, List<ReviewForView> reviews) throws Exception {
		super(product, category);
		this.theses = theses;
		this.reviews = reviews;
	}

	public List<ThesisForView> getTheses() {
		return theses;
	}

	public List<ReviewForView> getReviews() {
		return reviews;
	}
}