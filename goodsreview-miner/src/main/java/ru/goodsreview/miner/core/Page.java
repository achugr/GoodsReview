package ru.goodsreview.miner.core;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.lang.ref.SoftReference;

/**
 * User: daddy-bear
 * Date: 25.05.12
 * Time: 23:22
 */
public class Page {
    private final static Logger log = Logger.getLogger(Page.class);

    private final String rawSource;
    private final String url;

    private SoftReference<Document> docRef;

    public Page(final String rawSource, final String url) {
        this.rawSource = rawSource;
        this.url = url;
    }

    public String getRawSource() {
        return rawSource;
    }

    public Document getDocument() {
        Document document = docRef == null ? null : docRef.get();
        try {
            if (document == null) {
                document = parsePage();
                docRef = new SoftReference<Document>(document);
            }
        } catch (Exception e) {
            log.error("can't parse page " + url, e);
            docRef = null;
        }
        return document;
    }

    private Document parsePage() throws ParseExeption {
        DOMParser parser = new DOMParser();
        try {
            parser.parse(new InputSource(new StringReader(rawSource)));
        } catch (SAXException e) {
            throw new ParseExeption(e);
        } catch (IOException e) {
            throw new ParseExeption(e);
        }
        return parser.getDocument();
    }
}
