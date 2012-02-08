package ru.goodsReview.analyzer.algorithmTesting;

/**
 * Date: 05.02.12
 * Time: 21:18
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ThesisExtractionTestDocument {


    static ArrayList<Product> buildThesisList(String filePath, String encoding) throws IOException {
        ArrayList<Product> ProductList = new ArrayList<Product>();

        FileInputStream fis = new FileInputStream(filePath);
        InputStreamReader isr = new InputStreamReader(fis, encoding);
        BufferedReader in = new BufferedReader(isr);

        ArrayList<Review> reviewsList = new ArrayList<Review>();
        ArrayList<String> thesisList = new ArrayList<String>();
        String r = "null";
        String productId = "null";
        String s = in.readLine();

        while (s != null) {
            s = s.trim();
            s = s.toLowerCase();

            if (s.contains("<product id=")) {
                if(!productId.equals("null")){
                    reviewsList.add(new Review(r, (ArrayList<String>) thesisList.clone()));
                    thesisList.clear();
                    r = "null";

                    ProductList.add(new Product(productId, (ArrayList<Review>) reviewsList.clone()));
                    reviewsList.clear();
                }
                productId = s.substring(s.indexOf("=") + 2, s.indexOf("name") - 2);
            }

            if (s.contains("<review")) {
                if (!r.equals("null")) {
                    reviewsList.add(new Review(r, (ArrayList<String>) thesisList.clone()));
                    thesisList.clear();
                }
                r = s.substring(s.indexOf("\"") + 1, s.lastIndexOf("\""));
            }

            if (s.contains("<thesis>")) {
                String t = s.substring(s.indexOf("<thesis>")+8, s.indexOf("</thesis>")).trim();
                thesisList.add(t);
            }

            s = in.readLine();
        }

        reviewsList.add(new Review(r, thesisList));
        ProductList.add(new Product(productId, reviewsList));

        return ProductList;
    }


    static void compare(ArrayList<Product> algoProThesis, ArrayList<Product> humProThesis, String filePath) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath)));

        for (int i = 0; i < humProThesis.size(); i++) {
            comparator(humProThesis.get(i), algoProThesis.get(i), out);
        }

        out.flush();
    }


    static void comparator(Product p1, Product p2, PrintWriter out){
        out.println("<product id=\"" + p1.name + "\">");
        if (p1.reviews.size() > 0 && !p1.reviews.get(0).review.equals("null")) {
            compareThesisLists(p1.reviews, p2.reviews, out);
        }

        out.println("</product>");
    }


    static void compareThesisLists(ArrayList<Review> r1, ArrayList<Review> r2, PrintWriter out){
        for (int k = 0; k < r1.size(); k++) {
            out.println("   <review id=\"" + r1.get(k).review + "\">");
            ArrayList<String> t1 = r1.get(k).thesis;
            ArrayList<String> t2 = r2.get(k).thesis;
            int dist = 3;

            for (int i = 0; i < t1.size(); i++) {
                String s1 = t1.get(i).trim();
                for (int j = 0; j < t2.size(); j++) {
                    String s2 = t2.get(j).trim();
                    if (editdist(s1, s2) < dist) {
                        out.println("      <OK>" + s1 + "</OK>");
                    }
                }
            }

            for (int i = 0; i < t1.size(); i++) {
                boolean t = false;
                String s1 = t1.get(i).trim();
                for (int j = 0; j < t2.size(); j++) {
                    String s2 = t2.get(j).trim();
                    if (editdist(s1, s2) < dist) {
                        t = true;
                    }
                }
                if (t == false) {
                    out.println("      <hum>" + s1 + "</hum>");
                }
            }

            for (int i = 0; i < t2.size(); i++) {
                boolean t = false;
                String s1 = t2.get(i).trim();
                for (int j = 0; j < t1.size(); j++) {
                    String s2 = t1.get(j).trim();
                    if (editdist(s1, s2) < dist) {
                        t = true;
                    }
                }
                if (t == false) {
                    out.println("      <algo>" + s1 + "</algo>");
                }
            }
            //out.println("   </review>");
        }
    }


    static int editdist(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        int[] D2 = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            D2[i] = i;
        }

        for (int i = 1; i <= m; i++) {
            int[] D1 = D2;
            D2 = new int[n + 1];
            for (int j = 0; j <= n; j++) {
                if (j == 0) {
                    D2[j] = i;
                } else {
                    int cost = (s1.charAt(i - 1) != s2.charAt(j - 1)) ? 1 : 0;
                    if (D2[j - 1] < D1[j] && D2[j - 1] < D1[j - 1] + cost) {
                        D2[j] = D2[j - 1] + 1;
                    } else {
                        if (D1[j] < D1[j - 1] + cost) {
                            D2[j] = D1[j] + 1;
                        } else {
                            D2[j] = D1[j - 1] + cost;
                        }
                    }
                }
            }
        }

        return D2[n];
    }

    public static void main(String[] args) throws IOException {
        ArrayList<Product> algoProThesis = buildThesisList("analyzer/ThesisExtractionDocument.txt",  "utf8");
        ArrayList<Product> humProThesis = buildThesisList("analyzer/handMade.txt", "windows-1251");

        compare(algoProThesis, humProThesis, "analyzer/CompareThesis.txt");

             /*
        for (int i = 0; i < 80; i++) {
            System.out.println(algoProThesis.get(i).name);
            System.out.println(humProThesis.get(i).name);
            for (int j = 0; j < algoProThesis.get(i).reviews.size(); j++) {
                System.out.println("     "+algoProThesis.get(i).reviews.get(j).review);
                System.out.println("     "+humProThesis.get(i).reviews.get(j).review);
                for (int j2 = 0; j2 < algoProThesis.get(i).reviews.get(j).thesis.size(); j2++) {
                    System.out.println("           "+algoProThesis.get(i).reviews.get(j).thesis.get(j2));
                }
                for (int j2 = 0; j2 < humProThesis.get(i).reviews.get(j).thesis.size(); j2++) {
                    System.out.println("         "+humProThesis.get(i).reviews.get(j).thesis.get(j2));
                }

            }

        } */

    }
}

