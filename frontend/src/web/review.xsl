<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:include href="common.xsl"/>
    <xsl:include href="common-view.xsl"/>

    <xsl:template match="/">
        <xsl:call-template name="test"/>
    </xsl:template>

    <xsl:template name="main">
        <xsl:for-each select="/page/data[@id='review']/collection/review-for-view">
            <div>
                <a>
                    <xsl:attribute name="href">product.xml?id=<xsl:value-of select="@product-id"/>
                    </xsl:attribute>
                    Назад к странице товара
                </a>
            </div>
            <div class="comment">
                <h3>Комментарий</h3>
                <div class="text">
                    <span>
                        <xsl:value-of select="content"/>
                    </span>
                </div>
                <a>
                    <!--todo delete http from here... add this to grabbers-->
                    <xsl:attribute name="href">http://<xsl:value-of select="source-url"/>
                    </xsl:attribute>
                    Первоисточник комментария >>

                </a>
            </div>
            <div class="thesis">
                <h3>Тезисы из комментария</h3>
                <div class="text">
                    <ul>
                        <xsl:for-each select="//thesis-for-view">
                            <li>
                                <xsl:value-of select="content"/>
                            </li>
                        </xsl:for-each>
                    </ul>
                </div>
            </div>
        </xsl:for-each>
    </xsl:template>


</xsl:stylesheet>

