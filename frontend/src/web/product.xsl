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
            <div class="span3">
                <h3>Тезисы</h3>
                <div class="thesisList">
                    <ul>
                        <xsl:for-each select="//thesis-for-view">
                            <li>
                                <xsl:choose>
                                    <xsl:when test="@importance &gt; 0.5">
                                        <span style="font-size: {12*(1+number(@importance))}px; color: red">
                                            <xsl:value-of select="content" />
                                        </span>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <span style="font-size: {12*(1+number(@importance))}px; color: green">
                                            <xsl:value-of select="content" />
                                        </span>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </li>
                        </xsl:for-each>
                    </ul>
                </div>
            </div>
            <div class="span5">
                <a>
                    <xsl:attribute name="href"></xsl:attribute>
                    <img src="images/laptop.png"/>
                </a>
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

