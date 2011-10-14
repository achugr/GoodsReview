package net.sf.xfresh.ext;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Olga Bolshakova (obolshakova@yandex-team.ru)
 * Date: 06.01.11 14:24
 */
public class HttpMethodResult {

    private final InputStream inputStream;

    private final Map<String, List<String>> headers = new HashMap<String, List<String>>();

    private final int statusCode;

    public HttpMethodResult(final InputStream inputStream, final Map<String, List<String>> headers, final int statusCode) {
        this.inputStream = inputStream;
        this.headers.putAll(headers);
        this.statusCode = statusCode;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
