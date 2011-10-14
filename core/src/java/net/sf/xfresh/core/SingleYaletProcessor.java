package net.sf.xfresh.core;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 * Author: Olga Bolshakova (obolshakova@yandex-team.ru)
 * Date: 02.01.11 12:51
 */
public interface SingleYaletProcessor {

    void processYalet(String yaletId, ContentHandler handler,
                      InternalRequest request, InternalResponse response) throws SAXException;

}
