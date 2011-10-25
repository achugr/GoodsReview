package net.sf.xfresh.ext;

import net.sf.xfresh.core.*;
import net.sf.xfresh.core.xml.Xmler;
import net.sf.xfresh.util.XmlUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.UniqueTag;
import org.springframework.util.FileCopyUtils;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpCookie;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: Nov 24, 2010
 * Time: 11:13:37 PM
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public class ExtYaletFilter extends YaletFilter {
    private static final Logger log = Logger.getLogger(ExtYaletFilter.class);

    private static final String XFRESH_EXT_URI = "http://xfresh.sf.net/ext";
    private static final String HTTP_ELEMENT = "http";
    private static final String JS_ELEMENT = "js";
    private static final String AUTH_ELEMENT = "auth";
    private static final String REDIRECT_ELEMENT = "http-redirect";

    private static final String JS_OUT_NAME = "out";
    private static final String JS_SRC_ATTR = "src";

    private static final String DATA_ELEMENT = "data";
    private static final String ID_ATTRIBUTE = "id";
    private static final String JS_DATA = "jsData";

    private static final int DEFAULT_TIMEOUT = 300;

    private final HttpLoader httpLoader = new HttpLoader();
    private final String resourceBase;
    private final SaxGenerator saxGenerator;
    protected final AuthHandler authHandler;

    private String httpUrl;
    private StringBuilder jsContent;
    private Context jsContext;
    private Scriptable jsScope;
    private String redirTo;

    public ExtYaletFilter(final SingleYaletProcessor singleYaletProcessor,
                          final AuthHandler authHandler,
                          final InternalRequest request,
                          final InternalResponse response, final String resourceBase, final SaxGenerator saxGenerator) {
        super(singleYaletProcessor, request, response);
        this.resourceBase = resourceBase;
        this.saxGenerator = saxGenerator;
        this.authHandler = authHandler;
    }

    @Override
    public void startElement(final String uri,
                             final String localName,
                             final String qName,
                             final Attributes atts) throws SAXException {
        if (isHttpBlock(uri, localName)) {
            final String urlValue = atts.getValue("url").trim();
            if (urlValue.startsWith("js:")) {
                checkAndInitJsContext();
                httpUrl = processJsContent(wrapFunction(urlValue.substring(3)));
            } else {
                httpUrl = urlValue;
            }
        } else if (isJsBlock(uri, localName)) {
            if (jsContent != null) {
                log.error("Nested js blocks not possible, previous ignored");
            }
            jsContent = new StringBuilder();
            checkAndInitJsContext();
            final String src = atts.getValue(JS_SRC_ATTR);
            if (!StringUtils.isEmpty(src)) {
                try {
                    String jsFileContent;
                    final FileReader fileReader = new FileReader(resourceBase + File.separatorChar + src);
                    try {
                        jsFileContent = FileCopyUtils.copyToString(fileReader);
                    } finally {
                        fileReader.close();
                    }

                    processJsAndWrite(jsFileContent);
                } catch (IOException e) {
                    log.error("Can't read js from file: " + src, e); //ignored
                }
            }
        } else if (isAuthBlock(uri, localName)) {
            //do nothing
        } else if (isRedirectBlock(uri, localName)) {
            this.redirTo = atts.getValue("to");
            //do nothing
        } else {
            super.startElement(uri, localName, qName, atts);
        }
    }

    private void checkAndInitJsContext() {
        if (jsContext == null) {
            jsContext = Context.enter();
            jsScope = jsContext.initStandardObjects();
            jsScope.put("writer", jsScope, new ContentWriter(getContentHandler()));
            jsScope.put("request", jsScope, request);
            jsScope.put("response", jsScope, response);
            jsScope.put("httpLoader", jsScope, httpLoader);
        }
    }

    @Override
    public void characters(final char[] chars, final int start, final int length) throws SAXException {
        if (jsContent != null) {
            jsContent.append(chars, start, length);
        } else {
            super.characters(chars, start, length);
        }
    }

    @Override
    public void endElement(final String uri, final String localName, final String qName) throws SAXException {
        if (isHttpBlock(uri, localName)) {
            if (checkHttpUrl()) {
                processHttpBlock();
            }
            httpUrl = null;
        } else if (isJsBlock(uri, localName)) {
            if (jsContent != null) {
                processJs();
            }
            jsContent = null;
        } else if (isAuthBlock(uri, localName)) {
            processAuthBlock();
        } else if (isRedirectBlock(uri, localName)) {
            response.redirectTo(redirTo);
        } else {
            super.endElement(uri, localName, qName);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        if (jsContext != null) {
            Context.exit();
        }
    }

    private void processJs() throws SAXException {
        final String content = jsContent.toString();
        processJsAndWrite(content);
    }

    private void processJsAndWrite(final String content) throws SAXException {
        final String result = processJsContent(content);
        if (result != null) {
            XmlUtil.text(getContentHandler(), result);
        }
        if (!response.getData().isEmpty()) {
            XmlUtil.start(getContentHandler(), DATA_ELEMENT, ID_ATTRIBUTE, JS_DATA);

            saxGenerator.writeXml(getContentHandler(), response.getData());
            response.clear();
            XmlUtil.end(getContentHandler(), DATA_ELEMENT);
        }
    }

    @Nullable
    private String processJsContent(final String content) {
        if (!StringUtils.isEmpty(content)) {
            try {
                evaluateJs(content);
                final Object value = jsScope.get(JS_OUT_NAME, jsScope);
                if (isCorrectReturnedValue(value)) {
                    return value.toString();
                }
            } catch (Exception e) {
                log.error("Error while processing JS (ignored): " + content, e); //ignored
            } finally {
                jsScope.put(JS_OUT_NAME, jsScope, null);
            }
        }
        return null;
    }

    private void evaluateJs(final String content) {
        jsContext.evaluateString(jsScope, content, null, 1, null);
    }

    private boolean isCorrectReturnedValue(final Object value) {
        return value != null && !(value instanceof UniqueTag) && !(value instanceof Undefined);
    }

    private String wrapFunction(final String content) {
        return "function _f_wrpr() {" + content + " }; var " + JS_OUT_NAME + " = _f_wrpr();";
    }

    private void processHttpBlock() {
        try {
            final HttpMethodResult result = httpLoader.loadAsStreamWithHeaders(httpUrl, DEFAULT_TIMEOUT, constructParametersForRemoteCall());

            response.setCookies(collectCookies(result.getHeaders()));

            processStatus(result.getStatusCode());

            if (result.getStatusCode() == HttpServletResponse.SC_OK) {
                writeContent(result);
            }

        } catch (ConnectException e) {
            writeFailedBlock("BAD_CONNECTION");
            log.error("", e);
        } catch (IOException e) {
            writeFailedBlock("IO_PROBLEM");
            log.error("", e);
        } catch (ParserConfigurationException e) {
            writeFailedBlock("PARSE_PROBLEM");
            log.error("", e);
        } catch (SAXException e) {
            writeFailedBlock("PARSE_PROBLEM");
            log.error("", e);
        }
    }

    private void writeFailedBlock(final String message) {
        Xmler.tag("http-error", Xmler.attribute("url", httpUrl), message).writeTo(getContentHandler());
    }

    private void writeFailedStatusBlock(final String message, final int status) {
        Xmler.tag("http-error", Xmler.attribute("url", httpUrl).and("status", status), message).writeTo(getContentHandler());
    }

    private void writeContent(final HttpMethodResult result) throws ParserConfigurationException, SAXException, IOException {
        final InputStream content = result.getInputStream();
        final SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        parserFactory.setXIncludeAware(true);
        parserFactory.setNamespaceAware(true);
        final SAXParser saxParser = parserFactory.newSAXParser();
        final XMLReader xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(getContentHandler());
        xmlReader.parse(new InputSource(content));
    }

    private void processStatus(final int statusCode) {
        if (statusCode == HttpServletResponse.SC_OK) {
            return;
        }
        String message = "";
        if (statusCode == HttpServletResponse.SC_FORBIDDEN) {
            message = "FORBIDDEN";
            response.addError(new ErrorInfo(message));
        } else if (statusCode == HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
            message = "INTERNAL_ERROR";
            response.addError(new ErrorInfo(message));
        } else {
            message = "UNKNOWN_ERROR";
            response.addError(new ErrorInfo(message));
        }
        writeFailedStatusBlock(message, statusCode);
    }

    private Map<String, String> collectCookies(final Map<String, List<String>> headers) {
        final Map<String, String> cookies = new HashMap<String, String>();
        final List<String> cookieHeaders = headers.get("Set-Cookie");
        if (cookieHeaders != null) {
            for (final String header : cookieHeaders) {
                for (final HttpCookie cookie : HttpCookie.parse(header)) {
                    cookies.put(cookie.getName(), cookie.getValue());
                }
            }
        }
        return cookies;
    }

    private Map<String, List<String>> constructParametersForRemoteCall() {
        final Map<String, List<String>> allParameters = new HashMap<String, List<String>>(request.getAllParameters());
        final Long userId = request.getUserId();
        if (userId != null) {
            allParameters.put("__user_id", Collections.singletonList(Long.toString(userId)));
        }
        return allParameters;
    }

    private void processAuthBlock() {
        authHandler.processAuth(request, response, getContentHandler());
    }

    private boolean checkHttpUrl() {
        return httpUrl != null && httpUrl.startsWith("http://");
    }

    private boolean isHttpBlock(final String uri, final String localName) {
        return XFRESH_EXT_URI.equalsIgnoreCase(uri) && HTTP_ELEMENT.equalsIgnoreCase(localName);
    }

    private boolean isJsBlock(final String uri, final String localName) {
        return XFRESH_EXT_URI.equalsIgnoreCase(uri) && JS_ELEMENT.equalsIgnoreCase(localName);
    }

    private boolean isAuthBlock(final String uri, final String localName) {
        return XFRESH_EXT_URI.equalsIgnoreCase(uri) && AUTH_ELEMENT.equalsIgnoreCase(localName);
    }

    private boolean isRedirectBlock(final String uri, final String localName) {
        return XFRESH_EXT_URI.equalsIgnoreCase(uri) && REDIRECT_ELEMENT.equalsIgnoreCase(localName);
    }
}
