package ru.goodsReview.analyzer.util;
/*
 *  Date: 24.12.11
 *   Time: 01:46
 *   Author: 
 *      Artemij Chugreev 
 *      artemij.chugreev@gmail.com
 */

import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import ru.goodsReview.core.db.ControllerFactory;
import ru.goodsReview.core.db.controller.ProductController;
import ru.goodsReview.core.db.controller.ReviewController;
import ru.goodsReview.core.db.controller.ThesisController;
import ru.goodsReview.core.model.Product;
import ru.goodsReview.core.model.Review;
import ru.goodsReview.core.model.Thesis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class ThesisExtractionDocument {
    private static PrintWriter out;
    private static ControllerFactory controllerFactory;
    private static ProductController productController;
    private static ThesisController thesisController;
    private static ReviewController reviewController;

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
            out.println("<product name=\""+product.getName()+"\">");
            List<Review> reviews = reviewController.getReviewsByProductId(product.getId());
            for(Review review : reviews){
                out.println("    <review content=\"" + review.getId() + "\">");
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
      /*
    public void createExtractionThesisDocument(){
        PrintWriter out = null;
        try {
            out = new PrintWriter("ThesisExtractionDocument.txt");
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
                List<Thesis> thesises = thesisController.getThesesByReviewId(review.getId());
                for(Thesis thesis : thesises){
                    out.println("        <thesis> " + thesis.getContent().replaceAll("\\s+", " ") + " </thesis>");
                }
            }
        }
        out.close();
    }   */

    public static String nextNameOfDocument(String filePath) {
        String list[] = new File(filePath).list();
        int max = 0;
        String str =  "ThesisExtractionDocument";
        for (int i = 0; i < list.length; i++) {
            String s = list[i];
            if (s.contains(str)) {
                max = Math.max(Integer.parseInt(s.substring(str.length(), s.indexOf("."))), max);
            }
        }

        return "ThesisExtractionDocument" + Integer.toString(max + 1) + ".txt";
    }

    public static void main(String [] args) throws FileNotFoundException {
        final FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("scripts/database.xml");
        out = new PrintWriter(nextNameOfDocument("/algoReport"));
        showThesisOnAllProducts();
        out.close();
    }

}
