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

/**
 * Class for the analysis of words, using mystem program http://company.yandex.ru/technologies/mystem/
 */
public class MystemAnalyzer implements WordAnalyzer{

    private static final Logger log = Logger.getLogger(MystemAnalyzer.class);

    private static final String CHARSET = "UTF8";

    private Process analyzer;
    private Scanner sc;
    private PrintStream ps;

    public MystemAnalyzer() {
        try {
            analyzer = Runtime.getRuntime().exec("mystem -nig -e " + CHARSET);
        } catch (IOException e) {
            log.error("Caution! Analyzer wasn't created. Check if mystem is installed", e);
//            throw new IOException();
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

//        TODO fix this (split by !, but отличный! - returns ""
        if (!b) {
            return "";
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
        String wordCharact = wordCharacteristic.substring(pos1, pos2);
        if(wordCharact == null){
            return "";
        }
        return wordCharact;
    }

    /**
     * method for determine part of speech of word by means of Mystem
     * @param word which part of speech we want know
     * @return Part of speech
     * @throws UnsupportedEncodingException
     */
    public PartOfSpeech partOfSpeech(String word) throws UnsupportedEncodingException {
        switch (this.wordCharacteristic(word)) {
            case "A":
                return PartOfSpeech.ADJECTIVE;
            case "S":
                return PartOfSpeech.NOUN;
            case "ADV":
                return PartOfSpeech.ADVERB;
            case "V":
                return PartOfSpeech.VERB;
            case "PR":
                return PartOfSpeech.PREPOSITION;
            case "PART":
                return PartOfSpeech.PARTICLE;
            case "":
                return PartOfSpeech.UNKNOWN;
        }
//        throw new UnknownPartOfSpeechException();
        return PartOfSpeech.UNKNOWN;
    }

    public static void main(String [] args){
        try {
            MystemAnalyzer mystemAnalyzer = new MystemAnalyzer();
            System.out.println(mystemAnalyzer.partOfSpeech("отличный!"));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
