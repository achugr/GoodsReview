package ru.goodsreview.analyzer.wordAnalyzer;

import ru.goodsreview.analyzer.util.sentence.PartOfSpeech;

import java.io.UnsupportedEncodingException;

public interface WordAnalyzer {

    public PartOfSpeech partOfSpeech(String word) throws UnsupportedEncodingException;

//    TODO add methods, gets other useful features by word analyzer programs
}
