package ru.goodsReview.analyzer.util.dictionary;

/*
    Date: 01.02.12
    Time: 16:35
    Author: 
        Yaroslav Skudarnov 
        SkudarnovYI@gmail.com
*/

import org.apache.log4j.Logger;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import ru.goodsReview.analyzer.util.FrequencyDictionary;
import ru.goodsReview.analyzer.wordAnalyzer.PorterStemmer;
import ru.goodsReview.core.utils.HashMapUtil;

import javax.xml.xpath.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

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

    private static boolean hasWordBadSymbols(String word){
        char [] wordCharacters = word.toCharArray();
        for(int i=0; i<wordCharacters.length; i++){
            if(!isRussianLetter(wordCharacters[i])){
                return false;
            }
        }
        return true;
    }

    private static void frequencyDictionaryFromNormalized() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("normalized_words.txt"));
        HashMap<String, Integer> frequencyDictionary = new HashMap<>();
        String token = "";
        while (scanner.hasNext()) {
            token = scanner.next();
            if (!token.equals("")) {
                if(hasWordBadSymbols(token)){
                    FrequencyDictionary.addWord(frequencyDictionary, token);
                }
            }
        }
        frequencyDictionary = (HashMap<String, Integer>) HashMapUtil.sortByValue(frequencyDictionary);

        PrintWriter printWriter = new PrintWriter(new File("dictionaryFromNormalized.txt"));
        for (String str : frequencyDictionary.keySet()) {
            printWriter.println(str + " " + frequencyDictionary.get(str));
        }
        printWriter.close();

    }

    private static void extractWordsFromDataset() throws XPathExpressionException, FileNotFoundException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        File file = new File("cameras.xml");
        {
            XPathExpression xPathExpression = xPath.compile("//value[@columnNumber>'4']/text()");
            InputSource inputSource = new InputSource(new FileReader(file));
            Object object = xPathExpression.evaluate(inputSource, XPathConstants.NODESET);
            NodeList nodeList = (NodeList) object;
            HashSet<String> hashSet = new HashSet<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                StringTokenizer stringTokenizer = new StringTokenizer(nodeList.item(i).getNodeValue(), " .,-—:;!()+\'\"\\«»");
                while (stringTokenizer.hasMoreTokens()) {
                    String token = stringTokenizer.nextToken();
                    hashSet.add(token);
                }
            }
            PrintWriter printWriter = new PrintWriter(new File("source_dictionary.txt"));
            for(String str : hashSet){
                printWriter.println(str);
            }

            printWriter.close();
        }
    }

    public static void main(String[] args) {
        //          frequencyDictionary();
        try {
            frequencyDictionaryFromNormalized();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
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
