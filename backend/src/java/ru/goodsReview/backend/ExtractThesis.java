package ru.goodsReview.backend;

/*
    Date: 28.11.11
    Time: 21:25
    Author: 
        Yaroslav Skudarnov 
        SkudarnovYI@gmail.com
*/

import ru.goodsReview.backend.wordAnalyzer.AdjectiveAnalyzer;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.core.model.Thesis;
import ru.goodsReview.core.utils.EditDistance;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ExtractThesis {

    private List<String> dictionaryWords;

    /**
     * Check if words are similar, using Levenshtein distance. Should work great, I suppose.
     * @param word1 — firstWord
     * @param word2 — secondWord
     * @return true if words are similar enough, false — otherwise
     */
    private boolean areSimilar(String word1, String word2) {

        double ed = EditDistance.editDist(word1,word2);

        int minLength = Math.min(word1.length(),word2.length());
        int maxLength = Math.max(word1.length(),word2.length());

        if ((ed/minLength <= 0.25) && ((ed/maxLength <= 0.35))) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Filling dictionary with words, huh.
     */
    public void fillDictionary() {
        dictionaryWords.add("клавиатура");
        dictionaryWords.add("клава");
        dictionaryWords.add("клавиши");
        dictionaryWords.add("монитор");
        dictionaryWords.add("моник");
        dictionaryWords.add("дисплей");
        dictionaryWords.add("экран");
        dictionaryWords.add("картинка");
        dictionaryWords.add("разрешение");
        dictionaryWords.add("процессор");
        dictionaryWords.add("проц");
        dictionaryWords.add("камень");
        dictionaryWords.add("ядро");
        dictionaryWords.add("RAM");
        dictionaryWords.add("оператива");
        dictionaryWords.add("память");
        dictionaryWords.add("оперативная память");
        dictionaryWords.add("жёсткий диск");
        dictionaryWords.add("хард");
        dictionaryWords.add("винт");
        dictionaryWords.add("объём");
        dictionaryWords.add("винчестер");
        dictionaryWords.add("HDD");
        dictionaryWords.add("батарея");
        dictionaryWords.add("аккумулятор");
        dictionaryWords.add("аккумуль");
        dictionaryWords.add("батарейка");
        dictionaryWords.add("время работы");
        dictionaryWords.add("тачпад");
        dictionaryWords.add("тач");
        dictionaryWords.add("трекпоинт");
        dictionaryWords.add("микрофон");
        dictionaryWords.add("скорость");
        dictionaryWords.add("производительность");
        dictionaryWords.add("быстродействие");
        dictionaryWords.add("динамик");
        dictionaryWords.add("акустика");
        dictionaryWords.add("колонки");
        dictionaryWords.add("звук");
        dictionaryWords.add("корпус");
        dictionaryWords.add("пластик");
        dictionaryWords.add("поверхность");
        dictionaryWords.add("видеокарта");
        dictionaryWords.add("видяха");
        dictionaryWords.add("видеокарточка");
        dictionaryWords.add("цветопередача");
        dictionaryWords.add("ноут");
        dictionaryWords.add("бук");
        dictionaryWords.add("ноутбук");
        dictionaryWords.add("нетбук");
        dictionaryWords.add("лаптоп");
        dictionaryWords.add("нетбук");
        dictionaryWords.add("машинка");
        dictionaryWords.add("модель");
        dictionaryWords.add("девайс");
        dictionaryWords.add("компьютер");
        dictionaryWords.add("отделка");
        dictionaryWords.add("биос");
        dictionaryWords.add("вес");
        dictionaryWords.add("масса");
        dictionaryWords.add("камера");
        dictionaryWords.add("вебка");
        dictionaryWords.add("вебкамера");
        dictionaryWords.add("операционка");
        dictionaryWords.add("ось");
        dictionaryWords.add("операционная система");
        dictionaryWords.add("ОС");
        dictionaryWords.add("система");
        dictionaryWords.add("OS");
        dictionaryWords.add("цвет");
        dictionaryWords.add("железо");
        dictionaryWords.add("конфигурация");
        dictionaryWords.add("кулер");
        dictionaryWords.add("вентилятор");
        dictionaryWords.add("дизайн");
        dictionaryWords.add("стиль");
        dictionaryWords.add("вид");
        dictionaryWords.add("матрица");
        dictionaryWords.add("матричка");
        dictionaryWords.add("поддержка");
        dictionaryWords.add("сервис");
    }

    /**
     * Checking if word is in dictionary
     * @param word
     * @return true if word is here. false — otherwise
     */
    public boolean isInDictionary(String word) {
        for (String variant : dictionaryWords) {
            if (areSimilar(word, variant)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Extract theses from review. Now uses just a simple rule "adj + noun" or "noun + adj". Probably, will get tougher.
     * @param rev Review with theses had to be extracted.
     * @return List of theses.
     */
    public List<Thesis> doExtraction(Review rev) {
        List<Thesis> result = new ArrayList<Thesis>();

        String content = rev.getContent();

        StringTokenizer st = new StringTokenizer(content," .,-—:;()\'\"\\«»");
        boolean prevWasAdj = false;
        String currToken;
        String prevToken = "123456789";

        while (st.hasMoreTokens()) {
            if (AdjectiveAnalyzer.isAdjective(currToken = st.nextToken())) {
                if (isInDictionary(prevToken)) {
                    Thesis temp = new Thesis(rev.getId(),0,prevToken + " " + currToken,0,0,0);
                    result.add(temp);
                }
                prevWasAdj = true;
            } else {
                if (prevWasAdj) {
                    if (isInDictionary(currToken)) {
                        Thesis temp = new Thesis(rev.getId(),0,currToken + " " + prevToken,0,0,0);
                    }
                }
                prevWasAdj = false;
            currToken = st.nextToken().trim();
            }
        }

        return result;

    }
}