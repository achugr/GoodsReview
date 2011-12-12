package ru.common;

/*
 *  Date: 12.12.11
 *  Time: 08:09
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import java.util.*;

public class Serializer {
    private static final Logger log = Logger.getLogger(Serializer.class);
    private DocumentBuilder docBuilder;

    private static Serializer instance;

    private Serializer() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        docBuilder = docFactory.newDocumentBuilder();
    }

    public static Serializer instance() throws ParserConfigurationException {
        if (instance == null) {
            instance = new Serializer();
        }
        return instance;
    }

    private void write(Document doc, String path) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(path));
        transformer.transform(source, result);
        log.info("File saved!");
    }

    public void write(Map<String, Integer> map, String path) throws TransformerException {
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("objects");
        doc.appendChild(rootElement);

        Set<String> set = map.keySet();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String object = iterator.next();
            Integer value = map.get(object);

            Element objectElement = doc.createElement("object");
            Attr valueElement = doc.createAttribute("value");
            valueElement.setValue(value.toString());
            objectElement.setAttributeNode(valueElement);

            objectElement.appendChild(doc.createTextNode(object));
            rootElement.appendChild(objectElement);
        }
        write(doc, path);
    }

    public Map<String, Integer> readMap(String path) throws IOException, SAXException {
        Document doc = docBuilder.parse(new File(path));
        doc.getDocumentElement().normalize();

        NodeList nodelist = doc.getElementsByTagName("object");
        Map<String, Integer> map = new HashMap<String, Integer>();
        if (nodelist != null) {
            for (int i = 0; i < nodelist.getLength(); i++) {
                Element el = (Element) nodelist.item(i);
                String object = el.getFirstChild().getNodeValue();
                String value = el.getAttribute("value");
                map.put(object, Integer.parseInt(value));
            }
        }
        return map;
    }

    public void write(List<String> list, String path) throws TransformerException {
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("objects");
        doc.appendChild(rootElement);
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String object = iterator.next();
            Element objectElement = doc.createElement("object");
            objectElement.appendChild(doc.createTextNode(object));
            rootElement.appendChild(objectElement);
        }
        write(doc, path);
    }

    public List<String> readList(String path) throws IOException, SAXException {
        Document doc = docBuilder.parse(new File(path));
        doc.getDocumentElement().normalize();

        NodeList nodelist = doc.getElementsByTagName("object");
        List<String> list = new ArrayList<String>();
        if (nodelist != null) {
            for (int i = 0; i < nodelist.getLength(); i++) {
                Element el = (Element) nodelist.item(i);
                String object = el.getFirstChild().getNodeValue();
                list.add(object);
            }
        }
        return list;
    }
}
