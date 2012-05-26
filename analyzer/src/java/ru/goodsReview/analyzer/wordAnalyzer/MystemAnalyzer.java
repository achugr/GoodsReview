package ru.goodsreview.analyzer.wordAnalyzer;
/*
 *  Date: 12.02.12
 *   Time: 20:51
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import org.apache.log4j.Logger;
import ru.goodsreview.analyzer.util.sentence.PartOfSpeech;
import ru.goodsreview.core.utils.OSValidator;

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
            String command="";
            if(OSValidator.isUnix() || OSValidator.isMac()){
                command = "./";
            }
            analyzer = Runtime.getRuntime().exec(command + "mystem -nig -e " + CHARSET);
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

    public String[] wordCharacteristic1(String word) throws UnsupportedEncodingException {
        int wl = word.length(); boolean b = true;
        for (int i = 0; i < wl; ++i) {
            if (!isRussianLetter(word.charAt(i))) {
                b = false;
                break;
            }
        }
        String [] a = {"unk","unk","unk"};
//        TODO fix this (split by !, but отличный! - returns ""
        if (!b) {
            return a;
        }

        sc = new Scanner(analyzer.getInputStream(),CHARSET);
        ps = new PrintStream(analyzer.getOutputStream(),true,CHARSET);

        ps.println(word);
        String wordCharacteristic = sc.nextLine();

         /**/
        if(!((wordCharacteristic.contains("жен")&&wordCharacteristic.contains("муж"))||
        (wordCharacteristic.contains("жен")&&wordCharacteristic.contains("сред"))||
                (wordCharacteristic.contains("муж")&&wordCharacteristic.contains("сред")))){            
        
            if(wordCharacteristic.contains("жен")) {
                a[0] =  "жен";
            }
            if(wordCharacteristic.contains("муж")) {
                a[0] = "муж";
            }
            if(wordCharacteristic.contains("сред")) {
                a[0] = "сред";
            }
        }

        if(!((wordCharacteristic.contains("ед")&&wordCharacteristic.contains("мн")))){
            if(wordCharacteristic.contains("ед")) {
                a[1] =  "ед";
            }
            if(wordCharacteristic.contains("мн")) {
                a[1] = "мн";
            }
        }

        String[] cases = {"им", 
                        "род",	
                        "дат",	
                        "вин",	
                        "твор",
                        "пр",	
                        "парт",	
                        "местн",	
                        "зват"
                       };
        
        boolean t1 = false;
        for (int i = 0;i<cases.length;i++){
            for (int j = 0;j<i;j++){
                     if(wordCharacteristic.contains(cases[i])&&wordCharacteristic.contains(cases[j])){
                         if(i!=3&&j!=0){
                             t1 = true;
                             break;
                         }
                 }
            } 
        }

        if(!t1){
            for (int i = 0;i<cases.length;i++){
                if(wordCharacteristic.contains(cases[i])) {
                    a[2] = cases[i];
                    break;
                }
            }
        }


        return a;
    }

    public String normalizer(String word) throws UnsupportedEncodingException {
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
        String norm = wordCharacteristic.substring(wordCharacteristic.indexOf("{")+1,wordCharacteristic.indexOf("="));
        //System.out.println(word + " --> " + norm);
        return norm;
    }


    /**
     * method for determine part of speech of word by means of Mystem
     * @param word which part of speech we want know
     * @return Part of speech
     * @throws UnsupportedEncodingException
     */
    public PartOfSpeech partOfSpeech(String word) throws UnsupportedEncodingException {
        if(this.wordCharacteristic(word).equals("A")){
            return PartOfSpeech.ADJECTIVE;
        }
        if(this.wordCharacteristic(word).equals("S")){
            return PartOfSpeech.NOUN;
        }
        if(this.wordCharacteristic(word).equals("ADV")){
            return PartOfSpeech.ADVERB;
        }
        if(this.wordCharacteristic(word).equals("V")){
            return PartOfSpeech.VERB;
        }
        if(this.wordCharacteristic(word).equals("PR")){
            return PartOfSpeech.PREPOSITION;
        }
        if(this.wordCharacteristic(word).equals("PART")){
            return PartOfSpeech.PARTICLE;
        }
        if(this.wordCharacteristic(word).equals("")){
            return PartOfSpeech.UNKNOWN;
        }

        return PartOfSpeech.UNKNOWN;
    }

    public static void main(String [] args){
        try {
            MystemAnalyzer mystemAnalyzer = new MystemAnalyzer();
            System.out.println(mystemAnalyzer.normalizer("телефоном"));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
