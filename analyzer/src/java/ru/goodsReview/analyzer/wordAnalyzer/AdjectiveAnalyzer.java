package ru.goodsReview.analyzer.wordAnalyzer;

/*
    Date: 20.11.11
    Time: 22:46
    Author: 
        Yaroslav Skudarnov 
        SkudarnovYI@gmail.com
*/

public class AdjectiveAnalyzer {

    /**
     * Checks if word is an adjective.
     * @param word Word which is tested for being adjective.
     * @return True if word is an adjective, false — otherwise.
     */
    public static boolean isAdjective (String word) {
        word = word.trim();
        if (word.indexOf(" ") != -1) {
            throw new IllegalArgumentException("Word mustn't have a spaces.");
        } else if (word.length() < 3) {
            return false;
        } else {
            String end = word.substring(word.length() - 2, word.length());
            if ((end.equals("ой"))
                    || (end.equals("му")) || (end.equals("им"))
                    || (end.equals("ом")) || (end.equals("ий"))
                    || (end.equals("ем")) || (end.equals("ым"))
                    || (end.equals("ый")) || (end.equals("ей"))
                    || (end.equals("их")) || (end.equals("яя"))
                    || (end.equals("ая")) || (end.equals("ее"))
                    || (end.equals("ья")) || (end.equals("ое"))
                    || (end.equals("ье")) || (end.equals("ие"))) {
                return true;
            } else {
                return false;
            }
        }
    }
}
