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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Dictionary {
    private List<String> words;

    public Dictionary(List<String> words) {
        this.words = words;
    }
    
    public Dictionary(String dictionaryFileName){
        this.words = new ArrayList<String>();
        try {
            Scanner scanner = new Scanner(new File(dictionaryFileName));
            while (scanner.hasNext()){
                words.add(scanner.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public List<String> getWords() {
        return words;
    }

    public void print(){
        for(String word : words){
            System.out.println(word);
        }
    }

    public static void main(String [] args){
        Dictionary dictionary = new Dictionary("pure_opinion_words.txt");
        dictionary.print();
    }
}
