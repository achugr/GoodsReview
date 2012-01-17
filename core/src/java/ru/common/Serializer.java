package ru.common;

/*
 *  Date: 12.12.11
 *  Time: 08:09
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
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

    private void write(Document doc, File file) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);
        log.info("File saved!");
    }

    public void write(Map<String, Integer> map, File file) throws TransformerException {
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("objects");
        doc.appendChild(rootElement);

        Iterator<String> iterator = map.keySet().iterator();
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
        write(doc, file);
    }

    public Map<String, Integer> readMap(File file) throws IOException, SAXException {
        Document doc = docBuilder.parse(file);
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

    public void write(List<String> list, File file) throws TransformerException {
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
        write(doc, file);
    }

    public List<String> readList( File file) throws IOException, SAXException {
        Document doc = docBuilder.parse(file);
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
    public static void main(String argv[]) throws Exception{
        Map map = Serializer.instance().readMap(new File("data/miner/Citilink/list/LatterLinks.xml"));
        System.out.print(1);
    }
}
