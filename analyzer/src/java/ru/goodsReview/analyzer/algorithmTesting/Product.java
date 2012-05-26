package ru.goodsreview.analyzer.algorithmTesting;

/**
 * Date: 05.02.12
 * Time: 21:18
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */
import java.util.ArrayList;

public class Product {
    private String id;
    private ArrayList<Review> reviews;

    public Product(String id, ArrayList<Review> reviews) {
        this.id = id;
        this.reviews = reviews;
    }

    public String getId(){
        return id;
    }

    public ArrayList<Review> getReviews(){
        return reviews;
    }

}