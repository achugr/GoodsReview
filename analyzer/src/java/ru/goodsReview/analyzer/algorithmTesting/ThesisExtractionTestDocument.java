package ru.goodsReview.analyzer.algorithmTesting;

/**
 * Date: 05.02.12
 * Time: 21:18
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */

import ru.goodsReview.core.utils.EditDistance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

   // comparison of thesis for two products lists
    static void compare(ArrayList<Product> algoProThesis, ArrayList<Product> humProThesis, String filePath) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath)));

        for (int i = 0; i < humProThesis.size(); i++) {
            comparator(algoProThesis.get(i), humProThesis.get(i), out);
        }

        out.flush();
    }

    // comparison of thesis for two products
    static void comparator(Product algoProduct, Product humProduct, PrintWriter out){
        out.println("<product id=\"" + algoProduct.getName() + "\">");
        if (algoProduct.getReviews().size() > 0 && !algoProduct.getReviews().get(0).getReview().equals("null")) {
            compareThesisLists(algoProduct.getReviews(), humProduct.getReviews(), out);
        }

        out.println("</product>");
    }

    // comparison of thesis for two Review lists
    static void compareThesisLists(ArrayList<Review> algoReview, ArrayList<Review> humReview, PrintWriter out){
        int editDist = 3;

        for (int k = 0; k < algoReview.size(); k++) {
            out.println("   <review id=\"" + algoReview.get(k).getReview() + "\">");

            ArrayList<String> algoThesis = algoReview.get(k).getThesis();
            ArrayList<String> humThesis = humReview.get(k).getThesis();

            numAlgo += algoThesis.size();
            numHum += humThesis.size();

            for (int i = 0; i < algoThesis.size(); i++) {
                String s1 = algoThesis.get(i).trim();
                for (int j = 0; j < humThesis.size(); j++) {
                    String s2 = humThesis.get(j).trim();
                    if (EditDistance.editDist(s1, s2) < editDist) {
                        out.println("      <OK>" + s1 + "</OK>");
                        successExtract++;
                    }
                }
            }

            for (int i = 0; i < algoThesis.size(); i++) {
                boolean t = false;
                String s1 = algoThesis.get(i).trim();
                for (int j = 0; j < humThesis.size(); j++) {
                    String s2 = humThesis.get(j).trim();
                    if (EditDistance.editDist(s1, s2) < editDist) {
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
                    if (EditDistance.editDist(s1, s2) < editDist) {
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




    public static void main(String[] args) throws IOException {
        ArrayList<Product> algoProThesis = buildProductList("analyzer/ThesisExtractionDocument.txt", "utf8");
        ArrayList<Product> humProThesis = buildProductList("analyzer/handMade.txt", "windows-1251");

      compare(algoProThesis, humProThesis, "analyzer/CompareThesis.txt");

        System.out.println(successExtract);
        System.out.println(numAlgo);
        System.out.println(numHum);

    }
}

