package net.sf.xfresh.core.impl;

import net.sf.xfresh.core.*;
import net.sf.xfresh.util.XmlUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 * Author: Olga Bolshakova (obolshakova@yandex-team.ru)
 * Date: 02.01.11 12:52
 */
public class DefaultSingleYaletProcessor implements SingleYaletProcessor {
    private static final Logger log = Logger.getLogger(DefaultSingleYaletProcessor.class);

    private static final String DATA_ELEMENT = "data";
    private static final String ERRORS_ELEMENT = "errors";
    private static final String ID_ATTRIBUTE = "id";
    private static final String PROCESSING_TIME_ATTRIBUTE = "processing-time";

    private YaletResolver yaletResolver;
    private SaxGenerator saxGenerator;

    private boolean addAdditionalInfo = true;

    @Required
    public void setSaxGenerator(final SaxGenerator saxGenerator) {
        this.saxGenerator = saxGenerator;
    }

    @Required
    public void setYaletResolver(final YaletResolver yaletResolver) {
        this.yaletResolver = yaletResolver;
    }

    public void setAddAdditionalInfo(final boolean addAdditionalInfo) {
        this.addAdditionalInfo = addAdditionalInfo;
    }

    public void processYalet(final String yaletId, final ContentHandler handler, final InternalRequest request, final InternalResponse response) throws SAXException {
        final Yalet yalet = yaletResolver.findYalet(yaletId);

        log.info("Starting process yalet => {" + yaletId + "}");
        final long startTime = System.currentTimeMillis();
        try {
            yalet.process(request, response);
        } catch (Exception e) {
            log.error("Can't process yalet " + yaletId, e);
            response.addError(new ErrorInfo(e));
        }
        final long processingTime = System.currentTimeMillis() - startTime;
        log.info("Processing time for yalet => {" + yaletId + "} is " + processingTime + " ms");

        XmlUtil.start(handler, buildAttributes(yaletId, processingTime));

        saxGenerator.writeXml(handler, response.getData());

        if (!response.getErrors().isEmpty()) {
            XmlUtil.start(handler, ERRORS_ELEMENT, ID_ATTRIBUTE, yaletId);
            saxGenerator.writeXml(handler, response.getErrors());
            XmlUtil.end(handler, ERRORS_ELEMENT);
        }
        response.clear();
        XmlUtil.end(handler, DATA_ELEMENT);
    }

    private String[] buildAttributes(final String yaletId, final long processingTime) {
        final int attributesArraySize = addAdditionalInfo ? 5 : 3;
        final String[] attributes = new String[attributesArraySize];
        attributes[0] = DATA_ELEMENT;
        attributes[1] = ID_ATTRIBUTE;
        attributes[2] = yaletId;
        if (addAdditionalInfo) {
            attributes[3] = PROCESSING_TIME_ATTRIBUTE;
            attributes[4] = Long.toString(processingTime);
        }
        return attributes;
    }
}
