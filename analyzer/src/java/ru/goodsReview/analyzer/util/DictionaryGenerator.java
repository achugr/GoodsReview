package ru.goodsReview.analyzer.util;

/*
    Date: 01.02.12
    Time: 16:35
    Author: 
        Yaroslav Skudarnov 
        SkudarnovYI@gmail.com
*/

import org.apache.log4j.Logger;
import ru.goodsReview.analyzer.wordAnalyzer.PorterStemmer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TimerTask;

public class DictionaryGenerator extends TimerTask {
    private static Scanner scanner;
    private static PrintWriter printWriter;
    private static String pureDictionaryFileName;
    private static String dirtyDictionaryFileName;

    private static final Logger log = Logger.getLogger(DictionaryGenerator.class);

    DictionaryGenerator(String dirtyDictionaryFileName, String pureDictionaryFileName) throws FileNotFoundException {
        scanner = new Scanner(new File(dirtyDictionaryFileName));
        printWriter = new PrintWriter(new File(pureDictionaryFileName));
    }

    DictionaryGenerator(){}

    public void generateOpinionWordDictionary() {
        PorterStemmer porterStemmer = new PorterStemmer();
        HashSet<String> hs = new HashSet<String>();
        String word;
        while (scanner.hasNext()) {
            word = porterStemmer.stem(scanner.next().toLowerCase());
            hs.add(word);
        }
        Object [] objects = hs.toArray();
        for(Object obj : objects){
            printWriter.println(obj.toString());
        }
        printWriter.close();
    }

    @Override
    public void run(){
        DictionaryGenerator opinionWordDictionary = null;
        try {
            opinionWordDictionary = new DictionaryGenerator("dirty_opinion_words.txt", "pure_opinion_words.txt");
        } catch (FileNotFoundException e) {
            log.error("something wrong with generate opinion word dictionary, probably file with dirty opinion words not exist", e);
            e.printStackTrace();
        }
        opinionWordDictionary.generateOpinionWordDictionary();
        log.info("generating opinion word dictionary ended");
    }
}
