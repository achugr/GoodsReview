package ru.goodsreview.core.utils;

/*
    Date: 28.11.11
    Time: 22:08
    Author: 
        Yaroslav Skudarnov 
        SkudarnovYI@gmail.com
*/

import ru.goodsreview.core.model.Product;

import java.util.Arrays;
import java.util.List;

public class EditDistance {
    private String query;
    private List<Product> productList;
    static final int BIGDISTANCE = 99;
    static final int LIMITDISTANCE = 2;

    public EditDistance() {

    }

    public EditDistance(String query, List<Product> productList) {
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

    public static boolean isContainsTokens(String newProductName, List<String> productsName) {
        boolean isContainsTokens = false;
        String[] tokensNewProductName = newProductName.split("\\s");
        int[][] similarity = new int[productsName.size()][];

        for (int i = 0; i < productsName.size(); i++) {
            similarity[i] = new int[tokensNewProductName.length];
        }
        for (int i = 0; i < productsName.size(); i++) {
            int sim = 0;
            String[] tokensProductName = productsName.get(i).split("\\s");
            for (int j = 0; j < tokensNewProductName.length; j++) {
                similarity[i][j] = BIGDISTANCE;
            }
            for (int j = 0; j < tokensNewProductName.length; j++) {
                for (int k = 0; k < tokensProductName.length; k++) {
                    if (editDist(tokensNewProductName[j], tokensProductName[k]) < LIMITDISTANCE) {
                        similarity[i][j] = editDist(tokensNewProductName[j], tokensProductName[k]);
                        break;
                    }
                }
            }
            System.out.println(productsName.get(i) + " is relevan for <<" + newProductName + ">> ->  " + Arrays.toString(similarity[i]));

        }
        int i;
        int min = 0;
        int tmpMin = 0;
        int relevProductId = 0;
        for (i = 0; i < similarity[0].length; i++) {
            min += similarity[0][i];
        }
        for (i = 1; i < similarity.length; i++) {
            for (int j = 0; j < similarity[i].length; j++) {
                tmpMin += similarity[i][j];
            }
            if (tmpMin < min) {
                min = tmpMin;
                relevProductId = i;
            }
            tmpMin = 0;
        }
        System.out.println("RELEVAND Prod ID = " + relevProductId);

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

}

