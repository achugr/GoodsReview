package ru.goodsReview.analyzer.algorithmTesting;

/**
 * Date: 05.02.12
 * Time: 21:18
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */

import ru.goodsReview.analyzer.ExtractThesis;
import ru.goodsReview.analyzer.wordAnalyzer.MystemAnalyzer;

import java.io.*;
import java.util.ArrayList;

public class ThesisExtractionTestDocument {
    private static double successExtract = 0;
    private static double numAlgo = 0;
    private static double numHum = 0;

    //   build list of Products
    static ArrayList<Product> buildProductList(String filePath, String encoding) throws IOException {
        ArrayList<Product> ProductList = new ArrayList<Product>();

        FileInputStream fis = new FileInputStream(filePath);
        InputStreamReader isr = new InputStreamReader(fis, encoding);
        BufferedReader in = new BufferedReader(isr);

        ArrayList<Review> reviewsList = new ArrayList<Review>();
        ArrayList<String> thesisList = new ArrayList<String>();
        String reviewID = "-1";
        String productName = "-1";
        String s = in.readLine();

        while (s != null) {
            s = s.trim();

            if (s.contains("<product id=")) {
                if (!productName.equals("-1")) {
                    reviewsList.add(new Review(reviewID, (ArrayList<String>) thesisList.clone()));
                    thesisList.clear();
                    reviewID = "-1";

                    ProductList.add(new Product(productName, (ArrayList<Review>) reviewsList.clone()));
                    reviewsList.clear();
                }
                productName = s.substring(s.indexOf("name=") + 6, s.lastIndexOf("\""));
            }

            if (!productName.equals("-1")) {
                if (s.contains("<review")) {
                    if (!reviewID.equals("-1")) {
                        reviewsList.add(new Review(reviewID, (ArrayList<String>) thesisList.clone()));
                        thesisList.clear();
                    }
                    reviewID = s.substring(s.indexOf("\"") + 1, s.lastIndexOf("\""));
                }

                if (s.contains("##")) {
                    String t = s.substring(0, s.indexOf("##")).trim();

                    if (!t.equals("")) {
                        if (t.contains(",")) {
                            String[] arr = t.split(",");
                            for (int i = 0; i < arr.length; i++) {
                                String s1 = arr[i];
                                s1 = s1.trim();
                                if(s1.contains("[")){
                                    s1 = splitBracket(s1);
                                    if (!s1.equals("")&&!thesisList.contains(s1)) {
                                        thesisList.add(s1);
                                    }
                                }

                            }
                        } else {
                            t = splitBracket(t);
                            if(!t.equals("")){
                                thesisList.add(t);
                            }
                        }
                    }

                }

                s = in.readLine();
            }
        }

        reviewsList.add(new Review(reviewID, thesisList));
        ProductList.add(new Product(productName, reviewsList));

        return ProductList;
    }

    static  String splitBracket(String s){
        if(s.contains("[")){
            s = s.substring(0, s.indexOf("["));
        }
        return  s;
    }


