package ru.goodsReview.analyzer.util.dictionary;
/*
 *  Date: 08.02.12
 *   Time: 18:02
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import java.io.*;
import java.util.HashSet;

public class Dictionary {
    private HashSet<String> words;

    public Dictionary(HashSet<String> words) {
        this.words = words;
    }

    public Dictionary(String dictionaryFileName){
        this.words = new HashSet<String>();

        try {
            FileInputStream fis1 = new FileInputStream(dictionaryFileName);
            InputStreamReader isr1 = new InputStreamReader(fis1, "windows-1251");
            BufferedReader in = new BufferedReader(isr1);

            String s = in.readLine();
            while (s!=null){
                if(s.charAt(0)=='﻿') {
                    s = s.substring(1);
                }
                words.add(s);
                s = in.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void print(){
        for(String word : words){
            System.out.println(word);
        }
    }

    /**
     * Checking if word is in dictionary
     *
     * @param word
     * @return true if word is here. false — otherwise
     */
    public boolean contains(String word){
        return words.contains(word);
    }

    public static void main(String [] args) throws FileNotFoundException {
        Dictionary dictionary = new Dictionary("pure_opinion_words.txt");
        dictionary.print();
    }
}
