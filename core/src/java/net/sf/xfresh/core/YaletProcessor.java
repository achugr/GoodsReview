package net.sf.xfresh.core;

import org.apache.log4j.Logger;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Required;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Date: Nov 5, 2010
 * Time: 5:56:23 PM
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public class YaletProcessor {
    private static final Logger log = Logger.getLogger(YaletProcessor.class);

    private static final String DEFAULT_ENCODING = "utf-8";
    private static final OutputFormat DEFAULT_FORMAT = new OutputFormat("XML", DEFAULT_ENCODING, false);
    private static final int INITIAL_SIZE = 1024 * 64;
    private static final String TEXT_HTML = "text/html";
    private static final String TEXT_XML = "text/xml";

    private YaletSupport yaletSupport;
    private String encoding = DEFAULT_ENCODING;

    @Required
    public void setYaletSupport(final YaletSupport yaletSupport) {
        this.yaletSupport = yaletSupport;
    }

    public void setEncoding(final String encoding) {
        this.encoding = encoding;
    }

    public void process(final HttpServletRequest req,
                        final HttpServletResponse res,
                        final String realPath) throws UnsupportedEncodingException, ServletException {
        req.setCharacterEncoding(encoding);
        res.setCharacterEncoding(encoding);

        log.info("Start process user request => {" + realPath + "}, remote ip => {" + req.getRemoteAddr() + "}, method=" + req.getMethod());
        final long startTime = System.currentTimeMillis();

        final InternalRequest internalRequest = yaletSupport.createRequest(req, realPath);

        final InternalResponse internalResponse = yaletSupport.createResponse(res);

        process(internalRequest, internalResponse, new RedirHandler(res));
        log.info("Processing time for user request => {" + realPath + "} is " + (System.currentTimeMillis() - startTime) + " ms");
    }

    protected void process(final InternalRequest request,
                           final InternalResponse response,
                           final RedirHandler redirHandler) throws ServletException {
        final String realPath = request.getRealPath();
        try {
            final InputSource inputSource = new InputSource(realPath);

            final XMLReader xmlReader = createReader();

            final XMLFilter yaletFilter = yaletSupport.createFilter(request, response);

            yaletFilter.setParent(xmlReader);

            final ByteArrayOutputStream stream = new ByteArrayOutputStream(INITIAL_SIZE);
            try {
                final Transformer transformer = request.needTransform()
                        ? createTransformer(realPath)
                        : null;
                if (transformer != null) {
                    response.setContentType(TEXT_HTML);
                    final Source saxSource = new SAXSource(yaletFilter, inputSource);
                    transformer.transform(saxSource, new StreamResult(stream));
                } else {
                    response.setContentType(TEXT_XML);
                    yaletFilter.setContentHandler(new MyXMLSerializer(stream));
                    yaletFilter.parse(inputSource);
                }
            } finally {
                stream.close();
            }

            final String redir = response.getRedir();
            if (response.isProcessed()) {
                log.info("Content-Type: " + response.getContentType());
            } else if (redir == null || !response.getErrors().isEmpty()) {
                response.getOutputStream().write(stream.toByteArray());
            } else if (redir != null) {
                redirHandler.doRedirect(redir);
            }
        } catch (Exception e) {
            log.error("Can't process file " + realPath, e);
            throw new ServletException("Can't process file " + realPath, e);
        } finally {
            try {
                final OutputStream nativeStream = response.getOutputStream();
                nativeStream.flush();
                nativeStream.close();
            } catch (IOException e) {
                log.error("Error: ", e);//ignored
            }
        }
    }

    @Nullable
    private Transformer createTransformer(final String realPath) {
        // use system property javax.xml.transform.TransformerFactory to define special factory  (e.g. net.sf.saxon.TransformerFactoryImpl)
        Transformer transformer = null;
        try {
            final SAXTransformerFactory transformerFactory = (SAXTransformerFactory) TransformerFactory.newInstance();
            final Source streamSource = new StreamSource(realPath);
            final Source associatedStylesheet = transformerFactory.getAssociatedStylesheet(streamSource,
                    null, null, null);
            if (associatedStylesheet == null) {
                return null;
            }

            transformer = transformerFactory.newTransformer(associatedStylesheet);
        } catch (TransformerConfigurationException e) {
            log.info("Can't create transformer: " + e.getMessage());//ignored
        }
        return transformer;
    }

    private XMLReader createReader() throws ParserConfigurationException, SAXException {
        final SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        parserFactory.setNamespaceAware(true);
        parserFactory.setXIncludeAware(true);
        final SAXParser saxParser = parserFactory.newSAXParser();
        return saxParser.getXMLReader();
    }

    private static class MyXMLSerializer extends XMLSerializer {
        private static final String XML_STYLESHEET_PI = "xml-stylesheet";

        private MyXMLSerializer(final OutputStream writer) {
            super(writer, DEFAULT_FORMAT);
        }

        @Override
        public void processingInstructionIO(final String target, final String code) throws IOException {
            if (!XML_STYLESHEET_PI.equalsIgnoreCase(target)) {
                super.processingInstructionIO(target, code);
            }
        }
    }

}
