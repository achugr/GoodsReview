package algorithmTesting;

/**
 * Date: 05.02.12
 * Time: 21:18
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */
import java.util.ArrayList;

public class Product {
    String name;
    ArrayList<Review> reviews;

    public Product(String name, ArrayList<Review> reviews) {
        this.name = name;
        this.reviews = reviews;
    }

}