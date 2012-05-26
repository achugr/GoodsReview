package ru.goodsreview.analyzer.wordAnalyzer;

import java.io.IOException;
import java.util.*;

public class TurglemClient {
    public static class TurglemResult {
        public final String word;
        public final int partOfSpeech;
        public final String form;
        public final boolean isSource;

        public TurglemResult(String word, int partOfSpeech, String form, boolean isSource) {
            this.word = word;
            this.partOfSpeech = partOfSpeech;
            this.form = form;
            this.isSource = isSource;
        }

        public static TurglemResult parseLine(String line) {
            String[] result = line.split(" ");
            return new TurglemResult(result[0], Integer.parseInt(result[1]), result[2], result.length > 3 && result[3].charAt(0) == '*');
        }

        @Override
        public String toString() {
            return word + " " + partOfSpeech + " " + form + " " + isSource;
        }
    }

    private String path;

    public TurglemClient(String path) {
        this.path = path;
    }

    public List<TurglemResult> analyse(String word) {
        List<TurglemResult> result = new ArrayList<TurglemResult>();

        try {
            Process process = Runtime.getRuntime().exec(new String[]{path, word});
            Scanner scanner = new Scanner(process.getInputStream());

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (!line.endsWith(".") && line.length() > 0) {
                    result.add(TurglemResult.parseLine(line));
                }
            }
        } catch (Exception e) {
//            System.out.println("Exception with stemming " + word);
//            e.printStackTrace();
        }

        return result;
    }

    public Set<Integer> analysePartOfSpeech(String word) {
        Set<Integer> parts = new HashSet<Integer>();

        for (TurglemResult result : analyse(word)) {
            if (result.isSource) {
                parts.add(result.partOfSpeech);
            }
        }

        return parts;
    }

    public static void main(String[] args) throws IOException {
        TurglemClient client = new TurglemClient("./turglem-client");
        System.out.println(client.analysePartOfSpeech("хорошая"));
    }
}
