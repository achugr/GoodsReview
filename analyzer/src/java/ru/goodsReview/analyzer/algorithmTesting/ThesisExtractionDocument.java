package ru.goodsReview.analyzer.algorithmTesting;
/*
 *  Date: 24.12.11
 *   Time: 01:46
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import ru.goodsReview.analyzer.AnalyzeThesis;
import ru.goodsReview.core.db.ControllerFactory;
import ru.goodsReview.core.db.controller.ProductController;
import ru.goodsReview.core.db.controller.ReviewController;
import ru.goodsReview.core.db.controller.ThesisController;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.core.model.Thesis;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.TimerTask;

public class ThesisExtractionDocument extends TimerTask{
    private static PrintWriter out;
    private static ControllerFactory controllerFactory;
    private static ProductController productController;
    private static ThesisController thesisController;
    private static ReviewController reviewController;
    private static final Logger log = org.apache.log4j.Logger.getLogger(AnalyzeThesis.class);


    @Required
    public void setControllerFactory(ControllerFactory controllerFactory1){
        this.controllerFactory = controllerFactory1;
        setControllers();
    }

    public static void setControllers(){
        productController = controllerFactory.getProductController();
        reviewController = controllerFactory.getReviewController();
        thesisController = controllerFactory.getThesisController();
    }

    public static void showThesisOnAllProducts(){
        List<Product> list = productController.getAllProducts();
        for(Product product : list){
            out.println("<product id=\"" + product.getId() + "\"" + " name=\""+product.getName()+"\">");
            List<Review> reviews = reviewController.getReviewsByProductId(product.getId());
            for(Review review : reviews){
                out.println("    <review id=\"" + review.getId() + "\">");
                List<Thesis> thesises = thesisController.getThesesByReviewId(review.getId());
                for(Thesis thesis : thesises){
                    out.println("        <thesis> " + thesis.getContent().replaceAll("\\s+", " ") + " </thesis>");
                }
            }
        }
    }

    public void createDocumentForHandwork(){
        PrintWriter out = null;
        try {
            out = new PrintWriter("ReviewsForHandwork.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        List<Product> list = productController.getAllProducts();
        for(Product product : list){
            out.println("<product id=\""+product.getId() + "\">");
            List<Review> reviews = reviewController.getReviewsByProductId(product.getId());
            for(Review review : reviews){
                out.println("    <review id=\"" + review.getId() + "\">");
                out.println("       " + review.getContent().trim());
                out.println("   </review>");
            }
            out.println("</product>");
        }
        out.close();
    }

    public void createExtractionThesisDocument(){
        PrintWriter out = null;
        try {
            out = new PrintWriter("analyzer/ThesisExtractionDocument.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        List<Product> list = productController.getAllProducts();
        for(Product product : list){
            out.println("<product id=\""+product.getId() + "\" name=\""+ product.getName() + "\">");
            List<Review> reviews = reviewController.getReviewsByProductId(product.getId());
            for(Review review : reviews){
                List<Thesis> thesises = thesisController.getThesesByReviewId(review.getId());
                StringBuilder sb = new StringBuilder();
                for(Thesis thesis : thesises){
                    String [] thesisArray = thesis.getContent().split(" ");
                    sb.append(thesisArray[0]);
                    sb.append(", ");
                }
                out.println("    <review id=\"" + review.getId() + "\">");
                out.println("       "+sb.toString()+"##"+ review.getContent().trim());
                out.println("   </review>");
            }
        }
        out.close();
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter("ThesisExtractionDocument_" + System.currentTimeMillis() + ".txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        createExtractionThesisDocument();
        out.close();
        log.info("thesis extraction document is created");
    }

}
