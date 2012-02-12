package ru.goodsReview.analyzer.util.sentence;

public class MystemPartOfSpeech {

    private final String partOfSpeech;

    MystemPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String word() {
        return this.partOfSpeech;
    }
}
