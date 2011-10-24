/*
* Copyright (c) 2007, Xfresh Project
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*     * Redistributions of source code must retain the above copyright
*       notice, this list of conditions and the following disclaimer.
*     * Redistributions in binary form must reproduce the above copyright
*       notice, this list of conditions and the following disclaimer in the
*       documentation and/or other materials provided with the distribution.
*     * Neither the name of the Xfresh Project nor the
*       names of its contributors may be used to endorse or promote products
*       derived from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED `AS IS'' AND ANY
* EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL Xfresh Project BE LIABLE FOR ANY
* DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package net.sf.xfresh.util;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Date: 22.04.2007
 * Time: 7:36:17
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public class XmlUtil {
    public static final AttributesImpl EMPTY_ATTRIBUTES = new AttributesImpl();
    public static final String NULL_TYPE = "";

    public static void end(final ContentHandler handler, final String elementName) throws SAXException {
        handler.endElement(null, elementName, elementName);
    }

    public static void empty(final ContentHandler handler, final String... elementAndAttributes) throws SAXException {
        start(handler, elementAndAttributes);
        end(handler, elementAndAttributes[0]);
    }

    public static void start(final ContentHandler handler, final String... elementAndAttributes) throws SAXException {
        if (elementAndAttributes == null || elementAndAttributes.length == 0 || elementAndAttributes.length % 2 != 1) {
            throw new IllegalArgumentException(
                    "elementAndAttributes must contains element name and 0..n pais of attr name-value");
        }
        final String elementName = elementAndAttributes[0];
        AttributesImpl attributes = EMPTY_ATTRIBUTES;
        if (elementAndAttributes.length > 1) {
            attributes = new AttributesImpl();
            for (int i = 1; i + 1 < elementAndAttributes.length;) {
                final String attributeName = elementAndAttributes[i++];
                final String attributeValue = elementAndAttributes[i++];
                attributes.addAttribute("", attributeName, attributeName, NULL_TYPE, attributeValue);
            }
        }
        start(handler, elementName, attributes);
    }

    public static void start(final ContentHandler handler, final String elementName, final AttributesImpl attributes) throws SAXException {
        handler.startElement("", elementName, elementName, attributes);
    }

    public static String toStandart(final String name) {
        String result = name.replaceAll("([a-z\\d])([A-Z])", "$1-$2").replace('_', '-');
        result = result.toLowerCase();
        return result;
    }

    public static void text(final ContentHandler handler, final String value) throws SAXException {
        handler.characters(value.toCharArray(), 0, value.length());
    }
}
