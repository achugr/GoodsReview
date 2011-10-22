package test.goodsReview.miner;

import ru.goodsReview.core.model.CitilinkReview;
import ru.goodsReview.core.model.ListOfReviews;

/**
 * Created by IntelliJ IDEA.
 * User: Artemij
 * Date: 15.10.11
 * Time: 2:32
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String[] args) {
        ListOfReviews citilinkReviews;
        citilinkReviews = new ListOfReviews();
        //review initialization
        CitilinkReview citilinkReview = new CitilinkReview(1, "my product", "good", "bad", "i hate this laptop",5, 2);
        CitilinkReview citilinkReview1 = new CitilinkReview(1, "my product", "good", "bad", "i like this phone", 5, 2);
        //reviews list initialization
        citilinkReviews.addCitilinkReview(citilinkReview);
        citilinkReviews.addCitilinkReview(citilinkReview1);

        citilinkReviews.printCitilinkReviews();

        KGrams kGrams = new KGrams(citilinkReviews);
        kGrams.printKGramsTable();
        kGrams.comparecitilinkReviews();

        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer(citilinkReviews);
        frequencyAnalyzer.makeFrequencyDictionary();
        frequencyAnalyzer.printFrequencyDictionary();

    }
}
