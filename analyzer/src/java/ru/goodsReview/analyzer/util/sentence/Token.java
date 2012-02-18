package ru.goodsReview.analyzer.util.sentence;
/*
 *  Date: 11.02.12
 *   Time: 17:02
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

public class Token {
    private String content;
    private PartOfSpeech mystemPartOfSpeech;
//  private LemmatizerPartOfSpeech lemmatizerPartOfSpeech;

    public Token(String token) {
        this.content = token;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PartOfSpeech getMystemPartOfSpeech() {
        return mystemPartOfSpeech;
    }

    public void setMystemPartOfSpeech(PartOfSpeech mystemPartOfSpeech) {
        this.mystemPartOfSpeech = mystemPartOfSpeech;
    }
}
