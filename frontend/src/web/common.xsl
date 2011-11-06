<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <xsl:template name="product">
        <hr/>
        <a>
            <xsl:attribute name="href">product.xml?id=<xsl:value-of select="@id"/>
            </xsl:attribute>
            <xsl:value-of select="name"/>
        </a>
        <br/>
        Description:
        <xsl:value-of select="description"/>
        <br/>
        Category:
        <xsl:value-of select="category"/>
        <br/>
        CategoryId:
        <xsl:value-of select="@category-id"/>
        <br/>
        Popularity:
        <xsl:value-of select="@popularity"/>
        <br/>
        Id:
        <xsl:value-of select="@id"/>
        <br/>
    </xsl:template>

    <xsl:template name="productNew">
        <li>
            <div class="productName">
                <a>
                    <xsl:attribute name="href">product.xml?id=<xsl:value-of select="@id"/>
                    </xsl:attribute>
                    <xsl:value-of select="name"/>
                </a>
            </div>
            <table>
                <tr>
                    <td>
                        <a href="#">
                            <img src="images/lenovo.jpg"/>
                        </a>
                    </td>
                    <td>
                        <div>Популярность:
                            <xsl:value-of select="@popularity"/>
                        </div>
                        <div>Тезисов:8</div>
                        <div>Комментариев:12</div>
                    </td>
                </tr>
            </table>
        </li>
    </xsl:template>

    <xsl:template name="detailed-product">
        <xsl:for-each select="/page/data[@id='product']/collection/detailed-product-for-view">

            <div class="productName">
                <xsl:value-of select="name"/>
            </div>
            <div class="row">
                <div class="span5">
                    <img src="images/lenovoTabletBig.jpg"/>
                </div>
                <div class="span3">
                    <h3>Тезисы</h3>
                    <ul>
                        <xsl:for-each select="//thesis-for-view">
                            <li><xsl:value-of select="content"/></li>
                        </xsl:for-each>
                    </ul>
                </div>
                <div class="span5">
                    <h3>Похожие товары</h3>
                    <ul>
                        <li>
                            <a href="#">lenovo thinkpad x201</a>
                        </li>
                        <li>
                            <a href="#">lenovo thinkpad x220</a>
                        </li>
                    </ul>
                </div>
            </div>
            <h3>Лучшие комментарии</h3>

            <div class="comment">Хороший, легкий ноутбук, только качество сборки так себе..
                <a href="source">>></a>
            </div>
            <div class="comment">Хороший ноутбук, мне нравится! Удобная клавиатура, элегантный корпус, но качество
                сборки
                оставляет желать лучшего :(
                <a href="source">>></a>
            </div>
        </xsl:for-each>
    </xsl:template>


    <xsl:template name="popular">
        <xsl:for-each select="/page/data[@id='popularProducts']/collection/product-for-view">
            <xsl:call-template name="productNew"/>
        </xsl:for-each>
        <hr/>
    </xsl:template>

    <xsl:template name="citilink-review">
        Comment:
        <xsl:value-of select="comment"/>
        <br/>
        Description:
        <xsl:value-of select="description"/>
        <br/>
        Helpfulness-yes:
        <xsl:value-of select="@helpfulness-yes"/>
        <br/>
        Helpfulness-no:
        <xsl:value-of select="@helpfulness-no"/>
        <br/>
        Rate:
        <xsl:value-of select="@rate"/>
        <br/>
    </xsl:template>

    <xsl:template name="thesis">
        Thesis:
        <xsl:value-of select="content"/>
        <br/>
        positivity:
        <xsl:value-of select="@positivity"/>
        <br/>
        votes-no:
        <xsl:value-of select="@votes-no"/>
        <br/>
        votes-yes:
        <xsl:value-of select="@votes-yes"/>
        <br/>
        importance:
        <xsl:value-of select="@importance"/>
        <br/>
    </xsl:template>

    <xsl:template name="review">
        Thesis:
        <xsl:value-of select="content"/>
        <br/>
        Author:
        <xsl:value-of select="author"/>
        <br/>
        description:
        <xsl:value-of select="description"/>
        <br/>
        Source-url:
        <xsl:value-of select="source-url"/>
        <br/>
        positivity:
        <xsl:value-of select="@positivity"/>
        <br/>
        votes-no:
        <xsl:value-of select="@votes-no"/>
        <br/>
        votes-yes:
        <xsl:value-of select="@votes-yes"/>
        <br/>
        importance:
        <xsl:value-of select="@importance"/>
        <br/>
    </xsl:template>
</xsl:stylesheet>