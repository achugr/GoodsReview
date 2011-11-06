<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:include href="common.xsl"/>
    <xsl:include href="common-view.xsl"/>

    <xsl:template match="/">
        <xsl:call-template name="test"/>
    </xsl:template>

    <xsl:template match="/page">
        <div class="menu">
            <div class="fill">
                <div class="container">
                    <ul class="nav">
                        <li class="current">
                            <a href="#">Популярные товары</a>
                        </li>
                        <li>
                            <a href="#">Частые тезисы</a>
                        </li>
                        <li>
                            <a href="#">Последние запросы</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="productInfo">
            <xsl:for-each select="/page/data[@id='product']/collection/detailed-product-for-view">
                <xsl:call-template name="detailed-product"/>
            </xsl:for-each>
        </div>
    </xsl:template>
</xsl:stylesheet>

