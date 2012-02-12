package ru.goodsReview.analyzer.wordAnalyzer;
/*
 *  Date: 12.02.12
 *   Time: 20:51
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import org.apache.log4j.Logger;
import ru.goodsReview.analyzer.util.sentence.PartOfSpeech;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class MystemAnalyzer {

    private static final Logger log = Logger.getLogger(WordAnalyzer.class);

    private static final String CHARSET = "UTF8";
    private static final String ADJECTIVE = "A";
    private static final String NOUN = "S";

    private Process analyzer;
    private Scanner sc;
    private PrintStream ps;

    public MystemAnalyzer() throws IOException {
        try {
            analyzer = Runtime.getRuntime().exec("mystem -nig -e " + CHARSET);
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
     * Extract word
     * @param word
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public String wordCharacteristic(String word) throws UnsupportedEncodingException {
        int wl = word.length(); boolean b = true;
        for (int i = 0; i < wl; ++i) {
            if (!isRussianLetter(word.charAt(i))) {
                b = false;
                break;
            }
        }

        if (!b) {
            return null;
        }

        sc = new Scanner(analyzer.getInputStream(),CHARSET);
        ps = new PrintStream(analyzer.getOutputStream(),true,CHARSET);

        ps.println(word);
        String wordCharacteristic = sc.nextLine();

        int pos1,pos2;

        pos2 = (pos1 = (wordCharacteristic.indexOf('=') + 1));
        while (Character.isUpperCase(wordCharacteristic.charAt(pos2))) {
            pos2++;
        }
        return wordCharacteristic.substring(pos1, pos2);
    }
    /**
     * Checks if word is an adjective.
     * @param word Word which is tested for being adjective.
     * @return True if word is an adjective, false — otherwise.
     */
    public boolean isAdjective (String word) throws UnsupportedEncodingException {
        if(this.wordCharacteristic(word).equals(ADJECTIVE)){
            return true;
        }
        return false;
    }

    /**
     * Checks if word is an adjective.
     * @param word Word which is tested for being noun.
     * @return  True if word is an noun, false — otherwise.
     * @throws UnsupportedEncodingException
     */
    public boolean isNoun(String word) throws UnsupportedEncodingException {
        String wordCharacteristic = this.wordCharacteristic(word);
        if(wordCharacteristic != null && wordCharacteristic.equals(NOUN)){
            return true;
        }
        return false;
    }

    public PartOfSpeech partOfSpeech(String word) throws UnsupportedEncodingException {
        String wordCharacteristic = this.wordCharacteristic(word);
        if(wordCharacteristic.equals("A")){
            return PartOfSpeech.ADJECTIVE;
        } else if(wordCharacteristic.equals("S")){
            return PartOfSpeech.NOUN;
        }
        return PartOfSpeech.UNKNOWN;
    }

    public static void main(String [] args){
        try {
            WordAnalyzer wordAnalyzer = new WordAnalyzer();
            if(wordAnalyzer.isNoun("компьютер")){
                System.out.println("noun!");
            } else {
                System.out.println("not noun!");
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
