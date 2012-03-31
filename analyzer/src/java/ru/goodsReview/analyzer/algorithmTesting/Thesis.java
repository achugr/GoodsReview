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
    private String sentence;

    public Thesis(String thesis, String sentence) {
        this.sentence = sentence;
        this.thesis = thesis;
    }

    public Thesis(String thesis) {
        this.sentence = null;
        this.thesis = thesis;
    }

    public String getThesis(){
        return thesis;
    }

    public String getSentence(){
        return sentence;
    }

    public Object clone() {
        Thesis newObject = null;
        try {
            newObject = (Thesis) super.clone();
            newObject.thesis =   this.thesis;
            newObject.sentence =   this.sentence;

        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return newObject;
    }

}