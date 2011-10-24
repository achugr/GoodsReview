package net.sf.xfresh.core;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.util.Collection;
import java.util.Map;

/**
 * User: darl (darl@yandex-team.ru)
 * Date: 3/21/11 8:16 PM
 */
public interface SaxHandler {
    void writeAny(final String externalName, final Object value) throws SAXException;

    void writeCollection(final String externalName, final Collection<?> collection) throws SAXException;

    void writeMap(final String externalName, final Map<?, ?> map) throws SAXException;

    ContentHandler getContentHandler();
}
