package ru.goodsReview.analyzer.wordAnalyzer;

import org.apache.log4j.Logger;
import ru.goodsReview.core.utils.OSValidator;

import java.io.*;
import java.util.Scanner;

/**
 * Artemij Chugreev
 * Date: 08.04.12
 * Time: 14:21
 * email: artemij.chugreev@gmail.com
 * skype: achugr
 */
public class PyMorphyAnalyzer {
    private static final Logger log = Logger.getLogger(MystemAnalyzer.class);

    private static final String CHARSET = "utf-8";

    private Process analyzer;
    private Scanner sc;
    private PrintStream ps;

    PyMorphyAnalyzer() throws IOException {
        try {
            String command = "";
            if (OSValidator.isUnix()) {
                command = "./";
            }
            analyzer = Runtime.getRuntime().exec(command + "C:\\Python27\\python analyzeOneWord.py");
        } catch (IOException e) {
            log.error("Caution! Analyzer wasn't created. Check if you have python and pyMorphy", e);
//            throw new IOException();
        }
    }

    public void close() {
        analyzer.destroy();
    }

//    TODO it's popular method, so it must be in util

    /**
     * Checks if letter belongs to russian alphabet.
     *
     * @param letter The letter itself.
     * @return True if letter is russian, false — otherwise.
     */
    private static boolean isRussianLetter(char letter) {
        if ((letter >= 0x0410) && (letter <= 0x044F)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isRussianWord(String word) {
        char[] wordChars = word.toCharArray();
        for (int i = 0, j = wordChars.length; i < j; i++) {
            if (!isRussianLetter(wordChars[i])) {
                return false;
            }
        }
        return true;
    }

    public String normalizeWord(String word) throws IOException, InterruptedException {
        sc = new Scanner(analyzer.getInputStream(), CHARSET);
        ps = new PrintStream(analyzer.getOutputStream(), true, CHARSET);
        //   if (!isRussianWord(word)) {
        //      throw new IllegalArgumentException("Word must contain only russian letters");
        //  }
        ps.println(word);

//        String normalizedWord = sc.nextLine();
//        System.out.println(normalizedWord);
        
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(analyzer.getInputStream()));

        BufferedReader stdError = new BufferedReader(new InputStreamReader(analyzer.getErrorStream()));



        // read the output
//        String s;
//        while ((s = stdInput.readLine()) != null) {
//
//           System.out.println(s);
//
//       }
        String s =  stdInput.readLine();
          if(s!=null){
              return s;
          }



        // read any errors

        while ((s = stdError.readLine()) != null) {

          //  System.out.println(s);

        }
        return "";
    }

    public static String getNormalizedWord(String s) throws IOException {
        String res = "null";
        PyMorphyAnalyzer pyMorphyAnalyzer = new PyMorphyAnalyzer();
        try {
            //  long start = System.currentTimeMillis();

            //  String priwet = new String(s);
            //  byte[] utf8Bytes = priwet.getBytes("UTF8");
            res = pyMorphyAnalyzer.normalizeWord(s);

            //long stop = System.currentTimeMillis();
            // System.out.println("time in millis: " + (stop-start));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            pyMorphyAnalyzer.close();
        }

        return res;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(getNormalizedWord("утята"));

    }
}