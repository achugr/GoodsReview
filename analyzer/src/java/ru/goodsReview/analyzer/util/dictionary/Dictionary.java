package ru.goodsReview.analyzer.util.dictionary;
/*
 *  Date: 08.02.12
 *   Time: 18:02
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Dictionary {
    private HashSet<String> words;

    public Dictionary(HashSet<String> words) {
        this.words = words;
    }
    
    public Dictionary(String dictionaryFileName){
        this.words = new HashSet<String>();
        try {
            Scanner scanner = new Scanner(new File(dictionaryFileName));
            while (scanner.hasNext()){
                words.add(scanner.next());
            }
        } catch (FileNotFoundException e) {
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

    public static void main(String [] args){
        Dictionary dictionary = new Dictionary("pure_opinion_words.txt");
        dictionary.print();
    }
}
