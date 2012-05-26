package ru.goodsreview.analyzer.util.dictionary;

import java.io.*;
import java.util.HashMap;

/**
 * Date: 14.05.12
 * Time: 00:10
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */
public class MapDictionary {
    private HashMap<String,Double> words;

//    public MapDictionary(HashMap<String,Double> words) {
//        this.words = words;
//    }

    public HashMap<String,Double> getDictionary() {
        return this.words;
    }

    public MapDictionary(String dictionaryFileName, String encoding) {
        this.words = new HashMap<String,Double>();

        try {
            FileInputStream fis1 = new FileInputStream(dictionaryFileName);
            InputStreamReader isr1 = new InputStreamReader(fis1, encoding);
            BufferedReader in = new BufferedReader(isr1);

            String s = in.readLine();
            while (s != null) {
                s = s.trim();
                if (s.length() != 0) {

                    if (s.indexOf(" ") != -1) {
                        int n = s.indexOf(" ");
                        String word = s.substring(0, n);
                        Double positivity = Double.parseDouble(s.substring(n+1));
                        words.put(word,positivity);
                    }

                }
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

    public void print() {
        for (String word : words.keySet()) {
            System.out.println(word+" "+words.get(word));
        }
    }

    /**
     * Checking if word is in dictionary
     *
     * @param word
     * @return true if word is here. false — otherwise
     */
    public boolean contains(String word) {
        return words.containsKey(word);
    }

    public static void main(String[] args) throws FileNotFoundException {
        MapDictionary dictionary = new MapDictionary("adjective_opinion_words.txt", "utf-8");
        dictionary.print();
    }
}
