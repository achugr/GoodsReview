package ru.goodsReview.analyzer;

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

public class OpinionWordDictionary extends TimerTask {
    private static Scanner scanner;
    private static PrintWriter printWriter;
    private static PorterStemmer porterStemmer = new PorterStemmer();
    private static final String PURE_DICTIONARY = "pure_dictionary.txt";
    private static final Logger log = Logger.getLogger(OpinionWordDictionary.class);

    public void generateOpinionWordDictionary(String dirtyDictionaryFilename) throws FileNotFoundException {
        scanner = new Scanner(new File(dirtyDictionaryFilename));
        printWriter = new PrintWriter(new File(PURE_DICTIONARY));

        HashSet<String> hs = new HashSet<String>();
        String word;
        while (scanner.hasNext()) {
            word = porterStemmer.stem(scanner.next());
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
        OpinionWordDictionary opinionWordDictionary = new OpinionWordDictionary();
        try {
            opinionWordDictionary.generateOpinionWordDictionary("dirty_opinion_words.txt");
            log.info("generating opinion word dictionary ended");
        } catch (FileNotFoundException e) {
            log.error("something wrong with generate opinion word dictionary, probably file with dirty opinion words not exist");
            e.printStackTrace();
        }
    }
}
