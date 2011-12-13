package ru.goodsReview.analyzer.wordAnalyzer;

/*
    Date: 20.11.11
    Time: 22:46
    Author: 
        Yaroslav Skudarnov 
        SkudarnovYI@gmail.com
*/

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class AdjectiveAnalyzer {

    private static final Logger log = Logger.getLogger(AdjectiveAnalyzer.class);

    static final String charset = "UTF8";

    private Process analyzer;
    private Scanner sc;
    private PrintStream ps;

    public AdjectiveAnalyzer() throws IOException {
        try {
            analyzer = Runtime.getRuntime().exec("mystem -nig -e " + charset);
        } catch (IOException e) {
            log.error("Caution! Analyzer wasn't created. Check if mystem is installed", e);
            throw new IOException();
        }

    }

    public void close() {
        analyzer.destroy();
    }

    /**
     * Checks if letter belongs to russian alphabet.
     * @param letter The letter itself.
     * @return True if letter is russian, false — otherwise.
     */
    private static boolean isRussianLetter (char letter) {
        if ((letter >= 0x0410) && (letter <= 0x044F)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if word is an adjective.
     * @param word Word which is tested for being adjective.
     * @return True if word is an adjective, false — otherwise.
     */
    public boolean isAdjective (String word) throws IOException {

        //word = word.trim();
        //if (word.indexOf(" ") != -1) {
        //throw new IllegalArgumentException("Word mustn't have a spaces.");
        //} else if (word.length() < 3) {
        //return false;
        //} else {
        //String end = word.substring(word.length() - 2, word.length());
        //if ((end.equals("ой"))
        //|| (end.equals("му")) || (end.equals("им"))
        //|| (end.equals("ом")) || (end.equals("ий"))
        //|| (end.equals("ем")) || (end.equals("ым"))
        //|| (end.equals("ый")) || (end.equals("ей"))
        //|| (end.equals("их")) || (end.equals("яя"))
        //|| (end.equals("ая")) || (end.equals("ее"))
        //|| (end.equals("ья")) || (end.equals("ое"))
        //|| (end.equals("ье")) || (end.equals("ие"))) {
        //return true;
        //} else {
        //return false;
        //}
        //}

        int wl = word.length(); boolean b = true;
        for (int i = 0; i < wl; ++i) {
            if (!isRussianLetter(word.charAt(i))) {
                b = false;
                break;
            }
        }

        if (!b) {
            return false;
        }

        sc = new Scanner(analyzer.getInputStream(),charset);
        ps = new PrintStream(analyzer.getOutputStream(),true,charset);

        ps.println(word);
        String wordCharacteristic = sc.nextLine();

        int pos1,pos2;

        pos2 = (pos1 = (wordCharacteristic.indexOf('=') + 1));
        while (Character.isUpperCase(wordCharacteristic.charAt(pos2))) {
            pos2++;
        }

        wordCharacteristic = wordCharacteristic.substring(pos1, pos2);
        return wordCharacteristic.equals("A");
    }
}
