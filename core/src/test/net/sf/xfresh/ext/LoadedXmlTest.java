package net.sf.xfresh.ext;

import junit.framework.TestCase;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;

/**
 * Date: Dec 1, 2010
 * Time: 7:35:40 PM
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public class LoadedXmlTest extends TestCase {
    public void testEvaluateXpath() throws Throwable {
        final Document document = buildTestDocument();
        final LoadedXml loadedXml = new LoadedXml(document);
        checkPathSet(loadedXml);
//        assertEquals("dd", loadedXml.evaluateToNodeSet("//node()[1]"));
    }

    public void testPerf() throws Throwable {
        final Document document = buildTestDocument();
        final LoadedXml loadedXml = new LoadedXml(document);
        final int limit = 10;
//        final int limit = 10000; // ~ 20s/2ms
        for (int i = 0; i < limit; i++) {
            checkPathSet(loadedXml);
        }
    }

    private Document buildTestDocument() throws ParserConfigurationException, SAXException, IOException {
        final DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        final InputStream stream = new StringBufferInputStream("<test a='a'>text</test>");
        return builder.parse(stream);
    }

    private void checkPathSet(final LoadedXml loadedXml) {
        assertEquals("a", loadedXml.evaluateToString("/test/@a", null));
        assertEquals("default", loadedXml.evaluateToString("/test/@b", "default"));
        assertEquals("text", loadedXml.evaluateToString("//*[1]/text()", "default"));
        assertEquals("1", loadedXml.evaluateToString("count(//*)", "default"));
    }
}
