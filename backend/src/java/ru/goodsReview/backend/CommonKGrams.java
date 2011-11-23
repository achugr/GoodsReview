package ru.goodsReview.backend;
/*
 *  Date: 23.11.11
 *   Time: 11:26
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import ru.goodsReview.backend.Document.Document;

import java.util.*;

//this class implements k-grams method
public class CommonKGrams {
    private Map<String, Boolean[]> kGramsTable;
    private static final int tokenSize = 2;
    private int kGramTableColumnsNum;
    private int tokensNum = 0;

    //constructor
    public CommonKGrams(List<Document> documentList) {
        this.kGramTableColumnsNum = documentList.size();
        this.kGramsTable = new HashMap<String, Boolean[]>();
        this.kGramsTable = extractTokens(documentList);
    }

    //return matrix of tokens and contains
    public Map<String, Boolean[]> getKGramsTable() {
        return this.kGramsTable;
    }

    public Map<String, Boolean[]> extractTokens(List<Document> documentList) {
        char[] text;
        int i, j, k, n;
        int counter;
        String tokenS;
        char[] token = new char[CommonKGrams.tokenSize];
        Boolean[] tokenIsInDocument;
        // token must be realised as QUEUE ???
        //for (n = 0; n < reviewList.size(); n++) {
        n=0;
        for(Document document : documentList){
            //get content of document as char array
            text = document.getContent().toCharArray();
            for (i = 0; i < text.length - CommonKGrams.tokenSize + 1; i++) {
                //select k-grams block of characters
                for (j = i, k = 0; j < i + CommonKGrams.tokenSize; j++, k++) {
                    token[k] = text[j];
                }
                tokenS = new String(token);
                if (this.kGramsTable.containsKey(tokenS)) {
//                    get info about in which document token is already find
                    tokenIsInDocument = this.kGramsTable.get(tokenS);
//                    set that this token is now in document #n
                    tokenIsInDocument[n] = true;
                    System.out.print("\nthis token is already in map: " + tokenS + " ");
                    for (int m = 0; m < this.kGramTableColumnsNum; m++) {
                        System.out.print(tokenIsInDocument[m] + " -- ");
                    }
//                    update info about in which documents this token is find
                    this.kGramsTable.put(tokenS, tokenIsInDocument);
                } else {
//                    this token is new
                    tokenIsInDocument = new Boolean[this.kGramTableColumnsNum];
//                    set num of document in which this token is find
                    tokenIsInDocument[n] = true;
                    System.out.print("\n" + tokenS + " ");
                    for (int m = 0; m < this.kGramTableColumnsNum; m++) {
                        System.out.print(tokenIsInDocument[m] + " -- ");
                    }
                    this.kGramsTable.put(tokenS, tokenIsInDocument);
                }

            }
            n++;
        }

//        go on kGramsTable
//        tokenIsInDocument - Bool array, that specified that any token is exist in any document
//        if some value of tokenIsInDocument is NULL, set FALSE at this position
        Iterator tokens = this.kGramsTable.keySet().iterator();
        while (tokens.hasNext()) {
            this.tokensNum++;
            tokenS = tokens.next().toString();
            tokenIsInDocument = this.kGramsTable.get(tokenS);
            for (i = 0; i < this.kGramTableColumnsNum; i++) {
                if (tokenIsInDocument[i] == null) {
                    tokenIsInDocument[i] = false;
                }
                this.kGramsTable.put(tokenS, tokenIsInDocument);
            }
        }
        return this.kGramsTable;
    }


    /**
     * @return String array representation of key set
     */
    private String[] getKeys() {
        String[] tokens = new String[this.tokensNum];
        Iterator token = this.kGramsTable.keySet().iterator();
        for (int i = 0; token.hasNext(); i++) {
            tokens[i] = token.next().toString();
        }
        return tokens;
    }

    /**
     *
     @param token some String sequence
     @return Boolean array that specified in which documents this token is exist
     */
    private Boolean[] getValues(String token) {
        Boolean[] values;
        values = this.kGramsTable.get(token);
        return values;
    }


    public double compareDocuments() {
        double similarity = 0;
        int i, numOfTests = this.tokensNum;
        int[][] signatureMatrix = new int[numOfTests][numOfTests];
        for (i = 0; i < numOfTests; i++) {
            signatureMatrix[i] = this.setRowInSignatureMatrix();
        }
        System.out.println();

        for (i = 0; i < numOfTests; i++) {
            for (int j = 0; j < signatureMatrix[i].length; j++) {
                System.out.print(signatureMatrix[i][j] + " ");
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
        for (int i = 0; i < randomIndexes.length; i++) {
            System.out.print(randomIndexes[i] + " ");
        }
        for (int i = 0; i < this.kGramTableColumnsNum; i++) {
            row[i] = -1;
        }
        Boolean[] tokenIsInDocument;
//        go on array of random indexes(go on all tokens in random ??)
        for (int i = 0; i < randomIndexes.length; i++) {
//           get bool array which specified, in which documents this token is exist
            tokenIsInDocument = this.getValues(tokens[randomIndexes[i]]);
//            go on all documents
            for (int j = 0; j < this.kGramTableColumnsNum; j++) {
//                if token exist in some document set in row of signature matrix
                if (tokenIsInDocument[j] && (row[j] == -1)) {
                    row[j] = i;
                }
            }
        }
        return row;
    }

    /**
     creating random indexes
     @param arraySize size of array
     @return int array of random indexes
     */
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

    private double[][] createSimilarityMatrix(int[][] signatureMatrix) {
        double[][] similarityMatrix = new double[signatureMatrix.length][this.kGramTableColumnsNum];
        int i, j, k;

        for (i = 0; i < signatureMatrix.length; i++) {
            for (j = 0; j < this.kGramTableColumnsNum; j++) {
                for (k = j; k < this.kGramTableColumnsNum; k++) {
                    if (signatureMatrix[i][j] == signatureMatrix[i][k]) {
                        similarityMatrix[j][k] += 1;
                    }
                }
            }
        }
        for (i = 0; i < this.kGramTableColumnsNum; i++) {
            System.out.println();
            for (j = 0; j < this.kGramTableColumnsNum; j++) {
                similarityMatrix[i][j] /= similarityMatrix.length;
                System.out.printf("%1$5.2g ", similarityMatrix[i][j]);
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
