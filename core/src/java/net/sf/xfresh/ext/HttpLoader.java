package net.sf.xfresh.ext;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.*;

/**
 * Date: Nov 28, 2010
 * Time: 1:41:37 AM
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public class HttpLoader {
    private static final Logger log = Logger.getLogger(HttpLoader.class);

    private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY =
            DocumentBuilderFactory.newInstance();

    private final HttpClient httpClient;

    public HttpLoader() {
        httpClient = new DefaultHttpClient();
    }

    public InputStream loadAsStream(final String url, final int timeout, final Map<String, List<String>> params) throws IOException {
        return loadAsStream(url, timeout, params, Collections.<String, String>emptyMap());
    }

    public InputStream loadAsStream(final String url, final int timeout, final Map<String, List<String>> params, final Map<String, String> headers) throws IOException {
        return loadAsStreamWithHeaders(url, timeout, params, headers).getInputStream();
    }

    public HttpMethodResult loadAsStreamWithHeaders(final String url, final int timeout, final Map<String, List<String>> params) throws IOException {
        return loadAsStreamWithHeaders(url, timeout, params, Collections.<String, String>emptyMap());
    }

    public HttpMethodResult loadAsStreamWithHeaders(final String url, final int timeout, final Map<String, List<String>> params, final Map<String, String> headers) throws IOException {
        final HttpPost post = new HttpPost(url);
        for (final Map.Entry<String, String> entry : headers.entrySet()) {
            post.setHeader(entry.getKey(), entry.getValue());
        }

        if (!params.isEmpty()) {
            final List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
            for (final Map.Entry<String, List<String>> nameValues : params.entrySet()) {
                for (final String value : nameValues.getValue()) {
                    paramsList.add(new BasicNameValuePair(nameValues.getKey(), value));
                }
            }
            post.setEntity(new UrlEncodedFormEntity(paramsList, "UTF-8"));
        }

        final HttpParams httpParams = httpClient.getParams();

        HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
        HttpConnectionParams.setSoTimeout(httpParams, timeout);
        final HttpResponse httpResponse = httpClient.execute(post);

        final Map<String, List<String>> resultHeaders = new HashMap<String, List<String>>();
        final Header[] allHeaders = httpResponse.getAllHeaders();
        for (final Header header : allHeaders) {
            final String name = header.getName();
            final String value = header.getValue();
            List<String> values = resultHeaders.get(name);
            if (values == null) {
                values = new ArrayList<String>(2);
                resultHeaders.put(name, values);
            }
            values.add(value);
        }

        return new HttpMethodResult(httpResponse.getEntity().getContent(), resultHeaders, httpResponse.getStatusLine().getStatusCode());
    }

    public InputStream loadAsStream(final String url, final int timeout) throws IOException {
        return loadAsStream(url, timeout, Collections.<String, List<String>>emptyMap(), Collections.<String, String>emptyMap());
    }

    public LoadedXml load(final String url, final int timeout) {
        try {
            final InputStream stream = loadAsStream(url, timeout);
            final DocumentBuilder builder = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
            final Document document = builder.parse(stream);
            return new LoadedXml(document);
        } catch (SocketTimeoutException e) {
            return LoadedXml.EMPTY_XML;
        } catch (IOException e) {
            log.error("Error while processing url: " + url, e); //ignored
        } catch (ParserConfigurationException e) {
            log.error("Error while processing url: " + url, e); //ignored
        } catch (SAXException e) {
            log.error("Error while processing url: " + url, e); //ignored
        }
        return LoadedXml.EMPTY_XML;
    }
}

