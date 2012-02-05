package algorithmTesting;

/**
 * Created by IntelliJ IDEA.
 * User: IlyaMakeev
 * Date: 05.02.12
 * Time: 18:15
 * To change this template use File | Settings | File Templates.
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