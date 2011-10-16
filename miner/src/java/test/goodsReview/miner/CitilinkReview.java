package test.goodsReview.miner;

/**
 * Created by IntelliJ IDEA.
 * User: Artemij
 * Date: 15.10.11
 * Time: 19:11
 * To change this template use File | Settings | File Templates.
 */
public class CitilinkReview {
    private int rate;
    private String description;
    private String goodAspect;
    private String badAspect;
    private String comment;
    private int helpfulnessYes;
    private int helpfulnessNo;

    public CitilinkReview(){}

    public CitilinkReview(int rate, String description, String goodAspect, String badAspect, String comment, int helpfulnessYes, int helpfulnessNo) {
        this.rate = rate;
        this.description = description;
        this.goodAspect = goodAspect;
        this.comment = comment;
        this.badAspect = badAspect;
        this.helpfulnessYes = helpfulnessYes;
        this.helpfulnessNo = helpfulnessNo;
    }

    public int getRate() {
        return rate;
    }

    public String getDescription() {
        return description;
    }

    public String getComment(){
        return comment;
    }

    public String getGoodAspect() {
        return goodAspect;
    }

    public String getBadAspect() {
        return badAspect;
    }

    public int getHelpfulnessYes() {
        return helpfulnessYes;
    }

    public int getHelpfulnessNo() {
        return helpfulnessNo;
    }
}