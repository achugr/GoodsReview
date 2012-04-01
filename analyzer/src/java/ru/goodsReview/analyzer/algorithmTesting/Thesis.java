package ru.goodsReview.analyzer.algorithmTesting;



/**
 * Date: 31.03.12
 * Time: 23:16
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */
public class Thesis implements Cloneable{
    private String thesis;
    private String part2;

    public Thesis(String thesis, String part2) {
        this.part2 = part2;
        this.thesis = thesis;
    }

    public Thesis(String thesis) {
        this.part2 = null;
        this.thesis = thesis;
    }

    public String getThesis(){
        return thesis;
    }

    public String getPart2(){
        return part2;
    }

    public Object clone() {
        Thesis newObject = null;
        try {
            newObject = (Thesis) super.clone();
            newObject.thesis =   this.thesis;
            newObject.part2 =   this.part2;

        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return newObject;
    }

}