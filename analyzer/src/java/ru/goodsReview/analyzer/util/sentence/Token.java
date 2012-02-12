package ru.goodsReview.analyzer.util.sentence;
/*
 *  Date: 11.02.12
 *   Time: 17:02
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

public class Token {
    private String token;
    private MystemPartOfSpeech mystemPartOfSpeech;
//  private LemmatizerPartOfSpeech lemmatizerPartOfSpeech;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public MystemPartOfSpeech getMystemPartOfSpeech() {
        return mystemPartOfSpeech;
    }

    public void setMystemPartOfSpeech(MystemPartOfSpeech mystemPartOfSpeech) {
        this.mystemPartOfSpeech = mystemPartOfSpeech;
    }
}
