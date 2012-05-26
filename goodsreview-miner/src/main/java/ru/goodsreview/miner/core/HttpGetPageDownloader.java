package ru.goodsreview.miner.core;


import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * User: daddy-bear
 * Date: 25.05.12
 * Time: 23:26
 */
public class HttpGetPageDownloader implements PageDownloader {
    private final static Logger log = Logger.getLogger(HttpGetPageDownloader.class);

    private HttpClient httpClient;
    private int attempts = 5;
    private boolean isSilent = false;

    public void setAttempts(final int attempts) {
        this.attempts = attempts;
    }

    public void setSilent(final boolean silent) {
        isSilent = silent;
    }

    private HttpClient getHttpClient() {
        if (this.httpClient == null) {
            final DefaultHttpClient httpClient = new DefaultHttpClient();

            //TODO

            this.httpClient = httpClient;
        }
        return this.httpClient;
    }

    @Override
    public Page download(final String url) throws DownloadException {
        final HttpClient httpClient = getHttpClient();

        final HttpGet httpGet = new HttpGet(url);

        //final HttpResponse httpResponse;
        final ResponseHandler<String> responseHandler = new BasicResponseHandler();
        //responseHandler.handleResponse(httpResponse);

        String response = null;
        log.info("try to fetch page by url " + url);
        for (int i = 0; i < attempts; i++) {
            try {
                response = httpClient.execute(httpGet, responseHandler);
                log.info("attempt is OK for url " + url);
                break;
            } catch (ClientProtocolException e) {
                log.warn("can't fetch [" + i + " attempt]", e);
            } catch (IOException e) {
                log.warn("can't fetch [" + i + " attempt]", e);
            }
        }

        if (!isSilent && response == null) {
            throw new DownloadException("can't fetch page in " + attempts);
        }
        log.debug(response);

        //TODO
        return new Page(response, url);
    }

}
