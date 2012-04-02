package ru.goodsReview.analyzer;

/**
 * Date: 16.02.12
 * Time: 00:34
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */

import org.springframework.beans.factory.annotation.Required;
import ru.goodsReview.core.db.ControllerFactory;
import ru.goodsReview.core.db.controller.ThesisController;
import ru.goodsReview.core.model.Thesis;
import ru.goodsReview.core.utils.HashMapUtil;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YaSearcher extends TimerTask {
    private static final String YA_URL = "http://xmlsearch.yandex.ru/xmlsearch?";
    private static final String ENC = "UTF-8";
    private static final String AND = "&";
    private static final String userSettingsFileName = "ya_searcher_key.txt";
    private static String PASSWORD;
    private static String USER;
    private static final Pattern pagesCountPattern = Pattern.compile("<found priority=\"all\">(\\d+)</found>");

    public YaSearcher() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(userSettingsFileName));
        USER = scanner.next();
        PASSWORD = scanner.next();
    }

    private static ControllerFactory controllerFactory;
    private static ThesisController thesisController;


    @Required
    public void setControllerFactory(ControllerFactory controllerFactory1) {
        this.controllerFactory = controllerFactory1;
        setControllers();
    }

    public static void setControllers() {
        thesisController = controllerFactory.getThesisController();
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



    private static void thesisStatistic(java.util.HashMap thesisPopularity, String fileName){
        java.util.HashMap sortedThesisPopularity = HashMapUtil.sort(thesisPopularity);
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(fileName);
            Set<String> keys = sortedThesisPopularity.keySet();
            System.out.println(keys.size());
            for(String key : keys){
                printWriter.println(key + " -> " + sortedThesisPopularity.get(key));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            printWriter.close();
        }

    }

    @Override
    public void run() {
        List<Thesis> thesisList = controllerFactory.getThesisController().getAllTheses();
        java.util.HashMap thesisPopularity = new java.util.HashMap();
        int page =0;
//       500 - this limit is because we have ya.xml queries limit
        for (int i = 0; i < 500; i++) {
            Thesis thesis = thesisList.get(i);
            LineNumberReader lineReader = null;
            try {
                String [] tokens = thesis.getContent().split(" ");
                StringBuilder query = new StringBuilder();
                query.append("!");
                query.append(tokens[0]);
                query.append(" /2 ");
                query.append("!");
                query.append(tokens[1]);

                final YaSearcher searcher = new YaSearcher();
                lineReader = new LineNumberReader(
                        new InputStreamReader(searcher.retrieveResponseViaGetRequest(query.toString(), page)));

                String line = lineReader.readLine();
                while (line != null) {
                    Matcher matcher = pagesCountPattern.matcher(line);
                    if(matcher.matches()) {
                        thesisPopularity.put(thesis.getContent(), Integer.parseInt(matcher.group(1)));
                    }
                    line = lineReader.readLine();
                }
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
        }
        thesisStatistic(thesisPopularity, "thesis_stat.txt");
    }
}

