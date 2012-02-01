package ru.goodsReview.analyzer;

/*
    Date: 01.02.12
    Time: 16:35
    Author: 
        Yaroslav Skudarnov 
        SkudarnovYI@gmail.com
*/

import java.util.HashSet;
import java.util.Scanner;

public class ExtractLexems {
    String file;
    
    ExtractLexems(String filename) {
        this.file = filename;
    }

    /**
     * Returns lexem of word.
     * @param word — word itself.
     * @return
     */
    String lexem(String word) {
        return word; //Just stub. Should be rewritten.
    }

    /**
     * Extracts unique lexems.
     */
    void doExtracting() {
        Scanner sc = new Scanner(file);

        HashSet<String> hs = new HashSet<String>();

        while (sc.hasNext()) {
            String word = sc.next();

            hs.add(lexem(word));
        }
    }
}
