<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>


    <xsl:template name="product">
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
                        <div>Комментариев:
                            <xsl:value-of select="count(reviews/review-for-view)"/>
                        </div>
                        <div>Тезисов:
                            <xsl:value-of select="count(theses/thesis-for-view)"/>
                        </div>
                    </td>
                </tr>
            </table>
        </li>
    </xsl:template>



    <!--old-->
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