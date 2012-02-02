package ru.goodsReview.analyzer.util;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.core.model.Thesis;
import ru.goodsReview.storage.controller.ReviewDbController;
import ru.goodsReview.storage.controller.ThesisDbController;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: timur
 * Date: 25.12.11
 * Time: 22:27
 * To change this template use File | Settings | File Templates.
 */

public class XMLUtils {
    private static final String fileName = "reviews.xml";
    private static final Logger log = org.apache.log4j.Logger.getLogger(XMLUtils.class);

    public static void createXML(SimpleJdbcTemplate jdbcTemplate){
        log.info("XML-file creation starts...");
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element reviewsElem = doc.createElement("reviews");
            doc.appendChild(reviewsElem);

            ReviewDbController reviewDbController = new ReviewDbController(jdbcTemplate);
            ThesisDbController thesisDbController = new ThesisDbController(jdbcTemplate);

            List<Review> reviewList = reviewDbController.getAllReviews();
            for(Review review : reviewList){
                List<Thesis> thesisList = thesisDbController.getThesesByReviewId(review.getId());
                appendToXML(doc, reviewsElem, review, thesisList);
            }

            writeToXML(new File(fileName), doc);
            log.info("XML-file creation ends successfully...");
        } catch (ParserConfigurationException e) {
            log.info("Error in XML-file creation: ", e);
        }
    }

    private static void appendToXML(Document doc, Element reviewsElem, Review review, List<Thesis> thesisList) {
        Element reviewElem = doc.createElement("review");
        reviewsElem.appendChild(reviewElem);

        Element contentElem = doc.createElement("content");
        contentElem.appendChild(doc.createTextNode(review.getContent()));
        reviewElem.appendChild(contentElem);

        appendTheses(doc, reviewElem, thesisList);
    }

    private static void appendTheses(Document doc, Element reviewElem, List<Thesis> thesisList) {
        Integer currThesisId = 1;
        for(Thesis thesis : thesisList){
            Element thesisElem = doc.createElement("thesis");
            thesisElem.appendChild(doc.createTextNode(thesis.getContent()));
            reviewElem.appendChild(thesisElem);

            thesisElem.setAttribute("id", currThesisId.toString());
            currThesisId++;
        }
    }

    private static void writeToXML(File file, Document doc){
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            log.info("Error in XML-file creation: ", e);
        } catch (TransformerException e) {
            log.info("Error in XML-file creation: ", e);
        }
    }
}