    //   build list of Products
    static ArrayList<Product> buildAlgoProductList(String filePath, String encoding) throws IOException {
        ArrayList<Product> ProductList = new ArrayList<Product>();
        MystemAnalyzer mystemAnalyzer = new MystemAnalyzer();

        FileInputStream fis = new FileInputStream(filePath);
        InputStreamReader isr = new InputStreamReader(fis, encoding);
        BufferedReader in = new BufferedReader(isr);

        ArrayList<Review> reviewsList = new ArrayList<Review>();
        ArrayList<String> thesisList = new ArrayList<String>();
        String reviewID = "-1";
        String productName = "-1";
        String s = in.readLine();

        while (s != null) {
            s = s.trim();

            if (s.contains("<product id=")) {
                if (!productName.equals("-1")) {
                    reviewsList.add(new Review(reviewID, (ArrayList<String>) thesisList.clone()));
                    thesisList.clear();
                    reviewID = "-1";

                    ProductList.add(new Product(productName, (ArrayList<Review>) reviewsList.clone()));
                    reviewsList.clear();
                }
                productName = s.substring(s.indexOf("name=") + 6, s.lastIndexOf("\""));
            }

            boolean reviewOpen = false;
            if (!productName.equals("-1")) {
                if (s.contains("<review")) {  
                    reviewOpen = true;
                    if (!reviewID.equals("-1")) {
                        reviewsList.add(new Review(reviewID, (ArrayList<String>) thesisList.clone()));
                        thesisList.clear();
                    }
                    reviewID = s.substring(s.indexOf("\"") + 1, s.lastIndexOf("\""));
                }


                if (reviewOpen == true) {
                    StringBuffer sb = new StringBuffer();
                    while (reviewOpen == true && s != null) {
                        s = in.readLine();
                        if (s.contains("</review>")) {
                            reviewOpen = false;
                            String rev =  sb.toString();
                           // System.out.println(rev);
                            ArrayList<String> tList = ExtractThesis.doExtraction(rev, mystemAnalyzer);
                            for (String str : tList) {
                                thesisList.add(str);
                            }
                        } else {
                            sb.append(s);
                        }

                    }
                }

                s = in.readLine();
            }
        }

        reviewsList.add(new Review(reviewID, thesisList));
        ProductList.add(new Product(productName, reviewsList));

        mystemAnalyzer.close();
        return ProductList;
    }

    
    // comparison of thesis for two products lists
    static void compare(ArrayList<Product> algoProThesis, ArrayList<Product> humProThesis, String filePath) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath)));
        for (int i = 0; i < humProThesis.size(); i++) {
            Product humProduct = humProThesis.get(i);

            for (int j = 0; j < algoProThesis.size(); j++) {
                Product algoProduct = algoProThesis.get(j);
                
                if(editDist(algoProduct.getName(), humProduct.getName())<2){
                    /*
                    System.out.println("--------new product  "+algoProduct.getName());
                    for(Review review : algoProduct.getReviews()){
                        System.out.println("review_alg"+review.getReview());
                        for(String thesis : review.getThesis()){
                            System.out.println(thesis);
                        }
                    }
                    System.out.println("--------------------");
                    for(Review review : humProduct.getReviews()){
                        System.out.println("review_hum"+review.getReview());
                        for(String thesis : review.getThesis()){
                            System.out.println(thesis);
                        }
                    }  */
                    comparator(algoProduct, humProduct, out);
                    break;
                }
            }

        }

        out.flush();
    }

    // comparison of thesis for two products
    static void comparator(Product algoProduct, Product humProduct, PrintWriter out) {
        out.println("<product name=\"" + algoProduct.getName() + "\">");
        if (algoProduct.getReviews().size() > 0 && !algoProduct.getReviews().get(0).getReview().equals("null")) {
            compareThesisLists(algoProduct.getReviews(), humProduct.getReviews(), out);
        }

        out.println("</product>");
    }

    // comparison of thesis for two Review lists
    static void compareThesisLists(ArrayList<Review> algoReview, ArrayList<Review> humReview, PrintWriter out) {
        int editDist = 3;

        for (int k = 0; k < Math.min(algoReview.size(), humReview.size()); k++) {
            String reviewID = algoReview.get(k).getReview();

            if(!reviewID.equals("-1")){
                out.println("   <review id=\"" + reviewID + "\">");

                ArrayList<String> algoThesis = algoReview.get(k).getThesis();
                ArrayList<String> humThesis = humReview.get(k).getThesis();

                numAlgo += algoThesis.size();
                numHum += humThesis.size();

                for (int i = 0; i < algoThesis.size(); i++) {
                    String s1 = algoThesis.get(i).trim();
                    for (int j = 0; j < humThesis.size(); j++) {
                        String s2 = humThesis.get(j).trim();
                        if (editDist(s1, s2) < editDist) {
                            out.println("      <OK>" + s1 + "</OK>");
                            successExtract++;
                            break;
                        }
                    }
                }

                for (int i = 0; i < algoThesis.size(); i++) {
                    boolean t = false;
                    String s1 = algoThesis.get(i).trim();
                    for (int j = 0; j < humThesis.size(); j++) {
                        String s2 = humThesis.get(j).trim();
                        if (editDist(s1, s2) < editDist) {
                            t = true;
                        }
                    }
                    if (t == false) {
                        out.println("      <algo>" + s1 + "</algo>");
                    }
                }

                for (int i = 0; i < humThesis.size(); i++) {
                    boolean t = false;
                    String s1 = humThesis.get(i).trim();
                    for (int j = 0; j < algoThesis.size(); j++) {
                        String s2 = algoThesis.get(j).trim();
                        if (editDist(s1, s2) < editDist) {
                            t = true;
                        }
                    }
                    if (t == false) {
                        out.println("      <hum>" + s1 + "</hum>");
                    }
                }
                //out.println("   </review>");
            }
        }
    }

    public static int editDist(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[] D1;
        int[] D2 = new int[n + 1];
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        for (int i = 0; i <= n; i++) {
            D2[i] = i;
        }
        for (int i = 1; i <= m; i++) {
            D1 = D2;
            D2 = new int[n + 1];
            for (int j = 0; j <= n; j++) {
                if (j == 0) {
                    D2[j] = i;
                } else {
                    int cost = (s1.charAt(i - 1) != s2.charAt(j - 1)) ? 1 : 0;
                    if (D2[j - 1] < D1[j] && D2[j - 1] < D1[j - 1] + cost) {
                        D2[j] = D2[j - 1] + 1;
                    } else if (D1[j] < D1[j - 1] + cost) {
                        D2[j] = D1[j] + 1;
                    } else {
                        D2[j] = D1[j - 1] + cost;
                    }
                }
            }
        }
        return D2[n];
    }



    public static void main(String[] args) throws IOException {
        ArrayList<Product> algoProThesis = buildAlgoProductList("d://Notebooks.txt", "utf8");
        System.out.println("ok");
        /*
        for (Product p:algoProThesis){
            System.out.println("Product Name = "+p.getName());
            for (Review r:p.getReviews()){
                if(r.getReview()!="-1"){
                    System.out.println("        Review = "+r.getReview());
                    for (String t:r.getThesis()){
                        System.out.println("            "+t);
                    }
                }
            }
        }  */

        ArrayList<Product> humProThesis = buildProductList("d://Notebooks_marked_ds.txt", "utf8");
        System.out.println("ok");
        /*
        for (Product p:humProThesis){
            System.out.println("Product Name = "+p.getName());
            for (Review r:p.getReviews()){
                if(r.getReview()!="-1"){
                    System.out.println("        Review = "+r.getReview());
                    for (String t:r.getThesis()){
                        System.out.println("            "+t);
                    }
                }
            }
        } */
        compare(algoProThesis, humProThesis, "d://ans.txt");
        System.out.println("ok");

        System.out.println(successExtract);
        System.out.println(numAlgo);
        System.out.println(numHum);

        System.out.println(successExtract/(numAlgo + numHum));
        System.out.print(successExtract/numHum);

    }
}