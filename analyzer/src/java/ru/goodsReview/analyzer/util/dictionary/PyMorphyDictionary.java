package ru.goodsreview.analyzer.util.dictionary;

/**
 * Date: 17.04.12
 * Time: 16:16
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */

import java.io.*;
import java.util.HashMap;



public class PyMorphyDictionary {
    private HashMap words;

    public PyMorphyDictionary(HashMap words) {
        this.words = words;
    }

    public HashMap getDictionary() {
        return words;
    }

    public PyMorphyDictionary(String dictionaryFileName, String encoding){
        this.words = new HashMap();

        try {
            FileInputStream fis1 = new FileInputStream(dictionaryFileName);
            InputStreamReader isr1 = new InputStreamReader(fis1, encoding);
            BufferedReader in = new BufferedReader(isr1);

            String s = in.readLine();
            while (s!=null){
                s= s.trim();
                if(s.length()!=0){
                    if(s.charAt(0)=='﻿') {
                        s = s.substring(1);
                    }
                    if(s.contains(" ")){
                        int ind = s.indexOf(" ");
                        String key = s.substring(0,ind);
                        if(!words.containsKey(key)){
                            words.put(key,s.substring(ind+1));
                        }
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

        if(this.words.isEmpty()){
            System.out.println("PyMorphyDictionary dictionary is empty");
        }
    }

    public void print(){
        for(Object word : words.keySet()){
            System.out.println(word+" "+words.get(word));
        }
    }

    /**
     * Checking if word is in dictionary
     *
     * @param word
     * @return true if word is here. false — otherwise
     */
    public boolean contains(String word){
        return words.containsKey(word);
    }

    public static void main(String [] args) throws FileNotFoundException {
        PyMorphyDictionary dictionary = new PyMorphyDictionary("norm_dictionary.txt","utf-8");
        dictionary.print();
    }
}

