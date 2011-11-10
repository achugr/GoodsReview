package ru.goodsReview.backend.queryAnalyzer;
/*
 *  Date: 10.11.11
 *   Time: 15:20
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import ru.goodsReview.core.model.Product;

import java.util.ArrayList;
import java.util.List;

public class QueryProductAnalyzer {
    private String query;
    private List<Product> productList;

    public QueryProductAnalyzer(String query, List<Product> productList) {
        this.query = query;
        this.productList = productList;
    }

    /*public static int relevantProduct(String query, List<String> productName){
        int productId=0, min;
        int [] levenshtDist = new int[productName.size()];
        min = editDist(query, productName.get(0));
        System.out.println(productName.get(0) + " - " + min);
        for(int i=1; i< productName.size(); i++){
            levenshtDist[i] = editDist(query, productName.get(i));
            if(levenshtDist[i]<min){
                min=levenshtDist[i];
                productId = i;
            }
            System.out.println(productName.get(i) + " - " + levenshtDist[i]);
        }
        return productId;
    }  */

    public static boolean isContainsTokens(String query, List<String> productsName){
        boolean isContainsTokens=false;
        int similarity=0;
        String [] tokensQuery = query.split("\\s");
        for(int i=0; i<productsName.size(); i++){
            String [] tokensProductName = productsName.get(i).split("\\s");
            for(int j=0; j<tokensQuery.length; j++){
                for(int k=0; k<tokensProductName.length; k++){
                    if(editDist(tokensQuery[j], tokensProductName[k])>5){
                        similarity++;
                    }
                }
            }

        }
        return isContainsTokens;
    }
    public static int editDist(String S1, String S2) {
        int m = S1.length(), n = S2.length();
        int[] D1;
        int[] D2 = new int[n + 1];
        S1 = S1.toLowerCase();
        S2 = S2.toLowerCase();
        for (int i = 0; i <= n; i++)
            D2[i] = i;

        for (int i = 1; i <= m; i++) {
            D1 = D2;
            D2 = new int[n + 1];
            for (int j = 0; j <= n; j++) {
                if (j == 0) D2[j] = i;
                else {
                    int cost = (S1.charAt(i - 1) != S2.charAt(j - 1)) ? 1 : 0;
                    if (D2[j - 1] < D1[j] && D2[j - 1] < D1[j - 1] + cost)
                        D2[j] = D2[j - 1] + 1;
                    else if (D1[j] < D1[j - 1] + cost)
                        D2[j] = D1[j] + 1;
                    else
                        D2[j] = D1[j - 1] + cost;
                }
            }
        }
        return D2[n];
    }

    public static void main(String [] Args){
        List<String> productName = new ArrayList<String>();
        productName.add("lenovo x220");
        productName.add("lenovo thinkpad x220");
        productName.add("lenovo 220");
        productName.add("lenovo thinkpad");
        productName.add("asus");
    }
}
