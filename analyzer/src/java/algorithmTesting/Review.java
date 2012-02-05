package algorithmTesting;

/**
 * Created by IntelliJ IDEA.
 * User: IlyaMakeev
 * Date: 05.02.12
 * Time: 18:15
 * To change this template use File | Settings | File Templates.
 */
import java.util.ArrayList;

public class Review implements Cloneable{
    String review;
    ArrayList<String> thesis;

    public Review(String review, ArrayList<String> thesis) {
        this.review = review;
        this.thesis = thesis;
    }

    public Object clone() {
        Review newObject = null;
        try {
            newObject = (Review) super.clone();
            newObject.review =   this.review;
            ArrayList<String> t = new ArrayList<String>();
            for (int i = 0; i < this.thesis.size(); i++) {
                t.add(this.thesis.get(i));
            }
            newObject.thesis =   t;

        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return newObject;
    }

}
