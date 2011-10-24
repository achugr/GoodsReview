package net.sf.xfresh.jetty;

import net.sf.xfresh.core.*;
import org.apache.log4j.Logger;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.mortbay.jetty.handler.AbstractHandler;
import org.springframework.beans.factory.annotation.Required;
import org.xml.sax.ContentHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Author: Olga Bolshakova (obolshakova@yandex-team.ru)
 * Date: 01.01.11 23:58
 */
public class SingleYaletHandler extends AbstractHandler {

    private static final Logger log = Logger.getLogger(SingleYaletHandler.class);

    private static final String DEFAULT_ENCODING = "utf-8";
    private static final String CONTENT_TYPE_TEXT_XML = "text/xml";

    private String encoding = DEFAULT_ENCODING;

    private YaletSupport yaletSupport;

    private SingleYaletProcessor singleYaletProcessor;

    public void setEncoding(final String encoding) {
        this.encoding = encoding;
    }

    @Required
    public void setYaletSupport(final YaletSupport yaletSupport) {
        this.yaletSupport = yaletSupport;
    }

    @Required
    public void setSingleYaletProcessor(final SingleYaletProcessor singleYaletProcessor) {
        this.singleYaletProcessor = singleYaletProcessor;
    }

    public void handle(final String target, final HttpServletRequest req, final HttpServletResponse res, final int i) throws IOException, ServletException {

        final String realPath = target.substring(1);

        log.info("Start process yalet => {" + realPath + "}, remote ip {" + req.getRemoteAddr() + "}");

        final long startTime = System.currentTimeMillis();

        req.setCharacterEncoding(encoding);
        res.setCharacterEncoding(encoding);
        res.setContentType(CONTENT_TYPE_TEXT_XML);

        final InternalRequest internalRequest = yaletSupport.createRequest(req, realPath);
        final InternalResponse internalResponse = yaletSupport.createResponse(res);

        try {
            final PrintWriter writer = res.getWriter();

            singleYaletProcessor.processYalet(realPath, getHandler(writer), internalRequest, internalResponse);

            writer.flush();
            writer.close();
        } catch (Exception e) {
            log.error("", e);
            throw new RuntimeException(e);
        }

        log.info("Processing time for yalet => {" + realPath + "} is " + (System.currentTimeMillis() - startTime) + " ms");
    }

    private ContentHandler getHandler(final PrintWriter w) {
        try {
            final OutputFormat outputFormat = new OutputFormat("XML", encoding, false);
            outputFormat.setOmitXMLDeclaration(true);
            return new XMLSerializer(w, outputFormat).asContentHandler();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
