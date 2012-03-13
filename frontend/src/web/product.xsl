<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:include href="common.xsl"/>
    <xsl:include href="common-view.xsl"/>

    <xsl:template match="/">
        <xsl:call-template name="test"/>
    </xsl:template>

    <xsl:template name="main">
        <div class="productInfo">
            <xsl:for-each select="/page/data[@id='product']/collection/detailed-product-for-view">
                <xsl:call-template name="detailed-product"/>
            </xsl:for-each>
        </div>
    </xsl:template>

    <xsl:template name="detailed-product">
        <div class="productName">
            <xsl:value-of select="name"/>
        </div>
        <div class="row">
            <div class="span5">
                <a>
                    <xsl:attribute name="href"></xsl:attribute>
                    <img src="images/laptop.png"/>
                </a>
            </div>
            <div class="span3">
                <h3>Тезисы</h3>
                <xsl:variable name="theMax" select="thesis-for-view[not(importance &lt; ../thesis-for-view/importance)]/importance" />
                <xsl:value-of>theMax</xsl:value-of>   <!--some kind of debug output-->
                <xsl:variable name="theMin" select="thesis-for-view[not(importance &gt; ../thesis-for-view/importance)]/importance" />
                <xsl:value-of>theMin</xsl:value-of>
                <xsl:variable name="perc100" select="$theMax - number($theMin)"/>
                <xsl:variable name="perc1">
                    <xsl:choose>
                        <xsl:when test="$perc100 = 0">100</xsl:when>
                        <xsl:otherwise><xsl:value-of select="100 div $perc100"/></xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <xsl:variable name="maxFont">24</xsl:variable>
                <xsl:variable name="minFont">12</xsl:variable>
                <xsl:variable name="font" select="$maxFont - $minFont"/>
                <div class="thesisList">
                <ul>
                    <xsl:for-each select="//thesis-for-view">
                        <li>
                            <xsl:variable name="size" select="$minFont + ceiling($font div 100 * ((weight - number($theMin)) * $perc1))"/>
                            <span style="font-size: {$size}px">
                                <xsl:value-of select="content" />
                            </span>
                        </li>
                    </xsl:for-each>
                </ul>
                </div>
                <!--<div class = "thesisList">
                <ul>
                    <xsl:for-each select="//thesis-for-view">
                        <li>

                        </li>
                    </xsl:for-each>
                </ul>
                </div>-->
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
        <xsl:for-each select="//review-for-view">
            <div class="comment">
                <div class="text">
                <xsl:value-of select="content"/>
                <a>
                    <xsl:attribute name="href">review.xml?id=<xsl:value-of select="@id"/>
                    </xsl:attribute>
                </a>
                </div>
            </div>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>

