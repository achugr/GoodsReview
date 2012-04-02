package ru.goodsReview.miner;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by IntelliJ IDEA.
 * User: timur
 * Date: 03.04.12
 * Time: 0:19
 * To change this template use File | Settings | File Templates.
 */



/**
 * <p>Java class for categoryConfig complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="categoryConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="category" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nameRegexp" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "categoryConfig", propOrder = {
        "category",
        "nameRegexp"
})
public class CategoryConfig {

    @XmlElement(required = true)
    protected String category;
    @XmlElement(required = true)
    protected String nameRegexp;

    /**
     * Gets the value of the category property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCategory(String value) {
        this.category = value;
    }

    /**
     * Gets the value of the nameRegexp property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNameRegexp() {
        return nameRegexp;
    }

    /**
     * Sets the value of the nameRegexp property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNameRegexp(String value) {
        this.nameRegexp = value;
    }

}