package ru.goodsReview.analyzer.wordAnalyzer;

import java.util.Set;

public class AdjectiveAnalyzer {
    private TurglemClient client = new TurglemClient("./turglem-client");

    public boolean isAdjective(String word) {
        Set<Integer> result = client.analysePartOfSpeech(word);
        return result.contains(1) && result.size() == 1;
    }
}
