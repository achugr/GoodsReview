package test.goodsReview.miner;

/**
 * Created by IntelliJ IDEA.
 * User: Artemij
 * Date: 01.10.11
 * Time: 0:51
 * To change this template use File | Settings | File Templates.
 */

import java.lang.*;
import java.util.*;

//this class implements k-grams method
public class KGrams {
    private Map<String, Boolean[]> kGramsTable;
    private static final int tokenSize = 2;
    private int kGramTableColumnsNum;
    private int tokensNum = 0;

    //constructor
    public KGrams(ListOfReviews citilinkReviews) {
        this.kGramTableColumnsNum = citilinkReviews.getReviewsNum();
        this.kGramsTable = new HashMap<String, Boolean[]>();
        this.kGramsTable = extractTokens(citilinkReviews);
    }

    //return matrix of tokens and contains
    public Map<String, Boolean[]> getKGramsTable() {
        return this.kGramsTable;
    }

    public Map<String, Boolean[]> extractTokens(ListOfReviews citilinkReviews) {
        char[] text;
        int i, j, k, n;
        int counter;
        String tokenS;
        char[] token = new char[KGrams.tokenSize];
        Boolean[] tokenIncitilinkReviews;
        // token must be realised as QUEUE ???
        for (n = 0; n < citilinkReviews.getReviewsNum(); n++) {
            text = citilinkReviews.getCitilinkReviewChars(n);
            for (i = 0; i < text.length - KGrams.tokenSize + 1; i++) {
                for (j = i, k = 0; j < i + KGrams.tokenSize; j++, k++) {
                    token[k] = text[j];
                }
                tokenS = new String(token);
                if (this.kGramsTable.containsKey(tokenS)) {
                    tokenIncitilinkReviews = this.kGramsTable.get(tokenS);
                    tokenIncitilinkReviews[n] = true;
                    System.out.print("\nthis token is already in map: " + tokenS + " ");
                    for (int m = 0; m < this.kGramTableColumnsNum; m++) {
                        System.out.print(tokenIncitilinkReviews[m] + " -- ");
                    }

                    this.kGramsTable.put(tokenS, tokenIncitilinkReviews);

                } else {
                    tokenIncitilinkReviews = new Boolean[this.kGramTableColumnsNum];
                    tokenIncitilinkReviews[n] = true;
                    System.out.print("\n" + tokenS + " ");
                    for (int m = 0; m < this.kGramTableColumnsNum; m++) {
                        System.out.print(tokenIncitilinkReviews[m] + " -- ");
                    }
                    this.kGramsTable.put(tokenS, tokenIncitilinkReviews);
                }

            }
        }
        Iterator tokens = this.kGramsTable.keySet().iterator();
        Boolean[] isTokenIncitilinkReview;
        while (tokens.hasNext()) {
            this.tokensNum++;
            tokenS = tokens.next().toString();
            isTokenIncitilinkReview = this.kGramsTable.get(tokenS);
            for (i = 0; i < this.kGramTableColumnsNum; i++) {
                if (isTokenIncitilinkReview[i] == null) {
                    isTokenIncitilinkReview[i] = false;
                }
                this.kGramsTable.put(tokenS, isTokenIncitilinkReview);
            }
        }
        return this.kGramsTable;
    }

    private String[] getKeys() {
        String[] tokens = new String[this.tokensNum];
        Iterator token = this.kGramsTable.keySet().iterator();
        for (int i = 0; token.hasNext(); i++) {
            tokens[i] = token.next().toString();
        }
        return tokens;
    }

    private Boolean[] getValues(String token) {
        Boolean[] values;
        values = this.kGramsTable.get(token);
        return values;
    }


    public double comparecitilinkReviews() {
        double similarity = 0;
        int i, numOfTests = this.tokensNum;
        int[][] signatureMatrix = new int[numOfTests][numOfTests];
        for (i = 0; i < numOfTests; i++) {
            signatureMatrix[i] = this.setRowInSignatureMatrix();
        }
        System.out.println();

        for(i=0; i<numOfTests; i++){
            for(int j =0; j < signatureMatrix[i].length; j++){
                System.out.print(signatureMatrix[i][j]+" ");
            }
            System.out.println();
        }
        createSimilarityMatrix(signatureMatrix);
        return similarity;
    }

    public int[] setRowInSignatureMatrix() {
        int[] row = new int[this.kGramTableColumnsNum];
        int[] randomIndexes = createRandomIndexes(this.tokensNum);
        String[] tokens = this.getKeys();
        System.out.println();
        for(int i=0; i<randomIndexes.length; i++){
            System.out.print(randomIndexes[i] + " ");
        }
        for(int i=0; i<this.kGramTableColumnsNum; i++){
            row[i] = -1;
        }
        Boolean[] values;
        for (int i = 0; i < randomIndexes.length; i++) {
            values = this.getValues(tokens[randomIndexes[i]]);
            for (int j = 0; j < this.kGramTableColumnsNum; j++) {
                if (values[j] && (row[j]==-1)) {
                    row[j] = i;
                }
            }
        }
        return row;
    }

    public static int[] createRandomIndexes(int arraySize) {
        int[] randomIndexes = new int[arraySize];
        int i, tmp, rand1, rand2;
        Random randGen = new Random();
        for (i = 0; i < arraySize; i++) {
            randomIndexes[i] = i;
        }
        for (i = 0; i < arraySize; i++) {
            rand1 = randGen.nextInt(arraySize);
            rand2 = randGen.nextInt(arraySize);
            tmp = randomIndexes[rand1];
            randomIndexes[rand1] = randomIndexes[rand2];
            randomIndexes[rand2] = tmp;
        }
        return randomIndexes;
    }

    private double [][] createSimilarityMatrix(int [][] signatureMatrix){
        double  [][] similarityMatrix = new double[signatureMatrix.length][this.kGramTableColumnsNum];
        int i, j, k;

        for(i=0; i < signatureMatrix.length; i++){
            for(j=0; j<this.kGramTableColumnsNum; j++){
                for(k=j; k<this.kGramTableColumnsNum; k++){
                    if(signatureMatrix[i][j] == signatureMatrix[i][k]){
                        similarityMatrix[j][k] +=1;
                    }
                }
            }
        }
        for(i=0; i<this.kGramTableColumnsNum; i++){
            System.out.println();
            for(j=0; j<this.kGramTableColumnsNum; j++){
                similarityMatrix[i][j]/=similarityMatrix.length;
                System.out.printf("%1$5.2g ",similarityMatrix[i][j]);
            }
        }
        return similarityMatrix;
    }

    public void printKGramsTable() {
        int i;
        String tokenS;
        Boolean[] isTokenIncitilinkReview;
        Iterator token = this.kGramsTable.keySet().iterator();
        while (token.hasNext()) {
            tokenS = token.next().toString();
            System.out.print("\ntoken: " + tokenS + " ");
            isTokenIncitilinkReview = this.kGramsTable.get(tokenS);
            for (i = 0; i < this.kGramTableColumnsNum; i++) {
                System.out.print(isTokenIncitilinkReview[i] + " ");
            }
        }
    }
}

