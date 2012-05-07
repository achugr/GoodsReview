//package ru.goodsReview.api.util;

/**
 * Date: 16.02.12
 * Time: 00:34
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */

//import org.springframework.beans.factory.annotation.Required;
//import ru.goodsReview.core.db.ControllerFactory;
//import ru.goodsReview.core.db.controller.ThesisController;
//import ru.goodsReview.core.model.Thesis;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YaSearcher {
    private static final String YA_URL = "http://xmlsearch.yandex.ru/xmlsearch?";
    private static final String ENC = "UTF-8";
    private static final String AND = "&";
    private static final String userSettingsFileName = "ya_searcher_key.txt";
    private static String PASSWORD;
    private static String USER;
    private static final Pattern pagesCountPattern = Pattern.compile(".*<found priority=\"all\">(\\d+)</found>.*");

    public YaSearcher() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(userSettingsFileName));
        USER = scanner.next();
        PASSWORD = scanner.next();
    }

    /**
     * Retrieve Yandex.XML response stream via GET request
     *
     * @param query      search query
     * @param pageNumber number of search page
     * @return Yandex.XML response stream
     * @throws IOException input/output exception
     */
    public InputStream retrieveResponseViaGetRequest(
            final String query,
            final int pageNumber
    ) throws IOException {

        final StringBuilder address = new StringBuilder(YA_URL);
        address.append("user=").append(USER).append(AND)
                .append("key=").append(PASSWORD).append(AND)
                .append("query=").append(URLEncoder.encode(query, ENC)).append(AND)
                .append("page=").append(pageNumber);
        final URL url = new URL(address.toString());
        return url.openStream();
    }

    public String sendRequest(String query) {
        LineNumberReader lineReader = null;
        int page = 0;
        String response = "error";
        final YaSearcher searcher;
        try {
            searcher = new YaSearcher();

            lineReader = new LineNumberReader(
                    new InputStreamReader(searcher.retrieveResponseViaGetRequest(query.toString(), page)));

            String line = lineReader.readLine();

            while (line != null) {
                Matcher matcher = pagesCountPattern.matcher(line);
                if (matcher.matches()) {
                    response = matcher.group(1);
                }
                line = lineReader.readLine();
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {

        } finally {
            if (lineReader != null) {
                try {
                    lineReader.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
        return response;
    }

    public static void main(String[] args) {
        try {
            YaSearcher yaSearcher = new YaSearcher();
            System.out.println(yaSearcher.sendRequest("!ноутбук /2 !ужасный"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
//        Pattern pattern = Pattern.compile("<found priority >(\\d+)</found>");
////        String test = "<bla>1231241";
//        String test = "<found priority>161037152</found>";
//        Matcher matcher = pattern.matcher(test);
//        if (matcher.matches()) {
//            System.out.println(matcher.group(1));
//        }
    }
}

