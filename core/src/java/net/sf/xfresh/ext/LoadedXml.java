package net.sf.xfresh.ext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * Date: Nov 29, 2010
 * Time: 1:59:13 AM
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public class LoadedXml {
    private static final Logger log = Logger.getLogger(LoadedXml.class);

    public static final LoadedXml EMPTY_XML = new LoadedXml(null);

    private final Node content;
    private static final XPathFactory XPATH_FACTORY = XPathFactory.newInstance();

    public LoadedXml(final Node content) {
        this.content = content;
    }

    protected Node getNode() {
        return content;
    }

    public String evaluateToString(final String expression, final String defaultValue) {
        if (content == null) {
            log.warn("Can't use expression [" + expression + "], document is null");
            return defaultValue;
        }

        try {
            final XPath xpath = XPATH_FACTORY.newXPath();
            final String result = (String) xpath.evaluate(expression, content, XPathConstants.STRING);
            log.debug("xpath [" + expression +
                    "] evaluate result = " + result);
            if (StringUtils.isEmpty(result)) {
                return defaultValue;
            }
            return result.trim();
        } catch (XPathExpressionException e) {
            log.error("Error while execute expression: " + expression, e); //ignored
            return defaultValue;
        }
    }

    public Node evaluateToNode(final String expression) {
        if (content == null) {
            log.warn("Can't use expression [" + expression + "], document is null");
            return null;
        }

        try {
            final XPath xpath = XPATH_FACTORY.newXPath();
            final Node result = (Node) xpath.evaluate(expression, content, XPathConstants.NODE);
            log.debug("xpath [" + expression +
                    "] evaluate result = " + result);
            return result;
        } catch (XPathExpressionException e) {
            log.error("Error while execute expression: " + expression, e); //ignored
            return null;
        }
    }

    public boolean isEmpty() {
        return content == null;
    }
}
